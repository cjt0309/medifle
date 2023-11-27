package com.example.medifiles;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class VideoListActivity extends AppCompatActivity {

    private ListView videoListView;
    private ArrayList<VideoItem> videoList;
    private FirebaseDatabase database;
    private FirebaseAuth auth;
    private VideoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);

        videoListView = findViewById(R.id.videoListView);
        videoList = new ArrayList<>();
        adapter = new VideoAdapter(this, videoList);
        videoListView.setAdapter(adapter);

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            String currentUserUid = currentUser.getUid();

            DatabaseReference videosRef = database.getReference("patients/" + currentUserUid + "/videos");
            videosRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String videoName = snapshot.getKey();
                        String videoLength = snapshot.child("length").getValue(String.class); // 변경된 부분
                        // 썸네일 이미지는 그냥 video로 설정
                        VideoItem videoItem = new VideoItem("video", videoName, videoLength);
                        videoList.add(videoItem);
                    }
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // 에러 처리를 여기에 추가하세요.
                }
            });
        } else {
            Intent loginIntent = new Intent(VideoListActivity.this, Login_Activity.class);
            startActivity(loginIntent);
            finish();
        }
    }
}
