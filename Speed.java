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

public class Speed extends AppCompatActivity implements View.OnClickListener{
    private TextView location, deviceID, speed, timeReported, direction, latitude, longitude;
    private Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speed);
        location = (TextView) findViewById(R.id.locations);
        deviceID = (TextView) findViewById(R.id.deviceID);
        speed = (TextView) findViewById(R.id.speeds);
        timeReported = (TextView) findViewById(R.id.timeReported);
        direction = (TextView) findViewById(R.id.directions);
        latitude= (TextView) findViewById(R.id.latitudes);
        longitude = (TextView) findViewById(R.id.longitudes);
        back = (Button) findViewById(R.id.btback2);
        back.setOnClickListener(this);
        new MyTask().execute();
    }

    class MyTask extends AsyncTask<String, String, Void>
    {
        private ProgressDialog progressDialog = new ProgressDialog(Speed.this);
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
            String url_select = "http://trafficdb.x10host.com/demo4.php";

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
                    String deviceIDv = Jasonobject.getString("deviceID");
                    String speedsv= Jasonobject.getString("speed");
                    String timeReportedv = Jasonobject.getString("timeReported");
                    String directionv = Jasonobject.getString("direction");
                    String latitudev = Jasonobject.getString("latitude");
                    String longitudev = Jasonobject.getString("longitude");

                    location.setText("Location: "+ loc);
                    deviceID.setText("Device ID: " + deviceIDv);
                    speed.setText("Speed: " + speedsv);
                    timeReported.setText("Time Reported: " + timeReportedv);
                    direction.setText("Direction: " + directionv);
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
        getMenuInflater().inflate(R.menu.menu_speed, menu);
        return true;
    }



    public void onClick(View v) {
        // TODO Auto-generated method stub
        Intent j = new Intent(Speed.this, Second.class);
        startActivity(j);

    }
}
