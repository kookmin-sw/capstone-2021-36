package com.example.capstone_36team.ui.notifications;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.capstone_36team.GlobalVariable;
import com.example.capstone_36team.LoginActivity;
import com.example.capstone_36team.MainActivity;
import com.example.capstone_36team.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.CLIPBOARD_SERVICE;

public class NotificationsFragment extends Fragment {
    private Button btn_partin;
    TextView name;
    TextView email;
    ImageView image;
    Button signOut;
    Button revoke;
    Button familyCode;

    private NotificationsViewModel notificationsViewModel;
    private Dialog dialog03;
    private String uid;
    DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();
    DatabaseReference conditionRef;// = mDatabase.child("UserDB").child(uid);
    GlobalVariable familydata;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        final TextView textView = root.findViewById(R.id.family_view);
        final Button btn_partin = root.findViewById(R.id.bt_Partin);
        name= root.findViewById(R.id.id_Nname);
        email = root.findViewById(R.id.id_Email);
        image = root.findViewById(R.id.image_Google);
        signOut= root.findViewById(R.id.bt_Logout);
        revoke = root.findViewById(R.id.bt_Revoke);
        familyCode = root.findViewById(R.id.bt_FamilyCode);
        familydata = (GlobalVariable)getActivity().getApplicationContext();
        uid = familydata.getuId();
        conditionRef = mDatabase.child("UserDB").child(uid);

        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(getContext());

        if(signInAccount != null){
            name.setText(signInAccount.getDisplayName());
            email.setText(signInAccount.getEmail());
            Glide.with(this).load(signInAccount.getPhotoUrl()).into(image);
        }

        dialog03 = new Dialog(getActivity());       // Dialog ?????????
        dialog03.requestWindowFeature(Window.FEATURE_NO_TITLE); // ????????? ??????
        dialog03.setContentView(R.layout.search_dialog);
        btn_partin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog03();

            }
        });

        familyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("?????? ??????", familydata.getfamilyId());
                clipboard.setPrimaryClip(clip);
                openFamilyCodeFrag();

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

    @Override //onResume ???????????? ????????? ?????????
    public void onResume() {
        super.onResume();
        FragmentActivity activity = getActivity();
        if (activity != null) {
            ((MainActivity) activity).setActionBarTitle("My Info");
        }
    }

    public void showDialog03(){ //??????????????? ??????
        Button searchbutton = dialog03.findViewById(R.id.pbutton);
        searchbutton.setText("??????");
        dialog03.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog03.show(); // ??????????????? ?????????
        EditText edittext_searchname = (EditText) dialog03.findViewById(R.id.edittext_searchname);

        // *????????? ???: findViewById()??? ??? ?????? -> ?????? ????????? ??????????????? ????????? ????????? ??????.



        searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //?????? ???????????????
                ///////edittext_searchname??? ????????? ?????????//////
                Log.d("??????", "???????????? " + edittext_searchname);
                String newfamilycode = edittext_searchname.getText().toString();
                Map<String, Object> familyUpdates = new HashMap<>();
                familyUpdates.put("family", newfamilycode);

                conditionRef.updateChildren(familyUpdates);
                familydata.setfamilyId(newfamilycode);
                dialog03.dismiss();
            }
        });
        Button nosearchbutton = dialog03.findViewById(R.id.nbutton);
        nosearchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //?????? ???????????????
                Log.d("??????", "???????????????");
                // ????????? ?????? ??????
                dialog03.dismiss();

            }
        });
    }

    private void openRevokeFrag() { //dialog ?????? ??????

        RevokeFragment rvk = new RevokeFragment();
        rvk.setTargetFragment(this, 0);
        rvk.show(getFragmentManager(), "revoke");

    }

    private void openFamilyCodeFrag() { //dialog ?????? ??????

        FamilyCodeFragment fc = new FamilyCodeFragment();
        fc.setTargetFragment(this, 0);
        fc.show(getFragmentManager(), "familyCode");

    }


}