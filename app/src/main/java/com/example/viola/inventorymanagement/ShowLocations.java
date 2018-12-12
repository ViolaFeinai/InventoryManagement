package com.example.viola.engdict;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.concurrent.ExecutionException;

import static android.widget.RelativeLayout.CENTER_IN_PARENT;

public class ShowLocations extends Activity {
    String locationsumUrl;
    String locationsUrl;
    String result;
    String[] mistarray = {"1000"};
    String[] mistarray1 = {"1001"};
    String[] mistarray2 = {"1002"};
    String[] mistarray3 = {"1003"};
    String[] mistarray4 = {"1004"};
    String[] mistarray5 = {"1005"};
    String[] mistarray6 = {"1006"};
    String[] mistarray7 = {"1007"};
    Integer locationsum;
    String result1;
    int arrayposition=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_locations);
        ImageView imageview = findViewById(R.id.location);
        imageview.setImageDrawable(getResources().getDrawable(R.drawable.locationfilled));
        try {
            locationsumUrl = "http://u779583388.hostingerapp.com/showlocationsum.php";
            String stringsum = new AKLocationsSum().execute(locationsumUrl).get();
            locationsum = Integer.parseInt(stringsum);
            locationsUrl = "http://u779583388.hostingerapp.com/showlocations.php";
            new AKShowLocations().execute(locationsUrl);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    public void mainMenu(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        //intent.putExtra("UserID",UserID);
        startActivity(intent);
    }

    public void scanProduct(View view) {
        Intent intent = new Intent(this, ScanProduct.class);
        //intent.putExtra("UserID",UserID);
        intent.putExtra("flag","false");
        startActivity(intent);
    }

    public void showProducts(View view) {
        Intent intent = new Intent(this, ShowProductsofLocation.class);
        //intent.putExtra("UserID",UserID);
        intent.putExtra("flag","B");
        startActivity(intent);
    }

    public void showLocations(View view) {
        Intent intent = new Intent(this, ShowLocations.class);
       // intent.putExtra("UserID",UserID);
        startActivity(intent);
    }

    public void settings(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        //intent.putExtra("UserID",UserID);
        startActivity(intent);
    }

    class AKLocationsSum extends AsyncTask<String, Void, String> {
        public AKLocationsSum() {
            // TODO Auto-generated constructor stub
        }

        protected void onPreExecute() {
            //display progress dialog.
        }
        @Override
        protected String doInBackground(String... urls) {
            URL url;
            try {
                url = new URL(locationsumUrl);
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
            }
        }
    }

    class AKShowLocations extends AsyncTask<String, Void, String[]>{
        public AKShowLocations() {
            // TODO Auto-generated constructor stub
        }

        protected void onPreExecute() {
            //display progress dialog.
        }
        @Override
        protected String[] doInBackground(String... urls) {
            URL url;
            try {
                url = new URL(locationsUrl);
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return mistarray;
            }
            HttpURLConnection conn;
            try {
                conn = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return mistarray1;
            }
            if(conn==null){
                return mistarray2;}
            conn.setAllowUserInteraction(false);
            try {
                conn.connect();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return mistarray3;
            }
            InputStream in;
            try{
                in = conn.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
                return mistarray4;
            }
            BufferedInputStream bis = new BufferedInputStream(in);
            BufferedReader br = null;
            StringBuilder sb = new StringBuilder();
            String line;
            String[] testarray = new String[3*locationsum];
            try {
                br = new BufferedReader(new InputStreamReader(in));
                while ((line=br.readLine()) != null) {
                    sb.append(line);
                }
                result1=sb.toString();
            } catch (IOException e) {
                e.printStackTrace();
                return mistarray5;
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        return mistarray6;
                    }
                }
            }
            try{
                JSONArray jArray1 = new JSONArray(result1);
                for(int m=0;m<jArray1.length();m++){
                    JSONObject json_data = jArray1.getJSONObject(m);
                    String locationname=json_data.getString("locationame");
                    String locationimage=json_data.getString("locationimage");
                    String locationid=json_data.getString("locationid");
                    testarray[arrayposition]=locationname;
                    arrayposition++;
                    testarray[arrayposition]=locationimage;
                    arrayposition++;
                    testarray[arrayposition]=locationid;
                    arrayposition++;
                }
            }catch(JSONException e){
                Log.e("log_tag", "Error parsing data "+e.toString());
                return mistarray7;
            }
            conn.disconnect();
            return testarray;
        }
        @Override
        protected void onPostExecute(String[] result) {
            if(result[0].equals("1000")||result[0].equals("1001")||result[0].equals("1002")||result[0].equals("1003")||result[0].equals("1004")||result[0].equals("1005")||result[0].equals("1006")||result[0].equals("1007")){
                Toast toast = Toast.makeText(getApplicationContext(), "Aaaaaaaaaaaaaax" + result[0],Toast.LENGTH_LONG);
                toast.show();
            }
            else {
                //Toast toast = Toast.makeText(getApplicationContext(), "Yeeeeeees"+result[0] + result[5],Toast.LENGTH_LONG);
                //toast.show();
                int counter=0;
                for (int k = 0; k<result.length; k=k+3){
                    LinearLayout parent = new LinearLayout(ShowLocations.this);
                    parent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    parent.setOrientation(LinearLayout.HORIZONTAL);
                    final TextView textview = new TextView(ShowLocations.this);
                    textview.setText(result[counter]);
                    counter++;
                    textview.setTextSize(25);
                    textview.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
                    textview.setHeight(400);
                    final ImageView imageview = new ImageView(ShowLocations.this);
                    int resID = getResources().getIdentifier(result[counter] , "drawable", getPackageName());
                    counter++;
                    imageview.setImageResource(resID);
                    imageview.setId(resID);
                    RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(400,300);
                    imageview.setLayoutParams(params);
                    final View view = new View(ShowLocations.this);
                    view.setLayoutParams(new LinearLayout.LayoutParams(3000, 6));
                    view.setBackgroundColor(Color.parseColor("#eeeeee"));
                    final ImageView imageviewnext = new ImageView(ShowLocations.this);
                    imageviewnext.setImageDrawable(getResources().getDrawable(R.drawable.next));
                    LinearLayout.LayoutParams param=new LinearLayout.LayoutParams(150,150);
                    imageviewnext.setLayoutParams(param);
                    final String locationid = result[counter];
                    Integer locationidint = Integer.parseInt(locationid);
                    imageviewnext.setId(locationidint);
                    counter++;

                    imageviewnext.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(ShowLocations.this, ShowProductsofLocation.class);
                            //intent.putExtra("UserID",UserID);
                            intent.putExtra("flag","A");
                            intent.putExtra("locationid",locationid);
                            startActivity(intent);

                        }
                    });

                    LinearLayout layout= findViewById(R.id.linearlocations);
                    parent.addView(imageview);
                    parent.addView(textview);
                    parent.addView(imageviewnext);
                    layout.addView(parent);
                    layout.addView(view);

                }
            }
        }
    }

}
