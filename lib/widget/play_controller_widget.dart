import 'package:flutter/material.dart';
import 'package:jian_yue/constant/constant.dart';
class PlayControllerWidget extends StatefulWidget{
  @override
  State<StatefulWidget> createState() {
    
    return _PlayControllerWidgetState();
  }

}



class _PlayControllerWidgetState extends State<PlayControllerWidget>{

   bool _isPlaying = false;
  @override
  void initState() {
    // TODO: implement initState
    super.initState();


  }
  
  @override
  Widget build(BuildContext context) {

    return Container(
      color: Colors.white,
      width: MediaQuery.of(context).size.width,
      height: 50.0,
      child: Stack(
        children: <Widget>[

          Positioned(

            child: PageView.builder(
                itemCount: 1,
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

            child: Image.asset('images/default_cover.png',width: 30,height: 30,),

          ),
          Positioned(
            top: 7,
            left: 60,
            child: Text('无音乐',style: TextStyle(fontSize: 15),),
          ),
          Positioned(
            top: 28,
            left: 60,
            child: Text('艺术家',style: TextStyle(fontSize: 10,color: Colors.grey),),
          )
        ],
      ),
    );


  }

  void onClickPlayBtn(){
    _isPlaying = !_isPlaying;
     setState(() {

     });
  }


  void onChangeMusic(int position){
    print('viewpage: ${position}');
  }
}