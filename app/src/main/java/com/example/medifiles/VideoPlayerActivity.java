package com.example.medifiles;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class VideoPlayerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        // Video URL을 가져옴
        Intent intent = getIntent();
        String videoUrl = intent.getStringExtra("videoUrl");

        // VideoView를 초기화하고 비디오 재생
        VideoView videoView = findViewById(R.id.videoView);
        videoView.setVideoPath(videoUrl);
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        videoView.start();
    }
}
