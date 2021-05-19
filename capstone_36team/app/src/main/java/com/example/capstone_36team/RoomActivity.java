package com.example.capstone_36team;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


public class RoomActivity extends AppCompatActivity {
    private ImageView image;
    private ImageView image2;
    private ImageView image3;
    private ImageView image4;
    private Dialog dialog03;
    private Dialog dialog04;
    private Dialog dialog06;
    private boolean emdfhr = false;
    private static  final String IMAGEVIEW_TAG = "드래그 이미지";
    private String family_name = "family1";
    DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();
    DatabaseReference conditionRef = mDatabase.child("HomeDB").child(family_name).child("room1");





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        image = (ImageView) findViewById(R.id.image);
        image2 = (ImageView) findViewById(R.id.image2);
        image3 = (ImageView) findViewById(R.id.image3);
        image4 = (ImageView) findViewById(R.id.image4);
        ImageView image5 = (ImageView) findViewById(R.id.image5);
        ImageView image6 = (ImageView) findViewById(R.id.image6);
        ImageView image7 = (ImageView) findViewById(R.id.image7);
        ImageView image8 = (ImageView) findViewById(R.id.image8);
        ImageView image9 = (ImageView) findViewById(R.id.image9);
        ImageView image10 = (ImageView) findViewById(R.id.image10);
        dialog03 = new Dialog(RoomActivity.this);       // Dialog 초기화
        dialog03.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        dialog03.setContentView(R.layout.search_dialog);
        dialog04 = new Dialog(RoomActivity.this);       // Dialog 초기화
        dialog04.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        dialog04.setContentView(R.layout.search_result);
        dialog06 = new Dialog(RoomActivity.this);       // Dialog 초기화
        dialog06.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        dialog06.setContentView(R.layout.search_dialog);






        MyTouchListener myTouchListener = new MyTouchListener();


        float image2_x = (float) 0.0;
        float image2_y = (float) 1422.0;
        float image4_x = (float) 0.0;
        float image4_y = (float) 1615.0;
        float image1_x = (float) 193.0;
        float image1_y =  (float) 1422.0;
        float image3_x = (float) 193.0;
        float image3_y = (float) 1422.0;
        float image6_x = (float) 386.0;
        float image6_y = (float) 1422.0;
        float image5_x = (float) 386.0;
        float image5_y = (float) 1615.0;
        float image8_x = (float) 579.0;
        float image8_y = (float) 1422.0;
        float image7_x = (float) 579.0;
        float image7_y = (float) 1615.0;
        float image10_x = (float) 772.0;
        float image10_y = (float) 1422.0;
        float image9_x = (float) 772.0;
        float image9_y = (float) 1615.0;









        if(image.getY() < 1500){

            image.setOnTouchListener(new MyTouchListener());} //가구 이미지 mytouchlistner에 연결

        if(image2.getY() < 1500){

            image2.setOnTouchListener(new MyTouchListener());}
        if(image3.getY() < 1500){

            image3.setOnTouchListener(new MyTouchListener());}
        if(image4.getY() < 1500){

            image4.setOnTouchListener(new MyTouchListener());}
        if(image5.getY() < 1500){

            image5.setOnTouchListener(new MyTouchListener());}
        if(image6.getY() < 1500){

            image6.setOnTouchListener(new MyTouchListener());}
        if(image7.getY() < 1500){

            image7.setOnTouchListener(new MyTouchListener());}
        if(image8.getY() < 1500){

            image8.setOnTouchListener(new MyTouchListener());}
        if(image9.getY() < 1500){

            image9.setOnTouchListener(new MyTouchListener());}
        if(image10.getY() < 1500){

            image10.setOnTouchListener(new MyTouchListener());}



    }
    private final class MyTouchListener implements View.OnTouchListener {
        private View view;
        private float startXvalue;
        private float startYvalue;
        private boolean first = true;
        public boolean onTouch(View v, MotionEvent motionEvent) {
            this.view = v;
            int oldXvalue;
            int oldYvalue;
            int parentWidth = ((ViewGroup)v.getParent()).getWidth();    // 부모 View 의 Width
            int parentHeight = ((ViewGroup)v.getParent()).getHeight();



            if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                // 뷰 누름
                oldXvalue = (int) motionEvent.getX();
                oldYvalue = (int) motionEvent.getY();
                //삭제를 위한 코드
                startXvalue = v.getX();
                startYvalue = v.getY();



                Log.d("viewTest", "oldXvalue : "+ oldXvalue + " oldYvalue : " + oldYvalue);    // View 내부에서 터치한 지점의 상대 좌표값.
                Log.d("viewTest", "v.getX() : "+v.getX());    // View 의 좌측 상단이 되는 지점의 절대 좌표값.
                Log.d("viewTest", "v.getY() : "+v.getY());
                Log.d("viewTest", "RawX : " + motionEvent.getRawX() +" RawY : " + motionEvent.getRawY());    // View 를 터치한 지점의 절대 좌표값.
                Log.d("viewTest", "v.getHeight : " + v.getHeight() + " v.getWidth : " + v.getWidth());    // View 의 Width, Height

            }else if(motionEvent.getAction() == MotionEvent.ACTION_MOVE){
                // 뷰 이동 중
                v.setX(v.getX() + (motionEvent.getX()) - (v.getWidth()/2));
                v.setY(v.getY() + (motionEvent.getY()) - (v.getHeight()/2));

            }else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                Log.d("확인좀", String.valueOf(startYvalue));
                if (startYvalue >= 1417)
                    showDialog06();
                // 뷰에서 손을 뗌
                this.first = false;

                if(v.getX() < 0){
                    v.setX(0);
                }else if((v.getX() + v.getWidth()) > parentWidth){
                    v.setX(parentWidth - v.getWidth());
                }

                if(v.getY() < 0){
                    v.setY(0);
                }else if((v.getY() + v.getHeight()) > parentHeight){
                    v.setY(parentHeight - v.getHeight());
                }
                if(v.getX() <100 && v.getY()<100){ //삭제
                    v.setX(startXvalue);
                    v.setY(startYvalue);

                }
                else{
                    v.setOnTouchListener(null);
                    //// --> DB에 스티커 위치 등록
                    //// v.getY() 와 v.getX()로 절대좌표 얻을 수 있음
                    v.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(getApplicationContext(), FurnitureActivity.class);
                            startActivity(intent);
                            Log.d("확인 ", "클릭됨");

                        }
                    });
                    v.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            Log.d("확인", "긴 클릭 됨");
                            v.setOnClickListener(null);
                            v.setOnTouchListener(new MyTouchListener());

                            return false;
                        }
                    });
                }


            }

            return true;
        }
    }
    class DragListner implements View.OnDragListener { //드래그 + 위치에 따라 효과 주고싶을때


        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();

            //이벤트 시작
            switch (event.getAction()){

                case DragEvent.ACTION_DRAG_STARTED:
                    Log.d("드래그", "잘 됨");
                    break;

                case DragEvent.ACTION_DRAG_ENTERED:

                    //드래그한 이미지가 지역안으로 들어왔을때
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    break;

                case DragEvent.ACTION_DROP:
                    //드래그한 이미지를 지역안에서 드롭했을떄
                    View view = (View) event.getLocalState();
                    ViewGroup owner = (ViewGroup)view.getParent();
                    owner.removeView(view);
                    LinearLayout container = (LinearLayout) v;
                    container.addView(view);
                    view.setVisibility(View.VISIBLE);
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    break;
                default:
                    break;
            }
            return true;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) { //메뉴바 관련 함수(검색)
        getMenuInflater().inflate(R.menu.actionbar_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_search:
                showDialog03();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void showDialog03(){ //다이얼로그 함수(검색)
        dialog03.show(); // 다이얼로그 띄우기
        EditText edittext_searchname = dialog03.findViewById(R.id.edittext_searchname);



        Button searchbutton = dialog03.findViewById(R.id.pbutton);

        // *주의할 점: findViewById()를 쓸 때는 -> 앞에 반드시 다이얼로그 이름을 붙여야 한다.



        searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //검색 클릭했을때
                Log.d("확인", "검색클릭됨");
                Log.d("확인", "검색클릭됨");
                // 원하는 기능 구현
                ///////////////////////검색기능////////////////////
                Editable search = edittext_searchname.getText();
                //////////edittext에 입력한 문자의 경로를 query에 담습니다.//////
                Query query = conditionRef.child(String.valueOf(search));

                String stringquery = String.valueOf(query);
                stringquery.replace("/%EB%AC%BC%ED%92%88%20%EC%9D%B4%EB%A6%84\n","");

                StringBuffer stringBufferquery = new StringBuffer(stringquery);

                stringBufferquery.replace(0,58,"");
                stringquery = String.valueOf(stringBufferquery);

                TextView text_search_result = dialog04.findViewById(R.id.text_search_result);
                Button btn_search_result = dialog04.findViewById(R.id.btn_search_result);
                text_search_result.setText(stringquery);
                text_search_result.setTextSize(20);
                dialog03.dismiss();
                dialog04.show();
                btn_search_result.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog04.dismiss();
                    }
                });





            }
        });
        Button nosearchbutton = dialog03.findViewById(R.id.nbutton);
        nosearchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //취소 클릭했을때
                Log.d("확인", "취소릭됨");
                // 원하는 기능 구현
                dialog03.dismiss();

            }
        });
    }
    public void showDialog06(){ //다이얼로그 함수(검색)
        dialog03.show(); // 다이얼로그 띄우기
        EditText edittext_searchname = dialog03.findViewById(R.id.edittext_searchname);
        edittext_searchname.setHint("가구 이름");
        emdfhr = true;

        Button searchbutton = dialog03.findViewById(R.id.pbutton);
        searchbutton.setText("등록");
        // *주의할 점: findViewById()를 쓸 때는 -> 앞에 반드시 다이얼로그 이름을 붙여야 한다.



        searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //등록 클릭했을때

                Button btn_search_result = dialog04.findViewById(R.id.btn_search_result);

                dialog03.dismiss();

                btn_search_result.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog04.dismiss();
                    }
                });





            }
        });
        Button nosearchbutton = dialog03.findViewById(R.id.nbutton);
        nosearchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //취소 클릭했을때
                Log.d("확인", "취소릭됨");
                // 원하는 기능 구현
                dialog03.dismiss();

            }
        });
    }






}


