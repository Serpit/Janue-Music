import 'package:dio/dio.dart';
import 'package:jian_yue/model/repository.dart';
import 'package:jian_yue/view/base.dart';
import 'package:rxdart/rxdart.dart';

import 'package:jian_yue/bean/search_result_bean.dart';

class SearchProvide extends BaseProvide{
  final MusicRepo _repo;

  String keyword;

  SearchResultBean _response  ;


  final String title;
  bool _loading = false;
  SearchResultBean get response => _response;
  SearchProvide(this.title, this._repo);


  set response(SearchResultBean reponse){
    _response = reponse;
    notifyListeners();
  }


  bool get loading => _loading;



  set loading(bool loading) {
    _loading = loading;
    notifyListeners();
  }


  Observable searchMusic() => _repo
      .searchMusic(keyword)
      .doOnData((r) =>{
      response=  new SearchResultBean.fromJson(r)
  })
      .doOnError((e, stacktrace) {
    if (e is DioError) {
      response = null;
    //  response = e.response.data.toString();
    }
  })
      .doOnListen(() => loading = true)
      .doOnDone(() => loading = false);
}

