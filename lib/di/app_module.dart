import 'package:dartin/dartin.dart';
import 'package:dio/dio.dart';
import 'package:jian_yue/constant/constant.dart';
import 'package:jian_yue/model/repository.dart';
//import 'package:jian_yue/viewmodel/demo_provide.dart';
import 'package:jian_yue/utils/sp_utils.dart';
import 'package:jian_yue/model/repository.dart';
import 'package:jian_yue/viewmodel/splash_provide.dart';
import 'package:jian_yue/viewmodel/online_music_list_provide.dart';
import 'package:jian_yue/viewmodel/search_provide.dart';
import 'package:jian_yue/viewmodel/online_type_music_list_provide.dart';
import 'package:jian_yue/viewmodel/recent_play_list_provide.dart';
import 'package:jian_yue/viewmodel/local_music_provide.dart';
const testScope = DartInScope('test');


final dio = Dio()
      ..options = BaseOptions(baseUrl: Constants.BASE_URL,connectTimeout: 30 ,receiveTimeout: 30)
      ..interceptors.add(LogInterceptor(requestBody: true,responseBody: true));
     // ..interceptors.add(ResponseFormatInterceptor())

final remoteModule = Module([
  single<MusicService>(MusicService()),
  single<ImageService>(ImageService()),
]);



final localModule = Module([
  single<SpUtils>(spUtil),
]);


final viewModelModule = Module([
  //factory<HomeProvide>(({params}) => HomeProvide(params.get(0), get())),
  factory<LocalMusicListProvide>(({params})=>LocalMusicListProvide(get())),
  factory<RectentPlayListProvide>(({params})=>RectentPlayListProvide(get())),
  factory<OnlineTypeMusicListProvide>(({params})=>OnlineTypeMusicListProvide(get())),
  factory<OnLineMusicListProvide>(({params}) => OnLineMusicListProvide(get())),
  factory<SplashProvide>(({params}) => SplashProvide(get())),
  factory<SearchProvide>(({params})=> SearchProvide(params.get(0),get())),
])..addOthers(testScope, []);



final repoModule = Module([
  lazy<MusicRepo>(({params}) => MusicRepo(remote: get())),
  lazy<ImageRepo>(({params}) => ImageRepo(get())),
]);


final appModule = [viewModelModule, repoModule, remoteModule, localModule];
SpUtils spUtil;



init() async {
  spUtil = await SpUtils.getInstance();
  // DartIn start
  startDartIn(appModule);
}

class ResponseFormatInterceptor extends Interceptor{
  @override
  onResponse(Response response) {
    // TODO: implement onResponse

    return super.onResponse(response);
  }
}