import 'package:flutter/material.dart';
import 'base.dart';
import 'package:provide/provide.dart';
import 'package:dartin/dartin.dart';
import 'package:jian_yue/constant/constant.dart';
import 'package:jian_yue/viewmodel/local_music_provide.dart';


class LocalMusicListPage extends PageProvideNode{

  @override
  Widget buildContent(BuildContext context) {

    return _LoacalMusicListPageConetnt();
  }

  LocalMusicListPage(){
    final provide = inject<LocalMusicListProvide>();
    mProviders.provideValue(provide);
  }
}


class _LoacalMusicListPageConetnt extends StatefulWidget{

  @override
  State<StatefulWidget> createState() {
    return _LocalMusicListPageContentState();
  }
}


class _LocalMusicListPageContentState extends State<_LoacalMusicListPageConetnt>{

  LocalMusicListProvide mProvide;
  @override
  Widget build(BuildContext context) {
    mProvide = Provide.value(context);
    getLocalMusicList();
    return Material(
      child: Scaffold(
        appBar: AppBar(title: Text('本地音乐'),),
        body: Provide<LocalMusicListProvide>(builder: (context,child,value)=>buildPageContent()),
      ),
    ) ;
  }

  void getLocalMusicList(){
    final s = mProvide.getLocalMusicList().listen((_){},onError: (e){

    });
    mProvide.addSubscription(s);
  }

  Widget buildPageContent(){

    if(mProvide.isLoading){
      return Center(
        child: Text('加载中...'),
      );
    }
    return  Container(
      child:ListView.separated(
          itemBuilder: (context,position)=>buildListItemWidget(position),
          separatorBuilder: (context,position)=>Divider(color: Colors.grey,),
          itemCount: mProvide.response.length
      ),
    );

  }
  Widget buildListItemWidget(int position){
    return InkWell(
      onTap: ()=>onItemClick(position),
      child: Container(
        width: Constants.DisplayWidth,

        height: 70,
        child: Stack(
          children: <Widget>[

            Positioned(
                left: 20,
                top: 15,
                child: buildSongNameText(position)
            ),
            Positioned(
                left: 20,
                top: 45,
                child: buildArtist( position)
            ),
            Positioned(
                right: 0,
                child: GestureDetector(
                  onTap: ()=>onItemMoreOnClick(position),
                  child:  Container(
                    height: 70,
                    width: 30,
                    child: Icon(Icons.more_vert,color: Colors.grey,),
                  ),
                )
            ),

          ],
        ),
      ),
    );

  }

  Widget buildArtist(int position){

    String artist = mProvide.response[position].artist;


    return Text(artist,style: TextStyle(fontSize: 12,color: Colors.grey),);


  }
  Widget buildSongNameText(int position){
    String  songName = mProvide.response[position].songname;
    if(songName.length>14){
      songName = songName.substring(0,14)+"...";


    }
    return Text(songName,style: TextStyle(fontSize: 17),);
  }
  void onItemClick(int position){

  }
  void  onItemMoreOnClick(int position){
    print('type:${position}');
  }

}
