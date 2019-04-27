import 'package:dio/dio.dart';
import 'package:jian_yue/model/repository.dart';
import 'package:jian_yue/view/base.dart';
import 'package:rxdart/rxdart.dart';

// viewmodel
class SplashProvide extends BaseProvide{
    final ImageRepo _repo;
    String _response;

    String get response  => _response;

    set response(String reponse){
      _response = reponse;
      notifyListeners();
    }


    SplashProvide(this._repo);


    Observable getSplashUrl() => _repo.getSplashUrl()
        .doOnData((r)=>{response = r

    }) .doOnError((e, stacktrace) {
    if (e is DioError) {
        response = null;
    }
    }).doOnDone((){});


}
