<?php
class Customers_Model extends CI_Model {
	public function __construct(){
		$this->load->database();
	}

	public function create_customers($name, $phone, $email, $address){
		$data = array($name, $phone, $email, $address);
		$sql = "select phone from customers where phone = '" . $phone . "'";
		$query = $this->db->query($sql);

		if ($query->num_rows()>0){
			$sql = "insert into customers(name, phone, email, address) values(?,?,?,?)";
			$this->db->query($sql, $data);	
		}
		else{
			$sql = "update customers set name = '" . $name . "', email = '" . $email . "'" .
					",address = '" . $address. "' where phone = '" . $phone . "'";
			$this->db->query($sql);
		}
		
		$dbreturn = $this->db->error();

	}
}
?>