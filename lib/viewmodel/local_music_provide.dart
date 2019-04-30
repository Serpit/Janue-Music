import 'package:jian_yue/model/repository.dart';
import 'package:jian_yue/view/base.dart';
import 'package:rxdart/rxdart.dart';
import 'package:jian_yue/bean/base_play_list_bean.dart';
import 'dart:convert';

class LocalMusicListProvide extends BaseProvide{
  final MusicRepo _repo;

  LocalMusicListProvide(this._repo);
  List<BasePlayListItemInfo> _response;


  bool _isLoading ;


  bool get isLoading => _isLoading;

  set isLoading(bool value) {
    _isLoading = value;
    notifyListeners();
  }


  List<BasePlayListItemInfo> get response => _response;

  set response(List<BasePlayListItemInfo> value) {
    _response = value;
    notifyListeners();
  }

  Observable getLocalMusicList(){
    return _repo.getLocalMusicList().doOnData((value){
     // print(value);
      List list = json.decode(value);
      response = getMusicList(list);
    }).doOnListen((){isLoading = true;}).doOnDone((){isLoading = false;});
  }
}