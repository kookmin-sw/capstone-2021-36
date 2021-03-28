package com.example.capstone_36team.ui.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.capstone_36team.R;
import com.example.capstone_36team.ui.home.dialogfragment;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        final Button btn_add_food = root.findViewById(R.id.btn_add_food);
        btn_add_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("확인", "냉장고 프래그먼트 확인 버튼 눌림");
                openDialog();
            }
        });

        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }
    private void openDialog() { //dialog 관련 함수

        DialogFragment myDialogFragment = new dialogfragment();

        myDialogFragment.setTargetFragment(this, 0);

        myDialogFragment.show(getFragmentManager(), "Search Filter");

    }
}