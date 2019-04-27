import 'dart:async';
import 'package:shared_preferences/shared_preferences.dart';

class SpUtils{
  static SpUtils _instance;
  static Future<SpUtils> get instance async{
    return await getInstance();
  }

  static SharedPreferences _spf;


  SpUtils._();//私有构造


  Future<SpUtils> _init() async{
    _spf = await SharedPreferences.getInstance();
  }
  static Future<SpUtils> getInstance() async{
    if(_instance == null){
      _instance = new SpUtils._();
      return _instance._init();
    }
    return _instance;
  }


  static bool _beforeCheck(){
    if(_spf == null){
      return true;
    }
    return false;
  }

  bool hasKey(String key){
    Set keys = getKeys();
    return keys.contains(key);
  }


  Set<String> getKeys(){
    if(_beforeCheck()) return null;
    return _spf.getKeys();
  }


  get(String key) {
    if (_beforeCheck()) return null;
    return _spf.get(key);
  }

  getString(String key) {
    if (_beforeCheck()) return null;
    return _spf.getString(key);
  }

  Future<bool> putString(String key, String value) {
    if (_beforeCheck()) return null;
    return _spf.setString(key, value);
  }

  bool getBool(String key) {
    if (_beforeCheck()) return null;
    return _spf.getBool(key);
  }
  double getDouble(String key) {
    if (_beforeCheck()) return null;
    return _spf.getDouble(key);
  }
  List<String> getStringList(String key) {
    return _spf.getStringList(key);
  }

  Future<bool> putInt(String key,int value){
    if (_beforeCheck()) return null;
    return _spf.setInt(key, value);
  }


  dynamic getDynamic(String key) {
    if (_beforeCheck()) return null;
    return _spf.get(key);
  }



  Future<bool> remove(String key) {
    if (_beforeCheck()) return null;
    return _spf.remove(key);
  }

  Future<bool> clear() {
    if (_beforeCheck()) return null;
    return _spf.clear();
  }
}