<?php
class Orders_Model extends CI_Model {
	public function __construct(){
		$this->load->database();
	}

	public function create_orders($phone, $name, $address, $lat, $lng){
		
		$data = array($phone, $name, $address, $lat, $lng);
		$sql = "insert into orders(phone, name, address, lat, lng) 
								   values(?,?,?,?,?)";

		$this->db->query($sql, $data);

		$dbreturn = $this->db->error();

	}
}
?>