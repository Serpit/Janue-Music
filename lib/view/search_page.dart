import 'package:flutter/material.dart';
import 'base.dart';
import 'package:jian_yue/constant/constant.dart';
import 'package:jian_yue/widget/search_app_bar.dart';
import 'package:jian_yue/viewmodel/search_provide.dart';
import 'package:dartin/dartin.dart';
import 'package:provide/provide.dart';
import 'package:jian_yue/bean/search_result_bean.dart';
import 'package:jian_yue/bean/song_bean.dart';
import 'package:jian_yue/utils/method_channel_utils.dart';
class SearchPage extends PageProvideNode{



  @override
  Widget buildContent(BuildContext context) {
    return _SearchPageContent();
  }

  SearchPage(){
    final provide = inject<SearchProvide>();
    mProviders.provideValue(provide);
  }
}

class _SearchPageContent extends StatefulWidget{
  @override
  State<StatefulWidget> createState() {

    return _SearchPageContentState();
  }
}


class _SearchPageContentState extends State<_SearchPageContent>{
  SearchProvide mProvide;

  TextEditingController _controller = TextEditingController();


  @override
  Widget build(BuildContext context) {
    mProvide = Provide.value(context);

    return Material(
      child: Provide<SearchProvide>(builder: (context,child,value)=>Scaffold(
        appBar: SearchAppBarWidget(
          hintText: '输入歌手名、歌名',
          elevation: 2.0,
          onEditingComplete: ()=>onEditingComplete(_controller.value.text),
          controller: _controller,
          height: 45,
          leading: IconButton(
              icon: Icon(Icons.arrow_back),
              onPressed: () {
                Navigator.pop(context);
              }),

        ),
        body: mProvide.loading?Center(child: Text('加载中...'),):buildContent()
               ),
      )
    );
  }
  Widget buidListItem(int position,Song searchResult){
    return InkWell(
      onTap:()=> onItemOnClick(position,searchResult),
      child: Container(
        height: 70,
        width: Constants.DisplayWidth,
        child: Stack(

          children: <Widget>[
            Positioned(
              top: 10,
              left: 20,
              child: Text(searchResult.songname,style: TextStyle(fontSize: 17,color: Colors.black),),
            ),
            Positioned(
              top: 40,
              left: 20,
              child: Text(searchResult.artistname,style: TextStyle(fontSize: 12,color: Colors.grey),),
            ),

            Positioned(
              right: 0,
              child: InkWell(
                onTap: ()=>onItemMoreOnClick(position),
                child:  Container(
                  height: 70,
                  width: 30,
                  child: Icon(Icons.more_vert,color: Colors.grey,),
                ),
              )
            )
          ],
        ),
      ),
    );
  }

  Widget buildContent(){
      if(mProvide.response==null||mProvide.response.song==null){
        return Center(
          child: Text(
            '暂无搜索结果'
          ),
        );
      }
      return ListView.separated(itemBuilder: (context,index)=>buidListItem(index,mProvide.response.song[index]), separatorBuilder: (context,index)=> Divider(height: 1,color: Colors.grey,), itemCount:(mProvide.response==null||mProvide.response.song==null)?0: mProvide.response.song.length);

  }
  void onItemMoreOnClick(int position){
    print("item more click");
  }
  void onItemOnClick(int position,Song searchResult){
    ///print('item click');
    callNativeMethod(Constants.START_ACTIVITY_CHANNEL, 'startMusicActivityWithoutInfo',params: searchResult.toJson());

  }
  void onEditingComplete(String keyword){
    print('editing complete :${keyword}');
    mProvide.keyword = keyword;
    searchMusic();
  }


  void searchMusic(){
    final s = mProvide.searchMusic().doOnListen((){

    }).doOnDone((){

    }).doOnCancel((){
      print("======cancel======");
    }).listen((_){

    },onError: (e){

    });

    mProvide.addSubscription(s);
  }
}