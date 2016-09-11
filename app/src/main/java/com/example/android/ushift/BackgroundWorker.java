package com.example.android.ushift;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.speech.tts.Voice;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by vardan on 04-09-2016.
 */
public class BackgroundWorker extends AsyncTask<String, Void, String> {
    Context context;
    AlertDialog alertDialog;
    String user_name;
    String password;
    String type;
    private String result;

    BackgroundWorker(Context ctx) {
        context = ctx;
    }

    @Override
    protected String doInBackground(String... params) {
         type = params[0];

        String login_url = "http://ushift.in/check.php";
        try {
            user_name = params[1];
             password = params[2];
            URL url = new URL(login_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = URLEncoder.encode("userq", "UTF-8") + "=" + URLEncoder.encode(user_name, "UTF-8") + "&"
                    + URLEncoder.encode("pass", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8") +
                    "&"
                    + URLEncoder.encode("type3", "UTF-8") + "=" + URLEncoder.encode(type, "UTF-8");
            bufferedWriter.write(post_data);
            Log.e("one", "" + post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
           result="";
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                result += line;

            }
            Log.e("one", "" + result);
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Login Status");
    }

    @Override
    protected void onPostExecute(String result) {

        if(result.equals("no")) Toast.makeText(context, result, Toast.LENGTH_LONG).show();

        else {

                Intent intent = new Intent(context, user_dashboard.class);
                intent.putExtra("EMAIL", user_name);
            intent.putExtra("PASS", password);
            intent.putExtra("TYPE", type );
                context.startActivity(intent);



        }


    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
