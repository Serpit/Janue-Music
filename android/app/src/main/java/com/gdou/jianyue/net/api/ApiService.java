package com.gdou.jianyue.net.api;

import com.gdou.jianyue.Constants.Constants;
import com.gdou.jianyue.music.bean.Comment;
import com.gdou.jianyue.music.bean.CommentResponse;
import com.gdou.jianyue.music.bean.SongResultBean;
import com.gdou.jianyue.net.bean.BaseResponse;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
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

    @POST(Constants.BASE_URL+"/collect")
    Observable<BaseResponse> collectMusic(@Body Map<String,String> params);

    @GET(Constants.BASE_URL+"/cancelCollection/{songid}/{userid}")
    Observable<BaseResponse> cancelCollectMusic(@Path("songid") long songid,@Path("userid") String userid);

    @Streaming
    @GET
    Observable<ResponseBody> downloadMusic(@Url String url);

    @GET(Constants.BASE_URL+"/getCommentList/{songid}")
    Observable<CommentResponse> getCommentList(@Path("songid") long songid);

    @POST(Constants.BASE_URL+"/comment")
    Observable<CommentResponse> comment(@Body Map<String,String> params);
}
