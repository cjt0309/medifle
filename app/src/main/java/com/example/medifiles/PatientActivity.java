package com.example.medifiles;

// 필요한 라이브러리 import 구문
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class PatientActivity extends AppCompatActivity {

    // 사용자 이메일을 저장하는 멤버 변수
    private String useremail;

    // RecyclerView와 관련된 변수들
    private RecyclerView recyclerView;
    private MessageAdapter messageAdapter; // 사용자 정의 어댑터
    private List<Message> messageList; // 메시지 목록을 저장하는 리스트

    // Firebase 관련 변수들
    private FirebaseUser firebaseUser; // 현재 인증된 사용자
    private DatabaseReference reference; // 데이터베이스 참조

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);

        // 전달된 Intent에서 "useremail" 키로 전달된 데이터를 추출합니다.
        Intent intent = getIntent();
        useremail = intent.getStringExtra("useremail");

        // useremail이 null이 아니면 환영 메시지를 토스트로 출력합니다.
        if (useremail != null) {
            Toast.makeText(this, "환영합니다, " + useremail + "님!", Toast.LENGTH_SHORT).show();
        }

        // RecyclerView 및 관련 변수 초기화
        recyclerView = findViewById(R.id.recyclerViewChat);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true); // 아이템을 스택에서 아래에서 위로 쌓도록 설정
        recyclerView.setLayoutManager(linearLayoutManager);

        messageList = new ArrayList<>(); // 메시지 목록을 저장하는 ArrayList 초기화
        messageAdapter = new MessageAdapter(messageList); // 메시지 어댑터 초기화
        recyclerView.setAdapter(messageAdapter); // RecyclerView에 어댑터 설정

        // Firebase 관련 변수 초기화
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser(); // 현재 인증된 사용자 가져오기
        reference = FirebaseDatabase.getInstance().getReference("chats").child(firebaseUser.getUid()); // 현재 사용자의 채팅 데이터베이스 참조

        // Firebase 데이터베이스에서 데이터 변경 감지를 위한 ValueEventListener 설정
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                messageList.clear(); // 메시지 목록 초기화

                // 데이터베이스에서 가져온 각 스냅샷을 Message 객체로 변환하여 목록에 추가
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Message message = snapshot.getValue(Message.class);
                    messageList.add(message);
                }

                messageAdapter.notifyDataSetChanged(); // 어댑터에게 데이터가 변경되었음을 알림
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // 데이터베이스 읽기에 실패한 경우 처리할 내용
            }
        });
    }

    // CardView를 클릭했을 때 실행되는 메소드
    public void onCardViewClick(View view) {
        // Chat 액티비티로 이동하는 Intent 생성
        Intent intent = new Intent(this, Chat.class);
        // "useremail" 데이터를 추가로 넣어줍니다.
        intent.putExtra("useremail", useremail);
        // Chat 액티비티로 이동
        startActivity(intent);
    }
}
