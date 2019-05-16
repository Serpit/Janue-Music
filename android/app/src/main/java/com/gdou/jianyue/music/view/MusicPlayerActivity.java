package com.gdou.jianyue.music.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.gdou.jianyue.Constants.Constants;
import com.gdou.jianyue.JanueMusicApplication;
import com.gdou.jianyue.R;
import com.gdou.jianyue.databasetable.PlayingMusic;
import com.gdou.jianyue.music.MusicPlayList;

import com.gdou.jianyue.music.adapter.PlayingLitsAdapter;
import com.gdou.jianyue.music.constraint.MainMusicContract;
import com.gdou.jianyue.music.controller.MusicController;
import com.gdou.jianyue.music.controller.MusicControllerImpl;
import com.gdou.jianyue.music.presenter.MainMusicPresenter;
import com.gdou.jianyue.music.service.MusicService;
import com.gdou.jianyue.others.GlideBlurformation;
import com.gdou.jianyue.utils.AnimationUtils;
import com.gdou.jianyue.utils.DisPlayUtils;
import com.gdou.jianyue.utils.ObjectUtils;
import com.gdou.jianyue.utils.SPUtils;
import com.gdou.jianyue.utils.TimeUtils;
import com.gdou.jianyue.widget.MusicControlBar;
import com.gdou.jianyue.widget.MusicToolBar;
import com.gdou.jianyue.widget.PlayingListView;
import com.gdou.jianyue.widget.RotateImageView;
import com.gdou.share.ShareProxy;
import com.hw.lrcviewlib.LrcDataBuilder;
import com.hw.lrcviewlib.LrcRow;
import com.hw.lrcviewlib.LrcView;

import java.io.File;
import java.util.List;

public class MusicPlayerActivity extends BaseActivity implements View.OnClickListener,
        MusicControlBar.OnMusicControlEvent, MainMusicContract.View,
        MusicController.OnMusicStateChangeListener, MusicController.OnMusicPositionChangeListener,
        MusicController.OnMusicChangeListener, PlayingListView.OnDeletePlayingListListener {

    private  MusicToolBar mMusicToolBar;
    String mSongName,mArtists;
    private long mSongId;
    private PlayingListView playingListView;
    private MusicControlBar mMusicControlBar;
    private LrcView mLrcView;
    private ImageView iv_music_main_bg,iv_play_mode,iv_play_list,iv_collect,iv_download,iv_more,iv_comment;
    private List<LrcRow> mLrcRows;
    private RotateImageView iv_album;
    private SeekBar mMusicPositionBar;
    private MusicService.MusicBinder mBinder;
    private TextView tv_current_positon,tv_music_duration;
    private boolean isPlaying ,isCollect;
    private MainMusicContract.Presenter mPresenter;
    private MusicPlayList mMusicPlayList = MusicPlayList.getInstance();
    private MusicController mController = MusicControllerImpl.getInstance();
    private String currentMode = SPUtils.getString(Constants.SP_KEY_PLAY_MODE);

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_layout);
        Bundle bundle = getIntent().getExtras();
        mSongId = bundle.getLong(Constants.SONG_ID);
        mSongName = bundle.getString(Constants.SONG_NAME);
        mArtists = bundle.getString(Constants.ARTISTS);

        findViews();
        initData();
        initViews();
        mBinder = ((JanueMusicApplication) getApplication()).getMusicBinder();
    }




    @Override
    public void findViews(){
        mMusicToolBar = findViewById(R.id.toolbar);
        iv_album = findViewById(R.id.iv_album);
        mMusicControlBar = findViewById(R.id.musicControlBarId);
        mLrcView = findViewById(R.id.au_lrcView);
        iv_music_main_bg = findViewById(R.id.iv_music_main_bg);
        iv_comment = findViewById(R.id.iv_comment);
        mMusicPositionBar = findViewById(R.id.pb_music_position_bar);
        tv_current_positon = findViewById(R.id.tv_current_positon);
        tv_music_duration = findViewById(R.id.tv_music_duration);
        iv_play_mode = findViewById(R.id.iv_play_mode);
        iv_play_list = findViewById(R.id.iv_play_list);
        playingListView = new PlayingListView(this);
        iv_collect = findViewById(R.id.iv_collect);
        iv_more = findViewById(R.id.iv_more);
        iv_download = findViewById(R.id.iv_download);
    }
    @Override
    public void initViews() {
        playingListView.setOnDeletePlayingListListener(this);
        playingListView.setOnHandleItemClickListener(new PlayingLitsAdapter.OnHandleItemClickListener() {
            @Override
            public void onItemClick(int position) {

                mBinder.switchMusic(mMusicPlayList.getList().get(position).getSongId());
            }

            @Override
            public void onDeleteClick(int position) {
                mMusicPlayList.deleteMusic(mMusicPlayList.getList().get(position).getSongId());
                mBinder.nextMusic();
            }
        });
        mMusicPositionBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Log.d("onProgressChanged","onProgressChanged: "+i);
                if (mController.isParpred()){
                    int time = mController.getMusicDuration();
                    int position =  time/100 * i;
                    mLrcView.seekLrcToTime((long) position);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress = seekBar.getProgress();
              //  int time = mController.getMusicDuration();
                //int position = progress*time/100;
                mController.seekTo(progress);
                mLrcView.seekLrcToTime((long) progress);
            }
        });
        setPlayMode(currentMode);
        mMusicToolBar.setSongName(mSongName);
        mMusicToolBar.setArtists(mArtists);
        mMusicToolBar.setShareOnClickListener(this);
        mMusicControlBar.setIsPlaying(isPlaying);
        mMusicControlBar.setOnMusicControlEvent(this);
        iv_comment.setOnClickListener(this);
        iv_album.setOnClickListener(this);
        iv_play_mode.setOnClickListener(this);
        iv_play_list.setOnClickListener(this);
        iv_collect.setOnClickListener(this);
        iv_download.setOnClickListener(this);
        iv_more.setOnClickListener(this);
        mLrcView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AnimationUtils.fadeOut(mLrcView);
                AnimationUtils.fadeIn(iv_album);

            }
        });
        mLrcView.getLrcSetting()
                .setTimeTextSize(40)//时间字体大小
                .setSelectLineColor(Color.parseColor("#ffffff"))//选中线颜色
                .setSelectLineTextSize(25)//选中线大小
                .setHeightRowColor(getResources().getColor(R.color.color_FFFFFF))//高亮字体颜色
                .setNormalRowColor(getResources().getColor(R.color.color_A3A3A3))//正常字体颜色
                .setNormalRowTextSize(DisPlayUtils.sp2px(this, 17))//正常行字体大小
                .setHeightLightRowTextSize(DisPlayUtils.sp2px(this, 17))//高亮行字体大小
                .setTrySelectRowTextSize(DisPlayUtils.sp2px(this, 17))//尝试选中行字体大小
                .setTimeTextColor(Color.parseColor("#ffffff"))//时间字体颜色
                .setTrySelectRowColor(Color.parseColor("#55ffffff"));//尝试选中字体颜色

        mLrcView.commitLrcSettings();

    }

    @Override
    public void initData(){
        isPlaying = mController.isPalying();

        mPresenter = new MainMusicPresenter();
        mPresenter.attachView(this);
        mPresenter.loadPlayingMusicInfo(mSongId);
        if (SPUtils.getBoolean(Constants.SP_KEY_IS_LOGIN)){
            mPresenter.queryIsCollect(mSongId);
        }
        mController.setOnMusicStartAndStopListener(this);
        mController.setOnMusicPositionChangeListener(this);
        mController.setOnMusicChangeListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_album:
                AnimationUtils.fadeIn(mLrcView);
                AnimationUtils.fadeOut(iv_album);
                break;
            case R.id.iv_play_mode:
                changePlayMode();
                break;
            case R.id.iv_play_list:
                playingListView.updateList();
                playingListView.showAtLocation(this.findViewById(R.id.rootlayout), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL,0,0);
                break;
            case R.id.iv_share:
                ShareProxy.getInstance().shareText(this,"musicName: "+mSongName);
                break;
            case R.id.iv_collect:
                changeCollect();
                break;
            case R.id.iv_download:
                mPresenter.downloadMusic(mSongId);
                break;
            case R.id.iv_comment:
                Intent intent = new Intent(MusicPlayerActivity.this,CommentActivity.class);
                intent.putExtra(Constants.SONG_ID,mSongId);
                startActivity(intent);
                break;
            case R.id.iv_more:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

            if (mMusicPlayList.getCurMusic().getSongId()!=mSongId){
                mBinder.switchMusic(mSongId);
            }else {
                if (ObjectUtils.isNull(mMusicPlayList.getCurMusic().getDownloadLink())){
                    mBinder.switchMusic(mSongId);
                }else {
                    mBinder.startMusic();
                    mMusicPositionBar.setMax(mController.getMusicDuration());
                    tv_music_duration.setText(TimeUtils.generateTime(mController.getMusicDuration()));

                }
            }
            iv_album.startRotate();
            mMusicControlBar.setIsPlaying(true);



    }

    @Override
    public void onMusicInfoLoad(PlayingMusic music) {
        iv_album.initInAlbumImage(music.getPicLink());

        mPresenter.downloadLrcFile(music.getLrcLink(),music.getTitle());
        if(ObjectUtils.isNotNull(this)){
            Glide.with(this).load(music.getPicLink())
                    .transform(new GlideBlurformation(this),new CenterCrop())
                    .into(iv_music_main_bg);
        }

    }

    private void setPlayMode(String mode){
        switch (mode){
            case "play_mode_one":
                iv_play_mode.setBackground(getResources().getDrawable(R.drawable.ic_play_btn_one));
                break;
            case "play_mode_loop":
                iv_play_mode.setBackground(getResources().getDrawable(R.drawable.ic_play_btn_loop));
                break;
            case "play_mode_shuffle":
                iv_play_mode.setBackground(getResources().getDrawable(R.drawable.ic_play_btn_shuffle));
                break;
        }
    }
    private void changePlayMode(){
       String[] modes = {Constants.PLAY_MODE_ONE,Constants.PLAY_MODE_LOOP,Constants.PLAY_MODE_SHUFFLE};
        for (int i = 0; i < modes.length; i++) {
            if (currentMode.equals(modes[i]) ){
                if ( i<modes.length-1){
                    currentMode = modes[i+1];
                    break;
                }else {
                    currentMode = modes[0];
                    break;
                }

            }
        }
        setPlayMode(currentMode);
        mController.setPlayMode(currentMode);

    }


    @Override
    public void startAndStop() {
        if (isPlaying){
            mBinder.stopMusic();
            iv_album.stopRoate();
        }else {
            iv_album.startRotate();
            mBinder.startMusic();
        }
        isPlaying = !isPlaying;
        mMusicControlBar.setIsPlaying(isPlaying);
    }

    @Override
    public void next() {
        mBinder.nextMusic();
        isPlaying = true;
        mMusicControlBar.setIsPlaying(isPlaying);

    }

    @Override
    public void last() {
        mBinder.lastMusic();
        isPlaying = true;
        mMusicControlBar.setIsPlaying(isPlaying);
    }

    @Override
    public void onLrcLoad(File file) {
        mLrcRows = new LrcDataBuilder().Build(file);
        mLrcView.setLrcData(mLrcRows);
        mLrcView.setAutomaticMoveAnimationDuration(300);
        mLrcView.setLrcViewSeekListener((currentLrcRow,currentSelectedRowTime)->{
            mController.seekTo((int) currentSelectedRowTime);
        });

    }

    @Override
    public void onMusicStart() {


    }

    @Override
    public void onMusicStop() {

    }

    @Override
    public void onMusicPositionChange(int position) {
        tv_current_positon.setText(TimeUtils.generateTime(position));
        mMusicPositionBar.setProgress(position);
        mLrcView.smoothScrollToTime((long) position);
    }

    @Override
    public void onMusicParpred() {
        mMusicPositionBar.setMax(mController.getMusicDuration());
        tv_music_duration.setText(TimeUtils.generateTime(mController.getMusicDuration()));
    }

    @Override
    public void onMusicChange(int position) {
        PlayingMusic music=   mMusicPlayList.getCurMusic();
        mSongName = music.getTitle();
        mArtists = music.getAuthor();
        mMusicToolBar.setSongName(mSongName);
        mMusicToolBar.setArtists(mArtists);
        mSongId = music.getSongId();
        onMusicInfoLoad(music);
        iv_album.startRotate();
        isPlaying = true;
        mMusicControlBar.setIsPlaying(true);
    }


    @Override
    public void clearPlayingList() {
        mMusicPlayList.clearPlayingList();
        mBinder.resetPlayer();
        finish();
    }


    private void changeCollect(){
        if (isCollect){
            iv_collect.setBackground(getResources().getDrawable(R.drawable.ic_collected));
            mPresenter.cancelCollectMusic(mSongId);

        }else {
            iv_collect.setBackground(getResources().getDrawable(R.drawable.ic_have_collected));

            mPresenter.collectMusic(mSongId);
        }

    }

    @Override
    public void onIsNotLogin() {
         Toast.makeText(this,"你还没有登陆，请前去登陆",Toast.LENGTH_SHORT).show();
        iv_collect.setBackground(getResources().getDrawable(R.drawable.ic_collected));
    }

    @Override
    public void onError(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
        iv_collect.setBackground(getResources().getDrawable(R.drawable.ic_collected));
    }

    @Override
    public void onSuccess(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
        isCollect = !isCollect;
    }

    @Override
    public void onDownloadSuccess() {
        Toast.makeText(this,"下载成功！",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDownlaodFaild(String msg) {
        Toast.makeText(this,"下载失败: "+msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onIsCollect(boolean isCollect) {
        this.isCollect = isCollect;
        if(isCollect){
            iv_collect.setBackground(getResources().getDrawable(R.drawable.ic_have_collected));
        }
    }

    @Override
    public void onRefuseDownload() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("您已设置移动网络下不可下载，请到设置中打开移动网络下载功能");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setNegativeButton("关闭", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
    }
}
