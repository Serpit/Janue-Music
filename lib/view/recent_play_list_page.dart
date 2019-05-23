import 'package:flutter/material.dart';
import 'base.dart';
import 'package:jian_yue/viewmodel/recent_play_list_provide.dart';
import 'package:provide/provide.dart';
import 'package:dartin/dartin.dart';
import 'package:jian_yue/constant/constant.dart';
class RecentPlayListPage extends PageProvideNode{


  @override
  Widget buildContent(BuildContext context) {

    return _RecentPlayListPageContent();
  }

  RecentPlayListPage(){
    final provide = inject<RectentPlayListProvide>();
    mProviders.provideValue(provide);
  }
}


class _RecentPlayListPageContent extends StatefulWidget{
  @override
  State<StatefulWidget> createState() {

    return _RecentPlayListPageContentState();
  }
}


class _RecentPlayListPageContentState extends State<_RecentPlayListPageContent>{
  RectentPlayListProvide mProvide;

  @override
  void initState() {

    super.initState();
  }


  @override
  Widget build(BuildContext context) {
    mProvide = Provide.value(context);
    loadRecentListData();
    return Material(
      child: Scaffold(
        appBar: AppBar(title: Text('最近播放'),),
        body: Provide<RectentPlayListProvide>(
          builder: (context,child,value)=>Material(
            child:   buildPageContent(),
          ),
        ),
      ),
    );
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
    //callNativeMethod(Constants.DATA_BASE_CHANNEL, 'insertPlaying',params : mProvide.response.songList[position].toJson());
  }
  void  onItemMoreOnClick(int position){
    print('type:${position}');
  }
  void loadRecentListData(){

    final s  = mProvide.loadRecentPlayList().listen((_){
    },onError: (e){
    });
    mProvide.addSubscription(s);
  }
}




