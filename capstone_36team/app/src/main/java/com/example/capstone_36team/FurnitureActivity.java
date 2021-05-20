package com.example.capstone_36team;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.gun0912.tedpermission.PermissionListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FurnitureActivity extends AppCompatActivity {
    private Dialog dilaog01;
    private ListView listView_item;
    private Button btn_add_item;
    private Dialog dialog03;

    private Button modify;
    private Dialog dialog02;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_furniture);
        dilaog01 = new Dialog(FurnitureActivity.this);
        dilaog01.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        dilaog01.setContentView(R.layout.barcodeorcustom);
        listView_item = (ListView) findViewById(R.id.listview_item);
        btn_add_item = (Button) findViewById(R.id.btn_add_item);
        dialog02 = new Dialog(FurnitureActivity.this);       // Dialog 초기화
        dialog02.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        dialog02.setContentView(R.layout.plus_dialog_layout_nofood);
        dialog03 = new Dialog(FurnitureActivity.this);       // Dialog 초기화
        dialog03.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        dialog03.setContentView(R.layout.search_result);






        ///////추가버튼////////
        btn_add_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog01();
            }
        });

        ///////여기부터 리스트뷰 관련/////////
        String[] strDate = {"1", "2", "3", "1", "5", "6"};  //strDate -> 물품 수량
        int nDatCnt=0;
        ArrayList<ItemData> oData = new ArrayList<>();
        for (int i=0; i<strDate.length ; ++i)
        {
            ItemData oItem = new ItemData();
            oItem.strTitle = "데이터 " + (i+1); //strTitle -> 물품 이름
            oItem.strDate = strDate[nDatCnt++];
            oData.add(oItem);
            if (nDatCnt >= strDate.length) nDatCnt = 0;
        }

// ListView, Adapter 생성 및 연결 ------------------------

        ListAdapter oAdapter = new ListAdapter(oData);
        listView_item.setAdapter(oAdapter);



    }
    public void showDialog01(){ //다이얼로그 함수
        dilaog01.show(); // 다이얼로그 띄우기
        // *주의할 점: findViewById()를 쓸 때는 -> 앞에 반드시 다이얼로그 이름을 붙여야 함

        Button barcode = dilaog01.findViewById(R.id.barcode);
        barcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //바코드로 등록 클릭했을때
                Log.d("확인", "바코드로 등록 클릭됨");
                // 원하는 기능 구현
                dilaog01.dismiss(); // 다이얼로그 닫기
                BarcodeScanner(); //바코드 스캐너 실행



            }
        });
        Button custom = dilaog01.findViewById(R.id.custom);
        custom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //수동으로 등록 클릭했을때
                Log.d("확인", "수동으로 등록 클릭됨");
                // 원하는 기능 구현
                showDialog02();


            }
        });
    }
    PermissionListener permissionListener = new PermissionListener() { //퍼미션리스너
        @Override
        public void onPermissionGranted() {
            Toast.makeText(getApplicationContext(), "권한이 허용됨", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
            Toast.makeText(getApplicationContext(), "권한이 거부됨", Toast.LENGTH_SHORT).show();

        }
    };
    public void BarcodeScanner(){
        IntentIntegrator intentIntegrator = new IntentIntegrator(
                FurnitureActivity.this
        );

        intentIntegrator.setPrompt("볼륨 증가 키-> Flash On");

        intentIntegrator.setBeepEnabled(true);
        intentIntegrator.setOrientationLocked(true);
        intentIntegrator.setCaptureActivity(Capture.class);
        intentIntegrator.initiateScan();

    }
    public void showDialog02(){ //수동으로 입력 클릭하면 나오는다이얼로그 함수




        dialog02.show(); // 다이얼로그 띄우기

        Button plus_button = dialog02.findViewById(R.id.plus_button2);
        plus_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //등록

                ///////foodActivity에서의 물품추가 dialog와 다른 layout 파일(plus_dialog_layout_nofood.xml) 사용했어요!!!!!@@@@@@@
                ////유통기한이 없어요/////
                //////여기서 EditText에 써진 DB추가 필요해요/////////////


                dialog02.dismiss(); // 다이얼로그 닫기




            }
        });
        Button no_plus_button = dialog02.findViewById(R.id.no_plus_button2);
        no_plus_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //취소
                Log.d("확인", "취소 클릭됨");
                // 원하는 기능 구현
                dialog02.dismiss();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) { //메뉴바 관련 함수(검색)
        getMenuInflater().inflate(R.menu.trash_button, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_trash:
                Log.d("쓰레기통", "클릭됨");
                showDialog03();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
    public void showDialog03(){ //다이얼로그 함수
        Button searchbutton = dialog03.findViewById(R.id.btn_search_result);
        searchbutton.setText("삭제");
        dialog03.show(); // 다이얼로그 띄우기
        TextView edittext_searchname = dialog03.findViewById(R.id.text_search_result);
        edittext_searchname.setTextSize(20);
        edittext_searchname.setText("해당가구를 삭제하시겠습니까?");

        // *주의할 점: findViewById()를 쓸 때는 -> 앞에 반드시 다이얼로그 이름을 붙여야 한다.



        searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //삭제 클릭했을때

                ////////////////여기서 DB삭제되게//////////

                dialog03.dismiss();
            }
        });

    }


}