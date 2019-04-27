import 'package:dio/dio.dart';
import 'package:jian_yue/model/repository.dart';
import 'package:jian_yue/view/base.dart';
import 'package:rxdart/rxdart.dart';
import 'package:jian_yue/bean/music_list_bean.dart';

import 'package:jian_yue/bean/music_list_bean.dart';
//主页的viewmodel

class MusicListItem{
  final String typeName;
  final int type;

  MusicListItem(this.typeName, this.type);


}

class OnLineMusicListProvide extends BaseProvide{
  final  MusicRepo _repo;

  OnLineMusicListProvide(this._repo);

  Map<int,MusicListInfo> _response = {};

  Map<int,MusicListInfo> get response => _response;


  bool _isLoading ;


  bool get isLoading => _isLoading;

  set isLoading(bool value) {
    _isLoading = value;
    notifyListeners();
  }

  set response(Map<int,MusicListInfo> response) {
    _response = response;
  }
  void upDataResponse(int type,MusicListInfo info){
    if(info!=null){
      _response[type] = info;

      notifyListeners();
    }

  }


  Observable getBillList(int type) => _repo.getBillList(type)
      .doOnData((r){
    upDataResponse(type,MusicListInfo.fromJson(r));
      })
      .doOnError((){

  }).doOnListen((){isLoading=true;}).doOnDone((){isLoading = false;});
}