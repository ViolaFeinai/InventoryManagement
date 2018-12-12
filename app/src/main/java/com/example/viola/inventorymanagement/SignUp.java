package com.example.viola.engdict;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.webkit.CookieManager;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SignUp extends AppCompatActivity {
    public static final String PREFS_NAME = "MyPrefsFile";
    private static final String PREF_USERID = "UserID";
    String signUpUrl;
    String result;
    Integer UserID;
    EditText Edit;
    String text;
    boolean flagusername;
    boolean flagemail;
    boolean flagpassword;
    boolean flagpassword2;
    EditText mEditUsername;
    EditText mEditEmail;
    EditText mEditPassword;
    EditText mEditRetypePass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        mEditUsername = findViewById(R.id.editTextUsername);
        String username;
        username = mEditUsername.getText().toString();
        mEditEmail = findViewById(R.id.editTextEmail);
        String email;
        email = mEditEmail.getText().toString();
        mEditPassword = findViewById(R.id.editTextPassword);
        String password;
        password = mEditPassword.getText().toString();
        mEditRetypePass = findViewById(R.id.editTextReenterPassword);
        mEditUsername.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    text = mEditUsername.getText().toString();
                    Edit   = findViewById(R.id.editTextUsername);
                    if (text.length()<5){
                        Edit.setError("The username should be at least 5 letters long");
                        flagusername=false;
                    } else {
                        //stringUrl1="http://13.74.153.144/andscripts/getusernames2.php?value-1='" + text + "'";
                        //new AsKlunicusername().execute(stringUrl1);
                    }
                }
            }
        });
        mEditEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    text = mEditEmail.getText().toString();
                    Edit   = findViewById(R.id.editTextEmail);
                    boolean mail;
                    mail =android.util.Patterns.EMAIL_ADDRESS.matcher(text).matches();
                    if(mail==false){
                        Edit.setError("The email you entered is not valid");
                        flagemail=false;
                    } else {
                        //stringUrl1="http://13.74.153.144/andscripts/getemails2.php?value-1='" + text + "'";
                        //new AsKlunicusername().execute(stringUrl1);
                    }
                }
            }
        });
        mEditPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    text = mEditPassword.getText().toString();
                    Edit   = findViewById(R.id.editTextPassword);
                    if(text.length()<7) {
                        Edit.setError("The password should be at least 7 letters long");
                        flagpassword=false;
                    } else {
                        flagpassword=true;
                        }
                }
            }
        });
        mEditRetypePass.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    text = mEditRetypePass.getText().toString();
                    String text2 = mEditPassword.getText().toString();
                    Edit   = findViewById(R.id.editTextReenterPassword);
                    if(text.equals(text2)==false) {
                        Edit.setError("The passwords do not match");
                        flagpassword2=false;
                    } else {
                        flagpassword=true;
                    }
                }
            }
        });
        //mEditUsername.addTextChangedListener(new GenericTextWatcher(mEditUsername));

    }

    public void signup(View view) {
        EditText mEditUsername = findViewById(R.id.editTextUsername);
        String username;
        username = mEditUsername.getText().toString();
        EditText mEditEmail = findViewById(R.id.editTextEmail);
        String email;
        email = mEditEmail.getText().toString();
        EditText mEditPassword = findViewById(R.id.editTextPassword);
        String password;
        password = mEditPassword.getText().toString();
        Toast toast = Toast.makeText(getApplicationContext(), "dd "+username+" "+email+" "+password,Toast.LENGTH_LONG);
        toast.show();
        toast = Toast.makeText(getApplicationContext(), "bike1",Toast.LENGTH_LONG);
        toast.show();
        signUpUrl = "http://u779583388.hostingerapp.com/signup.php?username="+username+"&email="+email+"&password="+password+"";
        //new AKSignUp().execute(signUpUrl);
    }

    class AKSignUp extends AsyncTask<String, Void, String> {
        public AKSignUp() {
            // TODO Auto-generated constructor stub
        }

        protected void onPreExecute() {
            //display progress dialog.
        }
        @Override
        protected String doInBackground(String... urls) {
            URL url;
            try {
                url = new URL(signUpUrl);
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
                return "1001";
            }
            if(conn==null){
                return "1001";}
            conn.setAllowUserInteraction(false);
            try {
                conn.connect();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return "1001";
            }
            InputStream in;
            try{
                in = conn.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
                return "1001";
            }
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
                return "1001";
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        return "1001";
                    }
                }
            }

            conn.disconnect();
            return result;
        }
        @Override
        protected void onPostExecute(String result) {
            if (result.equals("NoUser")||result.equals("1001")){
                Context context = getApplicationContext();
                CharSequence text = "Ooops";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                Intent intent = new Intent(context, SignUp.class);
                intent.putExtra("UserID",UserID);
                startActivity(intent);
            }
            else {
                Toast toast = Toast.makeText(getApplicationContext(), ""+result,Toast.LENGTH_LONG);
                toast.show();
                getSharedPreferences(PREFS_NAME,MODE_PRIVATE)
                .edit()
                .putString(PREF_USERID, result)
                .commit();
            }
            //nextScreen1(result);
        }
    }

}
