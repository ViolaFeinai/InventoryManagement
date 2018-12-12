package com.example.viola.engdict;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
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

public class ShowProductsofLocation extends AppCompatActivity {
    String productssumUrl;
    int productssum;
    String productsUrl;
    String locationameUrl;
    String imagenameUrl;
    String typenameUrl;
    String descriptionUrl;
    String showallproductsUrl;
    String result;
    String result1;
    String typename="";
    String descrtiption="";
    String[] mistarray = {"1000"};
    String[] mistarray1 = {"1001"};
    String[] mistarray2 = {"1002"};
    String[] mistarray3 = {"1003"};
    String[] mistarray4 = {"1004"};
    String[] mistarray5 = {"1005"};
    String[] mistarray6 = {"1006"};
    String[] mistarray7 = {"1007"};
    int locationid;
    int arrayposition=0;
    String imagename="";
    String flag;
    String locationame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_products_of_location);
        getWindow().getDecorView().setBackgroundColor(Color.WHITE);
        Intent intent = getIntent();
        flag = intent.getStringExtra("flag");
        if (flag.equals("A")){
            ImageView imageview = findViewById(R.id.location);
            imageview.setImageDrawable(getResources().getDrawable(R.drawable.locationfilled));
            String location =intent.getStringExtra("locationid");
            locationid = Integer.parseInt(location);
            productssumUrl = "http://u779583388.hostingerapp.com/productsum.php?locationid="+ locationid+"";
            productsUrl = "http://u779583388.hostingerapp.com/showproductsoflocation.php?locationid="+ locationid+"";
            try {
                locationameUrl = "http://u779583388.hostingerapp.com/getlocationame.php?locationid="+ locationid+"";
                locationame = new AKLocationame().execute(locationameUrl).get();
                TextView textviewlocname = findViewById(R.id.locationame);
                textviewlocname.setText(locationame);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        } else if (flag.equals("B")) {
            ImageView imageview = findViewById(R.id.products);
            imageview.setImageDrawable(getResources().getDrawable(R.drawable.productsfilled));
            LinearLayout parent = findViewById(R.id.toptab);
            TextView instoreview = findViewById(R.id.locationame);
            instoreview.setText("In Store");
            TextView soldview = new TextView(this);
            soldview.setText("Sold");
            soldview.setTextSize(40);

            LinearLayout.LayoutParams lpinstore = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT, 1f);
            LinearLayout.LayoutParams lpsold =new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT, 1f);
            //lpsold.addRule(RelativeLayout.CENTER_IN_PARENT);
            //psold.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            //lpsold.setMargins(50, 0, 0, 0);
            lpsold.setMargins(100,0,0,0);
            //lpsold.addRule(RelativeLayout.RIGHT_OF, instoreview.getId());
            instoreview.setLayoutParams(lpinstore);
            parent.addView(soldview, lpsold);
            productssumUrl = "http://u779583388.hostingerapp.com/productsum.php";
            productsUrl = "http://u779583388.hostingerapp.com/showproductsoflocation.php";
        }

        try {
            //productssumUrl = "http://192.168.1.70/inventorymanagement/productsum.php?locationid="+ locationid+"";
            String stringsum = new AKProductsSum().execute(productssumUrl).get();
            productssum = Integer.parseInt(stringsum);
            if (productssum==0){
                RelativeLayout parent = findViewById(R.id.parent);
                final ImageView imageviewemptybox = new ImageView(ShowProductsofLocation.this);
                imageviewemptybox.setImageDrawable(getResources().getDrawable(R.drawable.emptybox));
                imageviewemptybox.setId(productssum+1);
                RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(200,200);
                //lp3.addRule(RelativeLayout.RIGHT_OF, imageview.getId());
                lp.addRule(RelativeLayout.CENTER_IN_PARENT);
                lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                lp.setMargins(50, 0, 0, 0);
                final TextView noproductsview = new TextView(ShowProductsofLocation.this);
                noproductsview.setText("There are no products in "+locationame);
                noproductsview.setTextSize(20);
                RelativeLayout.LayoutParams lp1 =new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                lp1.addRule(RelativeLayout.RIGHT_OF, imageviewemptybox.getId());
                lp1.addRule(RelativeLayout.CENTER_IN_PARENT);
                // lp1.addRule(RelativeLayout.BELOW, locationtitle.getId());
                //ScrollView scrollview = findViewById(R.id.scrollview);
                //parent.removeView(scrollview);
                parent.addView(imageviewemptybox, lp);
                parent.addView(noproductsview, lp1);
            } else {
                //productsUrl = "http://192.168.1.70/inventorymanagement/showproductsoflocation.php?locationid="+ locationid+"";
                new AKShowProducts().execute(productsUrl);
            }
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

    class AKLocationame extends AsyncTask<String, Void, String> {
        public AKLocationame() {
            // TODO Auto-generated constructor stub
        }

        protected void onPreExecute() {
            //display progress dialog.
        }
        @Override
        protected String doInBackground(String... urls) {
            URL url;
            try {
                url = new URL(locationameUrl);
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

    class AKProductsSum extends AsyncTask<String, Void, String> {
        public AKProductsSum() {
            // TODO Auto-generated constructor stub
        }

        protected void onPreExecute() {
            //display progress dialog.
        }
        @Override
        protected String doInBackground(String... urls) {
            URL url;
            try {
                url = new URL(productssumUrl);
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
    }

    class AKShowProducts extends AsyncTask<String, Void, String[]>{
        public AKShowProducts() {
            // TODO Auto-generated constructor stub
        }

        protected void onPreExecute() {
            //display progress dialog.
        }
        @Override
        protected String[] doInBackground(String... urls) {
            URL url;
            try {
                url = new URL(productsUrl);
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
            String[] testarray = new String[4*productssum];
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
                    String productid=json_data.getString("productid");
                    String productname=json_data.getString("productname");
                    String typeid=json_data.getString("typeid");
                    String quantity=json_data.getString("quantity");
                    testarray[arrayposition]=productid;
                    arrayposition++;
                    testarray[arrayposition]=productname;
                    arrayposition++;
                    testarray[arrayposition]=typeid;
                    arrayposition++;
                    testarray[arrayposition]=quantity;
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
                for (int k = 0; k<result.length; k=k+4){
                    //productid
                    final String productid = result[counter];
                    imagenameUrl = "http://u779583388.hostingerapp.com/getproductimagename.php?typeid="+ result[counter]+"";
                    RelativeLayout parent = new RelativeLayout(ShowProductsofLocation.this);
                    parent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    counter++;
                    //productname
                    final TextView textview = new TextView(ShowProductsofLocation.this);
                    final String producctname = result[counter];
                    textview.setText(producctname);
                    textview.setTextSize(18);
                    textview.setId(counter);
                    RelativeLayout.LayoutParams lp1 =new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    textview.setHeight(200);
                    counter++;
                    //typeid
                    typenameUrl = "http://u779583388.hostingerapp.com/getproductypename.php?typeid="+ result[counter]+"";
                    descriptionUrl = "http://u779583388.hostingerapp.com/getdescription.php?typeid="+ result[counter]+"";
                    imagenameUrl = "http://u779583388.hostingerapp.com/getproductimagename.php?typeid="+ result[counter]+"";
                    try {
                        typename = new AKTypename().execute(typenameUrl).get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    final ImageView imageview = new ImageView(ShowProductsofLocation.this);
                    imageview.setId(counter);
                    try {
                        imagename = new AKImagename().execute(imagenameUrl).get();
                        //final ImageView imageview = new ImageView(ShowProductsofLocation.this);
                        int resID = getResources().getIdentifier(imagename , "drawable", getPackageName());
                        imageview.setImageResource(resID);
                        RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(300,400);
                        imageview.setLayoutParams(params);
                        parent.addView(imageview);
                        //imageview.setId(counter);
                        lp1.addRule(RelativeLayout.RIGHT_OF, imageview.getId());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    counter++;
                    //quantity
                    final TextView quanttextview = new TextView(ShowProductsofLocation.this);
                    quanttextview.setText("Quantity : "+result[counter]);
                    quanttextview.setTextSize(15);
                    quanttextview.setId(counter);
                    RelativeLayout.LayoutParams lp2 =new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    lp2.addRule(RelativeLayout.BELOW, textview.getId());
                    lp2.addRule(RelativeLayout.RIGHT_OF, imageview.getId());
                    counter++;

                    final ImageView imageviewnext = new ImageView(ShowProductsofLocation.this);
                    imageviewnext.setImageDrawable(getResources().getDrawable(R.drawable.next));
                    RelativeLayout.LayoutParams lp3=new RelativeLayout.LayoutParams(130,130);
                    //lp3.addRule(RelativeLayout.RIGHT_OF, imageview.getId());
                    lp3.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    lp3.addRule(RelativeLayout.BELOW,  textview.getId());
                    lp3.setMargins(0, 0, 50, 0);
                    imageviewnext.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(ShowProductsofLocation.this, ScanProduct.class);
                            intent.putExtra("flag","true");
                            intent.putExtra("productid",productid);
                            startActivity(intent);
                        }
                    });


                    final View view = new View(ShowProductsofLocation.this);
                    view.setLayoutParams(new LinearLayout.LayoutParams(3000, 6));
                    view.setBackgroundColor(Color.parseColor("#eeeeee"));

                    LinearLayout layout= findViewById(R.id.linearproducts);
                    parent.addView(textview, lp1);
                    parent.addView(quanttextview, lp2);
                    parent.addView(imageviewnext, lp3);

                    layout.addView(parent);
                    layout.addView(view);
                }
            }
        }
    }

    class AKImagename extends AsyncTask<String, Void, String> {
        public AKImagename() {
            // TODO Auto-generated constructor stub
        }

        protected void onPreExecute() {
            //display progress dialog.
        }
        @Override
        protected String doInBackground(String... urls) {
            URL url;
            try {
                url = new URL(imagenameUrl);
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
    }

    class AKTypename extends AsyncTask<String, Void, String> {
        public AKTypename() {
            // TODO Auto-generated constructor stub
        }

        protected void onPreExecute() {
            //display progress dialog.
        }
        @Override
        protected String doInBackground(String... urls) {
            URL url;
            try {
                url = new URL(typenameUrl);
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
    }

    class AKDescription extends AsyncTask<String, Void, String> {
        public AKDescription() {
            // TODO Auto-generated constructor stub
        }

        protected void onPreExecute() {
            //display progress dialog.
        }
        @Override
        protected String doInBackground(String... urls) {
            URL url;
            try {
                url = new URL(descriptionUrl);
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
    }

}
