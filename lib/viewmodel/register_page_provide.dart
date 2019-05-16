import 'package:jian_yue/model/repository.dart';
import 'package:jian_yue/view/base.dart';
import 'package:rxdart/rxdart.dart';
import 'package:jian_yue/constant/constant.dart';

class RegisterPageProvide extends BaseProvide{
  final UserRepo _repo;

  String username;
  String password;

  bool _isRegistSuccess;
  String _msg;


  String get msg => _msg;

  set msg(String value) {
    _msg = value;
    notifyListeners();
  }

  bool _isLoading = false;

  bool get isLoading => _isLoading;

  set isLoading(bool value) {
    _isLoading = value;
    notifyListeners();
  }


  bool get isRegistSuccess => _isRegistSuccess;

  set isRegistSuccess(bool value) {
    _isRegistSuccess = value;
  }

  RegisterPageProvide(this._repo);
 Observable register()=> _repo.register(username, password)
     .doOnData((r){
       msg = r['msg'];
    if(r['state']=='success'){
      isRegistSuccess = true;
    }else{
      isRegistSuccess = false;
    }
 }).doOnListen(()=>isLoading = true).doOnDone(()=>isLoading = false);

}