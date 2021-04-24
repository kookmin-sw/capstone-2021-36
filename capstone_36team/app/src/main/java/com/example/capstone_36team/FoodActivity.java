package com.example.capstone_36team;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Dialog;
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
import android.widget.ImageView;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FoodActivity extends AppCompatActivity {
    private Button btn_add_food;
    private Dialog dilaog01;
    private Dialog dialog02;
    private static final int REQUEST_IMAGE_CAPTURE = 672;
    private String imageFilePath;
    private Uri photoUri;


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
                rnjsgks();
                Camera();



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
    public void Camera(){//카메라 실행
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)){
            File photoFile = null;
            try{
                photoFile = createImagefile();
            }catch (IOException e){

            }

            if(photoFile != null){
                photoUri = FileProvider.getUriForFile(getApplicationContext(),getPackageName(), photoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri); //화면 전환할떄 값을 가져와줌
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private File createImagefile() throws IOException{ //이미지 파일 생성
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "TEST_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        imageFilePath = image.getAbsolutePath();
        return image;

    }
    private void rnjsgks(){//권한 체크
        TedPermission.with(getApplicationContext())
                .setPermissionListener(permissionListener)
                .setRationaleMessage("카메라 권한이 필요합니다.")
                .setDeniedMessage("거부하셨습니다.")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bitmap bitmap = BitmapFactory.decodeFile(imageFilePath);
            ExifInterface exif = null;
            try{
                exif = new ExifInterface(imageFilePath);
            }catch (IOException e){
                e.printStackTrace();
            }
            int exifOrientation;
            int exifDegree;
            if (exif != null){
                exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                exifDegree = exifOrientationToDegrees(exifOrientation);

            }else{
                exifDegree = 0;
            }
            //이미지뷰(findviewbyid).setImageBitmap(rotate(bitmap.exifDegree)); --> 찍은 사진 띄우고싶을때

        }
    }
    private int exifOrientationToDegrees(int exifOrientation){
        if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_90){
            return 90;
        }else if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_180){
            return 180;
        }else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270){
            return 270;
        }
        return 0;
    }
    private Bitmap rotate(Bitmap bitmap, float degree){ //사진 띄우고 싶을떄
        Matrix matrix =new Matrix();
        matrix.postRotate(degree);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

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
    public void showDialog02(){ //수동으로 입력 클릭하면 나오는다이얼로그 함수

        dialog02.show(); // 다이얼로그 띄우기
        Button plus_button = dialog02.findViewById(R.id.plus_button);
        plus_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //등록
                Log.d("확인", "등록 클릭됨");
                // 원하는 기능 구현


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