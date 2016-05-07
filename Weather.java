package com.example.regina.myapptraffic;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Weather extends AppCompatActivity implements View.OnClickListener{
    private TextView location, datetime, air, dew, rh, spd_avg, spd_gst, dir_avg,vis, precip, intens, sys_number, rpu_number, status,minsfc, maxsfc , TzLabel, latitude, longitude ;
    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        location = (TextView) findViewById(R.id.location);
        datetime = (TextView) findViewById(R.id.datetime);
        air = (TextView) findViewById(R.id.air);
        dew = (TextView) findViewById(R.id.dew);
        rh = (TextView) findViewById(R.id.rh);
        spd_avg = (TextView) findViewById(R.id.spd_avg);
        spd_gst = (TextView) findViewById(R.id.spd_gst);
        dir_avg= (TextView) findViewById(R.id.dir_avg);
        precip= (TextView) findViewById(R.id.precip);
        vis = (TextView) findViewById(R.id.vis);
        intens = (TextView) findViewById(R.id.intens);
        sys_number = (TextView) findViewById(R.id.sys_number);
        rpu_number = (TextView) findViewById(R.id.rpu_number);
        status= (TextView) findViewById(R.id.status);
        minsfc = (TextView) findViewById(R.id.minsfc);
        maxsfc = (TextView) findViewById(R.id.maxsfc);
        TzLabel = (TextView) findViewById(R.id.TzLabel);
        latitude= (TextView) findViewById(R.id.latitude);
        longitude = (TextView) findViewById(R.id.longitude);
        back = (Button) findViewById(R.id.btback3);
        back.setOnClickListener(this);

        new MyTask().execute();
    }

    class MyTask extends AsyncTask<String, String, Void>
    {
        private ProgressDialog progressDialog = new ProgressDialog(Weather.this);
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
            String url_select = "http://trafficdb.x10host.com/demo2.php";

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url_select);

            ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();

            try {
                httpPost.setEntity(new UrlEncodedFormEntity(param));

                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();

                is =  httpEntity.getContent();

            } catch (Exception e) {

                Log.e("log_tag", "Error in http connection " + e.toString());
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
                for(int i=0;i<Jarray.length();i++)

                {
                    JSONObject Jasonobject = null;
                    Jasonobject = Jarray.getJSONObject(i);

                    String loc = Jasonobject.getString("location");
                    String datev = Jasonobject.getString("datetime");
                    String airv = Jasonobject.getString("air");
                    String dewv = Jasonobject.getString("dew");
                    String rhv = Jasonobject.getString("rh");
                    String spd_avgv = Jasonobject.getString("spd_avg");
                    String spd_gstv = Jasonobject.getString("spd_gst");
                    String dir_avgv = Jasonobject.getString("dir_avg");
                    String visv = Jasonobject.getString("vis");
                    String precipv = Jasonobject.getString("precip");
                    String intensv = Jasonobject.getString("intens");
                    String sys_numberv = Jasonobject.getString("sys_number");
                    String rpu_numberv = Jasonobject.getString("rpu_number");
                    String statusv = Jasonobject.getString("status");
                    String minsfcv = Jasonobject.getString("minsfc");
                    String maxsfcv= Jasonobject.getString("maxsfc");
                    String TzLabelv = Jasonobject.getString("TzLabel");
                    String latitudev = Jasonobject.getString("latitude");
                    String longitudev = Jasonobject.getString("longitude");

                    location.setText("Location: "+ loc);
                    datetime.setText("Date/Time: " + datev);
                    air.setText("Air: " + airv);
                    dew.setText("Dew: " + dewv);
                    rh.setText("Rh: " + rhv);
                    spd_avg.setText("Speed Avg: " + spd_avgv);
                    spd_gst.setText("Speed Gst: " + spd_gstv);
                    dir_avg.setText("Dir Avg: " + dir_avgv);
                    vis.setText("Vis: " + visv);
                    precip.setText("Precipitation: " + precipv);
                    intens.setText("Intnes: " + intensv);
                    sys_number.setText("Sys Number: " + sys_numberv);
                    rpu_number.setText("Rpu Number: " + rpu_numberv);
                    status.setText("Status: " + statusv);
                    minsfc.setText("Minsfc: " + minsfcv);
                    maxsfc.setText("Maxsfc: " + maxsfcv);
                    TzLabel.setText("TzLabel: " + TzLabelv);
                    latitude.setText("Latitude: " + latitudev);
                    longitude.setText("Longitude: " + longitudev);
                }
                this.progressDialog.dismiss();

            } catch (Exception e) {
                // TODO: handle exception
                Log.e("log_tag", "Error parsing data "+e.toString());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_weather, menu);
        return true;
    }


    public void onClick(View v) {
        // TODO Auto-generated method stub
        Intent j = new Intent(Weather.this, Second.class);
        startActivity(j);

    }
}
