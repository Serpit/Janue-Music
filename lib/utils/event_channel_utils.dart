import 'dart:async';
import 'package:flutter/services.dart';
 onNativeCall(String channel,  onData(dynamic event)){
  var eventChannel = EventChannel(channel);
  eventChannel.receiveBroadcastStream().listen(onData);
}