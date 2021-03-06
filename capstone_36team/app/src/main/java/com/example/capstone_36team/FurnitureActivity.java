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
        dilaog01.requestWindowFeature(Window.FEATURE_NO_TITLE); // ????????? ??????
        dilaog01.setContentView(R.layout.barcodeorcustom);
        listView_item = (ListView) findViewById(R.id.listview_item);
        btn_add_item = (Button) findViewById(R.id.btn_add_item);
        dialog02 = new Dialog(FurnitureActivity.this);       // Dialog ?????????
        dialog02.requestWindowFeature(Window.FEATURE_NO_TITLE); // ????????? ??????
        dialog02.setContentView(R.layout.plus_dialog_layout_nofood);
        dialog03 = new Dialog(FurnitureActivity.this);       // Dialog ?????????
        dialog03.requestWindowFeature(Window.FEATURE_NO_TITLE); // ????????? ??????
        dialog03.setContentView(R.layout.search_result);
        dialog05 = new Dialog(FurnitureActivity.this);       // Dialog ?????????
        dialog05.requestWindowFeature(Window.FEATURE_NO_TITLE); // ????????? ??????
        dialog05.setContentView(R.layout.plus_dialog_layout_nofood);
        dialog06 = new Dialog(FurnitureActivity.this);       // Dialog ?????????
        dialog06.requestWindowFeature(Window.FEATURE_NO_TITLE); // ????????? ??????
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



        ///////????????????////////
        btn_add_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog01();
            }
        });

        ///////???????????? ???????????? ??????/////////
//        String[] strDate = {"1", "2", "3", "1", "5", "6"};  //strDate -> ?????? ??????
//        int nDatCnt=0;
//        ArrayList<ItemData> oData = new ArrayList<>();
//        for (int i=0; i<strDate.length ; ++i)
//        {
//            ItemData oItem = new ItemData();
//            oItem.strTitle = "????????? " + (i+1); //strTitle -> ?????? ??????
//            oItem.strDate = strDate[nDatCnt++];
//            oData.add(oItem);
//            if (nDatCnt >= strDate.length) nDatCnt = 0;
//        }

// ListView, Adapter ?????? ??? ?????? ------------------------
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
//                fItem.strTitle =  "t";//dataSnapshot.getKey(); //strTitle -> ?????? ??????
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
    public void showDialog01(){ //??????????????? ??????
        dilaog01.show(); // ??????????????? ?????????
        // *????????? ???: findViewById()??? ??? ?????? -> ?????? ????????? ??????????????? ????????? ????????? ???

        Button barcode = dilaog01.findViewById(R.id.barcode);
        barcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //???????????? ?????? ???????????????
                Log.d("??????", "???????????? ?????? ?????????");
                // ????????? ?????? ??????
                dilaog01.dismiss(); // ??????????????? ??????
                BarcodeScanner(); //????????? ????????? ??????



            }
        });
        Button custom = dilaog01.findViewById(R.id.custom);
        custom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //???????????? ?????? ???????????????
                Log.d("??????", "???????????? ?????? ?????????");
                // ????????? ?????? ??????
                showDialog02();
                dilaog01.dismiss();


            }
        });
    }
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
                FurnitureActivity.this
        );

        intentIntegrator.setPrompt("?????? ?????? ???-> Flash On");

        intentIntegrator.setBeepEnabled(true);
        intentIntegrator.setOrientationLocked(true);
        intentIntegrator.setCaptureActivity(Capture.class);
        intentIntegrator.initiateScan();

    }
    public void showDialog02(){ //???????????? ?????? ???????????? ???????????????????????? ??????



        dialog02.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button plus_button = dialog02.findViewById(R.id.plus_button2);
        plus_button.setText("??????");
        dialog02.show(); // ??????????????? ?????????
        plus_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //??????

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
                Log.d("??????", foodname);


                ///////foodActivity????????? ???????????? dialog??? ?????? layout ??????(plus_dialog_layout_nofood.xml) ???????????????!!!!!@@@@@@@
                ////??????????????? ?????????/////
                //////????????? EditText??? ?????? DB?????? ????????????/////////////


                dialog02.dismiss(); // ??????????????? ??????




            }
        });
        Button no_plus_button = dialog02.findViewById(R.id.no_plus_button2);
        no_plus_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //??????
                Log.d("??????", "?????? ?????????");
                // ????????? ?????? ??????
                dialog02.dismiss();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) { //????????? ?????? ??????(??????)
        getMenuInflater().inflate(R.menu.trash_button, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_trash:
                Log.d("????????????", "?????????");
                showDialog03();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
    public void showDialog03(){ //??????????????? ??????

        Button searchbutton = dialog03.findViewById(R.id.btn_search_result);
        searchbutton.setText("??????");
        dialog03.show(); // ??????????????? ?????????
        TextView edittext_searchname = dialog03.findViewById(R.id.text_search_result);
        edittext_searchname.setTextSize(20);
        edittext_searchname.setText("??????????????? ?????????????????????????");

        // *????????? ???: findViewById()??? ??? ?????? -> ?????? ????????? ??????????????? ????????? ????????? ??????.



        searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //?????? ???????????????
                conditionRef.getParent().setValue(null);
                ////////////////????????? DB????????????//////////

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

            //result ??? null??? ?????????
            builder = new AlertDialog.Builder(
                    FurnitureActivity.this
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
    public void showdialog05(){
        dialog05.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));





        dialog05.show();

        EditText fnameinput = dialog05.findViewById(R.id.fnameInput2);
        fnameinput.setText(name + company);
        ////////////////////////////////////????????? ??????????????? ????????? ??? ??????///////////////////////////

        Button button = dialog05.findViewById(R.id.plus_button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog05.dismiss();
            }
        });

    }
    private String getTagValue(String tag, Element eElement){ //????????? ?????? ??????
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
        button.setText("??????");
        String fid = Furniture.getProductByIndex(index).getId();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //???????????? ???????????????
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
        button1.setText("??????");
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conditionRef.child(fid).setValue(null);
                dialog06.dismiss();
                // ???????????? ???????????????
            }
        });
    }


}
