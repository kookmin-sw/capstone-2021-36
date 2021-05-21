package com.example.capstone_36team;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import com.google.zxing.integration.android.IntentResult;
import com.gun0912.tedpermission.PermissionListener;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class FurnitureActivity extends AppCompatActivity {
    private Dialog dilaog01;
    private ListView listView_item;
    private Button btn_add_item;
    private Dialog dialog03;
    private Dialog dialog05;
    String Barcodedata ;
    String name;
    String company;
    String key = "593cd6a3496d4e1194ff";

    private Button modify;
    private Dialog dialog02;
    AlertDialog.Builder builder;

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
        dialog05 = new Dialog(FurnitureActivity.this);       // Dialog 초기화
        dialog05.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        dialog05.setContentView(R.layout.plus_dialog_layout_nofood);






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



        dialog02.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
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
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(
                requestCode,resultCode,data
        );
        if (intentResult.getContents() != null){

            //result 가 null이 아닐때
            builder = new AlertDialog.Builder(
                    FurnitureActivity.this
            );
            builder.setTitle("결과");

            Barcodedata = intentResult.getContents(); //바코드 번호
//            getXmlData();

//
            String queryUrl = "https://openapi.foodsafetykorea.go.kr/api/".concat(key).concat("/I2570/xml/1/1/BRCD_NO=").concat(Barcodedata);

            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    try  {

                        getXmlData(queryUrl);



                        //Your code goes here
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            builder.setMessage("상품명:" + name+ "  제조사:" +company); //근데 여기서 출력이 안돼요
            Log.d("제발요", name+company);


            builder.setPositiveButton("등록", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int which) {
                    //Diamiss 다이얼로그
                    dialogInterface.dismiss();
                }
            });
            builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
//            builder.create().show();


            showdialog05();

        }else{
            //result content가 null일때
            Toast.makeText(getApplicationContext(), "스캔하지 않으셨습니다.", Toast.LENGTH_SHORT).show();
        }
    }
    public String getXmlData(String s){
        StringBuffer buffer = new StringBuffer();

//        String queryUrl = "https://openapi.foodsafetykorea.go.kr/api/593cd6a3496d4e1194ff/I2570/xml/1/5/BRCD_NO=8809360172547";
        try{



            URL url = new URL(s);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new InputSource(url.openStream()));
            doc.getDocumentElement().normalize();

            doc.getDocumentElement().normalize();
            Log.d("확인", doc.getDocumentElement().getNodeName());
            NodeList nodeList = doc.getElementsByTagName("row");
            Log.d("리스트수", String.valueOf(nodeList.getLength()));
            for (int temp = 0; temp<nodeList.getLength(); temp++){
                Node nNode = nodeList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE){
                    Element eElement = (Element) nNode;
                    Log.d("제발", "상품이름 " + getTagValue("PRDT_NM", eElement));
                    Log.d("제발", "제조사" + getTagValue("CMPNY_NM", eElement));
                    name = getTagValue("PRDT_NM", eElement); //여기서의 name과 company는 잘 출력돼요
                    company = getTagValue("CMPNY_NM", eElement);
                    return name+company;



                }
            }



        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        buffer.append("파싱 끝\n");
        return "망했어";
    }
    public void showdialog05(){
        dialog05.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));





        dialog05.show();

        EditText fnameinput = dialog05.findViewById(R.id.fnameInput2);
        fnameinput.setText(name + company);
        ////////////////////////////////////여기서 수량정보를 얻어올 수 있음///////////////////////////

        Button button = dialog05.findViewById(R.id.plus_button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog05.dismiss();
            }
        });

    }
    private String getTagValue(String tag, Element eElement){ //바코드 인식 관련
        NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
        Node nValue = (Node)nlList.item(0);
        if(nValue == null)
            return null;
        return nValue.getNodeValue();
    }


}