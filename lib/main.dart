import 'package:flutter/material.dart';
import 'constant/constant.dart';
import 'package:flutter/services.dart';
import 'config/application.dart';
import 'event/theme_change_event.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'dart:async';
import 'config/theme.dart';
import 'package:event_bus/event_bus.dart';
import 'di/app_module.dart';
import 'package:jian_yue/view/splash_page.dart';
void main() async {
  await init();
  int themeIndex = await getDefaultTheme();

  runApp(new MyApp(themeIndex));
}

Future<int> getDefaultTheme() async {
  // 从shared_preferences中获取上次切换的主题
  SharedPreferences sp = await SharedPreferences.getInstance();
  int themeIndex = sp.getInt("themeIndex");
  print(themeIndex);
  if(themeIndex != null) {
    return themeIndex;
  }
  return 0;
}


class MyApp extends StatefulWidget {
  int themeIndex;

  MyApp(this.themeIndex);

  @override
  State<StatefulWidget> createState() {
    return _MyAppState();
  }
}

class _MyAppState extends State<MyApp>{

  Color themeColor;
  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    Application.eventBus = new EventBus();
    themeColor = themeList[widget.themeIndex];
    this.registerThemeEvent();

  }

  void registerThemeEvent() {
    Application.eventBus.on<ThemeChangeEvent>().listen((ThemeChangeEvent onData)=> this.changeTheme(onData));
  }

  void changeTheme(ThemeChangeEvent onData) async {
    SharedPreferences sp = await SharedPreferences.getInstance();
    sp.setInt("themeIndex", onData.themeIndex);

    setState(() {

      themeColor = themeList[onData.themeIndex];
    });
  }



  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
        title: Constants.APP_NAME,
        theme: new ThemeData(

            primaryColor: themeColor
        ),
        home: SplashPage()
    );
  }


  @override
  void dispose() {
    // TODO: implement dispose
    super.dispose();

    Application.eventBus.destroy();
  }
}

