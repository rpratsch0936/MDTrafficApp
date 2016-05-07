package com.example.regina.myapptraffic;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.HttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;


//  import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class MainActivity extends Activity implements OnClickListener{
    private static final String url="jdbc:mysql://192.168.56.1:3306/trafficdb";
    private static final String user ="regina";
    private  static final String password = "Tiesto1!";
    private Button login;
    private TextView err;
    private EditText username, pwd;
    private boolean k=true;
    protected Context mContext=this;
    private String pwdv="hallo1234";


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.activity_main, null);
        return v;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login= (Button) findViewById(R.id.btlogin);
        username = (EditText) findViewById(R.id.login);
        pwd = (EditText) findViewById(R.id.password);
        err = (TextView)findViewById(R.id.error);

        login.setOnClickListener(this);
    }

    class MyTask extends AsyncTask<String, String, Void>
    {
        private ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        InputStream is = null ;
        String result = "";
        protected void onPreExecute() {
            progressDialog.setMessage("Fetching data...");
            progressDialog.show();
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface arg0) {
                    MyTask.this.cancel(true);
                }
            });
        }
        @Override
        protected Void doInBackground(String... params) {
            String url_select = "http://trafficdb.x10host.com/demo.php";

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url_select);

            ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();

            try {
                httpPost.setEntity(new UrlEncodedFormEntity(param));

                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();

                is =  httpEntity.getContent();

            } catch (Exception e) {

                Log.e("log_tag", "Error in http connection "+e.toString());
            }
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                StringBuilder sb = new StringBuilder();
                String line = "";
                while((line=br.readLine())!=null)
                {
                    sb.append(line+"\n");
                }
                is.close();
                result=sb.toString();
                //Log.d("My string" , "string result"+ result);
            } catch (Exception e) {
                // TODO: handle exception
                Log.e("log_tag", "Error converting result "+e.toString());
            }

            return null;

        }
        protected void onPostExecute(Void v) {

            try {
                JSONArray Jarray = new JSONArray(result);
                for (int i = 0; i < Jarray.length(); i++) {
                    JSONObject Jasonobject = null;
                    Jasonobject = Jarray.getJSONObject(i);

                    String usname = Jasonobject.getString("username");
                    pwdv = Jasonobject.getString("password");
                    username.setText(usname);
                }
                this.progressDialog.dismiss();

            } catch (Exception e) {
                // TODO: handle exception
                Log.e("log_tag", "Error parsing data " + e.toString());
            }
        }
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch(v.getId()) {
            case R.id.btlogin : new MyTask().execute();
                if (pwd.getText().toString().equals(pwdv)) {
                    Intent i = new Intent(MainActivity.this, Second.class);
                    startActivity(i);
                }
                else{
                        err.setText("Wrong Password! Login failed!");
                }
                break;
        }

    }


}