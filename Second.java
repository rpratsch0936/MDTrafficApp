package com.example.regina.myapptraffic;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;

public class Second extends AppCompatActivity implements OnClickListener {
    private Button btic;
    private Button btsp;
    private Button btw;
    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        btic= (Button) findViewById(R.id.btic);
        btsp= (Button) findViewById(R.id.btsp);
        btw= (Button) findViewById(R.id.btw);
        back = (Button) findViewById(R.id.btlogout);

        btic.setOnClickListener(this);
        btw.setOnClickListener(this);
        btsp.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_second, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClick(View v) {
        // TODO Auto-generated method stub

        switch(v.getId()) {
            case R.id.btic :
                Intent i = new Intent(Second.this, IncidentsClosure.class);
                startActivity(i);
                break;
            case R.id.btw :
                Intent j = new Intent(Second.this, Weather.class);
                startActivity(j);
                break;
            case R.id.btsp :
                Intent k = new Intent(Second.this, Speed.class);
                startActivity(k);
                break;

            case R.id.btlogout :

                        Intent z = new Intent(Second.this, MainActivity.class);
                        startActivity(z);

                 break;
    }
    }
}
