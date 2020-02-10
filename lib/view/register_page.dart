import 'package:flutter/material.dart';
import 'dart:ui';
import 'package:dartin/dartin.dart';
import 'package:provide/provide.dart';
import 'base.dart';
import 'package:jian_yue/viewmodel/register_page_provide.dart';
import 'package:flutter/cupertino.dart';
import 'package:jian_yue/utils/toast.dart';

class RegisterPage extends PageProvideNode{


  @override
  Widget buildContent(BuildContext context) {
    return _RegisterPageContent();
  }

  RegisterPage(){
    final provide = inject<RegisterPageProvide>();
    mProviders.provideValue(provide);
  }
}

class _RegisterPageContent extends StatefulWidget{
  @override
  State<StatefulWidget> createState() {
    return _RegisterPageContentState();
  }
}

class _RegisterPageContentState extends State<_RegisterPageContent>{
  RegisterPageProvide mProvide;
  @override
  Widget build(BuildContext context) {
    mProvide = Provide.value<RegisterPageProvide>(context);
    return Material(
      child: Scaffold(

        body: Center(
          child: buildContent(),
        ),
      ),
    );
  }

  Widget buildContent(){
    return Provide<RegisterPageProvide>(
        builder: (BuildContext context,Widget child ,RegisterPageProvide value) {
          return Center(
            child:  Container(
                height: 350,
                margin: EdgeInsets.only(left: 20,right: 20),
                child:Column(
                  children: <Widget>[
                    Image.asset('images/ic_launcher.png',width: 80,height: 80,),
                    TextField(
                      onChanged: (value)=>mProvide.username = value,
                      decoration: InputDecoration(
                          labelText: "用户名",
                          hintText: "您的用户名",
                          prefixIcon: Icon(Icons.person)
                      ),
                    ),
                    TextField(
                      onChanged:  (value)=>mProvide.password = value,
                      decoration: InputDecoration(
                          prefixIcon: Icon(Icons.lock),
                          labelText: "密码",
                          hintText: "您的登录密码",
                          hintStyle: TextStyle(color: Colors.grey, fontSize: 13.0)
                      ),
                      obscureText: true,
                    ),
                    CupertinoButton(
                      onPressed: register,
                      pressedOpacity: 0.8,
                      child: Container(
                        margin: EdgeInsets.only(top: 30),
                        alignment: Alignment.center,
                        width: 300,
                        height: 48,
                        decoration: BoxDecoration(
                            borderRadius: BorderRadius.all(Radius.circular(30.0)),
                            gradient: LinearGradient(colors: [
                              Colors.red[300],
                              Colors.red[700],
                            ]),
                            boxShadow: [
                              BoxShadow(color: Colors.red[100],
                                  offset: Offset(0.0, 4.0),
                                  blurRadius: 13.0
                              )
                            ]),
                        child: buildSearchChild(value),
                      ),
                    )
                  ],
                )
            ),
          );
        });


  }

  register(){
    if(mProvide.username==null || mProvide.username==''){
      Toast.show('用户名不能为空', context);
      return;
    }
    if(mProvide.password==null || mProvide.password==''){
      Toast.show('密码不能为空', context);
      return;
    }
    final s = mProvide.register().doOnListen((){

    }).doOnDone((){
      Toast.show(mProvide.msg, context);
      if(mProvide.isRegistSuccess){
        Navigator.pop(context);
      }

    }).doOnCancel((){
    }).listen((_){

    },onError: (e){

    });
    mProvide.addSubscription(s);
  }
  Widget buildSearchChild(RegisterPageProvide value){
    if(value.isLoading){
      return const CircularProgressIndicator();
    }else{
      return const FittedBox(
        fit: BoxFit.scaleDown,
        child: const Text('Regist',
          textAlign: TextAlign.center,
          overflow: TextOverflow.fade,
          style: const TextStyle(fontWeight: FontWeight.bold, fontSize: 16.0, color: Colors.white),),
      );
    }
  }
}