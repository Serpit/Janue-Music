import 'package:rxdart/rxdart.dart';
import 'package:jian_yue/constant/constant.dart';
import 'package:jian_yue/utils/net_utils.dart';
class MusicService{
  Observable searchMusic(Map<String,String> params) => get("",params);
  Observable  getBillList(Map<String,dynamic> params) => get("", params);
}

class ImageService{
  Observable getSplashUrl() => get(Constants.SPLASH_URL, {});
}

class MusicRepo {
  final MusicService _remote;

  /// sharedPreference
  ///
  /// 也应该算在Model层，在这里面处理数据的读取


  MusicRepo(this._remote);


  Observable searchMusic(String keyword) {
    Map<String, String> params = {
      Constants.REQUEST_METHOD: Constants.REQUEST_METHOD_QUERY,
      Constants.PARAM_QUERY: keyword
    };
    return _remote.searchMusic(params);
  }

  Observable getBillList(int type){
    return getDetailList(type, 3, 0);
  }
  Observable getDetailList(int type,int size,int offset){
    Map<String,dynamic> params = {
      Constants.REQUEST_METHOD : Constants.REQUEST_METHOD_GET_LIST,
      Constants.PARAM_TYPE : type,
      Constants.PARAM_SIZE : size,
      Constants.PARAM_OFFSET : offset
    };
    return _remote.getBillList(params);
  }

}

class ImageRepo{
  final ImageService _remote;

  ImageRepo(this._remote);

  Observable getSplashUrl(){
    return _remote.getSplashUrl().map<String>((map) {
      print(map);

      var list = map['images'] as List;
      var image =  list[0] as Map;
      var url = image['url'] as String;
      String URL = "http://cn.bing.com${url}_720x1280.jpg";
      return URL;
    });
  }


}