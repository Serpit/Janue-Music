package com.gdou.jianyue.flutterplugin;

import android.util.Log;

import com.gdou.jianyue.event.MusicEvent;
import com.gdou.jianyue.utils.RxBus;

import io.flutter.plugin.common.EventChannel;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MusicPlugin implements EventChannel.StreamHandler {

    private static final String TAG = MusicEvent.class.getSimpleName();
    @Override
    public void onListen(Object o, EventChannel.EventSink eventSink) {
        RxBus.getInstance().toObservable(MusicEvent.class).subscribe(new Observer<MusicEvent>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(MusicEvent musicEvent) {
                int position = musicEvent.getPosition();
                Log.d(TAG,"音乐位置 : "+position);
                eventSink.success(position);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });



    }

    @Override
    public void onCancel(Object o) {

    }
}
