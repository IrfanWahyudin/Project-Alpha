<?php
class Customers_Model extends CI_Model {
	public function __construct(){
		$this->load->database();
	}

	public function create_customers($name, $phone, $email, $address){
		$data = array($name, $phone, $email, $address);
		$sql = "insert into customers(name, phone, email, address) values(?,?,?,?)";

		$this->db->query($sql, $data);

		$dbreturn = $this->db->error();

	}
}
?>