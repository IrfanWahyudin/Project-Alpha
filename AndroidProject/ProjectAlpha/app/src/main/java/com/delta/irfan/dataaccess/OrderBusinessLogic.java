package com.delta.irfan.dataaccess;

import com.delta.irfan.datastreamer.HttpGetData;

import java.io.IOException;

/**
 * Created by irfan on 12/17/2015.
 */
public class OrderBusinessLogic {
    public DetailOrder detailOrder;
    public OrderBusinessLogic(){
        detailOrder = new DetailOrder();
    }
    public class DetailOrder{

        public DetailOrder(){};
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getIdagent() {
            return idagent;
        }

        public void setIdagent(String idagent) {
            this.idagent = idagent;
        }

        public String getOrdertype() {
            return ordertype;
        }

        public void setOrdertype(String ordertype) {
            this.ordertype = ordertype;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        String name;
        String phone;
        String email;
        String address;
        String idagent;
        String ordertype;
        String lat;
        String lng;


    }

    public Integer createOrder(DetailOrder detailOrder) throws IOException {
        Integer orderId = 0;
        String responseOrders = "";
        String param = "phone/" + detailOrder.getPhone() + "/name/" + detailOrder.getName()
                + "/address/" + detailOrder.getAddress() + "/email/" + detailOrder.getEmail()
                + "/idagent/" + detailOrder.getIdagent() + "/ordertype/" + detailOrder.getOrdertype()
                + "/lat/" + detailOrder.getLat() + "/lng/" + detailOrder.getLng();
        HttpGetData httpGetData = new HttpGetData();
        String sURL = "http://192.168.0.104/project_alpha/v1_0/index.php/api/Orders/create_orders/";
        String sParam =  param + "/format/json";
        responseOrders = httpGetData.GetData(sURL, sParam);

        return orderId;
    }
}
