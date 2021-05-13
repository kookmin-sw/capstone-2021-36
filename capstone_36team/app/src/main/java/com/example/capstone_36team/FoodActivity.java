package com.example.capstone_36team;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FoodActivity extends AppCompatActivity {
    private Button btn_add_food;
    private Dialog dilaog01;
    private Dialog dialog02;
    private static final int REQUEST_IMAGE_CAPTURE = 672;
    private String imageFilePath;
    private Uri photoUri;
    String key = "593cd6a3496d4e1194ff";
    String Barcodedata ;
    String data;


    DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();
    //DatabaseReference conditionRef = mDatabase.child("text");
//    String userId = "user1";
//    String FamilyName = mDatabase.child("UserDB").child(userId).get();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        btn_add_food = (Button)findViewById(R.id.btn_add_food);
        dilaog01 = new Dialog(FoodActivity.this);       // Dialog 초기화
        dilaog01.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        dilaog01.setContentView(R.layout.barcodeorcustom);
        dialog02 = new Dialog(FoodActivity.this);       // Dialog 초기화
        dialog02.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        dialog02.setContentView(R.layout.plus_dialog_layout);





        btn_add_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog01();
            }
        });
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
    public String getXmlData(){
        StringBuffer buffer = new StringBuffer();
        String location = URLEncoder.encode(Barcodedata);
        String queryUrl = "http://openapi.foodsafetykorea.go.kr/"+key+"/I2570/xml/1/5/BRCD_NO="+Barcodedata;
        try{
            URL url = new URL(queryUrl); //문자열로 된 요청 url을 URL객체로 생성
            InputStream is = url.openStream();

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new InputStreamReader(is, "UTF-8"));

            String tag;

            xpp.next();
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT){
                switch (eventType){
                    case XmlPullParser.START_DOCUMENT:
                        buffer.append("파싱 시작...\n\n");
                        break;
                    case  XmlPullParser.START_TAG:
                        tag = xpp.getName();
                        if(tag.equals("PRDT_NM")){
                            buffer.append("상품명:");
                            xpp.next();
                            buffer.append(xpp.nextText());

                        }
                        break;
                    case XmlPullParser.TEXT:
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                eventType = xpp.next();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        buffer.append("파싱 끝\n");
        return  buffer.toString();
    }


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
            String r = getXmlData();
            builder.setMessage(r);
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

    public void showDialog02(){ //수동으로 입력 클릭하면 나오는다이얼로그 함수

        dialog02.show(); // 다이얼로그 띄우기
        Button plus_button = dialog02.findViewById(R.id.plus_button);

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


                mDatabase.child("HomeDB").child("family1").child("Fridge").child(foodname).setValue(taskMap);
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




}