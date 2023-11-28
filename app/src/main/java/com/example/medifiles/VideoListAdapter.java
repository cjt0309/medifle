package com.example.medifiles;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.VideoViewHolder> {

    private List<String> videoList;
    private OnVideoClickListener onVideoClickListener;


    public interface OnVideoClickListener {
        void onVideoClick(String videoUrl);
    }


    public VideoListAdapter(List<String> videoList, OnVideoClickListener onVideoClickListener) {
        this.videoList = videoList;
        this.onVideoClickListener = onVideoClickListener;
    }
    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        String videoUrl = videoList.get(position);

        // 동영상 썸네일 표시
        holder.videoThumbnail.setImageURI(Uri.parse(videoUrl));

        // 동영상 제목 표시
        holder.videoTitle.setText("Video " + (position + 1));

        // CardView 클릭 이벤트 처리
        holder.cardView.setOnClickListener(v -> {
            if (onVideoClickListener != null) {
                onVideoClickListener.onVideoClick(videoUrl);
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {
        ImageView videoThumbnail;
        TextView videoTitle;
        CardView cardView;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);

            videoThumbnail = itemView.findViewById(R.id.videoThumbnail);
            videoTitle = itemView.findViewById(R.id.videoTitle);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
