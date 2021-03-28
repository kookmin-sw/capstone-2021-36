package com.example.capstone_36team.ui.dashboard;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

import com.example.capstone_36team.R;
import com.example.capstone_36team.ui.home.dialogfragment;

public class dialogfragment2 extends DialogFragment {
    private EditText put_text;
    @Override

    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_layout, null);


        builder.setView(view)

                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {



                    }
                })

                .setPositiveButton("추가", new DialogInterface.OnClickListener() {


                    @Override

                    public void onClick(DialogInterface dialog, int id) {

                        EditText rfname = (EditText)getDialog().findViewById(R.id.put_text);

                        String Rfname = rfname.getText().toString();
                        //Rfname ---> 추가할 냉장고 이름
                        // Rfname이용해서 이곳에 코드 추가하면 DB에 추가 가능
                    }

                });





        return builder.create();

    }

}
