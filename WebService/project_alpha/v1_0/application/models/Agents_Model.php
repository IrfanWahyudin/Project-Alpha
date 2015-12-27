<?php
class Agents_Model extends CI_Model {
	public function __construct(){
		$this->load->database();
	}

	public function retrieve_agents($param){
		
		$result = [];
		$detail_result = [];
		$i = 0;
		$data = array($param);
		$sql = "select id, name, address, `long` as lng, lat from agents 
				where concat(name, ' ' , address) like '%" . $param . " %'";

		$dbreturn = $this->db->query($sql);

		$dbmessage = $this->db->error();
		foreach ($dbreturn->result() as $row)
		{
		   $detail_result = array('id' => $row->id, 'name' => $row->name, 'address' => $row->address);
		   $result[$i] = $detail_result;
		   
		   $i++;
		}
		$agents = array('agents'=>array_values($result));
		return $agents;

	}

	public function login($phone, $password){
		$sql = "select id from agents where phone = ? and password = ?";
		$where = array($phone, $password);

		$data = $this->db->query($sql, $where);

		if ($data->num_rows() > 0){
			return 1;
		}
		else{
			return 0;
		}
	}
}
?>