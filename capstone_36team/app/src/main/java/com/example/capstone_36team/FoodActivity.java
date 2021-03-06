package com.example.capstone_36team;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentActivity;

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
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
import java.util.UUID;

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
    private Dialog dialog07;
    private Dialog dialog07_1;
    private Dialog dialog08;


    String category;
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

    String stringquery;AlertDialog.Builder builder;

    String key = "593cd6a3496d4e1194ff";    String Barcodedata ;
    String data;
    Button dateset;
    String item;
    DatePickerDialog datePickerDialog;

    String date;

    private ListView flist;

    //private String user_name = "testuid";
    private String[] names; //////{"??????<??????", "??????<??????" "??????<??????"??????????????? ???????????? ???????????? ???????????????.}
    private ArrayList<String> nameslist;

    private String familyname;
    Room Fridge;
    UUID newUID;
    ArrayAdapter<String> adapter;




    DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();
    DatabaseReference conditionRef;

//    String userId = "user1";
//    String FamilyName = mDatabase.child("UserDB").child(userId).get();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        ListView flist = (ListView)findViewById(R.id.listview_food2);

        btn_add_food = (Button)findViewById(R.id.btn_add_food);
        dilaog01 = new Dialog(FoodActivity.this);       // Dialog ?????????
        dilaog01.requestWindowFeature(Window.FEATURE_NO_TITLE); // ????????? ??????
        dilaog01.setContentView(R.layout.barcodeorcustom);
        dialog02 = new Dialog(FoodActivity.this);       // Dialog ?????????
        dialog02.requestWindowFeature(Window.FEATURE_NO_TITLE); // ????????? ??????
        dialog02.setContentView(R.layout.plus_dialog_layout);
        dialog03 = new Dialog(FoodActivity.this);       // Dialog ?????????
        dialog03.requestWindowFeature(Window.FEATURE_NO_TITLE); // ????????? ??????
        dialog03.setContentView(R.layout.search_dialog);
        dialog04 = new Dialog(FoodActivity.this);       // Dialog ?????????
        dialog04.requestWindowFeature(Window.FEATURE_NO_TITLE); // ????????? ??????
        dialog04.setContentView(R.layout.search_result);
        dialog05 = new Dialog(FoodActivity.this);       // Dialog ?????????
        dialog05.requestWindowFeature(Window.FEATURE_NO_TITLE); // ????????? ??????
        dialog05.setContentView(R.layout.plus_dialog_layout);
        dialog07 = new Dialog(FoodActivity.this);       // Dialog ?????????
        dialog07.requestWindowFeature(Window.FEATURE_NO_TITLE); // ????????? ??????
        dialog07.setContentView(R.layout.search_result);
        dialog07_1 = new Dialog(FoodActivity.this);       // Dialog ?????????
        dialog07_1.requestWindowFeature(Window.FEATURE_NO_TITLE); // ????????? ??????
        dialog07_1.setContentView(R.layout.auto_search_dialog);
        dialog08 = new Dialog(FoodActivity.this);       // Dialog ?????????
        dialog08.requestWindowFeature(Window.FEATURE_NO_TITLE); // ????????? ??????
        dialog08.setContentView(R.layout.plus_dialog_layout);

        notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE); // ??????
        alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE); // ??????
        mCalender = new GregorianCalendar(); // ??????
        Log.v("HelloAlarmActivity", mCalender.getTime().toString()); // ??????
//        ArrayList<ItemData> fData = new ArrayList<>();
//        ItemData fItem = new ItemData();
//        for (int i=0; i<strDate.length ; ++i)
//        {
//            ItemData fItem = new ItemData();
//            fItem.strTitle = "????????? " + (i+1); //strTitle -> ?????? ??????
//            fItem.strDate = strDate[nDatCnt++];
//            fData.add(fItem);
//            if (nDatCnt >= strDate.length) nDatCnt = 0;
//        }

// ListView, Adapter ?????? ??? ?????? ------------------------

        // ListAdapter fAdapter = new ListAdapter(fData);
        //flist.setAdapter(fAdapter);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1);
        flist.setAdapter(adapter);
        Intent refnameIntent = getIntent();
        String fridgename = refnameIntent.getStringExtra("fridgename");
        String fridgername = refnameIntent.getStringExtra("category");
        Fridge = new Room(fridgename);
        nameslist = new ArrayList<String>();


        category = refnameIntent.getStringExtra("category");
        //user_name = refnameIntent.getStringExtra("userid");
        familyname = refnameIntent.getStringExtra("familyid");
        conditionRef = mDatabase.child("HomeDB").child(familyname).child("fridgelist").child(fridgename).child("foodlist");
        //DatabaseReference userRef = mDatabase.child("UserDB").child(user_name);
        conditionRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                adapter.add(dataSnapshot.child("name").getValue(String.class));
                Product p = new Product(dataSnapshot.getKey(),dataSnapshot.child("name").getValue(String.class), dataSnapshot.child("placedetail").getValue(String.class), dataSnapshot.child("count").getValue(Integer.class),dataSnapshot.child("date").getValue(String.class));
                Fridge.addProduct(p);
                nameslist.add(dataSnapshot.child("name").getValue(String.class) + "<" + fridgername);
                //Log.d("MainActivity", "ChildEventListener - onChildAdded : " + dataSnapshot + dataSnapshot.getKey());
//                fItem.strTitle =  "t";//dataSnapshot.getKey(); //strTitle -> ?????? ??????
//                fItem.strDate =  "1";//dataSnapshot.child("count").getValue(String.class);
//                fData.add(fItem);
                Log.d("MainActivity", "ChildEventListener - onChildChanged : ");
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String oldname = Fridge.searchProductbyid(dataSnapshot.getKey()).getName();
                Fridge.updateProduct(dataSnapshot.getKey(),dataSnapshot.child("name").getValue(String.class), dataSnapshot.child("placedetail").getValue(String.class), dataSnapshot.child("count").getValue(Integer.class));
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
                Fridge.delProduct(dataSnapshot.child("name").getValue(String.class));
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

        flist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showDialog06(position);

            }
        });




//        userRef.addChildEventListener(new ChildEventListener()
//        {
//            @Override
//            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
//                //adapter.add(snapshot.getValue(String.class));
//                conditionRef = mDatabase.child("HomeDB").child(snapshot.getValue(String.class)).child("fridgelist").child(fridgename).child("foodlist");
//                adapter.clear();
//                mkChildEventListener(adapter);
//
//            }
//
//            @Override
//            public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
//                conditionRef = mDatabase.child("HomeDB").child(snapshot.getValue(String.class)).child("Fridge");
//                adapter.clear();
//                mkChildEventListener(adapter);
////                adapter.clear();
////                conditionRef = mDatabase.child("HomeDB").child(snapshot.getValue(String.class)).child("Fridge");
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull @NotNull DatabaseError error) {
//
//            }
//        });


        btn_add_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog01();
            }
        });
    }

    @Override //onResume ???????????? ????????? ?????????
    public void onResume() {
        super.onResume();

        getSupportActionBar().setTitle("Food");
    }

//    public void mkChildEventListener(ArrayAdapter<String> adapter){
//        conditionRef.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//
//                adapter.add(dataSnapshot.getKey());
//                //Log.d("MainActivity", "ChildEventListener - onChildAdded : " + dataSnapshot + dataSnapshot.getKey());
////                fItem.strTitle =  "t";//dataSnapshot.getKey(); //strTitle -> ?????? ??????
////                fItem.strDate =  "1";//dataSnapshot.child("count").getValue(String.class);
////                fData.add(fItem);
//                Log.d("MainActivity", "ChildEventListener - onChildChanged : ");
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//                Log.d("MainActivity", "ChildEventListener - onChildChanged : " + dataSnapshot);
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//                Log.d("MainActivity", "ChildEventListener - onChildRemoved : " + dataSnapshot.getKey());
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//                Log.d("MainActivity", "ChildEventListener - onChildMoved" + s);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Log.d("MainActivity", "ChildEventListener - onCancelled" + databaseError.getMessage());
//            }
//        });
//    }

    private String getTodaysDate() {
        Calendar ca1 = Calendar.getInstance();
        int year = ca1.get(Calendar.YEAR);
        int month = ca1.get(Calendar.MONTH);
        int day = ca1.get(Calendar.DAY_OF_MONTH);
        month = month +1;
        return  makeDateString(day, month, year);


    }

    public void showDialog01(){ //??????????????? ??????
        dilaog01.show(); // ??????????????? ?????????
        dilaog01.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // *????????? ???: findViewById()??? ??? ?????? -> ?????? ????????? ??????????????? ????????? ????????? ??????.


        Button barcode = dilaog01.findViewById(R.id.barcode);
        barcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //???????????? ?????? ???????????????
                Log.d("??????", "???????????? ?????? ?????????");
                // ????????? ?????? ??????


                dilaog01.dismiss(); // ??????????????? ??????
                BarcodeScanner(); //?????????????????? ??????



            }
        });
        Button custom = dilaog01.findViewById(R.id.custom);
        custom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //???????????? ?????? ???????????????
                Log.d("??????", "???????????? ?????? ?????????");
                // ????????? ?????? ??????
                dilaog01.dismiss();
                showDialog02();
            }
        });
    }
//    public void Camera(){//????????? ??????
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
//                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri); //?????? ???????????? ?????? ????????????
//                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
//            }
//        }
//    }

//    private File createImagefile() throws IOException{ //????????? ?????? ??????
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
//    private void rnjsgks(){//?????? ??????
//        TedPermission.with(getApplicationContext())
//                .setPermissionListener(permissionListener)
//                .setRationaleMessage("????????? ????????? ???????????????.")
//                .setDeniedMessage("?????????????????????.")
//                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
//                .check();
//
//    }


    PermissionListener permissionListener = new PermissionListener() { //??????????????????
        @Override
        public void onPermissionGranted() {
            Toast.makeText(getApplicationContext(), "????????? ?????????", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
            Toast.makeText(getApplicationContext(), "????????? ?????????", Toast.LENGTH_SHORT).show();

        }
    };
    public void BarcodeScanner(){
        IntentIntegrator intentIntegrator = new IntentIntegrator(
                FoodActivity.this
        );

        intentIntegrator.setPrompt("?????? ?????? ???-> Flash On");

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
            Log.d("??????", doc.getDocumentElement().getNodeName());
            NodeList nodeList = doc.getElementsByTagName("row");
            Log.d("????????????", String.valueOf(nodeList.getLength()));
            for (int temp = 0; temp<nodeList.getLength(); temp++){
                Node nNode = nodeList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE){
                    Element eElement = (Element) nNode;
                    Log.d("??????", "???????????? " + getTagValue("PRDT_NM", eElement));
                    Log.d("??????", "?????????" + getTagValue("CMPNY_NM", eElement));
                    name = getTagValue("PRDT_NM", eElement); //???????????? name??? company??? ??? ????????????
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
        buffer.append("?????? ???\n");
        return "?????????";
    }
    private String getTagValue(String tag, Element eElement){ //????????? ?????? ??????
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
            
            //result ??? null??? ?????????
            builder = new AlertDialog.Builder(
                    FoodActivity.this
            );
            builder.setTitle("??????");

            Barcodedata = intentResult.getContents(); //????????? ??????
//            getXmlData();

//
            String queryUrl = "https://openapi.foodsafetykorea.go.kr/api/".concat(key).concat("/I2570/xml/1/1/BRCD_NO=").concat(Barcodedata);

            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    try  {

                        getXmlData(queryUrl);
                        Log.d("??????", result1+name+company); //???????????? ????????? ??? ??????


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
            builder.setMessage("?????????:" + name+ "  ?????????:" +company); //?????? ????????? ????????? ?????????
            Log.d("?????????", name+company);


            builder.setPositiveButton("??????", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int which) {
                    //Diamiss ???????????????
                    dialogInterface.dismiss();
                }
            });
            builder.setNegativeButton("??????", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
//            builder.create().show();


            showdialog05();

        }else{
            //result content??? null??????
            Toast.makeText(getApplicationContext(), "???????????? ??????????????????.", Toast.LENGTH_SHORT).show();
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
        Log.d("????????????", year+ "???"+month+"???" + day + "???" );
        date = year + "-" + month + "-" + day;
        return year+ "???" + month+"???"+ day +"???"; ///////////////////////////////////// ???????????? ????????????
    }

    public void openDatePicker(View view){
        datePickerDialog.show();

    }

    public void showDialog02(){ //???????????? ?????? ???????????? ???????????????????????? ??????
        Button plus_button = dialog02.findViewById(R.id.plus_button);
        initDatePicker();
        dateset = (Button)dialog02.findViewById(R.id.dateset);
        dateset.setText(getTodaysDate());
        dialog02.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        plus_button.setText("??????");


        dialog02.show(); // ??????????????? ?????????


        plus_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //??????

                EditText fnameinput = (EditText)dialog02.findViewById(R.id.fnameInput);
                EditText fposinput = (EditText)dialog02.findViewById(R.id.fposInput);
                EditText fcountinput = (EditText)dialog02.findViewById(R.id.fcountInput);




                String foodname = fnameinput.getText().toString();
                String f_detail_place = fposinput.getText().toString();
                int fcount = Integer.parseInt(fcountinput.getText().toString());
                String dlimit = dateset.getText().toString();
                Map<String, Object> taskMap = new HashMap<String, Object>();
                taskMap.put("name", foodname);
                taskMap.put("count", fcount);
                taskMap.put("placedetail", f_detail_place);
                taskMap.put("date", dlimit);

                newUID = UUID.randomUUID();
                String nfoodid = newUID.toString();
                setAlarm(dlimit);
                conditionRef.child(nfoodid).setValue(taskMap);
                //conditionRef = mDatabase.child("HomeDB").child("family2").child("Fridge");
                Log.d("??????", foodname);


                dialog02.dismiss(); // ??????????????? ??????




            }
        });
        Button no_plus_button = dialog02.findViewById(R.id.no_plus_button);
        no_plus_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //??????
                Log.d("??????", "?????? ?????????");
                // ????????? ?????? ??????
                dialog02.dismiss();
            }
        });
    }

    private void setAlarm(String date) {
        //AlarmReceiver??? ??? ??????
        Intent receiverIntent = new Intent(FoodActivity.this, AlarmRecevier.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(FoodActivity.this, 0, receiverIntent, 0);

        String from = date + " 07:00:00";
        //String from = "2021-05-17 03:37:00"; //????????? ????????? ????????? ??????

        //?????? ????????? ???????????? ????????????
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy???MM???dd??? HH:mm:ss");
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
                showDialog07_1();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
    public void showdialog05(){
        dialog05.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));



        initDatePicker();
        dateset = (Button)dialog05.findViewById(R.id.dateset);
        dateset.setText(getTodaysDate());
        dialog05.show();

        EditText fnameinput = dialog05.findViewById(R.id.fnameInput);
        fnameinput.setText(name + company);
        ////////////////////////////////////????????? ??????????????? ????????? ??? ??????///////////////////////////

        Button button = dialog05.findViewById(R.id.plus_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog05.dismiss();
            }
        });

    }

//    public void showDialog03(){ //??????????????? ??????(??????)
//        dialog03.show(); // ??????????????? ?????????
//        dialog03.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        EditText edittext_searchname = dialog03.findViewById(R.id.edittext_searchname);
//
//
//        dialog03.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
//
//        Button searchbutton = dialog03.findViewById(R.id.pbutton);
//
//        // *????????? ???: findViewById()??? ??? ?????? -> ?????? ????????? ??????????????? ????????? ????????? ??????.
//
//        searchbutton.setText("??????");
//
//        searchbutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) { //?????? ???????????????
//                Log.d("??????", "???????????????");
//                Log.d("??????", "???????????????");
//                // ????????? ?????? ??????
//                ///////////////////////????????????////////////////////
//                Editable search = edittext_searchname.getText();
//                //////////edittext??? ????????? ????????? ????????? query??? ????????????.//////
//                Query query = conditionRef.child(String.valueOf(search));
//                conditionRef.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
//                        if(!snapshot.hasChild(String.valueOf(search)))
//                            stringquery = "??????????????? ????????????.";
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
//            public void onClick(View view) { //?????? ???????????????
//                Log.d("??????", "????????????");
//                // ????????? ?????? ??????
//                dialog03.dismiss();
//
//            }
//        });
//    }
    public void showDialog06(int index){ //???????????? ?????? ???????????? ???????????????????????? ??????
        Button plus_button = dialog02.findViewById(R.id.plus_button);
        dialog02.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        initDatePicker();
        dateset = (Button)dialog02.findViewById(R.id.dateset);

        dialog02.show(); // ??????????????? ?????????
        EditText fnameinput = (EditText)dialog02.findViewById(R.id.fnameInput);
        EditText fposinput = (EditText)dialog02.findViewById(R.id.fposInput);
        EditText fcountinput = (EditText)dialog02.findViewById(R.id.fcountInput);
        dateset.setText(Fridge.getProductByIndex(index).getDate());
        fnameinput.setText(Fridge.getProductByIndex(index).getName());
        fposinput.setText(Fridge.getProductByIndex(index).getDetailPlace());
        fcountinput.setText(String.valueOf(Fridge.getProductByIndex(index).getCount()));//DB?????? ???????????? ?????????????????????///////////////////////
        String fid = Fridge.getProductByIndex(index).getId();
        plus_button.setText("??????");


        plus_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //??????
                String newfoodname = fnameinput.getText().toString();
                String newf_detail_place = fposinput.getText().toString();
                int newfcount = Integer.parseInt(fcountinput.getText().toString());
                Map<String, Object> ctaskMap = new HashMap<String, Object>();
                ctaskMap.put("name", newfoodname);
                ctaskMap.put("count", newfcount);
                ctaskMap.put("placedetail", newf_detail_place);
                //conditionRef.updateChildren(ctaskMap);
                conditionRef.child(fid).updateChildren(ctaskMap);
                //adapter.remove(Fridge.getProductByIndex(index).getName());
                //adapter.insert(newfoodname, index);
                //adapter.notifyDataSetChanged();
                //setAlarm();
                dialog02.dismiss(); // ??????????????? ??????
                ////////////////////////////////////EditText??? ????????? DB??? ??????/////////////////////////////////



            }
        });
        Button no_plus_button = dialog02.findViewById(R.id.no_plus_button);
        no_plus_button.setText("??????");
        no_plus_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //??????
                Log.d("??????", "?????? ?????????");

                dialog02.dismiss();
                showDialog07(index);
            }
        });
    }
    public void showDialog07(int index) { //??????????????? ??????

        dialog07.show();
        dialog07.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button button1 = dialog07.findViewById(R.id.btn_search_result);
        TextView textView = dialog07.findViewById(R.id.text_search_result);
        String fid = Fridge.getProductByIndex(index).getId();
        //String fname = Fridge.getProductByIndex(index).getName();
        button1.setText("???");
        textView.setText("?????? ?????????????????????????");
        textView.setTextSize(20);

        // *????????? ???: findViewById()??? ??? ?????? -> ?????? ????????? ??????????????? ????????? ????????? ??????.


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //??? ??????????????????
                conditionRef.child(fid).setValue(null);
                //adapter.remove(fname);
                //adapter.notifyDataSetChanged();


                ////////////////////////DB ??????////////////////////////
                dialog07.dismiss();

            }
        });
    }
    public void showDialog07_1(){  //???????????? ???????????????
        dialog07_1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        AutoCompleteTextView editText = dialog07_1.findViewById(R.id.actv);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nameslist);
        editText.setAdapter(adapter);
        dialog07_1.show();
        editText.setText("");
        editText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                item = nameslist.get(position);
                int a = item.indexOf("<");
                item = item.substring(0,a);

                dialog07_1.dismiss();
                showDialog08();
            }
        });


    }
    public void showDialog08(){
        initDatePicker();
        dateset = (Button)dialog08.findViewById(R.id.dateset);
        dialog08.show();
        dialog08.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        EditText editText = dialog08.findViewById(R.id.fnameInput);

        EditText fnameinput = (EditText)dialog08.findViewById(R.id.fnameInput);
        EditText fposinput = (EditText)dialog08.findViewById(R.id.fposInput);
        EditText fcountinput = (EditText)dialog08.findViewById(R.id.fcountInput);
        Product searchProduct = Fridge.searchProduct(item);
        dateset.setText(searchProduct.getDate());
        fnameinput.setText(searchProduct.getName());
        fposinput.setText(searchProduct.getDetailPlace());
        fcountinput.setText(String.valueOf(searchProduct.getCount()));


        ////@@@@@@@@@@@@@@@@@@@@@@@@@@@///DB??? ????????????, ???????????? ?????????????????? ????????????//////@@@@@@@@@@@@@@@@@@@@@//

        editText.setText(item);//////////.....????????????,,,??????????????????////////////setText??? ????????????
        Button button1 = dialog08.findViewById(R.id.plus_button);
        Button button2 = dialog08.findViewById(R.id.no_plus_button);
        button1.setText("??????");
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newfoodname = fnameinput.getText().toString();
                String newf_detail_place = fposinput.getText().toString();
                int newfcount = Integer.parseInt(fcountinput.getText().toString());
                Map<String, Object> ctaskMap = new HashMap<String, Object>();
                ctaskMap.put("name", newfoodname);
                ctaskMap.put("count", newfcount);
                ctaskMap.put("placedetail", newf_detail_place);
                //conditionRef.updateChildren(ctaskMap);
                conditionRef.child(searchProduct.getId()).updateChildren(ctaskMap);
                //setAlarm();
                /////////////////////////////////'@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@////////-> ??????????????? ???????????????
                dialog08.dismiss();
                Toast.makeText(FoodActivity.this,"??????????????? ?????????????????????.", Toast.LENGTH_SHORT).show();
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
