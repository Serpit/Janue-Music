package com.gdou.jianyue;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import com.gdou.jianyue.Constants.Constants;
import com.gdou.jianyue.music.MusicPlayList;
import com.gdou.jianyue.music.service.MusicService;
import com.gdou.jianyue.proxy.MethodChannelManager;
import com.gdou.jianyue.proxy.MethodProxy;

import java.util.Map;

import io.flutter.app.FlutterActivity;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugins.GeneratedPluginRegistrant;

public class MainActivity extends FlutterActivity {
  private static final String CHANNEL = "janeMusic.flutter.io/startActivity";
  private Bundle bundleFromFlutter = new Bundle();
  private MusicService.MusicBinder mBinder;

  ServiceConnection serviceConnection = new ServiceConnection() {
    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
      mBinder = (MusicService.MusicBinder) iBinder;
      ((JanueMusicApplication)getApplication()).setMusicBinder(mBinder);
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {

    }
  };
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    GeneratedPluginRegistrant.registerWith(this);

    MethodChannelManager.getInstance().registerMethodChannel(getFlutterView());
    bindService(new Intent(this,MusicService.class),serviceConnection,BIND_AUTO_CREATE);
  }


  @Override
  protected void onStop() {
    super.onStop();
    MusicPlayList.getInstance().saveCurIndex();
  }



  @Override
  protected void onDestroy() {
    super.onDestroy();
    unbindService(serviceConnection);
  }


}
