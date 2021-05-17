package com.example.capstone_36team;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ModifyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);
        EditText EditTextModifyname = (EditText)findViewById(R.id.editTextModifyname);
        EditText EditTextModifycount = (EditText)findViewById(R.id.editTextModifycount);

        EditTextModifyname.setText("원래 물품 이름 DB에서 받아오기");
        EditTextModifycount.setText("원래 물품 수량 DB에서 받아오기");

        Button btn_modify_yes = (Button)findViewById(R.id.btn_modify_yes);
        Button btn_modify_no = (Button) findViewById(R.id.btn_modify_no);
        btn_modify_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //확인버튼 클릭시
                Intent intent = new Intent(getApplicationContext(), FurnitureActivity.class);
                /////////////////////////수정된 사항 DB에 반영!!!!//////////////////
                startActivity(intent);
            }
        });
        btn_modify_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //취소버튼 클릭시
                Intent intent = new Intent(getApplicationContext(), FurnitureActivity.class);
                startActivity(intent);
            }
        });



   }
}