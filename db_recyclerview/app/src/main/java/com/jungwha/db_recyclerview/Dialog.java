package com.jungwha.db_recyclerview;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Dialog extends AppCompatDialogFragment {

    private EditText ed_place_plus;
    private String name;
    @NonNull
    @Override
    public android.app.Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_plus, null);
        builder.setView(view)
                .setTitle("장소를 입력해주세요")
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }
                })
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        name = ed_place_plus.getText().toString();
                        FirebaseDatabase database = FirebaseDatabase.getInstance();



                        //나중에 구글 연결하면 유저이름 받아와서 Username에 넣어줘
                        DatabaseReference data_name = database.getReference("Username");



                        data_name.child("room").child(name).setValue(name);


                    }
                });
        ed_place_plus = view.findViewById(R.id.ed_place_plus);
        return builder.create();
    }

}
