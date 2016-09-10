package com.example.android.ushift;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;





import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

public class user_dashboard extends AppCompatActivity {
    TextView dashboard;
    private TextView textViewResult;

    private ProgressDialog loading;
    private String email;
    private  String password;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);
        dashboard = (TextView) findViewById(R.id.dashboard);
        Intent intent = getIntent();
        email = intent.getStringExtra("EMAIL");
        password=intent.getStringExtra("PASS");
        type=intent.getStringExtra("TYPE");


        Log.e("onCreate", "yes1");
        getData();
        Log.e("onCreate", "yes");
    }

    private void getData() {

        DataFetcher dataFetcher = new DataFetcher(getApplicationContext());
        Log.e("getData", "yes");
        dataFetcher.execute(email,password,type);
    }

    public class DataFetcher extends AsyncTask<String, Void, String> {
        Context context;
        AlertDialog alertDialog;
        String user_name;
        String password;
        private String result;

        DataFetcher(Context ctx) {
            context = ctx;
        }

        @Override
        protected String doInBackground(String... params) {


            String login_url = "http://ushift.in/android/dashboard_user.php";
            try {
                user_name = params[0];
                password=params[1];
                type=params[2];


                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                Log.e("VALUE IS", "" + user_name);
                String post_data = URLEncoder.encode("userq", "UTF-8") + "=" + URLEncoder.encode(user_name, "UTF-8") + "&"
                        + URLEncoder.encode("pass", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8") +
                        "&"
                        + URLEncoder.encode("type3", "UTF-8") + "=" + URLEncoder.encode(type, "UTF-8");
                bufferedWriter.write(post_data);

                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                result="";
                Log.e("one122323234343", "dfasdfveafewqa" );
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                Log.e("one122323234343", "" + result);
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                Log.e("one122323234343", "EXC" );
                e.printStackTrace();
            } catch (IOException e) {
                Log.e("one122323234343", "CAT" );
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String result) {

            dashboard.setText(result);



            }


        }


    }


