import 'base.dart';
import 'package:flutter/material.dart';
import 'package:jian_yue/constant/constant.dart';
import 'recent_play_list_page.dart';
import 'local_music_page.dart';
class MinePage extends PageProvideNode{
  @override
  Widget buildContent(BuildContext context) {
    return _MinePageContent();
  }
}


class _MinePageContent extends StatefulWidget{
  @override
  State<StatefulWidget> createState() {

    return _MinePageContentState();
  }
}


class _MinePageContentState extends State<_MinePageContent> implements ItemPresenter<Null>{
  BuildContext _context;
  @override
  Widget build(BuildContext context) {
    _context = context;
      return Material(
        child: Scaffold(
          body: ConstrainedBox(
              constraints: BoxConstraints.expand(),
              child: Container(
                margin: EdgeInsets.only(top: 10),
                child:  ListView.separated(
                    itemBuilder: (context,index)=>buildListItem(index) ,
                    separatorBuilder: (context,index)=> Divider(height: 1,color: Colors.grey,indent:80,),
                    itemCount: 4),
              )
              ),
          ),
        );
  }


  List<String> images = ['images/ic_music_local.png','images/ic_music_recently.png','images/ic_music_zan.png','images/ic_music_download.png'];
  List<String> titles = ['本地音乐','最近播放','我的收藏','下载管理'];
  List<String> actions = ['ACTION_LOCAL','ACTION_RECENTLY','ACTION_COLLECTION','ACTION_DOWNLOAD'];
  Widget buildListItem(int position){
    return InkWell(
      onTap: ()=>onItemClick(actions[position],null),
      child: Container(
        width: Constants.DisplayWidth,
        height: 50,
        child: Stack(
          children: <Widget>[
            Positioned(
                left: 20,
                top: 10,
                child:  Image.asset(images[position],width: 30,height: 30,)
            ),
            Positioned(
              left: 80,
              top: 13,
              child: Text(
                titles[position],
                style: TextStyle(fontSize: 16),
              ),
            )
          ],
        ),
      ),
    );
  }

  @override
  void onItemClick(String action, Null item) {
    switch(action){
      case 'ACTION_LOCAL':
        Navigator.push(_context, MaterialPageRoute(builder: (context)=>LocalMusicListPage()));
        break;
      case 'ACTION_RECENTLY':
        Navigator.push(_context, MaterialPageRoute(builder: (context)=>RecentPlayListPage()));
        break;
      case 'ACTION_COLLECTION':
        break;
      case 'ACTION_DOWNLOAD':
        break;
    }
  }


}