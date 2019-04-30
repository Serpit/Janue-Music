import 'package:flutter/material.dart';
import 'dart:ui';
import 'package:dartin/dartin.dart';
import 'package:provide/provide.dart';
import 'package:jian_yue/viewmodel/online_type_music_list_provide.dart';
import 'base.dart';
import 'package:jian_yue/viewmodel/online_music_list_provide.dart';
import 'package:jian_yue/constant/constant.dart';
import 'package:jian_yue/bean/music_list_bean.dart';
import 'package:jian_yue/widget/play_controller_widget.dart';
import 'package:jian_yue/utils/method_channel_utils.dart';

class OnlineTypeMusicListPage extends PageProvideNode{
  MusicListItem _info;
  @override
  Widget buildContent(BuildContext context) {
    return _OnLineTypeMusicListPageContent( _info);
  }

  OnlineTypeMusicListPage(this._info){
    final provide = inject<OnlineTypeMusicListProvide>();
    mProviders.provideValue(provide);

  }
}


class _OnLineTypeMusicListPageContent extends StatefulWidget{
  MusicListItem _info;


  _OnLineTypeMusicListPageContent(this._info);

  @override
  State<StatefulWidget> createState() {
    return _OnLineTypeMusicListPageContentState(_info);
  }
}

class _OnLineTypeMusicListPageContentState extends State<_OnLineTypeMusicListPageContent>{

  MusicListItem _info;
  OnlineTypeMusicListProvide mProvide;


  _OnLineTypeMusicListPageContentState(this._info);

  @override
  Widget build(BuildContext context) {
    mProvide = Provide.value(context);

    loadList(_info.type,10,0);



    return Scaffold(
      appBar: AppBar(title: Text(_info.typeName),),
      body:  Provide<OnlineTypeMusicListProvide>(
        builder: (context,child,value)=>Material(
          child:   buildPageContent(),
        ),
      )

    );
  }


  Widget buildPageContent(){
    if(mProvide.isLoading){
      return Center(
        child: Text('搜索中...'),
      );
    }else{
      return Container(
          child: ConstrainedBox(
            constraints: BoxConstraints.expand(),
            child: Stack(
              children: <Widget>[
                Positioned(
                  child: Container(
                    height: 150,
                    child: Image.network(mProvide.response.billboard.picS640,width: Constants.DisplayWidth,fit: BoxFit.fitWidth,),
                  ),
                ),
                Positioned(
                  child: BackdropFilter(
                    filter: ImageFilter.blur(sigmaX: 20,sigmaY: 20),
                    child: Container(
                      height: 150,
                      width: Constants.DisplayWidth,
                      color: Colors.grey.withOpacity(0.1),
                    ),
                  ),
                ),
                Positioned(
                  top: 15,
                  left: 15,
                  child: Image.network(mProvide.response.billboard.picS640,width: 120,height: 120,)
                ),
                Positioned(
                  top:15,
                  left: 145,
                  child: Text(mProvide.response.billboard.name,style: TextStyle(color: Colors.white,fontSize: 18),),
                ),
                Positioned(
                  left: 145,
                  top: 43,
                  child: Text('最近更新时间: ${mProvide.response.billboard.updateDate}',style: TextStyle(color: Colors.white),),
                ),
                Positioned(
                  left: 145,
                  top: 65,
                  child: Container(
                    width: 200,
                    child: Text(mProvide.response.billboard.comment,style: TextStyle(color: Colors.white),softWrap: true,),
                  ),
                ),
                Positioned(
                    child: Container(
                      margin: EdgeInsets.only(top: 150,bottom: 50),
                      child:  ListView.separated(
                          itemBuilder: (context,position)=>buildListItemWidget(position),
                          separatorBuilder: (context,position)=>Divider(color: Colors.grey,),
                          itemCount: mProvide.responseList.length
                      ),
                    )
                ),
                Positioned(
                  bottom: 0,
                  child: PlayControllerWidget(),
                )
              ],
            ),
          )
      );
    }
  }

  Widget buildListItemWidget(int position){
    if(position==mProvide.responseList.length-1){
      print(position);
      loadList(_info.type, 10, position+1);
      return Container(
        padding: const EdgeInsets.all(16.0),
        alignment: Alignment.center,
        child: SizedBox(
            width: 24.0,
            height: 24.0,
            child: CircularProgressIndicator(strokeWidth: 2.0)
        ),
      );
    }else{
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
                  child: buildMusicIcon(position)
              ),
              Positioned(
                  left: 90,
                  top: 15,
                  child: buildSongNameText(position)
              ),
              Positioned(
                  left: 90,
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




  }


  Widget buildMusicIcon(int position){
    if(mProvide.isLoading){
      return Image.asset('images/default_cover.png',width: 50,height: 50,);
    }else{
      return Image.network(mProvide.responseList[position].album500500,width: 50,height: 50,);
    }
  }
  Widget buildArtist(int position){
    String artist ;
    if(mProvide.isLoading){
      artist = '加载中...';
    }else{
      artist = mProvide.responseList[position].author;
    }

    return Text(artist,style: TextStyle(fontSize: 12,color: Colors.grey),);


  }
  Widget buildSongNameText(int position){
    String songName;
    if(mProvide.isLoading){
      songName = '加载中...';
    }else{
      songName = mProvide.responseList[position].title;
      if(songName.length>14){
        songName = songName.substring(0,14)+"...";
      }

    }
    return Text(songName,style: TextStyle(fontSize: 17),);
  }
  void onItemClick(int position){
    callNativeMethod(Constants.DATA_BASE_CHANNEL, 'insertPlaying',params : mProvide.responseList[position].toJson());
  }

  void  onItemMoreOnClick(int position){
    print('type:${position}');
  }

  void loadList(int type,int size,int offset){
    final s =  mProvide.getDetailList(type,size,offset).listen((_){},onError: (e){

    });
    mProvide.addSubscription(s);
  }
}
