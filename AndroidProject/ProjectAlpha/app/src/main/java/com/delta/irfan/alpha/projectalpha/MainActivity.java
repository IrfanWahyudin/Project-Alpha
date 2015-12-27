package com.delta.irfan.alpha.projectalpha;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.widget.EditText;
import android.widget.Toast;

import com.delta.irfan.alpha.dataaccess.CustomerBusinessLogic;
import com.delta.irfan.alpha.datastreamer.HttpGetData;
import com.delta.irfan.alpha.location.GPSTracker;
import com.google.android.gms.maps.GoogleMap;

import org.apache.http.client.methods.HttpGet;

import java.io.IOException;
import com.delta.irfan.alpha.R;
public class MainActivity extends AppCompatActivity {
    String name ;
    String address ;
    String email ;
    String phone ;
    String lat;
    String lng;
    GoogleMap googleMap ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        try {
            // Loading map
//            initializeMap();
            checkCustomerExist(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Boolean checkCustomerExist(Context mContext){
        AccessibilityService mAppContext = new AccessibilityService() {
            @Override
            public void onAccessibilityEvent(AccessibilityEvent event) {
            }

            @Override
            public void onInterrupt() {

            }
        };
//        TelephonyManager tMgr = (TelephonyManager)mAppContext.getSystemService(Context.TELEPHONY_SERVICE);
        String mPhoneNumber = "";//tMgr.getLine1Number();
        EditText etPhone = (EditText)findViewById(R.id.customer_phone);
        EditText etName = (EditText)findViewById(R.id.customer_name);
        EditText etAddress = (EditText)findViewById(R.id.customer_address);
        EditText etEmail = (EditText)findViewById(R.id.customer_email);
        CustomerBusinessLogic bl = new CustomerBusinessLogic(getApplicationContext());
        CustomerBusinessLogic.FeedEntry feedEntry = new CustomerBusinessLogic.FeedEntry();
        feedEntry = bl.readCustomer(mPhoneNumber);

        if (feedEntry.getPhone().toString().compareTo("Empty")==0){
//            etPhone.setText(feedEntry.getPhone());
            return true;
        }
        else{
            name = feedEntry.getName();
            phone = feedEntry.getPhone();
            address = feedEntry.getAddress();
            email = feedEntry.getEmail();
            etName.setText(name);
            etPhone.setText(phone);
            etAddress.setText(address);
            etEmail.setText(email);
            return false;
        }
    }

    public void createCustomers(View view) {
        String ret = "";
        String responseString = "";
        Integer iResponseCode = 0;
        String s = "";
        HttpGetData httpGetData = new HttpGetData();
        CustomerBusinessLogic bl = new CustomerBusinessLogic(getApplicationContext());
        CustomerBusinessLogic.FeedEntry feedEntry = new CustomerBusinessLogic.FeedEntry();

        EditText etName = (EditText)findViewById(R.id.customer_name);
        EditText etAddress = (EditText)findViewById(R.id.customer_address);
        EditText etEmail = (EditText)findViewById(R.id.customer_email);
        EditText etPhone = (EditText)findViewById(R.id.customer_phone);

        name = etName.getText().toString();
        address = etAddress.getText().toString();
        email= etEmail.getText().toString();
        phone = etPhone.getText().toString();
        String sURL = "http://192.168.0.104/project_alpha/v1_0/index.php/api/Customers/create_customers";
        String sParam = "";
        feedEntry.setName(name);
        feedEntry.setAddress(address);
        feedEntry.setEmail(email);
        feedEntry.setPhone(phone);
        bl.addCustomer(feedEntry);
        try {
            HttpGet request = new HttpGet();

            sParam = "/name/" + name + "/phone/" + phone + "/email/" + email + "/address/" + address;

            responseString = httpGetData.GetData(sURL,sParam);
            Toast.makeText(getApplicationContext(), responseString, Toast.LENGTH_LONG).show();
        }
        catch (IOException ex) {
        }
    }


    public void createOrders(View view){
        this.openListAgent();
    }
    private void openListAgent(){
        GPSTracker gpsTracker = new GPSTracker(this);
        Double Lng = gpsTracker.getLongitude();
        Double Lat = gpsTracker.getLatitude();

        Intent customersIntent  = new Intent(MainActivity.this, ListAgentActivity.class);
        customersIntent.putExtra("phone",phone);
        customersIntent.putExtra("name",name);
        customersIntent.putExtra("address",address);
        customersIntent.putExtra("lat",Lat.toString());
        customersIntent.putExtra("lng",Lng.toString());
        customersIntent.putExtra("email",email);
        MainActivity.this.startActivity(customersIntent);
    }
    public void showLocation(View view){
        Intent customersIntent  = new Intent(MainActivity.this, MapsActivity.class);
        customersIntent.putExtra("phone",phone);
        MainActivity.this.startActivity(customersIntent);
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
        if (id == R.id.action_new_order) {
            this.openListAgent();
        }
        else if (id == R.id.action_old_order){

        }

        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_outgoing) {
//            Toast.makeText(this,"Outgoing", Toast.LENGTH_LONG).show();
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}

