import 'package:jian_yue/model/repository.dart';
import 'package:jian_yue/view/base.dart';
import 'package:rxdart/rxdart.dart';
import 'package:jian_yue/constant/constant.dart';
import 'package:jian_yue/utils/method_channel_utils.dart';
import 'package:jian_yue/bean/collection_response_bean.dart';
import 'dart:convert';
class LoginPageProvide extends BaseProvide{
    final UserRepo _repo;

    String username;
    String password;

    bool _isLoginSuccess;


    bool _isLoading = false;

    bool get isLoading => _isLoading;

    set isLoading(bool value) {
      _isLoading = value;
      notifyListeners();
    }


    bool get isLoginSuccess => _isLoginSuccess;

    set isLoginSuccess(bool value) {
      _isLoginSuccess = value;
    }


    Observable getCollectionList(String userid){
      _repo.getCollectionList(userid).doOnData((r){
        CollectionResponse res = CollectionResponse.fromJson(r);

        Map params = {
          'collection': jsonEncode(r['data'])
        };
        callNativeMethod(Constants.DATA_BASE_CHANNEL, 'insertCollection',params: params);
      }).listen((_){});
    }

    LoginPageProvide(this._repo);
    Observable login()=> _repo.login(username, password).doOnData((r){
        isLoginSuccess = r["state"]=="success";
        if(isLoginSuccess){
          Map map = {
            'userid':r['msg'],
            'username':username
          };
          callNativeMethod(Constants.USER_CHANNEL, 'saveUserInfo',params: map);
          getCollectionList(r['msg']);

        }
    }).doOnListen((){
      isLoading = true;
    }).doOnDone((){
      isLoading = false;
    });


}