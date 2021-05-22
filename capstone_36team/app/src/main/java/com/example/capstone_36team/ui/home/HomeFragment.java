package com.example.capstone_36team.ui.home;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.capstone_36team.MainActivity;
import com.example.capstone_36team.R;
import com.example.capstone_36team.RoomActivity;

import static android.view.Gravity.CENTER;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    private Dialog dialog04;
    private String category;
    private Dialog dialog03;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final Button btn_add_place = root.findViewById(R.id.btn_add_place);

        dialog04 = new Dialog(getActivity());       // Dialog 초기화
        dialog04.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        dialog04.setContentView(R.layout.search_dialog);
        dialog03 = new Dialog(getActivity());       // Dialog 초기화
        dialog03.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        dialog03.setContentView(R.layout.search_result);
        btn_add_place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("확인", "버튼 클릭됨");
                openDialog();

            }
        });


        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        final String[] LIST_MENU = {"방1", "방2", "방3"} ; //데이터 담는 부분
        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, LIST_MENU);
        final ListView listview = root.findViewById(R.id.listview_place);
        listview.setAdapter(listViewAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() { //리스트뷰 클릭시
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String category = LIST_MENU[position];

                Intent intent = new Intent(getActivity(), RoomActivity.class);
                intent.putExtra("category",category);
                startActivity(intent);

            }
        });
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                category = LIST_MENU[position];
                Toast.makeText(getActivity(), category, Toast.LENGTH_SHORT).show();

                showDialog04();

                return true;
            }
        });



        return root;




    }

    @Override //onResume 주기일때 타이틀 바꿔줌
    public void onResume() {
        super.onResume();
        FragmentActivity activity = getActivity();
        if (activity != null) {
            ((MainActivity) activity).setActionBarTitle("Room");
        }
    }

    private void openDialog() { //dialog 관련 함수

        DialogFragment myDialogFragment = new dialogfragment();

        myDialogFragment.setTargetFragment(this, 0);

        myDialogFragment.show(getFragmentManager(), "Search Filter");

    }

    public void showDialog04(){ //다이얼로그 함수
        dialog04.show();
        dialog04.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        EditText editText = dialog04.findViewById(R.id.edittext_searchname);
        Button button1 = dialog04.findViewById(R.id.pbutton);
        Button button2 = dialog04.findViewById(R.id.nbutton);
        button1.setText("변경");
        button2.setText("삭제");
        editText.setHint(category);


        // *주의할 점: findViewById()를 쓸 때는 -> 앞에 반드시 다이얼로그 이름을 붙여야 한다.


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /////////////////////////DB변경//////////////////////
                dialog04.dismiss();

            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //취소 클릭하였을때
                showDialog03();

                dialog04.dismiss();
            }
        });

    }
    public void showDialog03(){ //다이얼로그 함수

        dialog03.show();
        dialog03.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button button1 = dialog03.findViewById(R.id.btn_search_result);
        TextView textView = dialog03.findViewById(R.id.text_search_result);
        button1.setText("네");
        textView.setText(category + "와\n" + category + "안의 목록을\n 정말 삭제하시겠습니까? ");
        textView.setGravity(CENTER);
        textView.setTextSize(20);

        // *주의할 점: findViewById()를 쓸 때는 -> 앞에 반드시 다이얼로그 이름을 붙여야 한다.


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //네 선택하였을떄



                ////////////////////////DB 삭제////////////////////////
                dialog03.dismiss();

            }
        });


    }








}