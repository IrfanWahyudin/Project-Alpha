package com.delta.irfan.projectbeta;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import com.delta.irfan.projectbeta.datastreamer.HttpPostData;

import org.json.JSONException;
import org.json.JSONObject;

import static com.delta.irfan.projectbeta.R.*;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    public void doLogin(View view) throws IOException, JSONException {

        String phone = "";
        String password = "";
        EditText etPhone = (EditText)findViewById(R.id.phone);
        EditText etPassword = (EditText)findViewById(R.id.password);
        phone = etPhone.getText().toString();
        password = etPassword.getText().toString();

        String sParam  = "phone=" + phone + "&password=" + password;
        String sURL        = "http://192.168.0.104/project_alpha/v1_0/index.php/api/Agents/login/format/json";

        HttpPostData httppost =  new HttpPostData();
        String response = httppost.PostData(sURL,sParam);
        JSONObject JSONresponse = new JSONObject(response);
        String responseCode = JSONresponse.getString("response");
        if (responseCode.equals(new String("OK"))){
            Intent intent = new Intent(MainActivity.this, OrderInActivity.class);
            intent.putExtra("phone", phone);

            MainActivity.this.startActivity(intent);
        }
        else{
            Toast.makeText(this, "Nomor Telepon / Password salah",Toast.LENGTH_LONG).show();
        }
    }
}
