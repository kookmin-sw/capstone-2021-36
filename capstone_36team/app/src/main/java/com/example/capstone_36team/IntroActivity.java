package com.example.capstone_36team;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;


public class IntroActivity extends AppCompatActivity {


    private OnboardingAdapter onboardingAdapter;
    private LinearLayout layoutOnboardingIndicators;
    private MaterialButton buttonOnboardingAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        layoutOnboardingIndicators = findViewById(R.id.layoutOnboardingIndicators);
        buttonOnboardingAction = findViewById(R.id.buttonOnboardingAction);

        setupOnboardingItems();

        ViewPager2 onboardingViewPager = findViewById(R.id.onboardingViewPager);
        onboardingViewPager.setAdapter(onboardingAdapter);

        setupOnboardingIndicators();
        setCurrentOnboardingIndicator(0);

        onboardingViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentOnboardingIndicator(position);
            }
        });

        buttonOnboardingAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onboardingViewPager.getCurrentItem() + 1 < onboardingAdapter.getItemCount()) {
                    onboardingViewPager.setCurrentItem(onboardingViewPager.getCurrentItem() + 1);
                } else{
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
            }
        });
    }

    private void setupOnboardingItems() {

        List<OnboardingItem> onboardingItems = new ArrayList<>();

        OnboardingItem itemOne = new OnboardingItem();
        itemOne.setTitle("HousePedia에 오신걸 환영합니다");
        itemOne.setDescription("당신의 삶의 편리함을 추구합니다\n집의 모든정보가 당신의 손끝에 있습니다");
        itemOne.setImage(R.drawable.houseicon);

        OnboardingItem itemTwo = new OnboardingItem();
        itemTwo.setTitle("가구 및 물건 정리");
        itemTwo.setDescription("가구들의 구조 및 집안의 모든 물건의 위치\n 수량정보를 저장해놓을 수 있습니다");
        itemTwo.setImage(R.drawable.furniture);

        OnboardingItem itemThree = new OnboardingItem();
        itemThree.setTitle("신선한 식재료 관리");
        itemThree.setDescription("식재료 수량 관리가 가능하고\n음식 유통기한에 맞춰 알려주는 알람기능");
        itemThree.setImage(R.drawable.food2);

        OnboardingItem itemFour = new OnboardingItem();
        itemFour.setTitle("가족 공유 기능");
        itemFour.setDescription("집안의 모든물건을\n 가족과 동시에 관리가 가능합니다");
        itemFour.setImage(R.drawable.family);

        onboardingItems.add(itemOne);
        onboardingItems.add(itemTwo);
        onboardingItems.add(itemThree);
        onboardingItems.add(itemFour);

        onboardingAdapter = new OnboardingAdapter(onboardingItems);

    }

    private void setupOnboardingIndicators() {
        ImageView[] indicators = new ImageView[onboardingAdapter.getItemCount()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8,0,8,0);
        for (int i = 0; i < indicators.length; i++){
            indicators[i] = new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(
                    getApplicationContext(),
                    R.drawable.onboarding_indicator_inactive
            ));
            indicators[i].setLayoutParams(layoutParams);
            layoutOnboardingIndicators.addView(indicators[i]);
        }
    }

    private void setCurrentOnboardingIndicator(int index){
        int childCount = layoutOnboardingIndicators.getChildCount();
        for(int i = 0; i < childCount; i++){
            ImageView imageView = (ImageView) layoutOnboardingIndicators.getChildAt(i);
            if(i == index) {
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(), R.drawable.onboarding_indicator_active)
                );
            } else{
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(), R.drawable.onboarding_indicator_inactive)
                );
            }
        }
        if(index == onboardingAdapter.getItemCount() - 1) {
            buttonOnboardingAction.setText("Start");
        } else {
            buttonOnboardingAction.setText("Next");
        }
    }
}