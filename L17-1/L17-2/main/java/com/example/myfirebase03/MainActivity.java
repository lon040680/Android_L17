package com.example.myfirebase03;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    //宣告變數
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference Ref_light = database.getReference("light");
    DatabaseReference Ref_msg = database.getReference("message");
    //要在Firebase 上面新增 light : false 節點 不然下面抓不到會出錯

    TextView light_show;
    ImageButton mylight;
    Context context;
    boolean state;
    EditText message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context=this;
        light_show = findViewById(R.id.light_show);
        mylight=findViewById(R.id.MyLight);
        message=findViewById(R.id.message);

        //ImageButton 加按鍵監聽 點擊圖片
        mylight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                state = !state;
                if(state){
                    // for Firebase
                    Ref_light.setValue(true);
                    //App 換圖
                    mylight.setImageResource(R.drawable.lighton);
                    light_show.setText("電燈已啟動...");
                }else {
                    // for Firebase
                    Ref_light.setValue(false);
                    //App 換圖
                    mylight.setImageResource(R.drawable.lightoff);
                    light_show.setText("電燈關閉...");
                }
            }
        });

        // Read from the Firebase 取得 Firebase 資料
        Ref_light.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                 state = dataSnapshot.getValue(Boolean.class); //注意這裡要大寫Boolean. 規定
                if(state){
                    // for Firebase
                    Ref_light.setValue(true);
                    //App 換圖
                    mylight.setImageResource(R.drawable.lighton);
                    light_show.setText("電燈已開啟...");
                } else {
                    // for Firebase
                    Ref_light.setValue(false);
                    //App 換圖
                    mylight.setImageResource(R.drawable.lightoff);
                    light_show.setText("電燈關閉...");
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                light_show.setText("無法連線...");
            }
        });

        //開啟自動獲取文字欄位部分
        Ref_msg.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String mm = snapshot.getValue(String.class);
                message.setText(mm);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                message.setText("無法連線...");
            }
        });
    }

    //修改文字部分
    public void onclick(View view) {
        String ss = message.getText().toString().trim();
        Ref_msg.setValue(ss);
    }
}