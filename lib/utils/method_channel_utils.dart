
import 'package:jian_yue/constant/constant.dart';
import 'dart:async';
import 'package:flutter/services.dart';


Future callNativeMethod(String channel,String nativeMethod,{Map params}) async {
  var platform =  MethodChannel(channel);
  return await platform.invokeMethod(nativeMethod,params);
}