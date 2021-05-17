package com.example.capstone_36team;

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
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
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


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

public class FoodActivity extends AppCompatActivity {
    private Button btn_add_food;
    private Dialog dilaog01;
    private Dialog dialog02;
    private static final int REQUEST_IMAGE_CAPTURE = 672;
    private String imageFilePath;
    private Uri photoUri;
    private AlarmManager alarmManager;
    private GregorianCalendar mCalender;
    private NotificationManager notificationManager;
    NotificationCompat.Builder builder;

    String key = "593cd6a3496d4e1194ff";
    String Barcodedata ;
    String data;
    Button dateset;
    DatePickerDialog datePickerDialog;
<<<<<<< HEAD
    String date;
=======
    private ListView flist;
    private String family_name = "family1";

>>>>>>> dc1d3987eba6e9224013c4a12e60620e4cf2934e


    DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();
    DatabaseReference conditionRef = mDatabase.child("HomeDB").child(family_name).child("Fridge");
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


        btn_add_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog01();
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
//    public String getXmlData(){
//        StringBuffer buffer = new StringBuffer();
//        String location = URLEncoder.encode(Barcodedata);
//        String queryUrl = "http://openapi.foodsafetykorea.go.kr/api/"+key+"/I2570/xml/1/5/BRCD_NO="+Barcodedata;
//        try{
//            URL url = new URL(queryUrl); //문자열로 된 요청 url을 URL객체로 생성
//            InputStream is = url.openStream();
//
//            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
//            XmlPullParser xpp = factory.newPullParser();
//            xpp.setInput(new InputStreamReader(is, "UTF-8"));
//
//            String tag;
//
//
//            int eventType = xpp.getEventType();
//            Log.d("확인", String.valueOf(eventType));
//            xpp.next();
//
//
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (XmlPullParserException e) {
//            e.printStackTrace();
//        }
//        buffer.append("파싱 끝\n");
//        return  buffer.toString();
//    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(
                requestCode,resultCode,data
        );
        if (intentResult.getContents() != null){
            //result 가 null이 아닐때
            AlertDialog.Builder builder = new AlertDialog.Builder(
                    FoodActivity.this
            );
            builder.setTitle("결과");

            Barcodedata = intentResult.getContents(); //바코드 번호
//            getXmlData();

            String queryUrl = "http://openapi.foodsafetykorea.go.kr/api/".concat(key).concat("/I2570/xml/1/1/BRCD_NO=").concat(Barcodedata);
//            OpenApI dust = new OpenApI(queryUrl);
//            dust.execute();
            builder.setMessage(queryUrl);
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int which) {
                    //Diamiss 다이얼로그
                    dialogInterface.dismiss();
                }
            });
            builder.show();
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


                mDatabase.child("HomeDB").child(family_name).child("Fridge").child(foodname).setValue(taskMap);
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




}
