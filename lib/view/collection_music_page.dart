import 'package:flutter/material.dart';
import 'base.dart';
import 'package:dartin/dartin.dart';
import 'package:provide/provide.dart';
import 'package:jian_yue/constant/constant.dart';
import 'package:jian_yue/utils/method_channel_utils.dart';
import 'package:jian_yue/viewmodel/collection_music_provide.dart';
class CollectionMusicPage extends PageProvideNode{


  @override
  Widget buildContent(BuildContext context) {
    return _CollectionMusicPageContent();
  }

  CollectionMusicPage(){
    final provide = inject<CollectionMusicProvide>();
    mProviders.provideValue(provide);
  }
}


class _CollectionMusicPageContent extends StatefulWidget{
  @override
  State<StatefulWidget> createState() {

    return _CollectionMusicPageContentState();
  }
}


class _CollectionMusicPageContentState extends State<_CollectionMusicPageContent>{


  CollectionMusicProvide mProvide;
  @override
  Widget build(BuildContext context) {
    mProvide = Provide.value(context);
    _getUserId();

    return Material(
      child: Scaffold(
        appBar: AppBar(title: Text('我的收藏'),),
        body: Provide<CollectionMusicProvide>(
          builder:(context,child,value)=> Container(
            child:  buildConetnt()
          )
        )
      ),

    );
  }

  Widget buildConetnt(){
    if(mProvide.isLoading){
      return Center(
        child: Text('加载中...'),
      );
    }else{
      return ListView.separated(
          itemBuilder: (context,position)=>buildListItemWidget(position),
          separatorBuilder: (context,position)=>Divider(color: Colors.grey,),
          itemCount: mProvide.response.length
      ) ;
    }
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
    String  songName = mProvide.response[position].title;
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

  loadCollectionMusic(){
    final s = mProvide.getCollectionMusicList(_userid).listen((_){

    });

    mProvide.addSubscription(s);
  }
  String _userid;

  set userid(String value) {
    _userid = value;
    loadCollectionMusic();
  }

  _getUserId() {
    Future future = callNativeMethod(Constants.USER_CHANNEL, 'getUserId');
    future.then((value){
      userid = value[Constants.SP_kEY_USER_ID];
    });

  }

}

