package com.gdou.jianyue.music.presenter;

import android.text.TextUtils;

import com.gdou.jianyue.Constants.Constants;
import com.gdou.jianyue.music.bean.CommentResponse;
import com.gdou.jianyue.music.constraint.CommentContract;
import com.gdou.jianyue.music.model.CommentModel;
import com.gdou.jianyue.utils.SPUtils;

import io.reactivex.disposables.Disposable;

public class CommentPresenter implements CommentContract.Presenter {
    private CommentContract.Model model;
    private CommentContract.View view;
    public CommentPresenter(CommentContract.View view) {
        model = new CommentModel();
        this.view = view;
    }

    @Override
    public void loadComment(long songid) {
        Disposable disposable = model.loadComment(songid).subscribe(commentResponse -> {
           if (commentResponse.getState().equals("success")){
               view.onCommentLoad(commentResponse.getData());
           }else {

           }
        });
    }

    @Override
    public void comment(long songid, String comment) {
        if (!SPUtils.getBoolean(Constants.SP_KEY_IS_LOGIN)){
            view.onCommentFaild("您还没有登陆，请前去登陆");
        }
        if (TextUtils.isEmpty(comment)){
            view.onCommentFaild("内容不能为空");
            return;
        }
        Disposable disposable = model.comment(songid,comment,Long.parseLong(SPUtils.getString(Constants.SP_kEY_USER_ID)))
                .subscribe(baseResponse -> {
                   if (baseResponse.getState().equals("success")){
                       view.onCommentSuccess(baseResponse.getMsg(),baseResponse.getData());
                   }else {
                       view.onCommentFaild(baseResponse.getMsg());
                   }
                });
    }
}
