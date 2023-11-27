package com.example.medifiles;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide; // Glide 라이브러리를 사용하기 위해 추가

import java.util.List;

public class VideoAdapter extends ArrayAdapter<VideoItem> {
    public VideoAdapter(Context context, List<VideoItem> videoList) {
        super(context, 0, videoList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        VideoItem videoItem = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.video_list_item, parent, false);
        }

        // 이미지 뷰, 텍스트 뷰 등을 찾아서 설정
        ImageView thumbnailImageView = convertView.findViewById(R.id.thumbnailImageView);
        TextView videoNameTextView = convertView.findViewById(R.id.videoNameTextView);
        TextView videoLengthTextView = convertView.findViewById(R.id.videoLengthTextView);

        // 썸네일 이미지가 없으므로 기본 이미지 사용
        thumbnailImageView.setImageResource(R.drawable.video);

        videoNameTextView.setText(videoItem.getVideoName());
        videoLengthTextView.setText(videoItem.getVideoLength());

        // 리스트 아이템 클릭 시 동작 추가
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // VideoPlayerActivity로 전환하는 Intent 생성
                Intent intent = new Intent(getContext(), VideoPlayerActivity.class);
                // Video URL을 전달
                intent.putExtra("videoUrl", videoItem.getVideoUrl());
                // VideoPlayerActivity 시작
                getContext().startActivity(intent);
            }
        });

        return convertView;
    }
}
