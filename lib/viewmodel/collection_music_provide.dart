import 'package:jian_yue/view/base.dart';
import 'package:jian_yue/model/repository.dart';
import 'package:rxdart/rxdart.dart';
import 'package:jian_yue/bean/collection_response_bean.dart';
class CollectionMusicProvide extends BaseProvide{
  final MusicRepo _repo;

  CollectionMusicProvide(this._repo);

  List<CollectionItem> _response;
  bool _isLoading = true;


  List<CollectionItem> get response => _response;

  set response(List<CollectionItem> value) {
    _response = value;
    notifyListeners();
  }

  bool get isLoading => _isLoading;

  set isLoading(bool value) {
    _isLoading = value;
    notifyListeners();
  }

  Observable getCollectionMusicList(String userid){
    return _repo.getCollectionList(userid).doOnData((r){
      print(r);
      CollectionResponse res = CollectionResponse.fromJson(r);
      _response = res.data;
    }).doOnListen((){
      isLoading = true;
    }).doOnDone((){
      isLoading = false;
    });
  }

}