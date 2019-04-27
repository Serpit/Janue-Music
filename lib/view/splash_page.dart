import 'package:flutter/material.dart';
import 'package:jian_yue/viewmodel/splash_provide.dart';
import 'package:provide/provide.dart';
import 'base.dart';
import 'package:dartin/dartin.dart';
import 'dart:async';
import 'package:jian_yue/utils/widget_utils.dart';
import 'package:jian_yue/constant/constant.dart';
import 'home_page.dart';
class SplashPage extends PageProvideNode{


  @override
  Widget buildContent(BuildContext context) {
    Constants.DisplayWidth = MediaQuery.of(context).size.width;
    Constants.DisplayHeight = MediaQuery.of(context).size.height;
    return SpalshPageContent();
  }

  SplashPage(){
    final provide = inject<SplashProvide>();
    mProviders.provideValue(provide);
  }
}


class SpalshPageContent extends StatelessWidget{
  SplashProvide mProvide;
  BuildContext mContext;

  Widget buildNetImage(String url){
    if(url!=null){
     return Image.network(
        url,
        width: double.infinity,
        height: double.infinity,

        fit: BoxFit.cover,
      );
    }else{
      return SizedBox(width: double.infinity,);
    }
  }



  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    mContext = context;
    mProvide = Provide.value<SplashProvide>(context);
    getImageUrl();
    timerRouteStrat();
    return Material(
      child: Scaffold(

        body: Center(
          child: Provide<SplashProvide>(
          builder: ((BuildContext context, Widget child, SplashProvide value)  {
            return Column(
              mainAxisSize: MainAxisSize.max,
              children: <Widget>[
                Expanded(
                  child:  Container(
                    child:
                    buildNetImage(value.response),
                    color: Colors.red,
                  ),
                  flex: 5,
                ),
                Expanded(
                  child:  Container(
                    child: SizedBox(
                      width: double.infinity,
                      child: Center(
                        child:Row(
                          children: <Widget>[
                          Image.asset("images/ic_launcher.png",width: 50,height: 50,),
                          Container(
                            child: Text(Constants.APP_NAME, style: TextStyle(fontSize: 30.0,),),
                            margin: EdgeInsets.only(left: 15),
                          )
                        ],
                         mainAxisAlignment: MainAxisAlignment.center,
                        ),
                      ),
                    ),
                    color: Colors.white,
                  ),
                  flex: 1,
                ),
              ],
            );
          }),
          ),
        ),
      )
    );

  }

  void getImageUrl(){
    final s = mProvide.getSplashUrl().listen((_){
      //Toast.show("success", mContext,type: Toast.SUCCESS);
    },onError: (e){
      dispatchFailure( e);
    });

    mProvide.addSubscription(s);
  }

  void timerRouteStrat(){
    Timer(Duration(milliseconds: 3000), (){
      Navigator.of(mContext).pushAndRemoveUntil(new MaterialPageRoute(
          builder: (BuildContext context) => HomePage()), (//跳转到主页
          Route route) => route == null);

    });
  }
}