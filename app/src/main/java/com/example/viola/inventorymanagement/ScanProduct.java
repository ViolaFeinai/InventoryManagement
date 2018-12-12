package com.example.viola.engdict;

import android.content.Intent;
import android.graphics.Color;
import android.hardware.Camera;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ScanProduct extends AppCompatActivity {
    public final int CUSTOMIZED_REQUEST_CODE = 0x0000ffff;
    static Camera camera = null;
    private Spinner spinner2, spinner3;
    String addproductUrl;
    String checkproductUrl;
    String updatelocationUrl;
    String productname;
    String productid;
    String result;
    String result1;
    String checkproductid;
    String scanCode;
    public static List<String> locatonlist = new ArrayList();
    public static List<String> typeslist = new ArrayList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.scan_product);
        locatonlist = Lists.getLocationList();
        typeslist = Lists.getTypesList();
        Intent intent = getIntent();
        String flag = intent.getStringExtra("flag");
        if (flag.equals("true")){
            productid = intent.getStringExtra("productid");
            checkproductUrl = "http://u779583388.hostingerapp.com/showproduct.php?productid="+productid+"";
            try {
                checkproductid = new AKCheckProduct().execute(checkproductUrl).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        } else{
            IntentIntegrator scanner = new IntentIntegrator(this);
            //camera = Camera.open();
            scanner.initiateScan();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result =   IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this,    "Cancelled",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ScanProduct.this , MainActivity.class);
                startActivity(intent);
            } else {
                scanCode = result.getContents();
                checkproductUrl = "http://u779583388.hostingerapp.com/showproduct.php?productid="+scanCode+"";
                try {
                    checkproductid = new AKCheckProduct().execute(checkproductUrl).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    public void addnewproduct(View view) {
        Spinner spinner3 = findViewById(R.id.spinner3);
        String audiphonotype;
        audiphonotype = spinner3.getSelectedItem().toString();
        EditText mEditquantity = findViewById(R.id.quantity);
        Integer quantity;
        quantity = Integer.parseInt(mEditquantity.getText().toString());
        Spinner spinner2 = findViewById(R.id.spinner2);
        String location;
        location = spinner2.getSelectedItem().toString();
        long typeid = spinner3.getSelectedItemId() + 1;
        long locationid = spinner2.getSelectedItemId() + 1;
        Toast toast = Toast.makeText(getApplicationContext(), "name "+productname+" type "+typeid+" quantity "+quantity+" location "+locationid+"",Toast.LENGTH_LONG);
        //toast.show();
        //addproductUrl = "http://192.168.1.62:80/inventorymanagement/addproduct.php?productname=1234567890&audiphonotype=1&quantity=1&location=1";
        addproductUrl = "http://u779583388.hostingerapp.com/addproduct.php?productid="+productid+"&productname="+productname+"&audiphonotype="+typeid+"&quantity="+quantity+"&location="+locationid+"";
        new AKAddproduct().execute(addproductUrl);
    }

    public void updateproduct(View view) {
        Spinner spinner2 = findViewById(R.id.spinner2);
        long locationid = spinner2.getSelectedItemId() + 1;
        updatelocationUrl = "http://u779583388.hostingerapp.com/updatelocation.php?location="+locationid+"&productid="+productid+"";
        new AKUpdatelocation().execute(updatelocationUrl);
    }


    class AKCheckProduct extends AsyncTask<String, Void, String> {
        public AKCheckProduct() {
            // TODO Auto-generated constructor stub
        }

        protected void onPreExecute() {
            //display progress dialog.
        }
        @Override
        protected String doInBackground(String... urls) {
            URL url;
            try {
                url = new URL(checkproductUrl);
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return "1001";
            }
            HttpURLConnection conn;
            try {
                conn = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return "1002";
            }
            if(conn==null){
                return "1003";}
            conn.setAllowUserInteraction(false);
            try {
                conn.connect();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return e.toString();
            }
            //  conn.setDoOutput(true);
            InputStream in;
            try{
                in = conn.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
                return ""+e;
            }
            BufferedInputStream bis = new BufferedInputStream(in);
            BufferedReader br = null;
            StringBuilder sb = new StringBuilder();
            String line;
            try {
                br = new BufferedReader(new InputStreamReader(in));
                while ((line=br.readLine()) != null) {
                    sb.append(line);
                }
                result=sb.toString();
            } catch (IOException e) {
                e.printStackTrace();
                return "1006";
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        return "1007";
                    }
                }
            }

            conn.disconnect();
            return result;
        }
        @Override
        protected void onPostExecute(String result) {
            if (result.equals("1001")||result.equals("1002")||result.equals("1003")||result.equals("1004")||result.equals("1005")||result.equals("1006")||result.equals("1007")||result.equals("0")){
                Toast toast = Toast.makeText(getApplicationContext(), "Ooooopss"+result,Toast.LENGTH_LONG);
                toast.show();
                Log.i("UserIDLogin", result);
                //Intent intent = new Intent(context, LoginScreen.class);
                //startActivity(intent);
            } else if (result.equals("does not exist")){
                setContentView(R.layout.scan_product_2);
                getWindow().getDecorView().setBackgroundColor(Color.WHITE);
                productname = "Producto N" +scanCode;
                productid= scanCode;
                TextView nameView = findViewById(R.id.audiphonotitle);
                nameView.setText(productname);
                spinner2 = findViewById(R.id.spinner2);
                ArrayAdapter<String> dataAdapter = new ArrayAdapter(ScanProduct.this,
                        android.R.layout.simple_spinner_item, locatonlist);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(dataAdapter);
                spinner3 = findViewById(R.id.spinner3);
                ArrayAdapter<String> dataAdapter2 = new ArrayAdapter(ScanProduct.this,
                        android.R.layout.simple_spinner_item, typeslist);
                dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner3.setAdapter(dataAdapter2);

            } else {
                setContentView(R.layout.scan_product);
                getWindow().getDecorView().setBackgroundColor(Color.WHITE);
                try {
                    JSONArray jArray1 = new JSONArray(result);
                    JSONObject json_dataproduct = jArray1.getJSONObject(0);
                    JSONObject json_dataproducttype = jArray1.getJSONObject(1);
                    String prodname = json_dataproduct.getString("productname");
                    String quantity = json_dataproduct.getString("quantity");
                    String location = json_dataproduct.getString("locationid");
                    String typename = json_dataproducttype.getString("name");
                    String description = json_dataproducttype.getString("description");
                    String imagename = json_dataproducttype.getString("image");
                    int imageID = getResources().getIdentifier(imagename,
                            "drawable", getPackageName());
                    int locationid = Integer.parseInt(location);
                    //Toast toast = Toast.makeText(getApplicationContext(), "aaaaaaaaaaaa "+locationid,Toast.LENGTH_LONG);
                    //toast.show();
                    TextView textviewName = findViewById(R.id.audiphonotitle);
                    textviewName.setText(prodname);
                    TextView textviewType = findViewById(R.id.audiphonotype);
                    textviewType.setText(typename);
                    TextView textviewDescr = findViewById(R.id.audiphonodetails);
                    textviewDescr.setText(description);
                    ImageView imageView = findViewById(R.id.photo);
                    imageView.setImageResource(imageID);
                    TextView textviewQuantity = findViewById(R.id.quantity);
                    textviewQuantity.setText("Quantity : "+quantity);
                    spinner2 = findViewById(R.id.spinner2);
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter(ScanProduct.this,
                            android.R.layout.simple_spinner_item, locatonlist);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner2.setAdapter(dataAdapter);
                    spinner2.setSelection(locationid-1);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast toast = Toast.makeText(getApplicationContext(), "Json exception  "+e,Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        }
    }

    class AKAddproduct extends AsyncTask<String, Void, String> {
        public AKAddproduct() {
            // TODO Auto-generated constructor stub
        }

        protected void onPreExecute() {
            //display progress dialog.
        }
        @Override
        protected String doInBackground(String... urls) {
            URL url;
            try {
                url = new URL(addproductUrl);
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return "1001";
            }
            HttpURLConnection conn;
            try {
                conn = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return "1002";
            }
            if(conn==null){
                return "1003";}
            conn.setAllowUserInteraction(false);
            try {
                conn.connect();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return e.toString();
            }
            //  conn.setDoOutput(true);
            InputStream in;
            try{
                in = conn.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
                return e.toString();
            }
            BufferedInputStream bis = new BufferedInputStream(in);
            BufferedReader br = null;
            StringBuilder sb = new StringBuilder();
            String line;
            try {
                br = new BufferedReader(new InputStreamReader(in));
                while ((line=br.readLine()) != null) {
                    sb.append(line);
                }
                result=sb.toString();
            } catch (IOException e) {
                e.printStackTrace();
                return "1006";
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        return "1007";
                    }
                }
            }

            conn.disconnect();
            return result;
        }
        @Override
        protected void onPostExecute(String result) {
            if (result.equals("NoUser")||result.equals("1001")||result.equals("1002")||result.equals("1003")||result.equals("1004")||result.equals("1005")||result.equals("1006")||result.equals("1007")||result.equals("0")){
                Toast toast = Toast.makeText(getApplicationContext(), "Ooooopss"+result,Toast.LENGTH_LONG);
                toast.show();
                Log.i("UserIDLogin", result);
                //Intent intent = new Intent(context, LoginScreen.class);
                //startActivity(intent);
            }
            else {
                Toast toast = Toast.makeText(getApplicationContext(), ""+result,Toast.LENGTH_LONG);
                toast.show();
                Intent intent = new Intent(ScanProduct.this , MainActivity.class);
                startActivity(intent);
            }
        }
    }

    class AKUpdatelocation extends AsyncTask<String, Void, String> {
        public AKUpdatelocation() {
            // TODO Auto-generated constructor stub
        }

        protected void onPreExecute() {
            //display progress dialog.
        }
        @Override
        protected String doInBackground(String... urls) {
            URL url;
            try {
                url = new URL(updatelocationUrl);
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return "1001";
            }
            HttpURLConnection conn;
            try {
                conn = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return "1002";
            }
            if(conn==null){
                return "1003";}
            conn.setAllowUserInteraction(false);
            try {
                conn.connect();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return e.toString();
            }
            //  conn.setDoOutput(true);
            InputStream in;
            try{
                in = conn.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
                return e.toString();
            }
            BufferedInputStream bis = new BufferedInputStream(in);
            BufferedReader br = null;
            StringBuilder sb = new StringBuilder();
            String line;
            try {
                br = new BufferedReader(new InputStreamReader(in));
                while ((line=br.readLine()) != null) {
                    sb.append(line);
                }
                result=sb.toString();
            } catch (IOException e) {
                e.printStackTrace();
                return "1006";
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        return "1007";
                    }
                }
            }

            conn.disconnect();
            return result;
        }
        @Override
        protected void onPostExecute(String result) {
            if (result.equals("NoUser")||result.equals("1001")||result.equals("1002")||result.equals("1003")||result.equals("1004")||result.equals("1005")||result.equals("1006")||result.equals("1007")||result.equals("0")){
                Toast toast = Toast.makeText(getApplicationContext(), "Ooooopss"+result,Toast.LENGTH_LONG);
                toast.show();
                Log.i("UserIDLogin", result);
            }
            else {
                Toast toast = Toast.makeText(getApplicationContext(), "The location is updated ",Toast.LENGTH_LONG);
                toast.show();
                Intent intent = new Intent(ScanProduct.this, MainActivity.class);
                startActivity(intent);
            }
        }
    }

}

