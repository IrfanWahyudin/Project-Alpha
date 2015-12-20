<?php
class Agents_Model extends CI_Model {
	public function __construct(){
		$this->load->database();
	}

	public function retrieve_agents($param){
		
		$result = new ArrayObject();
		$detail_result = [];
		$i = 0;
		$data = array($param);
		$sql = "select id, name, address, `long` as lng, lat from agents where concat(name, ' ' , address) like '%" . $param . " %'";

		$dbreturn = $this->db->query($sql);

		$dbmessage = $this->db->error();
		foreach ($dbreturn->result() as $row)
		{
		   $detail_result = array('id' => $row->id, 'name' => $row->name, 'address' => $row->address);
		   $result->append($detail_result);
		   
		   $i++;
		}
		$agents = array('agents'=>[$result]);
		return $agents;

	}
}
?>