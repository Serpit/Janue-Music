package com.gdou.jianyue.music.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.gdou.jianyue.Constants.Constants;
import com.gdou.jianyue.R;
import com.gdou.jianyue.music.adapter.CommentListAdapter;
import com.gdou.jianyue.music.bean.Comment;
import com.gdou.jianyue.music.constraint.CommentContract;
import com.gdou.jianyue.music.presenter.CommentPresenter;

import java.util.ArrayList;
import java.util.List;

/*评论界面*/
public class CommentActivity extends BaseActivity implements CommentContract.View, View.OnClickListener {

    private long mSongId;
    private RecyclerView rv_commentlist;
    private CommentContract.Presenter mPresnter;
    private CommentListAdapter adapter;
    private List<Comment> listData;
    private EditText et_comment;
    private TextView bt_submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        Intent intent = getIntent();
        setTitle("评论区");
        mSongId =  intent.getLongExtra(Constants.SONG_ID,0);
        findViews();
        initData();
        initViews();
    }

    @Override
    public void initViews() {
        rv_commentlist.setAdapter(adapter);
        bt_submit.setOnClickListener(this);
    }

    @Override
    public void findViews() {
        rv_commentlist = findViewById(R.id.rv_commentlist);
        et_comment = findViewById(R.id.et_comment);
        bt_submit = findViewById(R.id.bt_submit);
    }

    @Override
    public void initData() {
        mPresnter = new CommentPresenter(this);
        mPresnter.loadComment(mSongId);
        listData = new ArrayList<>();
        adapter = new CommentListAdapter(listData,this);
        rv_commentlist.setAdapter(adapter);
    }

    @Override
    public void onCommentLoad(List<Comment> data) {
        listData.addAll(data);
        adapter.updateList(listData);
    }

    @Override
    public void onCommentSuccess(String msg,List<Comment> data) {
        listData.clear();
        listData.addAll(data);
        adapter.updateList(listData);
    }

    @Override
    public void onCommentFaild(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_submit:
                mPresnter.comment(mSongId,et_comment.getText().toString());
                et_comment.setText("");
                break;
        }
    }
}
