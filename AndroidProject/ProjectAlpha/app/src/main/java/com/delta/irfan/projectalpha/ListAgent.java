package com.delta.irfan.projectalpha;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.delta.irfan.dataaccess.AgentBusinessLogic;
import com.delta.irfan.dataaccess.AgentBusinessLogic.DetailAgent;
import org.json.JSONException;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import java.io.IOException;

import com.delta.irfan.dataaccess.OrderBusinessLogic;
import com.delta.irfan.dataaccess.OrderBusinessLogic.*;


public class ListAgent extends AppCompatActivity {
    ListView listView ;
    OrderBusinessLogic order;
    int selectedItem = 0;
    private String [] id;
    String name;
    String phone;
    String address;
    String lat;
    String lng;
    String email;
    private String [] drawDisplayList(DetailAgent[] detailAgent){
        String [] value = new String[detailAgent.length];
        id = new String[detailAgent.length];

        for (int i =0; i<detailAgent.length;i++) {
            value[i] = detailAgent[i].getName() + " Lokasi: " + detailAgent[i].getAddress();
            id[i] = detailAgent[i].getId();
        }
        return value;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_agent);
        AgentBusinessLogic agent = new AgentBusinessLogic();
        String [] infoAgent;
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        phone = intent.getStringExtra("phone");
        address = intent.getStringExtra("address");
        lat = intent.getStringExtra("lat").replace(".","_dot_").replace("-","_minus_");
        lng = intent.getStringExtra("lng").replace(".","_dot_").replace("-","_minus_");
        email = intent.getStringExtra("email");
        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.list);

        // Defined Array values to show in ListView
//        String[] values = new String[]{"Android List View",
//                "Adapter implementation",
//                "Simple List View In Android",
//                "Create List View Android",
//                "Android Example",
//                "List View Source Code",
//                "List View Array Adapter",
//                "Android Example List View"
//        };
        DetailAgent[] values;
        infoAgent = null;
        try {
            values = agent.retrieveAgents("pondok");
            infoAgent = new String[values.length];
            if (values.length==0)
                return;
            else
                infoAgent = this.drawDisplayList(values);
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
                android.R.layout.simple_list_item_1, android.R.id.text1, infoAgent);


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
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Pilih Order");
        menu.add(0, v.getId(), 0, "Order Sekarang");//groupId, itemId, order, title
        menu.add(0, v.getId(), 0, "Order Nanti");
    }
    @Override
    public boolean onContextItemSelected(MenuItem item){
        order = new OrderBusinessLogic();


        if(item.getTitle()=="Order Sekarang"){
            order.detailOrder.setName(name);
            order.detailOrder.setPhone(phone);
            order.detailOrder.setAddress(address);
            order.detailOrder.setLat(lat);
            order.detailOrder.setLng(lng);
            order.detailOrder.setIdagent(id[selectedItem]);
            order.detailOrder.setEmail(email);
            order.detailOrder.setOrdertype("0");

            try {
                order.createOrder(order.detailOrder);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Toast.makeText(getApplicationContext(),"Order Sekarang",Toast.LENGTH_LONG).show();
        }
        else if(item.getTitle()=="Order Nanti"){
            Toast.makeText(getApplicationContext(),"Fitur Order Nanti belum tersedia",Toast.LENGTH_LONG).show();
        }else{
            return false;
        }
        return true;
    }
}
