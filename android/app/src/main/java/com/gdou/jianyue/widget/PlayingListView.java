package com.gdou.jianyue.widget;


import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.gdou.jianyue.Constants.Constants;
import com.gdou.jianyue.R;
import com.gdou.jianyue.music.MusicPlayList;
import com.gdou.jianyue.music.adapter.PlayingLitsAdapter;
import com.gdou.jianyue.utils.DisPlayUtils;
import com.gdou.jianyue.utils.ObjectUtils;
import com.gdou.jianyue.utils.SPUtils;

public class PlayingListView extends PopupWindow {



    private View rootView;
    private TextView tv_playMode;
    private ImageView iv_delete;
    private RecyclerView rv_playingList;
    private PlayingLitsAdapter adapter;
    private OnDeletePlayingListListener onDeletePlayingListListener;
    private PlayingLitsAdapter.OnHandleItemClickListener onHandleItemClickListener;
    public PlayingListView(Activity context) {
        super(context);

        rootView = LayoutInflater.from(context).inflate(R.layout.layout_playinglist_view, null);
        rv_playingList = rootView.findViewById(R.id.rv_playing_list);
        tv_playMode = rootView.findViewById(R.id.tv_play_mode);
        iv_delete = rootView.findViewById(R.id.iv_delete);
        init(context);
        initView(context);

    }

    public void setOnDeletePlayingListListener(OnDeletePlayingListListener onDeletePlayingListListener) {
        this.onDeletePlayingListListener = onDeletePlayingListListener;
    }

    public void setOnHandleItemClickListener(PlayingLitsAdapter.OnHandleItemClickListener onHandleItemClickListener) {
        this.onHandleItemClickListener = onHandleItemClickListener;
        adapter.setListener(onHandleItemClickListener);
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
        rv_playingList.scrollToPosition(MusicPlayList.getInstance().getCurrentIndex());
        String playmode = "";
        switch (SPUtils.getString(Constants.SP_KEY_PLAY_MODE)){
            case Constants.PLAY_MODE_LOOP:
                playmode = "列表循环";
                break;
            case Constants.PLAY_MODE_ONE:
                playmode = "单曲循环";
                break;
            case Constants.PLAY_MODE_SHUFFLE:
                playmode = "随机播放";
                break;
        }
        tv_playMode.setText(playmode);
    }

    private void initView(Context context) {

        adapter = new PlayingLitsAdapter(MusicPlayList.getInstance().getList(),context);

        rv_playingList.setAdapter(adapter);
    }
    public void updateList(){
        adapter.notifyDataSetChanged();
    }



    private void init(Context context){
        //设置SelectPicPopupWindow的View
        this.setContentView(rootView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(1000);
        this.setFocusable(true);
        this.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bg_playinglist));
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        rootView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = rootView.findViewById(R.id.pop_layout).getTop();
                int y=(int) event.getY();
                if(event.getAction()==MotionEvent.ACTION_UP){
                    if(y<height){
                        dismiss();
                    }
                }
                return true;
            }
        });


        iv_delete.setOnClickListener(view -> {
            if (ObjectUtils.isNotNull(onDeletePlayingListListener)){
                onDeletePlayingListListener.clearPlayingList();
            }
        });
    }


    public interface OnDeletePlayingListListener{
        void clearPlayingList();
    }
}
