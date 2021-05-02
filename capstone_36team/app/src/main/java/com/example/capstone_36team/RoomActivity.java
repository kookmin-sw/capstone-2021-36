package com.example.capstone_36team;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;


public class RoomActivity extends AppCompatActivity {
    private ImageView image;
    private static  final String IMAGEVIEW_TAG = "드래그 이미지";
    private ImageView trash;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        trash = (ImageView) findViewById(R.id.trash);
        image = (ImageView) findViewById(R.id.image);


        MyTouchListener myTouchListener = new MyTouchListener();



        image.setOnTouchListener(new MyTouchListener()); //가구 이미지 mytouchlistner에 연결



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
                if (this.first == true){ //삭제를 위한 코드
                    startXvalue = v.getX();
                    startYvalue = v.getY();}
                this.first = false;


                Log.d("viewTest", "oldXvalue : "+ oldXvalue + " oldYvalue : " + oldYvalue);    // View 내부에서 터치한 지점의 상대 좌표값.
                Log.d("viewTest", "v.getX() : "+v.getX());    // View 의 좌측 상단이 되는 지점의 절대 좌표값.
                Log.d("viewTest", "RawX : " + motionEvent.getRawX() +" RawY : " + motionEvent.getRawY());    // View 를 터치한 지점의 절대 좌표값.
                Log.d("viewTest", "v.getHeight : " + v.getHeight() + " v.getWidth : " + v.getWidth());    // View 의 Width, Height

            }else if(motionEvent.getAction() == MotionEvent.ACTION_MOVE){
                // 뷰 이동 중
                v.setX(v.getX() + (motionEvent.getX()) - (v.getWidth()/2));
                v.setY(v.getY() + (motionEvent.getY()) - (v.getHeight()/2));

            }else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                // 뷰에서 손을 뗌

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


}


