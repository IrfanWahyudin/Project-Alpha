package com.delta.irfan.projectbeta;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.delta.irfan.projectbeta.datastreamer.HttpGetData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

public class OrderInActivity extends AppCompatActivity {
    ListView listView ;
    int selectedItem = 0;
    public class OrderBusinessLogic{
        public OrderBusinessLogic(){};
        DetailOrder [] detailOrder ;
        public DetailOrder [] retrieveOrders(String param) throws IOException, JSONException {
            String responseOrders = "";
            String [] listAgent = null;
            int numOfOrder = 0;
            HttpGetData httpGetData = new HttpGetData();
            String sURL = "http://192.168.0.104/project_alpha/v1_0/index.php/api/Orders/" +
                          "retrieve_orders_by_agent";
            String sParam = "/phone/" + param +  "/status/0/format/json";
            responseOrders = httpGetData.GetData(sURL, sParam);

            JSONObject agents = new JSONObject(responseOrders);
            JSONArray agent = agents.getJSONArray("orders");
            numOfOrder = agent.length();


            detailOrder = new DetailOrder[numOfOrder];
            for (Integer i=0; i < numOfOrder; i++){
                JSONObject orderDetail = agent.getJSONObject(i);
                detailOrder[i] = new DetailOrder();
                detailOrder[i].setCustomerAddress(orderDetail.getString("address"));
                detailOrder[i].setCustomerName(orderDetail.getString("name"));
                detailOrder[i].setCustomerPhone(orderDetail.getString("phone"));
                detailOrder[i].setCustomerLat(orderDetail.getString("lat"));
                detailOrder[i].setCustomerLng(orderDetail.getString("lng"));
                detailOrder[i].setOrderDate(orderDetail.getString("order_date"));
                detailOrder[i].setOrderId(orderDetail.getString("order_id"));
//            detailAgent[i].setPic(agentDetail.getString("pic"));
            }

            return detailOrder;

        }
    }
    public class DetailOrder{

        String orderId = "";
        String customerPhone = "";
        String customerAddress = "";
        String customerName = "";
        String customerLat = "";
        String customerLng = "";
        String orderDate = "";
        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getCustomerPhone() {
            return customerPhone;
        }

        public void setCustomerPhone(String customerPhone) {
            this.customerPhone = customerPhone;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public String getCustomerLat() {
            return customerLat;
        }

        public void setCustomerLat(String customerLat) {
            this.customerLat = customerLat;
        }

        public String getCustomerLng() {
            return customerLng;
        }

        public void setCustomerLng(String customerLng) {
            this.customerLng = customerLng;
        }

        public String getOrderDate() {
            return orderDate;
        }

        public void setOrderDate(String orderDate) {
            this.orderDate = orderDate;
        }

        public String getCustomerAddress() {
            return customerAddress;
        }

        public void setCustomerAddress(String customerAddress) {
            this.customerAddress = customerAddress;
        }
    }
    String phone = "";
    String [] id = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_in);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        Intent intent = getIntent();
        String [] infoOrder;
        phone = intent.getStringExtra("phone");

        OrderBusinessLogic order = new OrderBusinessLogic();

        DetailOrder[] values;
        infoOrder = null;

        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.list_order_in);
        try {
            values = order.retrieveOrders(phone);
            infoOrder = new String[values.length];
            if (values.length==0)
                return;
            else
                infoOrder = this.drawDisplayList(values);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Define a new Adapter
        // First parameter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Forth - the Array of data

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, infoOrder);


        // Assign adapter to ListView
        listView.setAdapter(adapter);

        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition = position;
                selectedItem = itemPosition;
                // ListView Clicked item value
                String itemValue = (String) listView.getItemAtPosition(position);

                // Show Alert
                Toast.makeText(getApplicationContext(),
                        "Position :" + itemPosition + "  ListItem : " + itemValue, Toast.LENGTH_LONG)
                        .show();
            }

        });
        registerForContextMenu(listView);
    }
    private String [] drawDisplayList(DetailOrder[] detailOrder){


        String [] value = new String[detailOrder.length];
        id = new String[detailOrder.length];

        for (int i =0; i<detailOrder.length;i++) {
            value[i] = detailOrder[i].getCustomerName() + " (T):" + detailOrder[i].getCustomerPhone()
                    + " (A):" + detailOrder[i].getCustomerAddress();
        }
        return value;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        int vId = 0;
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Pilih Proses");
        menu.add(0, v.getId(), 0, "Proses Sekarang");//groupId, itemId, order, title
        menu.add(0, v.getId(), 0, "Lihat Lokasi di Map");
    }



}
