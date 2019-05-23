import 'package:flutter/material.dart';
import 'package:dartin/dartin.dart';
import 'package:jian_yue/viewmodel/online_music_list_provide.dart';
import 'base.dart';
import 'package:provide/provide.dart';
import 'package:jian_yue/utils/widget_utils.dart';
import 'package:jian_yue/bean/music_list_bean.dart';
import 'package:jian_yue/widget/play_controller_widget.dart';
import 'online_type_music_list_page.dart';

List<MusicListItem> listData = [ MusicListItem('热歌榜',2),MusicListItem('新歌榜',1),
MusicListItem('华语',20),MusicListItem('欧美',21),MusicListItem('影视',24),
MusicListItem('情歌',23),MusicListItem('网络',25),MusicListItem('经典',22),MusicListItem('摇滚',11),MusicListItem('ktv必点',6)];

class OnLineMusicListPage extends PageProvideNode{
  OnLineMusicListPage(){
    final provide = inject<OnLineMusicListProvide>();
    mProviders.provideValue(provide);
  }

  @override
  Widget buildContent(BuildContext context) {
    return _MusicListContent();
  }
}


class _MusicListContent extends StatefulWidget{
    @override
  State<StatefulWidget> createState() {

    return _MusicListContentState();
  }
}


class _MusicListContentState extends State<_MusicListContent> with SingleTickerProviderStateMixin<_MusicListContent> {
  OnLineMusicListProvide mProvide;
 

  @override
  void initState() {
    super.initState();
  }
  void loadBillList(){
    listData.forEach((item){
      final s =  mProvide.getBillList(item.type).listen((_){},onError: (e){
        dispatchFailure( e);
      });
      mProvide.addSubscription(s);
    });

  }
  @override
  Widget build(BuildContext context) {

    mProvide = Provide.value<OnLineMusicListProvide>(context);
    loadBillList();
    return Material(
      child: Scaffold(

        body: DefaultTextStyle(
            style: TextStyle(color: Colors.black),
            child: Provide<OnLineMusicListProvide>(builder:(BuildContext context, Widget child, OnLineMusicListProvide value)=> ConstrainedBox(
                constraints: BoxConstraints.expand(),
                child: Stack(

                  children: <Widget>[
                    Positioned(
                      child:  ListView.separated(
                        shrinkWrap:true,
                        itemCount: listData.length,
                        itemBuilder: (context,index)=> MuiscBillItemWidget(index,value.response[listData[index].type],value.isLoading),
                        separatorBuilder: (context,index)=> Divider(height: 1,color: Colors.grey,indent: 100,),
                      ),
                    ),
                    Positioned(
                      bottom: 0,
                      child: PlayControllerWidget(),
                    )
                  ],
                ),
            )
            )
        ),
      ),
    );
  }

}


class MuiscBillItemWidget extends StatelessWidget implements ItemPresenter<MusicListItem>{
  final int position;
  BuildContext mContext;
  final MusicListInfo info;
  final bool isLoading;
  MuiscBillItemWidget(this.position, this.info,this.isLoading);

  @override
  Widget build(BuildContext context)  {
    mContext = context;
    return InkWell(
      onTap: ()=>onItemClick('ACTION_ITEM_CLICK',listData[position]),
      child: buildItemView(position),
    );

  }

  Widget buildItemView(int position){
    return Container(

      height: (position==0||position==2||position==5)?140:100,
      margin: position==listData.length-1 ?EdgeInsets.only(bottom: 60,top: 10) : EdgeInsets.only(top: 10),
      child:  (position==0||position==2||position==5)?buildItemWithTitle(position):buildItemWithNoTitle(position)
    );
  }


  @override
  void onItemClick(String action, MusicListItem item) {
    print(item.typeName);

    Navigator.push(mContext, MaterialPageRoute(builder: (BuildContext context) => OnlineTypeMusicListPage(item)));

  }

  Widget buildItemWithNoTitle(int position){
    return Stack(
      children: <Widget>[

        Positioned(
          left: 10,
          top: 5,
          child: buildTypeIcon(info),
        ),
        Positioned(
          left:  100,
          top: 10,
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: <Widget>[
              Container(
                margin: EdgeInsets.only(top: 4),
                child: buildMusicName(info, 0)
              ),
              Container(
                margin: EdgeInsets.only(top: 4),
                child: buildMusicName(info,1)
              ),
              Container(
                margin: EdgeInsets.only(top: 4),
                child: buildMusicName(info, 2)
              ),
              
            ],
          ),
        )
      ],
    );

  }
  Widget buildItemWithTitle(int position){
    String title  ;
    switch(position){
      case 0:
        title = '主打榜单';
        break;
      case 2:
        title = '分类榜单';
        break;
      case 5:
        title = '媒体榜单';
        break;
    }
    return  Stack(
      children: <Widget>[
        Positioned(
          left: 10,
          top: 7,
          child:  Image.asset(
            'images/ic_online_music_list_profile_headline.png',
            width: 10.0,
            height: 17.0,
          ),
        ),
        Positioned(
          left: 25,
          top: 4,
          child: Text(title,style: TextStyle(fontSize: 17),),
        ),
        Positioned(
          left: 10,
          top: 40,
          child:  buildTypeIcon(info),
        ),
        Positioned(
          left:  100,
          top: 45,
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: <Widget>[

              Container(
                  margin: EdgeInsets.only(top: 4),
                  child: buildMusicName(info,0)
              ),
              Container(
                margin: EdgeInsets.only(top: 4),
                child: buildMusicName(info, 1),
              ),
              Container(
                  margin: EdgeInsets.only(top: 4),
                  child: buildMusicName(info,2)
              )
            ],
          )
        )
      ],
    );
  }
  Widget buildTypeIcon(MusicListInfo info){

      if(isLoading){
        return Image.asset('images/default_cover.png',width: 80,height: 80,fit: BoxFit.fill,);
      }else{
        if(info==null){
          return Image.asset('images/default_cover.png',width: 80,height: 80,fit: BoxFit.fill,);
        }
        return Image.network(info.billboard.picS260,width: 80,height: 80,fit: BoxFit.fill,);

      }


  }
  Widget buildMusicName(MusicListInfo info,int index){
    String text='';
        if(isLoading){
          text = "加载中...";
        }else{
          if(info==null){
            text = "加载中...";
          }else{
            text = info.songList[index].title;
            if(text.length>19){
              text = text.substring(0,17)+'...';
            }
          }
        }
        return Text('${index+1}.${text}',maxLines: 1,overflow: TextOverflow.ellipsis,textAlign: TextAlign.start,style: TextStyle(fontSize: 14,color: Colors.black54),);
  }

}
