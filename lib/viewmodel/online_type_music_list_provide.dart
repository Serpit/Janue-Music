import 'package:dio/dio.dart';
import 'package:jian_yue/model/repository.dart';
import 'package:jian_yue/view/base.dart';
import 'package:rxdart/rxdart.dart';
import 'package:jian_yue/bean/music_list_bean.dart';

class OnlineTypeMusicListProvide extends BaseProvide{

  final  MusicRepo _repo;

  OnlineTypeMusicListProvide(this._repo);
  MusicListInfo _response;

  bool _isLoading ;


  bool get isLoading => _isLoading;

  set isLoading(bool value) {
    _isLoading = value;
    notifyListeners();
  }

  MusicListInfo get response => _response;

  set response(MusicListInfo value) {
    _response = value;
  }


  Observable getDetailList(int type,int size,int offset) => _repo.getDetailList(type,size,offset)
      .doOnData((r){
    response = MusicListInfo.fromJson(r);
  })
      .doOnError((){

  }).doOnListen((){isLoading=true;}).doOnDone((){isLoading = false;});

}