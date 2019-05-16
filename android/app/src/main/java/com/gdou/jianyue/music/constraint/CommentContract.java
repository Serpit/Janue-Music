package com.gdou.jianyue.music.constraint;

import com.gdou.jianyue.music.bean.Comment;
import com.gdou.jianyue.music.bean.CommentResponse;
import com.gdou.jianyue.net.bean.BaseResponse;

import java.util.List;

import io.reactivex.Observable;


public interface CommentContract {
    interface View{
        void onCommentLoad(List<Comment> data);
        void onCommentSuccess(String msg,List<Comment> data);
        void onCommentFaild(String msg);
    }

    interface Model{
        Observable<CommentResponse> loadComment(long songid);
        Observable<CommentResponse> comment(long songid, String comment, long userid);
    }

    interface Presenter{
        void loadComment(long songid);
        void comment(long songid,String comment);
    }

}
