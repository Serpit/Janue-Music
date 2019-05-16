import 'package:flutter/material.dart';
import 'package:jian_yue/constant/constant.dart';
import 'package:jian_yue/utils/method_channel_utils.dart';
import 'package:jian_yue/model/repository.dart';
import 'package:jian_yue/bean/base_play_list_bean.dart';
import 'dart:convert';
class PlayControllerWidget extends StatefulWidget{
  @override
  State<StatefulWidget> createState() {
    return _PlayControllerWidgetState();
  }

}



class _PlayControllerWidgetState extends State<PlayControllerWidget>{

  MusicRepo _repo;
   bool _isPlaying  = false;
  List<BasePlayListItemInfo> _response;
  int _curIndex;
  @override
  void initState() {

    super.initState();
    _repo = MusicRepo();

  }

  set isPlaying(bool value) {
    _isPlaying = value;
    setState(() {

    });
  }


  set curIndex(int value) {
    _curIndex = value;
    setState(() {

    });
  }

  set response(List<BasePlayListItemInfo> value) {
    _response = value;
    setState(() {

    });
  }

  @override
  Widget build(BuildContext context) {
    _repo.getIsPlaying().listen((value){
         isPlaying = value;
    });
    _repo.getPlayingList().listen((value){
      List list =  json.decode(value);
      response = getMusicList(list);
    });
    _repo.getCurIndex().listen((value){
      curIndex = value;

    });
    return InkWell(
      onTap: onTab,
      child: Container(
        color: Colors.white,
        width: MediaQuery.of(context).size.width,
        height: 50.0,
        child: Stack(
          children: <Widget>[

            Positioned(

              child: PageView.builder(
                controller: PageController(initialPage: _curIndex),
                itemCount: _response==null ?0 :_response.length,
                itemBuilder: (context,index)=>buildPlayingListItem(index),
                onPageChanged:(position)=> onChangeMusic(position),
              ),
            ),
            Positioned(
                top:3,
                right: 30,
                child: GestureDetector(
                    onTap: onClickPlayBtn,
                    child:  _isPlaying?Image.asset('images/ic_play_bar_btn_pause.png',width: 45,height: 45,):Image.asset('images/ic_play_bar_btn_play.png',width: 45,height: 45,)

                )
            ),
          ],
        ),
      ),
    );
  }


  Widget buildPlayingListItem(int position){

    return Container(
      width: Constants.DisplayWidth,

      height: 50,
      child: Stack(
        children: <Widget>[
          Positioned(
            top: 10,
            left: 20,

            child: buildIcon(position)

          ),
          Positioned(
            top: 7,
            left: 60,
            child: Text(_response.length==0?'无音乐':_response[position].songname,style: TextStyle(fontSize: 15),),
          ),
          Positioned(
            top: 28,
            left: 60,
            child: Text(_response.length==0?'艺术家':_response[position].artist,style: TextStyle(fontSize: 10,color: Colors.grey),),
          )
        ],
      ),
    );


  }

  Widget buildIcon(int position){
    if(_response==null){
      return Image.asset('images/default_cover.png',width: 30,height: 30,);
    }else{
      return Image.network(_response[position].piclink,width: 30,height: 30,);
    }
  }
  void onClickPlayBtn(){
    if(_isPlaying){
      callNativeMethod(Constants.MUSIC_CONTROL_CHANNEL, 'stopMusic');
    }else{
      callNativeMethod(Constants.MUSIC_CONTROL_CHANNEL, 'startMusic');

    }
    _isPlaying = !_isPlaying;
     setState(() {

     });
  }

  void onTab(){
    callNativeMethod(Constants.START_ACTIVITY_CHANNEL, 'startMusicActivityFromBar');
  }

  void onChangeMusic(int position){

    callNativeMethod(Constants.MUSIC_CONTROL_CHANNEL, 'switchMusic',params: {'songId':'${_response[position].songid}'});
  }
}