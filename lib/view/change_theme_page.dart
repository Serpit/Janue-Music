import 'package:event_bus/event_bus.dart';
import 'package:jian_yue/config/application.dart';
import 'package:jian_yue/event/theme_change_event.dart';
import 'package:flutter/material.dart';
import 'package:jian_yue/config/theme.dart';
class ChangeThemePage extends StatelessWidget{
  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return Material(
      child: Scaffold(
        appBar: AppBar(title: Text('个性换肤'),),
        body: Center(
          child: Column(
            children: <Widget>[
              Container(
                margin: EdgeInsets.symmetric(horizontal: 20.0),
                child: Text('选择一款喜欢的主题',style: TextStyle(fontSize: 15),),
              ),
              Wrap(
                spacing: 8.0, // 主轴(水平)方向间距
                runSpacing: 4.0, // 纵轴（垂直）方向间距
                alignment: WrapAlignment.center, //沿主轴方向居中
                children: getThemeList(),
              ),
            ],
          )
        )
      ),
    );
  }
}


List<Widget> getThemeList(){
  
  List<Widget> list = [];
  themeList.forEach((color)=>{
    list.add(
    InkWell(
      onTap: (){
        Application.eventBus.fire(ThemeChangeEvent(themeList.indexOf(color)));
  },
      child: Container(width: 80.0, height:80.0, color: color,),
  )
  )
  });
  return list;
}
