package com.example.capstone_36team;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
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




   }
}