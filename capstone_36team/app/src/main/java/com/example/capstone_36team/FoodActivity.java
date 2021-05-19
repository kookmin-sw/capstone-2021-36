package com.example.capstone_36team;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.Query;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.journeyapps.barcodescanner.CaptureActivity;


import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class FoodActivity extends AppCompatActivity {
    private Button btn_add_food;
    private Dialog dilaog01;
    private Dialog dialog02;
    private Dialog dialog03;
    private Dialog dialog04;
    private Dialog dialog05;
    String name;
    String company;
    private static final int REQUEST_IMAGE_CAPTURE = 672;
    private String imageFilePath;
    private Uri photoUri;
    private AlarmManager alarmManager;
    private GregorianCalendar mCalender;
    private NotificationManager notificationManager;
    String barcoderesult1;
    String result1;
    AlertDialog.Builder builder;

    String key = "593cd6a3496d4e1194ff";
    String Barcodedata ;
    String data;
    Button dateset;
    DatePickerDialog datePickerDialog;

    String date;

    private ListView flist;
    private String user_name = "testuid";





    DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();
    DatabaseReference userRef = mDatabase.child("UserDB").child(user_name);
    DatabaseReference conditionRef; //= mDatabase.child("HomeDB").child(family_name).child("Fridge");

//    String userId = "user1";
//    String FamilyName = mDatabase.child("UserDB").child(userId).get();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        ListView flist = (ListView)findViewById(R.id.listview_food2);




        btn_add_food = (Button)findViewById(R.id.btn_add_food);
        dilaog01 = new Dialog(FoodActivity.this);       // Dialog 초기화
        dilaog01.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        dilaog01.setContentView(R.layout.barcodeorcustom);
        dialog02 = new Dialog(FoodActivity.this);       // Dialog 초기화
        dialog02.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        dialog02.setContentView(R.layout.plus_dialog_layout);
        dialog03 = new Dialog(FoodActivity.this);       // Dialog 초기화
        dialog03.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        dialog03.setContentView(R.layout.search_dialog);
        dialog04 = new Dialog(FoodActivity.this);       // Dialog 초기화
        dialog04.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        dialog04.setContentView(R.layout.search_result);
        dialog05 = new Dialog(FoodActivity.this);       // Dialog 초기화
        dialog05.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        dialog05.setContentView(R.layout.plus_dialog_layout);

        notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE); // 하추
        alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE); // 하추
        mCalender = new GregorianCalendar(); // 하추
        Log.v("HelloAlarmActivity", mCalender.getTime().toString()); // 하추

//        ArrayList<ItemData> fData = new ArrayList<>();
//        ItemData fItem = new ItemData();
//        for (int i=0; i<strDate.length ; ++i)
//        {
//            ItemData fItem = new ItemData();
//            fItem.strTitle = "데이터 " + (i+1); //strTitle -> 물품 이름
//            fItem.strDate = strDate[nDatCnt++];
//            fData.add(fItem);
//            if (nDatCnt >= strDate.length) nDatCnt = 0;
//        }

// ListView, Adapter 생성 및 연결 ------------------------

        // ListAdapter fAdapter = new ListAdapter(fData);
        //flist.setAdapter(fAdapter);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1);
        flist.setAdapter(adapter);



        userRef.addChildEventListener(new ChildEventListener()
        {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                //adapter.add(snapshot.getValue(String.class));
                conditionRef = mDatabase.child("HomeDB").child(snapshot.getValue(String.class)).child("Fridge");
                adapter.clear();
                mkChildEventListener(adapter);

            }

            @Override
            public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                conditionRef = mDatabase.child("HomeDB").child(snapshot.getValue(String.class)).child("Fridge");
                adapter.clear();
                mkChildEventListener(adapter);
//                adapter.clear();
//                conditionRef = mDatabase.child("HomeDB").child(snapshot.getValue(String.class)).child("Fridge");
            }

            @Override
            public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {
                
            }

            @Override
            public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


        btn_add_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog01();
            }
        });
    }

    public void mkChildEventListener(ArrayAdapter<String> adapter){
        conditionRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                adapter.add(dataSnapshot.getKey());
                //Log.d("MainActivity", "ChildEventListener - onChildAdded : " + dataSnapshot + dataSnapshot.getKey());
//                fItem.strTitle =  "t";//dataSnapshot.getKey(); //strTitle -> 물품 이름
//                fItem.strDate =  "1";//dataSnapshot.child("count").getValue(String.class);
//                fData.add(fItem);
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
    }

    private String getTodaysDate() {
        Calendar ca1 = Calendar.getInstance();
        int year = ca1.get(Calendar.YEAR);
        int month = ca1.get(Calendar.MONTH);
        int day = ca1.get(Calendar.DAY_OF_MONTH);
        month = month +1;
        return  makeDateString(day, month, year);


    }

    public void showDialog01(){ //다이얼로그 함수
        dilaog01.show(); // 다이얼로그 띄우기


        // *주의할 점: findViewById()를 쓸 때는 -> 앞에 반드시 다이얼로그 이름을 붙여야 한다.


        Button barcode = dilaog01.findViewById(R.id.barcode);
        barcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //바코드로 등록 클릭했을때
                Log.d("확인", "바코드로 등록 클릭됨");
                // 원하는 기능 구현


                dilaog01.dismiss(); // 다이얼로그 닫기
                BarcodeScanner(); //바코드스캐너 열기



            }
        });
        Button custom = dilaog01.findViewById(R.id.custom);
        custom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //수동으로 등록 클릭했을때
                Log.d("확인", "수동으로 등록 클릭됨");
                // 원하는 기능 구현
                dilaog01.dismiss();
                showDialog02();
            }
        });
    }
//    public void Camera(){//카메라 실행
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if(getApplicationContext().getPackageManager().hasSystemFeature(
//                PackageManager.FEATURE_CAMERA)){
//            File photoFile = null;
//            try{
//                photoFile = createImagefile();
//            }catch (IOException e){
//
//            }
//
//            if(photoFile != null){
//                photoUri = FileProvider.getUriForFile(getApplicationContext(),getPackageName(), photoFile);
//                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri); //화면 전환할떄 값을 가져와줌
//                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
//            }
//        }
//    }

//    private File createImagefile() throws IOException{ //이미지 파일 생성
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String imageFileName = "TEST_" + timeStamp + "_";
//        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        File image = File.createTempFile(
//                imageFileName,
//                ".jpg",
//                storageDir
//        );
//        imageFilePath = image.getAbsolutePath();
//        return image;
//
//    }
//    private void rnjsgks(){//권한 체크
//        TedPermission.with(getApplicationContext())
//                .setPermissionListener(permissionListener)
//                .setRationaleMessage("카메라 권한이 필요합니다.")
//                .setDeniedMessage("거부하셨습니다.")
//                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
//                .check();
//
//    }


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
                FoodActivity.this
        );

        intentIntegrator.setPrompt("볼륨 증가 키-> Flash On");

        intentIntegrator.setBeepEnabled(true);
        intentIntegrator.setOrientationLocked(true);
        intentIntegrator.setCaptureActivity(Capture.class);
        intentIntegrator.initiateScan();

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
    private String getTagValue(String tag, Element eElement){ //바코드 인식 관련
        NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
        Node nValue = (Node)nlList.item(0);
        if(nValue == null)
            return null;
        return nValue.getNodeValue();
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(
                requestCode,resultCode,data
        );
        if (intentResult.getContents() != null){
            
            //result 가 null이 아닐때
            builder = new AlertDialog.Builder(
                    FoodActivity.this
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
                        Log.d("제발", result1+name+company); //여기서도 출력이 잘 돼요


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
    private void initDatePicker(){
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month +1;
                String date = makeDateString(day, month, year);
                dateset.setText(date);
            }
        };
        Calendar ca1 = Calendar.getInstance();
        int year = ca1.get(Calendar.YEAR);
        int month = ca1.get(Calendar.MONTH);
        int day = ca1.get(Calendar.DAY_OF_MONTH);
        int style = AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
    }
    private String makeDateString(int day,int month, int year ){
        Log.d("날짜확인", year+ "년"+month+"월" + day + "일" );
        date = year + "-" + month + "-" + day;
        return year+ "년" + month+"월"+ day +"일"; ///////////////////////////////////// 유통기한 등록장소
    }

    public void openDatePicker(View view){
        datePickerDialog.show();

    }

    public void showDialog02(){ //수동으로 입력 클릭하면 나오는다이얼로그 함수
        Button plus_button = dialog02.findViewById(R.id.plus_button);
        initDatePicker();
        dateset = (Button)dialog02.findViewById(R.id.dateset);
        dateset.setText(getTodaysDate());

        dialog02.show(); // 다이얼로그 띄우기


        plus_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //등록

                EditText fnameinput = (EditText)dialog02.findViewById(R.id.fnameInput);
                EditText fposinput = (EditText)dialog02.findViewById(R.id.fposInput);
                EditText fcountinput = (EditText)dialog02.findViewById(R.id.fcountInput);




                String foodname = fnameinput.getText().toString();
                String f_detail_place = fposinput.getText().toString();
                int fcount = Integer.parseInt(fcountinput.getText().toString());
                Map<String, Object> taskMap = new HashMap<String, Object>();
                taskMap.put("name", foodname);
                taskMap.put("count", fcount);
                taskMap.put("place", f_detail_place);
                setAlarm();


                conditionRef.child(foodname).setValue(taskMap);
                //conditionRef = mDatabase.child("HomeDB").child("family2").child("Fridge");
                Log.d("확인", foodname);


                dialog02.dismiss(); // 다이얼로그 닫기




            }
        });
        Button no_plus_button = dialog02.findViewById(R.id.no_plus_button);
        no_plus_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //취소
                Log.d("확인", "취소 클릭됨");
                // 원하는 기능 구현
                dialog02.dismiss();
            }
        });
    }

    private void setAlarm() {
        //AlarmReceiver에 값 전달
        Intent receiverIntent = new Intent(FoodActivity.this, AlarmRecevier.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(FoodActivity.this, 0, receiverIntent, 0);

        String from = date + " 00:00:00";
        //String from = "2021-05-17 03:37:00"; //임의로 날짜와 시간을 지정

        //날짜 포맷을 바꿔주는 소스코드
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date datetime = null;
        try {
            datetime = dateFormat.parse(from);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(datetime);

        alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(),pendingIntent);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
    public void showdialog05(){

        initDatePicker();
        dateset = (Button)dialog02.findViewById(R.id.dateset);
        dateset.setText(getTodaysDate());
        dialog05.show();

        EditText fnameinput = dialog05.findViewById(R.id.fnameInput);
        fnameinput.setText(name + company);
        ////////////////////////////////////여기서 수량정보를 얻어올 수 있음///////////////////////////

        Button button = dialog05.findViewById(R.id.plus_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog05.dismiss();
            }
        });

    }

    public void showDialog03(){ //다이얼로그 함수(검색)
        dialog03.show(); // 다이얼로그 띄우기


        // *주의할 점: findViewById()를 쓸 때는 -> 앞에 반드시 다이얼로그 이름을 붙여야 한다.
        EditText edittext_searchname = dialog03.findViewById(R.id.edittext_searchname);



        Button searchbutton = dialog03.findViewById(R.id.pbutton);
        searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //검색 클릭했을때
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




                Log.d("쿼리", stringquery);





            }
        });
        Button nosearchbutton = dialog03.findViewById(R.id.nbutton);
        nosearchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //취소 클릭했을때
                Log.d("확인", "취소클릭됨");
                // 원하는 기능 구현
                dialog03.dismiss();

            }
        });

    }







}
