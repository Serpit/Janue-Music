import 'package:flutter/material.dart';
import 'package:provide/provide.dart';
import 'package:dartin/dartin.dart';
import 'base.dart';
import 'package:jian_yue/viewmodel/download_list_page_provide.dart';
import 'package:jian_yue/constant/constant.dart';
import 'package:jian_yue/utils/method_channel_utils.dart';
class DownloadListPage extends PageProvideNode{

  DownloadListPage(){
    final provide = inject<DownloadPageProvide>();
    mProviders.provideValue(provide);
  }
  @override
  Widget buildContent(BuildContext context) {
    return _DownloadListPageContent();
  }
}


class _DownloadListPageContent extends StatefulWidget{
  @override
  State<StatefulWidget> createState() {
    return _DownloadListPageContentState();
  }
}

class _DownloadListPageContentState extends State<_DownloadListPageContent>{

  DownloadPageProvide mProvide;
  @override
  Widget build(BuildContext context) {
    mProvide = Provide.value(context);
    loadDownloadMusicList();
    return Material(
      child: Scaffold(
        appBar: AppBar(title: Text('下载管理'),),
        body:Provide<DownloadPageProvide>(
          builder: (context,child,value)=>Material(
            child: buildBody(),
          ),
        ),
      ),
    );
  }


  Widget buildBody(){
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
    showDialog(
      context: context,
      builder: (ctx){
        return SimpleDialog(
          backgroundColor: Colors.white,
          elevation: 5,
          shape: RoundedRectangleBorder(
            borderRadius: BorderRadius.all(Radius.circular(6)),
          ),
          children: <Widget>[
             SimpleDialogOption(
              child:  Text('从列表中删除'),
              onPressed: (){
                int songid = mProvide.response[position].songid;
                callNativeMethod(Constants.DATA_BASE_CHANNEL, 'deleteDownloadMusicOnList',params: {Constants.PARAM_SONG_ID:"${songid}"});
                setState(() {

                });
                Navigator.pop(context);
              }
            ),
            SimpleDialogOption(
              child:  Text('删除本地文件'),
              onPressed:(){
                int songid = mProvide.response[position].songid;
                callNativeMethod(Constants.DATA_BASE_CHANNEL, 'deleteDownloadMusic',params: {Constants.PARAM_SONG_ID:"${songid}"});
                setState(() {

                });
                Navigator.pop(context);
              }
            ),
          ],
        );
      }
    );
  }
  void  onItemMoreOnClick(int position){
    print('type:${position}');
  }
  void loadDownloadMusicList(){

    final s  = mProvide.loadDownloadMusicList().listen((_){
    },onError: (e){
    });
    mProvide.addSubscription(s);
  }
  

}