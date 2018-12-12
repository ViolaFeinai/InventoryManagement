package com.example.viola.engdict;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;


public class MainActivity extends AppCompatActivity {
    public static final String PREFS_NAME = "MyPrefsFile";
    private static final String PREF_USERID = "UserID";
    String testcoo="";
    String UserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences pref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        testcoo = pref.getString(PREF_USERID,"");
        if (testcoo!=null&&!testcoo.equals("NoUser")&&!testcoo.equals("No Connected Service Found")&&!testcoo.equals(""))
        {
            Log.i("new111111111", testcoo);
            UserID= testcoo;
            setContentView(R.layout.activity_main);
            ImageView imageview = findViewById(R.id.menu);
            imageview.setImageDrawable(getResources().getDrawable(R.drawable.menufilled));
        }
        else {
            Intent intent = new Intent(this, LogIn.class);
            startActivity(intent);
        }
    }

    public void mainMenu(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("UserID",UserID);
        startActivity(intent);
    }

    public void scanProduct(View view) {
        Intent intent = new Intent(this, ScanProduct.class);
        intent.putExtra("UserID",UserID);
        intent.putExtra("flag","false");
        startActivity(intent);
    }

    public void showProducts(View view) {
        Intent intent = new Intent(this, ShowProductsofLocation.class);
        intent.putExtra("UserID",UserID);
        intent.putExtra("flag","B");
        startActivity(intent);
    }

    public void showLocations(View view) {
        Intent intent = new Intent(this, ShowLocations.class);
        intent.putExtra("UserID",UserID);
        startActivity(intent);
    }

    public void settings(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("UserID",UserID);
        startActivity(intent);
    }
}