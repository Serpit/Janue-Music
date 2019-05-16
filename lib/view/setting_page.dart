import 'package:flutter/material.dart';
import 'package:jian_yue/constant/constant.dart';
import 'package:jian_yue/utils/method_channel_utils.dart';
import 'package:rxdart/rxdart.dart';
class SettingPage extends StatefulWidget{
  @override
  State<StatefulWidget> createState() {
    return _SettingPageState();
  }
}

class _SettingPageState extends State<SettingPage>{
  bool _valuePlay ;
  bool _valueDownload ;


  set valuePlay(bool value) {

     setState(() {
       _valuePlay = value;
     });
  }
  set valueDownload(bool value) {

    setState(() {
      _valueDownload = value;
    });
  }

  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    loadSettingInfo();
  }

  @override
  Widget build(BuildContext context) {
    //
    return Material(
      child: Scaffold(
        appBar: AppBar(title: Text('设置'),),
        body: Column(
          children: <Widget>[
            Container(
              width: double.infinity,
              height: 70,
              child: Stack(
                children: <Widget>[
                  Positioned(
                    right: 10,
                    child:  Switch(
                      value: _valuePlay,
                      onChanged:switchNetPlay,
                      activeColor: Colors.red,
                      activeTrackColor:Colors.red[200],
                      inactiveThumbColor:Colors.red,
                      inactiveTrackColor: Colors.grey,
                    ),
                  ),
                  Positioned(
                    top: 15,
                    left: 20,
                    child: Text('使用移动网络播放',style: TextStyle(fontSize: 15),),
                  )
                ],
              ),
            ),
            Container(
              width: double.infinity,
              height: 70,
              child: Stack(
                children: <Widget>[
                  Positioned(
                    right: 10,
                    child:  Switch(
                      value: _valueDownload,
                      onChanged: switchNetDownload,
                      activeColor: Colors.red,
                      activeTrackColor:Colors.red[200],
                      inactiveThumbColor:Colors.red,
                      inactiveTrackColor: Colors.grey,
                    ),
                  ),
                  Positioned(
                    top: 15,
                    left: 20,
                    child: Text('使用移动网络下载',style: TextStyle(fontSize: 15),),
                  )
                ],
              ),
            )
          ],
        ),
      ),
    );
  }

  switchNetPlay(bool value){

    valuePlay = !_valuePlay;
    print("play :${_valuePlay}");
    callNativeMethod(Constants.SETTING_CHANNEL, 'setMobilePlayEnable',params: {Constants.SP_kEY_NET_PLAY:value});
  }
  switchNetDownload(bool value){

    valueDownload = !_valueDownload;
    print("download :${_valueDownload}");
    callNativeMethod(Constants.SETTING_CHANNEL, 'setMobileDownLoadEnable',params: {Constants.SP_kEY_NET_DOWNLOAD:value});
  }


  loadSettingInfo(){
    Future future = callNativeMethod(Constants.SETTING_CHANNEL, 'getSettingInfo');
    Observable.fromFuture(future).doOnListen((){
      _valuePlay = true;
      _valueDownload = true;
    }).listen((info){
      valuePlay = info[Constants.SP_kEY_NET_PLAY];
      valueDownload = info[Constants.SP_kEY_NET_DOWNLOAD];
    });

  }
}