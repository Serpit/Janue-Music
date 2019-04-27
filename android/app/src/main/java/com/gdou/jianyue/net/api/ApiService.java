package com.gdou.jianyue.net.api;

import com.gdou.jianyue.music.bean.SongResultBean;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface ApiService {
    @Streaming
    @GET
    Observable<ResponseBody> downloadLrc(@Url String lrcUrl);

    @GET("/v1/restserver/ting")
    Observable<SongResultBean> getSongMsg( @Query("method") String method,@Query("songid") String songid);
}
