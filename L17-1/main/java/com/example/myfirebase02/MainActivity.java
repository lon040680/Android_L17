package com.example.myfirebase02;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    //宣告變數
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("project_1");
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
    }

    public void onClick(View view) {
        String str = editText.getText().toString().trim();
        switch (view.getId()){
            case R.id.button:
                //myRef.setValue(str);
                myRef.child("ch_1").setValue(str);
                myRef.child("ch_2").setValue(str+"2");
                break;
            case R.id.button2:
                //myRef = database.getReference(); 直接處理刪除
                //myRef.removeValue(); 全刪 包含父節點
                myRef.child("ch_1").removeValue(); //刪除 子節點(Node)
                //說明 :
                //刪除ch_1過程如果只有它，資料就不會留著project_1父節點了 因為只有一筆資料
                //但是如果有了ch_2 就會留著 project_1
                break;
            case R.id.button3:
                //更新
                HashMap<String, Object> hm = new HashMap<>();
                hm.put("ch_2", (str+"更新的")); //想更新的節點 與 值
                myRef.updateChildren(hm);
                break;
        }
    }
}