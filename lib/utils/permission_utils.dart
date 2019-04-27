import 'package:flutter/material.dart';
import 'dart:async';
import 'package:simple_permissions/simple_permissions.dart';

requestPermission() async {
  //Permission permission = Permission.WriteExternalStorage;

  await SimplePermissions.requestPermission(Permission.WriteExternalStorage);
  await SimplePermissions.requestPermission(Permission.ReadExternalStorage);
  //print("permission request result is " + res.toString());
}