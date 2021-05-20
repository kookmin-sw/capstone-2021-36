package com.example.capstone_36team.ui.notifications;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.capstone_36team.LoginActivity;
import com.example.capstone_36team.R;
import com.example.capstone_36team.RoomActivity;
import com.example.capstone_36team.ui.home.dialogfragment;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class NotificationsFragment extends Fragment {
    private Button btn_partin;
    TextView name;
    TextView email;
    ImageView image;
    Button signOut;
    Button revoke;

    private NotificationsViewModel notificationsViewModel;
    private Dialog dialog03;
    private String uid = "testuid";
    DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();
    DatabaseReference conditionRef = mDatabase.child("UserDB").child(uid);

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        final TextView textView = root.findViewById(R.id.text_notifications);
        final Button btn_partin = root.findViewById(R.id.bt_Partin);
        name= root.findViewById(R.id.id_Nname);
        email = root.findViewById(R.id.id_Email);
        image = root.findViewById(R.id.image_Google);
        signOut= root.findViewById(R.id.bt_Logout);
        revoke = root.findViewById(R.id.bt_Revoke);

        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(getContext());

        if(signInAccount != null){
            name.setText(signInAccount.getDisplayName());
            email.setText(signInAccount.getEmail());
            Glide.with(this).load(signInAccount.getPhotoUrl()).into(image);
        }

        dialog03 = new Dialog(getActivity());       // Dialog 초기화
        dialog03.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        dialog03.setContentView(R.layout.search_dialog);
        btn_partin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog03();

            }
        });

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity().getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        revoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRevokeFrag();
            }
        });

        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
    public void showDialog03(){ //다이얼로그 함수
        Button searchbutton = dialog03.findViewById(R.id.pbutton);
        searchbutton.setText("참여");
        dialog03.show(); // 다이얼로그 띄우기
        EditText edittext_searchname = (EditText) dialog03.findViewById(R.id.edittext_searchname);

        // *주의할 점: findViewById()를 쓸 때는 -> 앞에 반드시 다이얼로그 이름을 붙여야 한다.



        searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //검색 클릭했을때
                ///////edittext_searchname에 텍스트 입력됨//////
                Log.d("확인", "가족변경 " + edittext_searchname);
                Map<String, Object> familyUpdates = new HashMap<>();
                familyUpdates.put("family", edittext_searchname.getText().toString());

                conditionRef.updateChildren(familyUpdates);
                dialog03.dismiss();
            }
        });
        Button nosearchbutton = dialog03.findViewById(R.id.nbutton);
        nosearchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //취소 클릭했을때
                Log.d("확인", "취소클릭됨");
                // 원하는 기능 구현
                dialog03.dismiss();

            }
        });
    }

    private void openRevokeFrag() { //dialog 관련 함수

        RevokeFragment rvk = new RevokeFragment();
        rvk.setTargetFragment(this, 0);
        rvk.show(getFragmentManager(), "revoke");

    }

}