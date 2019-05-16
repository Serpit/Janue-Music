import 'package:flutter/material.dart';
import 'base.dart';
import 'mine_dart.dart';
import 'package:jian_yue/widget/play_controller_widget.dart';
import 'online_music_list_page.dart';
import 'package:jian_yue/constant/constant.dart';
import 'package:jian_yue/view/search_page.dart';
import 'package:jian_yue/model/repository.dart';
import 'login_page.dart';
import 'package:jian_yue/utils/toast.dart';
import 'package:jian_yue/utils/method_channel_utils.dart';
import 'package:flutter/services.dart';
import 'change_theme_page.dart';
import 'setting_page.dart';
class HomePage extends PageProvideNode{

  @override
  Widget buildContent(BuildContext context) {
    return _HomePageContent();
  }
}


class _HomePageContent extends StatefulWidget{

  @override
  _HomePageContentState createState() => _HomePageContentState();
}


class _HomePageContentState extends State<_HomePageContent>{

//  static GlobalKey<ScaffoldState> _globalKey= new GlobalKey();
  @override
  Widget build(BuildContext context) {
      return Material(
        child: Scaffold(
          drawer: MineDrawer(),
          appBar: AppBar(
           // key: _globalKey , //设置key
            title: Text('简悦音乐'),
            leading: Builder(builder: (context){
              return IconButton(
                icon: Icon(Icons.format_indent_increase, color: Colors.white), //自定义图标
                onPressed: () {
                  // 打开抽屉菜单
                  Scaffold.of(context).openDrawer();
                  //_globalKey.currentState.openDrawer();
                },
              );
            }),
            actions: <Widget>[
              new IconButton(
                icon: const Icon(Icons.search),
                tooltip: 'search',
                onPressed: () {
                  startSearchPage();
                },
              ),
            ],
          ),
          body: ConstrainedBox(
              constraints: BoxConstraints.expand(),
              child: Stack(
                children: <Widget>[
                  Positioned(
                      child: Container(
                        width: Constants.DisplayWidth,
                        height: Constants.DisplayHeight,
                        color: Colors.green,
                        child: PageView.builder(
                            itemCount: 2,
                            itemBuilder: (context,position) => buildPage(position)),
                      ),
                  ),
                  Positioned(
                    bottom: 0,
                    child: PlayControllerWidget(),
                  )
                ],
              ),
          ),
        ),
      );
  }
  void startSearchPage(){

    Navigator.push(context, MaterialPageRoute(builder: (BuildContext context) => SearchPage()));
  }

  Widget buildPage(int position){
    switch(position){
      case 0:
        return MinePage();
      case 1:
        return OnLineMusicListPage();

    }
  }
}
class MineDrawer extends StatefulWidget {


   MineDrawer({
    Key key,
  }) : super(key: key){

  }


  @override
  State<StatefulWidget> createState() {
    // TODO: implement createState
    return _Drawer();
  }


}

class _Drawer extends State<MineDrawer>{
  UserRepo _repo;

  bool _isLogin =false;
  String _username;

  set isLogin(bool value) {
    _isLogin = value;
    setState(() {

    });
  }


  bool get isLogin => _isLogin;


  String get username => _username;

  set username(String value) {
    _username = value;
    setState(() {

    });
  }

  @override
  void initState() {
    super.initState();
    _repo = UserRepo();
  }
  @override
  Widget build(BuildContext context) {

    _repo.getUserIsLogin().listen((value){
      isLogin = value['sp_key_is_login'];
      username = value['sp_key_username'];
    });

    return Drawer(
      child: MediaQuery.removePadding(
        context: context,
        removeTop: true,
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            buildTitle(isLogin),
            Expanded(
              child: ListView(
                children: <Widget>[
                  ListTile(
                    onTap:()=>{ Navigator.push(context, MaterialPageRoute(builder: (BuildContext context) => ChangeThemePage()))},
                    leading: const Icon(Icons.tonality),
                    title: const Text('个性换肤'),
                  ),
                  ListTile(
                    onTap:()=>{ Navigator.push(context, MaterialPageRoute(builder: (BuildContext context) => SettingPage()))},
                    leading: const Icon(Icons.settings),
                    title: const Text('设置'),
                  ),
                  ListTile(
                    onTap: () async => await SystemChannels.platform.invokeMethod('SystemNavigator.pop'),
                    leading: const Icon(Icons.exit_to_app),
                    title: const Text('退出'),
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }


  Widget buildTitle(bool isLogin){
    if(isLogin){
      return Container(
        height: 200,
        padding: const EdgeInsets.only(top: 38.0),
        child: Row(
          children: <Widget>[
            Padding(
              padding: const EdgeInsets.symmetric(horizontal: 16.0),
              child: ClipOval(
                child: Image.asset(
                  "images/ic_launcher.png",
                  width: 80,
                ),
              ),
            ),
            Container(
              width:150,
              height: 80,
              child: Stack(
                children: <Widget>[
                  Positioned(
                    top:10,
                    child: Text(
                      '欢迎您：',
                      style: TextStyle(fontSize: 18),
                    ),
                  ),
                  Positioned(
                    top: 40,
                    child: InkWell(
                      onDoubleTap: logout,
                      child: Text(
                        username,
                        style: TextStyle(fontSize: 30,color: Colors.red),
                      ),
                    )
                  ),

                ],
              ),
            ),

          ],
        ),
      );
    }else{
      return Container(
        height: 200,
        padding: const EdgeInsets.only(top: 38.0),
        child: Center(
          child: OutlineButton(
            child: Text("立即登陆"),
            shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(20.0)),
            onPressed: ()  {
                Navigator.pop(context);
                Navigator.push(context, MaterialPageRoute(builder: (BuildContext context) => LoginPage()));
            },
          ),
        )
      );
    }

  }

  logout(){
    callNativeMethod(Constants.USER_CHANNEL, 'logout');
    setState(() {

    });
    Toast.show('logout success', context);
  }
}