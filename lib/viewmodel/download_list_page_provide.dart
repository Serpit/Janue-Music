import 'package:jian_yue/model/repository.dart';
import 'package:jian_yue/view/base.dart';
import 'package:rxdart/rxdart.dart';
import 'package:jian_yue/bean/base_play_list_bean.dart';
import 'dart:convert';


class DownloadPageProvide extends BaseProvide{
  final MusicRepo _repo;

  DownloadPageProvide(this._repo);
  bool _isLoading = false;
  List<BasePlayListItemInfo> _response;
  bool get isLoading => _isLoading;


  List<BasePlayListItemInfo> get response => _response;

  set response(List<BasePlayListItemInfo> value) {
    _response = value;
    notifyListeners();
  }

  set isLoading(bool value) {
    _isLoading = value;
    notifyListeners();
  }

  Observable loadDownloadMusicList(){
   return _repo.loadDownloadMusicList().doOnData((r){
     print(r);
      List list = json.decode(r);
      response = getMusicList(list);

    }).doOnError((){
    }).doOnListen((){isLoading=true;}).doOnDone((){isLoading = false;});
  }


}