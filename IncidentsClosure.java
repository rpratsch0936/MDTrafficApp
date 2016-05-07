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

public class IncidentsClosure extends AppCompatActivity  implements View.OnClickListener {

     private TextView permit, permittype, startdate, enddate, starttime, endtime, reason, countyto, countyfrom, route,  daysaffected, direction,  numlanes, locationlimits;
     private Button back;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ic);
        permit = (TextView) findViewById(R.id.permit);
        permittype = (TextView) findViewById(R.id.permittype2);
        startdate = (TextView) findViewById(R.id. startdate);
        enddate = (TextView) findViewById(R.id.enddate);
        starttime = (TextView) findViewById(R.id.starttime);
        endtime = (TextView) findViewById(R.id.endtime);
        reason = (TextView) findViewById(R.id. reason);
        countyto= (TextView) findViewById(R.id.countyto);
        countyfrom= (TextView) findViewById(R.id.countyfrom);
        route = (TextView) findViewById(R.id.route);
        daysaffected = (TextView) findViewById(R.id.daysaffected);
        direction = (TextView) findViewById(R.id. direction);
        numlanes = (TextView) findViewById(R.id. numlanes);
        locationlimits= (TextView) findViewById(R.id.locationlimits);
        back = (Button) findViewById(R.id.btback);
        back.setOnClickListener(this);
        new MyTask().execute();

    }
    class MyTask extends AsyncTask<String, String, Void>
    {
        private ProgressDialog progressDialog = new ProgressDialog(IncidentsClosure.this);
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
            String url_select = "http://trafficdb.x10host.com/demo3.php";

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

                    String permitv = Jasonobject.getString("permit");
                    String permittypev = Jasonobject.getString("permitType");
                    String startdatev = Jasonobject.getString("startDate");
                    String enddatev = Jasonobject.getString("finishDate");
                    String starttimev = Jasonobject.getString("startTime");
                    String endtimev = Jasonobject.getString("endTime");
                    String reasonv = Jasonobject.getString("reason");
                    String countytov = Jasonobject.getString("countyTo");
                    String countyfromv = Jasonobject.getString("countyFrom");
                    String routev = Jasonobject.getString("route");
                    String daysaffectedv = Jasonobject.getString("daysAffected");
                    String directionv = Jasonobject.getString("direction");
                    String numlanesv = Jasonobject.getString("numLanes");
                    String locationlimitsv = Jasonobject.getString("locationLimits");

                    permit.setText("Permit: "+ permitv);
                    permittype.setText("Permit Type " +permittypev);
                    startdate.setText("Start Date: " + startdatev);
                    enddate.setText("End Date: " + enddatev);
                    starttime.setText("Start Time: " + starttimev);
                    endtime.setText("End Time: " + endtimev);
                    reason.setText("Reason: " + reasonv);
                    countyto.setText("County To: " + countytov);
                    countyfrom.setText("County From : " + countyfromv);
                    route.setText("Route: " + routev);
                    daysaffected.setText("Days Affected: " + daysaffectedv);
                    direction.setText("Direction: " + directionv);
                    numlanes.setText("Num Lanes: " + numlanesv);
                    locationlimits.setText("Location Limits: " +  locationlimitsv);

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
        Intent j = new Intent(IncidentsClosure.this, Second.class);
        startActivity(j);

        }
}
