package com.gdou.jianyue.utils;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

public class RxBus {
    private volatile static RxBus instance;
    private final Subject<Object> mBus;
    private RxBus(){
        mBus = PublishSubject.create().toSerialized();
    }

    public static RxBus getInstance(){
        if (ObjectUtils.isNull(instance)){
            synchronized (RxBus.class){
                if (ObjectUtils.isNull(instance)){
                    instance = new RxBus();
                }
            }
        }

        return instance;
    }

    public void post(Object event){
        mBus.onNext(event);
    }

    public <T> Observable<T> toObservable(final Class<T> eventType){
        return mBus.ofType(eventType);
    }


    /**
     * 判断是否有订阅者
     */
    public boolean hasObservers() {
        return mBus.hasObservers();
    }

    public void reset() {
        instance = null;
    }





}
