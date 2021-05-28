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

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
import java.util.UUID;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class FurnitureActivity extends AppCompatActivity {
    private Dialog dilaog01;
    private ListView listView_item;
    private Button btn_add_item;
    private Dialog dialog03;
    private Dialog dialog05;
    private Dialog dialog06;
    String Barcodedata ;
    String name;
    String company;
    String key = "593cd6a3496d4e1194ff";
    String itemname;
    String category;

    private Button modify;
    private Dialog dialog02;
    AlertDialog.Builder builder;
    private String familyid;
    Room Furniture;
    UUID newUID;
    String furnitureid;
    DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();
    DatabaseReference conditionRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_furniture);
        getSupportActionBar().setTitle("Room");

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
        dialog06 = new Dialog(FurnitureActivity.this);       // Dialog 초기화
        dialog06.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        dialog06.setContentView(R.layout.plus_dialog_layout_nofood);


        Intent ItemIntent = getIntent();
        itemname = ItemIntent.getStringExtra("itemname");
        getSupportActionBar().setTitle(itemname);

        Intent refnameIntent = getIntent();


        category = refnameIntent.getStringExtra("category");
        furnitureid = refnameIntent.getStringExtra("furnitureid");
        String furniturename = refnameIntent.getStringExtra("furniturename");
        familyid = refnameIntent.getStringExtra("familyid");
        conditionRef = mDatabase.child("HomeDB").child(familyid).child("roomlist").child(category).child("furniturelist").child(furnitureid).child("productlist");
        Furniture = new Room(furniturename);



        ///////추가버튼////////
        btn_add_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog01();
            }
        });

        ///////여기부터 리스트뷰 관련/////////
//        String[] strDate = {"1", "2", "3", "1", "5", "6"};  //strDate -> 물품 수량
//        int nDatCnt=0;
//        ArrayList<ItemData> oData = new ArrayList<>();
//        for (int i=0; i<strDate.length ; ++i)
//        {
//            ItemData oItem = new ItemData();
//            oItem.strTitle = "데이터 " + (i+1); //strTitle -> 물품 이름
//            oItem.strDate = strDate[nDatCnt++];
//            oData.add(oItem);
//            if (nDatCnt >= strDate.length) nDatCnt = 0;
//        }

// ListView, Adapter 생성 및 연결 ------------------------
        //ArrayList<ItemData> oData = new ArrayList<>();
        //ListAdapter oAdapter = new ListAdapter(oData);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1);
        listView_item.setAdapter(adapter);
        listView_item.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                showdialog06(position);
            }
        });

        conditionRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //ItemData oItem = new ItemData(dataSnapshot.child("name").getValue(String.class), dataSnapshot.child("count").getValue(Integer.class).toString());
                //oData.add(oItem);
                adapter.add(dataSnapshot.child("name").getValue(String.class));
                Product p = new Product(dataSnapshot.getKey(),dataSnapshot.child("name").getValue(String.class), dataSnapshot.child("placedetail").getValue(String.class), dataSnapshot.child("count").getValue(Integer.class));
                Furniture.addProduct(p);

                //Log.d("MainActivity", "ChildEventListener - onChildAdded : " + dataSnapshot + dataSnapshot.getKey());
//                fItem.strTitle =  "t";//dataSnapshot.getKey(); //strTitle -> 물품 이름
//                fItem.strDate =  "1";//dataSnapshot.child("count").getValue(String.class);
//                fData.add(fItem);
                Log.d("MainActivity", "ChildEventListener - onChildChanged : ");
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String oldname = Furniture.searchProductbyid(dataSnapshot.getKey()).getName();
                Furniture.updateProduct(dataSnapshot.getKey(),dataSnapshot.child("name").getValue(String.class), dataSnapshot.child("placedetail").getValue(String.class), dataSnapshot.child("count").getValue(Integer.class));
                int pos = adapter.getPosition(oldname);
                adapter.remove(oldname);
                adapter.insert(dataSnapshot.child("name").getValue(String.class), pos);
                adapter.notifyDataSetChanged();
                Log.d("MainActivity", "ChildEventListener - onChildChanged : " + dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                adapter.remove(dataSnapshot.child("name").getValue(String.class));
                adapter.notifyDataSetChanged();
                Furniture.delProduct(dataSnapshot.child("name").getValue(String.class));
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
                dilaog01.dismiss();


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

        Button plus_button = dialog02.findViewById(R.id.plus_button2);
        plus_button.setText("등록");
        dialog02.show(); // 다이얼로그 띄우기
        plus_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //등록

                EditText fnameinput = (EditText)dialog02.findViewById(R.id.fnameInput2);
                EditText fposinput = (EditText)dialog02.findViewById(R.id.fposInput2);
                EditText fcountinput = (EditText)dialog02.findViewById(R.id.fcountInput2);




                String foodname = fnameinput.getText().toString();
                String f_detail_place = fposinput.getText().toString();
                int fcount = Integer.parseInt(fcountinput.getText().toString());
                Map<String, Object> taskMap = new HashMap<String, Object>();
                taskMap.put("name", foodname);
                taskMap.put("count", fcount);
                taskMap.put("placedetail", f_detail_place);

                newUID = UUID.randomUUID();
                String nfoodid = newUID.toString();

                conditionRef.child(nfoodid).setValue(taskMap);
                //conditionRef = mDatabase.child("HomeDB").child("family2").child("Fridge");
                Log.d("확인", foodname);


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
                conditionRef.getParent().setValue(null);
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
    public void showdialog06(int index){
        dialog06.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        dialog06.show();
        EditText fnameinput = (EditText)dialog06.findViewById(R.id.fnameInput2);
        EditText fposinput = (EditText)dialog06.findViewById(R.id.fposInput2);
        EditText fcountinput = (EditText)dialog06.findViewById(R.id.fcountInput2);
        fnameinput.setText(Furniture.getProductByIndex(index).getName());
        fposinput.setText(Furniture.getProductByIndex(index).getDetailPlace());
        fcountinput.setText(String.valueOf(Furniture.getProductByIndex(index).getCount()));
        Button button = (Button)dialog06.findViewById(R.id.plus_button2);
        button.setText("변경");
        String fid = Furniture.getProductByIndex(index).getId();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //변경버튼 클릭했을떄
                String newfoodname = fnameinput.getText().toString();
                String newf_detail_place = fposinput.getText().toString();
                int newfcount = Integer.parseInt(fcountinput.getText().toString());
                Map<String, Object> ctaskMap = new HashMap<String, Object>();
                ctaskMap.put("name", newfoodname);
                ctaskMap.put("count", newfcount);
                ctaskMap.put("placedetail", newf_detail_place);
                //conditionRef.updateChildren(ctaskMap);
                conditionRef.child(fid).updateChildren(ctaskMap);
                dialog06.dismiss();
            }
        });
        Button button1 = (Button)dialog06.findViewById(R.id.no_plus_button2) ;
        button1.setText("삭제");
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conditionRef.child(fid).setValue(null);
                dialog06.dismiss();
                // 삭제버튼 클릭했을떄
            }
        });
    }


}
