package com.gdou.jianyue.music.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gdou.jianyue.R;
import com.gdou.jianyue.databasetable.PlayingMusic;
import com.gdou.jianyue.music.MusicPlayList;
import com.gdou.jianyue.utils.DisPlayUtils;
import com.gdou.jianyue.utils.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

public class PlayingLitsAdapter extends RecyclerView.Adapter<PlayingLitsAdapter.ViewHolder> {

    private List<PlayingMusic> list;
    private Context context;
    private OnHandleItemClickListener listener;





    public PlayingLitsAdapter(List<PlayingMusic> list, Context context) {
        this.list = list;
        this.context = context;
        Log.d("PlayingLitsAdapter","" + MusicPlayList.getInstance().getCurrentIndex());
    }


    public void setListener(OnHandleItemClickListener listener) {
        this.listener = listener;
    }

    public void updateList(List<PlayingMusic> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_playing_list,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        String title = list.get(position).getTitle();
        holder.tv_songName.setText(title);
        holder.tv_artist.setText(" — "+list.get(position).getAuthor());
        if (position== MusicPlayList.getInstance().getCurrentIndex()){
            Log.d("PlayingLitsAdapter","position:  " + position);
            RelativeLayout.LayoutParams layoutParams= (RelativeLayout.LayoutParams) holder.tv_songName.getLayoutParams();
            layoutParams.leftMargin = DisPlayUtils.dip2px(context,42);
            holder.tv_songName.setLayoutParams(layoutParams);
            holder.iv_playing.setVisibility(View.VISIBLE);
            holder.tv_songName.setTextColor(Color.RED);
            holder.tv_artist.setTextColor(Color.RED);
        }else {
            RelativeLayout.LayoutParams layoutParams= (RelativeLayout.LayoutParams) holder.tv_songName.getLayoutParams();
            layoutParams.leftMargin = DisPlayUtils.dip2px(context,20);
            holder.tv_songName.setLayoutParams(layoutParams);
            holder.iv_playing.setVisibility(View.GONE);
            holder.tv_songName.setTextColor(Color.BLACK);
            holder.tv_artist.setTextColor(Color.GRAY);
        }
        if (ObjectUtils.isNull(listener)){
            return;
        }
        holder.itemView.setTag(holder.tv_songName);
        holder.itemView.setTag(holder.tv_artist);
        holder.itemView.setOnClickListener(view -> {
            listener.onItemClick(position);
            notifyDataSetChanged();
            //updateList(MusicPlayList.getInstance().getList());
        });
        holder.iv_delete_item.setOnClickListener(view -> {
            listener.onDeleteClick(position);
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_artist,tv_songName;
        public ImageView iv_playing,iv_delete_item;
        public View rootView;
        public ViewHolder(View itemView) {
            super(itemView);
            rootView = itemView;
            iv_delete_item = itemView.findViewById(R.id.iv_delete_item);
            tv_artist = itemView.findViewById(R.id.tv_artists);
            tv_songName = itemView.findViewById(R.id.tv_songname);
            iv_playing = itemView.findViewById(R.id.iv_playing);
        }
    }


    public interface OnHandleItemClickListener{
        void onItemClick(int position);
        void onDeleteClick(int position);
    }
}
