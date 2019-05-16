import 'package:flutter/material.dart';
class Constants{
  static final String _IP = "192.168.1.102";
  static final String APP_NAME = '简悦音乐';
  static final String SPLASH_URL = "http://cn.bing.com/HPImageArchive.aspx?format=js&idx=0&n=1";
  static final String LOGIN_URL = "http://${_IP}:8899/janue_music_web/login";
  static final String REGISTER_URL = "http://${_IP}:8899/janue_music_web/register";
  static final String COLLECTION_URL = "http://${_IP}:8899/janue_music_web/getAllColletcion/";
  static final String BASE_URL = 'http://tingapi.ting.baidu.com/v1/restserver/ting';
  static final String REQUEST_METHOD = "method";
  static final String REQUEST_METHOD_QUERY = "baidu.ting.search.catalogSug";
  static final String REQUEST_METHOD_GET_LIST = "baidu.ting.billboard.billList";
  static final String REQUEST_METHOD_ARTIST_INFO = "baidu.ting.artist.getInfo";
  static final String REQUEST_METHOD_DOWNLOAD_INFO =  "baidu.ting.song.play";
  static final String PARAM_TYPE = "type";
  static final String PARAM_SIZE = "size";
  static final String PARAM_OFFSET = "offset";
  static final String PARAM_SONG_ID = "songid";
  static final String PARAM_TING_UID = "tinguid";
  static final String PARAM_QUERY = "query";
  static final String PARAM_USERNAME = "username";
  static final String PARAM_PASSWORD = "password";

  static double DisplayWidth;
  static double DisplayHeight;

   static final String SP_kEY_USER_ID = "sp_key_user_id";
   static final String SP_KEY_IS_LOGIN = "sp_key_is_login";
   static final String SP_KEY_USER_NAME = "sp_key_username";
   static final String SP_kEY_NET_PLAY = "sp_key_net_play";
   static final String SP_kEY_NET_DOWNLOAD = "sp_key_net_download";
  static final String SETTING_CHANNEL = "janeMusic.flutter.io/setting";
  static final String DATA_BASE_CHANNEL = "janeMusic.flutter.io/database";
  static final String START_ACTIVITY_CHANNEL = "janeMusic.flutter.io/startActivity";
  static final String MUSIC_CONTROL_CHANNEL = "janeMusic.flutter.io/controlMusic";
  static final String USER_CHANNEL = "janeMusic.flutter.io/user";


}