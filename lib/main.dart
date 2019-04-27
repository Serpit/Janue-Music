import 'package:flutter/material.dart';
import 'constant/constant.dart';
import 'package:flutter/services.dart';
import 'dart:async';
import 'utils/permission_utils.dart';
import 'di/app_module.dart';
import 'package:jian_yue/view/splash_page.dart';
void main() async {
  await init();
  runApp(new MyApp());
}

class MyApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: Constants.APP_NAME,
      theme: new ThemeData(
       
        primarySwatch: Colors.red,
      ),
      home: SplashPage()
    );
  }
}

class MyHomePage extends StatefulWidget {
  MyHomePage({Key key}) : super(key: key);

  @override
  _MyHomePageState createState() => new _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  static const platform = const MethodChannel('janeMusic.flutter.io/startActivity');

  @override
  Widget build(BuildContext context) {
    requestPermission();
    return new Scaffold(
      appBar: AppBar(
        title: Text(Constants.APP_NAME),
      ),
      body: Center(
        child: Column(
          children: <Widget>[
            FlatButton(onPressed: _startMainMusicActivity, child: Text("跳转"))
          ],
        ),
      ),
    );
  }

  Future<Null> _startMainMusicActivity() async {
    /*Map<String, String> map = Map();
    map['songName'] ='光年之外';
    map['artists'] = '邓紫棋';
    await platform.invokeMethod('startActivity',map);*/



  }
}
