import 'package:flutter/material.dart';
import 'constant/constant.dart';
import 'package:flutter/services.dart';
import 'dart:async';

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

