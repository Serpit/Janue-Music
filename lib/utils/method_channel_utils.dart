
import 'package:jian_yue/constant/constant.dart';
import 'dart:async';
import 'package:flutter/services.dart';


Future<Null> callNativeMethod(String channel,String nativeMethod,{Map params}) async {
  var platform =  MethodChannel(channel);
  await platform.invokeMethod(nativeMethod,params);
}