package com.example.capstone_36team;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class RoomActivity extends AppCompatActivity {
    private ImageView image;
    private ImageView image2;
    private ImageView image3;
    private ImageView image4;
    private Dialog dialog03;
    private Dialog dialog04;
    private Dialog dialog06;
    private Dialog dialog07;
    private Dialog dialog08;
    private String stringquery;
    private boolean emdfhr = false;
    private static  final String IMAGEVIEW_TAG = "๋๋๊ทธ ์ด๋ฏธ์ง";
    private String userid;
    private String familyid;
    UUID newUID;
    DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();
    //DatabaseReference userRef = mDatabase.child("UserDB").child(userid);
    DatabaseReference conditionRef;
//    HashMap<String, String> fridgemap = new HashMap<String, String>();
//    GlobalVariable familydata;

    //    private String family_name = "family1";
//    DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();
//    DatabaseReference conditionRef = mDatabase.child("HomeDB").child(family_name).child("room1");
    private String[] names = {"abc<๊ฐ๊ตฌ1<๋ฐฉ1", "ab2<๊ฐ๊ตฌ2<๋ฐฉ1"}; //////{"๋ฌผํ<์ฅ์", "๋ฌผํ<์ฅ์" "๋ฌผํ<์ฅ์"์ด๋ฐ์์ผ๋ก ๋ฐ์ดํฐ๊ฐ ๋ค์ด์์ ์ข๊ฒ?์ต๋๋ค.}
    String item;
    String category;
    GridLayout g;
    int h;
    Map<String, String> iiduidmap;


    private void tostMsg1() {
        Toast.makeText(this,"ใใ"+category,Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        Intent RoomnameIntent = getIntent();
        category = RoomnameIntent.getStringExtra("category");
        getSupportActionBar().setTitle("Room");


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
        dialog03 = new Dialog(RoomActivity.this);       // Dialog ์ด๊ธฐํ
        dialog03.requestWindowFeature(Window.FEATURE_NO_TITLE); // ํ์ดํ ์?๊ฑฐ
        dialog03.setContentView(R.layout.search_dialog);
        dialog04 = new Dialog(RoomActivity.this);       // Dialog ์ด๊ธฐํ
        dialog04.requestWindowFeature(Window.FEATURE_NO_TITLE); // ํ์ดํ ์?๊ฑฐ
        dialog04.setContentView(R.layout.search_result);
        dialog06 = new Dialog(RoomActivity.this);       // Dialog ์ด๊ธฐํ
        dialog06.requestWindowFeature(Window.FEATURE_NO_TITLE); // ํ์ดํ ์?๊ฑฐ
        dialog06.setContentView(R.layout.search_dialog);
        dialog07 = new Dialog(RoomActivity.this);       // Dialog ์ด๊ธฐํ
        dialog07.requestWindowFeature(Window.FEATURE_NO_TITLE); // ํ์ดํ ์?๊ฑฐ
        dialog07.setContentView(R.layout.auto_search_dialog);
        dialog08 = new Dialog(RoomActivity.this);       // Dialog ์ด๊ธฐํ
        dialog08.requestWindowFeature(Window.FEATURE_NO_TITLE); // ํ์ดํ ์?๊ฑฐ
        dialog08.setContentView(R.layout.plus_dialog_layout_nofood);


        g = findViewById(R.id.g);

        Intent refnameIntent = getIntent();
        String fridgename = refnameIntent.getStringExtra("fridgename");
        //Fridge = new Room(fridgename);
        category = refnameIntent.getStringExtra("category");
        //user_name = refnameIntent.getStringExtra("userid");
        familyid = refnameIntent.getStringExtra("familyid");
        iiduidmap = new HashMap<String, String>();
        conditionRef = mDatabase.child("HomeDB").child(familyid).child("roomlist").child(category).child("furniturelist");


        conditionRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String fridgename = dataSnapshot.child("furniturename").getValue(String.class);
                //ImageView newimage = <- ์ฌ๊ธฐ์ ์ ์ด๋ฏธ์ง ์์ฑ.
                //newimage.setX(dataSnapshot.child("xpos").getValue(float.class));
                //newimage.setY(dataSnapshot.child("ypos").getValue(float.class));
                int resourceid = getResources().getIdentifier(dataSnapshot.child("type").getValue(String.class), "id", getPackageName());
                ImageView timage = (ImageView) findViewById(resourceid);
                timage.setX(dataSnapshot.child("xpos").getValue(float.class));
                timage.setY(dataSnapshot.child("ypos").getValue(float.class));
                iiduidmap.put(dataSnapshot.child("type").getValue(String.class),dataSnapshot.getKey());

                //fridgemap.put(fridgename, dataSnapshot.getKey());
                Log.d("MainActivity", "ChildEventListener - onChildChanged : ");
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String fridgename = dataSnapshot.child("furniturename").getValue(String.class);
                //newimage.setX(dataSnapshot.child("xpos").getValue(float.class));
                //newimage.setY(dataSnapshot.child("ypos").getValue(float.class));
                image2.setX(dataSnapshot.child("xpos").getValue(float.class));
                image2.setY(dataSnapshot.child("ypos").getValue(float.class));
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

            image.setOnTouchListener(new MyTouchListener());} //๊ฐ๊ตฌ ์ด๋ฏธ์ง mytouchlistner์ ์ฐ๊ฒฐ

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
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        h = g.getHeight();
        Log.d("ํฌ๋ก", String.valueOf(h));

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
            int parentWidth = ((ViewGroup)v.getParent()).getWidth();    // ๋ถ๋ชจ View ์ Width
            int parentHeight = ((ViewGroup)v.getParent()).getHeight();



            if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                // ๋ทฐ ๋๋ฆ
                oldXvalue = (int) motionEvent.getX();
                oldYvalue = (int) motionEvent.getY();
                //์ญ์?๋ฅผ ์ํ ์ฝ๋
                startXvalue = v.getX();
                startYvalue = v.getY();



                Log.d("viewTest", "oldXvalue : "+ oldXvalue + " oldYvalue : " + oldYvalue);    // View ๋ด๋ถ์์ ํฐ์นํ ์ง์?์ ์๋ ์ขํ๊ฐ.
                Log.d("viewTest", "v.getX() : "+v.getX());    // View ์ ์ข์ธก ์๋จ์ด ๋๋ ์ง์?์ ์?๋ ์ขํ๊ฐ.
                Log.d("viewTest", "v.getY() : "+v.getY());
                Log.d("viewTest", "RawX : " + motionEvent.getRawX() +" RawY : " + motionEvent.getRawY());    // View ๋ฅผ ํฐ์นํ ์ง์?์ ์?๋ ์ขํ๊ฐ.
                Log.d("viewTest", "v.getHeight : " + v.getHeight() + " v.getWidth : " + v.getWidth());    // View ์ Width, Height

            }else if(motionEvent.getAction() == MotionEvent.ACTION_MOVE){
                // ๋ทฐ ์ด๋ ์ค
                v.setX(v.getX() + (motionEvent.getX()) - (v.getWidth()/2));
                v.setY(v.getY() + (motionEvent.getY()) - (v.getHeight()/2));

            }else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                Log.d("ํ์ธ์ข", String.valueOf(startYvalue));
                if (startYvalue >= h * (1417/1808))
                    showDialog06(v.getX(), v.getY(), v);

                // ๋ทฐ์์ ์์ ๋
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
                if(v.getX() <100 && v.getY()<100){ //์ญ์?
                    v.setX(startXvalue);
                    v.setY(startYvalue);

                }
                else{
                    v.setOnTouchListener(null);
                    //// --> DB์ ์คํฐ์ปค ์์น ๋ฑ๋ก
                    //// v.getY() ์ v.getX()๋ก ์?๋์ขํ ์ป์ ์ ์์
                    v.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String type = v.getResources().getResourceEntryName(v.getId());
                            Intent intent = new Intent(getApplicationContext(), FurnitureActivity.class);
                            intent.putExtra("furnitureid", iiduidmap.get(type));
                            intent.putExtra("furniturename", "์นจ๋");
                            intent.putExtra("category",category);
                            intent.putExtra("familyid", familyid);
                            startActivity(intent);
                            Log.d("ํ์ธ ", "ํด๋ฆญ๋จ");

                        }
                    });
                    v.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            Log.d("ํ์ธ", "๊ธด ํด๋ฆญ ๋จ");
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
    class DragListner implements View.OnDragListener { //๋๋๊ทธ + ์์น์ ๋ฐ๋ผ ํจ๊ณผ ์ฃผ๊ณ?์ถ์๋


        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();

            //์ด๋ฒคํธ ์์
            switch (event.getAction()){

                case DragEvent.ACTION_DRAG_STARTED:
                    Log.d("๋๋๊ทธ", "์ ๋จ");
                    break;

                case DragEvent.ACTION_DRAG_ENTERED:

                    //๋๋๊ทธํ ์ด๋ฏธ์ง๊ฐ ์ง์ญ์์ผ๋ก ๋ค์ด์์๋
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    break;

                case DragEvent.ACTION_DROP:
                    //๋๋๊ทธํ ์ด๋ฏธ์ง๋ฅผ ์ง์ญ์์์ ๋๋กญํ์๋
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
    public boolean onCreateOptionsMenu(Menu menu) { //๋ฉ๋ด๋ฐ ๊ด๋?จ ํจ์(๊ฒ์)
        getMenuInflater().inflate(R.menu.actionbar_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_search:
                showDialog07();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

//    public void showDialog03(){ //๋ค์ด์ผ๋ก๊ทธ ํจ์(๊ฒ์)
//        dialog03.show(); // ๋ค์ด์ผ๋ก๊ทธ ๋์ฐ๊ธฐ
//        dialog03.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        EditText edittext_searchname = dialog03.findViewById(R.id.edittext_searchname);
//
//
//        dialog03.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
//
//        Button searchbutton = dialog03.findViewById(R.id.pbutton);
//
//        // *์ฃผ์ํ? ์?: findViewById()๋ฅผ ์ธ ๋๋ -> ์์ ๋ฐ๋์ ๋ค์ด์ผ๋ก๊ทธ ์ด๋ฆ์ ๋ถ์ฌ์ผ ํ๋ค.
//
//        searchbutton.setText("๊ฒ์");
//
//        searchbutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) { //๊ฒ์ ํด๋ฆญํ์๋
//                Log.d("ํ์ธ", "๊ฒ์ํด๋ฆญ๋จ");
//                Log.d("ํ์ธ", "๊ฒ์ํด๋ฆญ๋จ");
//                // ์ํ๋ ๊ธฐ๋ฅ ๊ตฌํ
//                ///////////////////////๊ฒ์๊ธฐ๋ฅ////////////////////
//                Editable search = edittext_searchname.getText();
//                //////////edittext์ ์๋?ฅํ ๋ฌธ์์ ๊ฒฝ๋ก๋ฅผ query์ ๋ด์ต๋๋ค.//////
//                Query query = conditionRef.child(String.valueOf(search));
//                conditionRef.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
//                        if(!snapshot.hasChild(String.valueOf(search)))
//                            stringquery = "๊ฒ์๊ฒฐ๊ณผ๊ฐ ์์ต๋๋ค.";
//                        else{
//                            stringquery = String.valueOf(query);
//                            stringquery.replace("/%EB%AC%BC%ED%92%88%20%EC%9D%B4%EB%A6%84\n","");
//
//                            StringBuffer stringBufferquery = new StringBuffer(stringquery);
//
//                            stringBufferquery.replace(0,58,"");
//                            stringquery = String.valueOf(stringBufferquery);
//
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull @NotNull DatabaseError error) {
//
//                    }
//                });
//
//
//
//
//                TextView text_search_result = dialog04.findViewById(R.id.text_search_result);
//                Button btn_search_result = dialog04.findViewById(R.id.btn_search_result);
//                text_search_result.setText(stringquery);
//                text_search_result.setTextSize(20);
//                dialog03.dismiss();
//                dialog04.show();
//                btn_search_result.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog04.dismiss();
//                    }
//                });
//
//
//
//
//
//            }
//        });
//        Button nosearchbutton = dialog03.findViewById(R.id.nbutton);
//        nosearchbutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) { //์ทจ์ ํด๋ฆญํ์๋
//                Log.d("ํ์ธ", "์ทจ์๋ฆญ๋จ");
//                // ์ํ๋ ๊ธฐ๋ฅ ๊ตฌํ
//                dialog03.dismiss();
//
//            }
//        });
//    }




    public void showDialog06(float xpos, float ypos, View v){ //๋ค์ด์ผ๋ก๊ทธ ํจ์(๊ฒ์)
        dialog03.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog03.show(); // ๋ค์ด์ผ๋ก๊ทธ ๋์ฐ๊ธฐ
        EditText edittext_searchname = dialog03.findViewById(R.id.edittext_searchname);
        edittext_searchname.setHint("๊ฐ๊ตฌ ์ด๋ฆ");
        emdfhr = true;
        String type = v.getResources().getResourceEntryName(v.getId());

        Button searchbutton = dialog03.findViewById(R.id.pbutton);
        Button searchbutton2 = dialog03.findViewById(R.id.nbutton);
        searchbutton.setText("๋ฑ๋ก");
        // *์ฃผ์ํ? ์?: findViewById()๋ฅผ ์ธ ๋๋ -> ์์ ๋ฐ๋์ ๋ค์ด์ผ๋ก๊ทธ ์ด๋ฆ์ ๋ถ์ฌ์ผ ํ๋ค.



        searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //EditText fnameinput = (EditText)dialog03.findViewById(R.id.edittext_searchname);
                String furniturename = edittext_searchname.getText().toString();
                Map<String, Object> taskMap = new HashMap<String, Object>();
                taskMap.put("furniturename", furniturename);
                taskMap.put("xpos", xpos);
                taskMap.put("ypos", ypos);
                taskMap.put("type", type); // <- ์ฌ๊ธฐ ์ด๋ฏธ์ง ํ์ ์ถ๊ฐ.
                newUID = UUID.randomUUID();
                String nfoodid = newUID.toString();

                conditionRef.child(nfoodid).setValue(taskMap);
                ////////////////////////////////////////DB///////////////////////////////๋ฑ๋ก
                dialog03.dismiss();
            }
        });

        searchbutton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog03.dismiss();
            }
        });




    }
    public void showDialog07(){  //์๋์์ฑ ๋ค์ด์ผ๋ก๊ทธ
        dialog07.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        AutoCompleteTextView editText = dialog07.findViewById(R.id.actv);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names);
        editText.setAdapter(adapter);
        dialog07.show();
        editText.setText("");
        editText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                item = names[position];
                int a = item.indexOf("<");
                item = item.substring(0,a);


                dialog07.dismiss();
                showDialog08();
            }
        });


    }
    public void showDialog08(){
        dialog08.show();
        dialog08.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        EditText editText = dialog08.findViewById(R.id.fnameInput2);
                                   ////@@@@@@@@@@@@@@@@@@@@@@@@@@@///DB๋ฅผ ๊ฐ์?ธ์์, ๋ชจ๋?์?๋ณด ๋ค์ด์ผ๋ก๊ทธ์ ๋์์ฃผ๊ธฐ//////@@@@@@@@@@@@@@@@@@@@@//

        editText.setText(item);//////////.....์์ธ์ฅ์,,,์๋๊น์ง////////////setText๋ก ๋์์ฃผ๊ธฐ
        Button button1 = dialog08.findViewById(R.id.plus_button2);
        Button button2 = dialog08.findViewById(R.id.no_plus_button2);
        button1.setText("๋ณ๊ฒฝ");
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /////////////////////////////////'@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@////////-> ๋ณ๊ฒฝ์ฌํญ์ ์?์ฅํด์ฃผ๊ธฐ
                dialog08.dismiss();
                Toast.makeText(RoomActivity.this,"๋ณ๊ฒฝ์ฌํญ์ด ์?์ฅ๋์์ต๋๋ค.", Toast.LENGTH_SHORT).show();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog08.dismiss();
            }
        });


    }







}


