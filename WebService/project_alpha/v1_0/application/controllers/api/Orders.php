<?php

defined('BASEPATH') OR exit('No direct script access allowed');

// This can be removed if you use __autoload() in config.php OR use Modular Extensions
require APPPATH . '/libraries/REST_Controller.php';

/**
 * This is abs(number)n example of a few basic user interaction methods you could use
 * all done with a hardcoded array
 *
 * @package         CodeIgniter
 * @subpackage      Rest Server
 * @category        Controller
 * @author          Phil Sturgeon, Chris Kacerguis
 * @license         MIT
 * @link            https://github.com/chriskacerguis/codeigniter-restserver
 */
class Orders extends REST_Controller {

    function __construct()
    {
        // Construct the parent class
        parent::__construct();

        // Configure limits on our controller methods
        // Ensure you have created the 'limits' table and enabled 'limits' within application/config/rest.php
        $this->methods['create_orders_get']['limit'] = 500; // 500 requests per hour per user/key
    }

    public function create_orders_get()
    {   

        $this->load->model('Orders_Model');

        $name = $this->get('name');
        $phone = $this->get('phone');
        $email = $this->get('email');
        $address = $this->get('address');
        $idagent = $this->get('idagent');
        $ordertype = $this->get('ordertype');
        $lat = $this->get('lat');
        $lng = $this->get('lng');

        $message = [
                    'id' => 100,
                    'name' => $name,
                    'phone' => $email,
                    'address' => $address,
                    'idagent' => $idagent,
                    'ordertype' => $ordertype,
                    'lat' => $lat,
                    'lng' => $lng
                    ];
        log_message('error', 'incoming ' . $lat . ' ' . $lng . ' ' . $name . ' idagent '. $idagent ) ;
        if (is_null($name) | $name == ''){
            $this->set_response(" Gagal menyimpan", REST_Controller::HTTP_BAD_REQUEST);
        }
        else{
            $this->Orders_Model->create_orders($phone, $name, $address, $lat, $lng, $idagent);
            $this->set_response(" Sukses menyimpan", REST_Controller::HTTP_CREATED);
        }            
    }

    public function retrieve_orders_by_customer_get()
    {
        $this->load->model('Orders_Model');

        $phone = $this->get('phone');
        $status = $this->get('status');

        $orders = $this->Orders_Model->retrieve_orders_by_customer($phone, $status);
        $this->response($orders, REST_Controller::HTTP_OK); 
    }

    public function retrieve_orders_by_agent_get()
    {
        $this->load->model('Orders_Model');

        $idagent = $this->get('phone');
        $status = $this->get('status');

        $orders = $this->Orders_Model->retrieve_orders_by_agent($idagent, $status);
        $this->response($orders, REST_Controller::HTTP_OK); 
    }

    public function update_order_status_get()
    {
        $this->load->model('Orders_Model');

        $phone = $this->get('phone');
        $idagent = $this->get('idagent');
        $status = $this->get('status');


    }

    
}
