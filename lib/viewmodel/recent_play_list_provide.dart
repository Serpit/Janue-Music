import 'package:jian_yue/model/repository.dart';
import 'package:jian_yue/view/base.dart';
import 'package:rxdart/rxdart.dart';
import 'package:jian_yue/bean/base_play_list_bean.dart';

import 'dart:convert';


class RectentPlayListProvide extends BaseProvide{
  final MusicRepo _repo;

  RectentPlayListProvide(this._repo);

  List<BasePlayListItemInfo> _response;

  List<BasePlayListItemInfo> get response => _response;
  bool _isLoading ;


  bool get isLoading => _isLoading;

  set isLoading(bool value) {
    _isLoading = value;
    notifyListeners();
  }

  set response(List<BasePlayListItemInfo> value) {
    _response = value;
    notifyListeners();
  }

  Observable loadRecentPlayList() => _repo.loadRecentPlayList().doOnData((r){
    print(r);
    List list = json.decode(r);
    response = getMusicList(list);

    })
        .doOnError((){
    }).doOnListen((){isLoading=true;}).doOnDone((){isLoading = false;});


}