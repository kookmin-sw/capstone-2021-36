package com.example.capstone_36team.ui.dashboard;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.capstone_36team.FoodActivity;
import com.example.capstone_36team.GlobalVariable;
import com.example.capstone_36team.MainActivity;
import com.example.capstone_36team.R;
import com.example.capstone_36team.ui.home.dialogfragment;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class DashboardFragment extends Fragment {


    private DashboardViewModel dashboardViewModel;
    private String category;
    private Dialog dialog04;
    private Dialog dialog03;
    private String userid;
    private String familyid;
    DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();
    //DatabaseReference userRef = mDatabase.child("UserDB").child(userid);
    DatabaseReference conditionRef;
    HashMap<String, String> fridgemap = new HashMap<String, String>();
    GlobalVariable familydata;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        final Button btn_add_food = root.findViewById(R.id.btn_add_food);
        dialog04 = new Dialog(getActivity());       // Dialog ?????????
        dialog04.requestWindowFeature(Window.FEATURE_NO_TITLE); // ????????? ??????
        dialog04.setContentView(R.layout.search_dialog);
        dialog03 = new Dialog(getActivity());       // Dialog ?????????
        dialog03.requestWindowFeature(Window.FEATURE_NO_TITLE); // ????????? ??????
        dialog03.setContentView(R.layout.search_result);
        familydata = (GlobalVariable)getActivity().getApplicationContext();
        userid = familydata.getuId();
        familyid = familydata.getfamilyId();
        conditionRef = mDatabase.child("HomeDB").child(familyid).child("fridgelist");


        btn_add_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("??????", "????????? ??????????????? ?????? ?????? ??????");
                openDialog();
            }
        });

        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        //????????? ??? ?????????
        //final String[] LIST_MENU = {"?????????1", "?????????2", "?????????3"} ; //????????? ?????? ??????
        ArrayAdapter<String>listViewAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1
        );
        final ListView listview = root.findViewById(R.id.listview_food);
        listview.setAdapter(listViewAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() { //???????????? ?????????
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String fridgename = fridgemap.get((String)parent.getItemAtPosition(position));
                category = (String)parent.getItemAtPosition(position);
                Intent intent = new Intent(getActivity(), FoodActivity.class);
                intent.putExtra("fridgename",fridgename);
                intent.putExtra("userid",userid);
                intent.putExtra("familyid", familyid);
                intent.putExtra("category",category);
                startActivity(intent);

            }
        });

        conditionRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String fridgename = dataSnapshot.child("fridgename").getValue(String.class);
                listViewAdapter.add(fridgename);
                fridgemap.put(fridgename, dataSnapshot.getKey());

                Log.d("MainActivity", "ChildEventListener - onChildChanged : ");
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.d("MainActivity", "ChildEventListener - onChildChanged : " + dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d("MainActivity", "ChildEventListener - onChildRemoved : " + dataSnapshot.getKey());
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Log.d("MainActivity", "ChildEventListener - onChildMoved" + s);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("MainActivity", "ChildEventListener - onCancelled" + databaseError.getMessage());
            }
        });
//        userRef.addChildEventListener(new ChildEventListener()
//        {
//            @Override
//            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
//                //adapter.add(snapshot.getValue(String.class));
//                familyid = snapshot.getValue(String.class);
//                conditionRef = mDatabase.child("HomeDB").child(familyid).child("fridgelist");
//                listViewAdapter.clear();
//                fridgemap.clear();
//                mkChildEventListener(listViewAdapter);
//
//            }
//
//            @Override
//            public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
//                familyid = snapshot.getValue(String.class);
//                conditionRef = mDatabase.child("HomeDB").child(familyid).child("fridgelist");
//                listViewAdapter.clear();
//                fridgemap.clear();
//                mkChildEventListener(listViewAdapter);
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull @NotNull DatabaseError error) {
//
//            }
//        });

        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                category = fridgemap.get((String)parent.getItemAtPosition(position));
                showDialog04();


                return true;
            }
        });




        return root;
    }
    @Override //onResume ???????????? ????????? ?????????
    public void onResume() {
        super.onResume();
        FragmentActivity activity = getActivity();
        if (activity != null) {
            ((MainActivity) activity).setActionBarTitle("Food");
        }
    }



//    public void mkChildEventListener(ArrayAdapter<String> adapter){
//        conditionRef.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                String fridgename = dataSnapshot.child("fridgename").getValue(String.class);
//                adapter.add(fridgename);
//                fridgemap.put(fridgename, dataSnapshot.getKey());
//
//                Log.d("MainActivity", "ChildEventListener - onChildChanged : ");
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//                Log.d("MainActivity", "ChildEventListener - onChildChanged : " + dataSnapshot);
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//                Log.d("MainActivity", "ChildEventListener - onChildRemoved : " + dataSnapshot.getKey());
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//                Log.d("MainActivity", "ChildEventListener - onChildMoved" + s);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Log.d("MainActivity", "ChildEventListener - onCancelled" + databaseError.getMessage());
//            }
//        });
//    }

    private void openDialog() { //dialog ?????? ??????

        DialogFragment myDialogFragment = new dialogfragment();

        myDialogFragment.setTargetFragment(this, 0);

        myDialogFragment.show(getFragmentManager(), "Search Filter");

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            String newroomname = data.getStringExtra("roomname");
            String refridgeid = UUID.randomUUID().toString();
            Map<String, Object> taskMap = new HashMap<String, Object>();
            taskMap.put("fridgename", newroomname);


            conditionRef.child(refridgeid).setValue(taskMap);
        }
    }

    public void showDialog04(){ //??????????????? ??????
        dialog04.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog04.show();
        EditText editText = dialog04.findViewById(R.id.edittext_searchname);
        Button button1 = dialog04.findViewById(R.id.pbutton);
        Button button2 = dialog04.findViewById(R.id.nbutton);
        button1.setText("??????");
        button2.setText("??????");
        editText.setHint(category);


        // *????????? ???: findViewById()??? ??? ?????? -> ?????? ????????? ??????????????? ????????? ????????? ??????.


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //???????????? ??????????????????
                /////////////////////////DB??????//////////////////////
                dialog04.dismiss();

            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //?????? ??????????????????
                showDialog03();

                dialog04.dismiss();
            }
        });

    }
    public void showDialog03(){ //??????????????? ??????
        dialog03.show();


        Button button1 = dialog03.findViewById(R.id.btn_search_result);
        TextView textView = dialog03.findViewById(R.id.text_search_result);
        button1.setText("???");
        textView.setText(category + "???\n" + category + "?????? ????????? ?????? ????????????????????????? ");
        textView.setTextSize(20);

        // *????????? ???: findViewById()??? ??? ?????? -> ?????? ????????? ??????????????? ????????? ????????? ??????.


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //??? ??????????????????



                ////////////////////////DB ??????////////////////////////
                dialog03.dismiss();

            }
        });


    }








}