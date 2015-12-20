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
class Agents extends REST_Controller {

    function __construct()
    {
        // Construct the parent class
        parent::__construct();

        // Configure limits on our controller methods
        // Ensure you have created the 'limits' table and enabled 'limits' within application/config/rest.php
        $this->methods['retrieve_agents_get']['limit'] = 500; // 500 requests per hour per user/key

    }



    public function retrieve_agents_get()
    {
        $this->load->model('Agents_Model');

        $param = $this->get('param');

        $agents = $this->Agents_Model->retrieve_agents($param);
        log_message('error', 'incoming ' . $param) ;
        $this->response($agents, REST_Controller::HTTP_OK); 
    }
}
