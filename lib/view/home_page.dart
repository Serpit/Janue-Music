import 'package:flutter/material.dart';
import 'package:dartin/dartin.dart';
import 'base.dart';
import 'package:provide/provide.dart';
import 'package:jian_yue/utils/widget_utils.dart';
import 'mine_dart.dart';
import 'package:jian_yue/widget/play_controller_widget.dart';
import 'online_music_list_page.dart';
import 'package:jian_yue/constant/constant.dart';
import 'package:jian_yue/view/search_page.dart';

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
          drawer: MyDrawer(),
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
class MyDrawer extends StatelessWidget {
  const MyDrawer({
    Key key,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Drawer(
      child: MediaQuery.removePadding(
        context: context,
        removeTop: true,
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            Padding(
              padding: const EdgeInsets.only(top: 38.0),
              child: Row(
                children: <Widget>[
                  Padding(
                    padding: const EdgeInsets.symmetric(horizontal: 16.0),
                    child: ClipOval(
                      child: Image.asset(
                        "imgs/avatar.png",
                        width: 80,
                      ),
                    ),
                  ),
                  Text(
                    "Wendux",
                    style: TextStyle(fontWeight: FontWeight.bold),
                  )
                ],
              ),
            ),
            Expanded(
              child: ListView(
                children: <Widget>[
                  ListTile(
                    leading: const Icon(Icons.add),
                    title: const Text('Add account'),
                  ),
                  ListTile(
                    leading: const Icon(Icons.settings),
                    title: const Text('Manage accounts'),
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
}