
package com.example.viola.engdict;


        import android.content.Intent;
        import android.os.AsyncTask;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.webkit.CookieManager;
        import android.widget.EditText;
        import android.widget.Toast;

        import java.io.BufferedInputStream;
        import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.net.HttpURLConnection;
        import java.net.MalformedURLException;
        import java.net.URL;


public class LogIn extends AppCompatActivity {
    String value ="1";
    String name = "session";
    String loginUrl;
    String result;
    public static final String PREFS_NAME = "MyPrefsFile";
    private static final String PREF_USERID = "UserID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);
    }

    public void loginuser(View view) {
        CookieManager.getInstance().setAcceptCookie(true);
        CookieManager.getInstance().setCookie(name, value);
        EditText mEditEmail = findViewById(R.id.editTextEmail);
        String email;
        email = mEditEmail.getText().toString();
        EditText mEditPassword = findViewById(R.id.editTextPassword);
        String password;
        password = mEditPassword.getText().toString();
        Toast toast = Toast.makeText(getApplicationContext(), "dd"+email+""+password,Toast.LENGTH_LONG);
        toast.show();
        loginUrl = "http://u779583388.hostingerapp.com/loginuser.php?email="+email+"&password="+password+"";
        new AKLogin().execute(loginUrl);
    }

    public void loggedin(String userID1) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("UserID",userID1);
        startActivity(intent);
    }

    public void signupuser(View view) {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }

    class AKLogin extends AsyncTask<String, Void, String> {
        public AKLogin() {
            // TODO Auto-generated constructor stub
        }

        protected void onPreExecute() {
            //display progress dialog.
        }
        @Override
        protected String doInBackground(String... urls) {
            URL url;
            try {
                url = new URL(loginUrl);
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
                Toast toast = Toast.makeText(getApplicationContext(), "Ooooopss "+e,Toast.LENGTH_LONG);
                toast.show();
                return "1005";
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
                //toast.show();
                Log.i("UserIDLogin", result);
                //Intent intent = new Intent(context, LoginScreen.class);
                //startActivity(intent);
            }
            else {
                Toast toast = Toast.makeText(getApplicationContext(), "Logged in succesful ",Toast.LENGTH_LONG);
                toast.show();
                getSharedPreferences(PREFS_NAME,MODE_PRIVATE)
                        .edit()
                        .putString(PREF_USERID, result)
                        .commit();
                Log.i("UserIDLogin", result);
                loggedin(result);
            }
        }
    }
}

