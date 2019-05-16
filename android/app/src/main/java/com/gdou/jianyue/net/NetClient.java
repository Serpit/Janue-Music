package com.gdou.jianyue.net;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.webkit.MimeTypeMap;

import com.gdou.jianyue.BuildConfig;
import com.gdou.jianyue.JanueMusicApplication;
import com.gdou.jianyue.utils.FileUtils;
import com.gdou.jianyue.utils.ObjectUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetClient {

    private static NetClient instance = new NetClient();
    private static Retrofit retrofit;
    private NetClient(){

        OkHttpClient.Builder client = new OkHttpClient.Builder().connectTimeout(5000, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true);
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        /*if(BuildConfig.DEBUG){
            //显示日志
            logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        }else {
            logInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }*/

        client.addInterceptor(logInterceptor);

        client.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request newRequest = chain.request().newBuilder()
                        .removeHeader("User-Agent")
                        .addHeader(
                                "User-Agent",
                                "Mozilla/5.0 ( Windows; U; Windows NT 5.1; en-US; rv:0.9.4 )").build() ;
                return chain.proceed(newRequest);
            }
        }) ;

        retrofit = new Retrofit.Builder()
                .client(client.build())
                .baseUrl("http://tingapi.ting.baidu.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public static NetClient getInstance(){
        if (ObjectUtils.isNotNull(instance)){
            return instance;
        }else {
            return new NetClient();
        }


    }


    public <T> T createService(Class<T> service){
        return retrofit.create(service);
    }

    public Observable<Boolean> downloadMusic(String url,String filename){
        try {
            Uri uri = Uri.parse(url);
            DownloadManager.Request request = new DownloadManager.Request(uri);
            request.setTitle(filename);
            request.setDescription("正在下载");
            request.setDestinationInExternalPublicDir(FileUtils.getRelativeMusicDir(),filename);
            request.setMimeType(MimeTypeMap.getFileExtensionFromUrl(url));
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
            request.setAllowedOverRoaming(false); // 不允许漫游
            DownloadManager downloadManager = (DownloadManager) JanueMusicApplication.getContext().getSystemService(Context.DOWNLOAD_SERVICE);
            long id = downloadManager.enqueue(request);
            return Observable.just(true);
        } catch (Throwable th) {
            th.printStackTrace();
            return Observable.just(false);
        }

    }
}
