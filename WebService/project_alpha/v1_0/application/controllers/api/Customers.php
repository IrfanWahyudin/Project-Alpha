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
class Customers extends REST_Controller {

    function __construct()
    {
        // Construct the parent class
        parent::__construct();

        // Configure limits on our controller methods
        // Ensure you have created the 'limits' table and enabled 'limits' within application/config/rest.php
        $this->methods['user_get']['limit'] = 500; // 500 requests per hour per user/key
        $this->methods['user_post']['limit'] = 100; // 100 requests per hour per user/key
        $this->methods['user_delete']['limit'] = 50; // 50 requests per hour per user/key
    }

    public function create_customers_get()
    {   

        $this->load->model('Customers_Model');

        $name = $this->get('name');
        $phone = $this->get('phone');
        $email = $this->get('email');
        $address = $this->get('address');

        $message = [
                    'id' => 100,
                    'name' => $name,
                    'phone' => $email,
                    'address' => $address
        ];
        log_message('error', 'incoming ' . $name) ;
        if (is_null($name) | $name == ''){
            $this->set_response(" Gagal menyimpan", REST_Controller::HTTP_BAD_REQUEST);
        }
        else{
            $this->Customers_Model->create_customers($name, $phone, $email, $address);
            $this->set_response(" Sukses menyimpan", REST_Controller::HTTP_CREATED);
        }            
    }

    public function update_customers_get()
    {   
        $this->load->model('Customers_Model');

        $name = $this->get('id');
        $name = $this->get('name');
        $phone = $this->get('phone');
        $email = $this->get('email');
        $address = $this->get('address');

        $message = [
                    'id' => $id,
                    'name' => $name,
                    'phone' => $email,
                    'address' => $address
        ];

        if (is_null($id) | $id == ''){
            $this->set_response($message, REST_Controller::HTTP_BAD_REQUEST);
        }
        else{
            $this->Customers_Model->update_customers($name, $phone, $email, $address);
            $this->set_response($message, REST_Controller::HTTP_CREATED);
        }            
    }
}
