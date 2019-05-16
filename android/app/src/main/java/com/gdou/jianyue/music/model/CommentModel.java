package com.gdou.jianyue.music.model;

import com.gdou.jianyue.music.bean.CommentResponse;
import com.gdou.jianyue.music.constraint.CommentContract;
import com.gdou.jianyue.net.NetClient;
import com.gdou.jianyue.net.api.ApiService;
import com.gdou.jianyue.net.bean.BaseResponse;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CommentModel implements CommentContract.Model {
    @Override
    public Observable<CommentResponse> loadComment(long songid) {
        return NetClient.getInstance().createService(ApiService.class).getCommentList(songid).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<CommentResponse> comment(long songid, String content, long userid) {
        Map<String,String> map = new HashMap<>();
        map.put("songid",songid+"");
        map.put("userid",userid+"");
        map.put("content",content);
        return NetClient.getInstance().createService(ApiService.class).comment(map).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


}
