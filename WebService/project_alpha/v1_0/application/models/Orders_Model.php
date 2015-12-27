<?php
class Orders_Model extends CI_Model {
	public function __construct(){
		$this->load->database();
	}

	public function create_orders($phone, $name, $address, $lat, $lng, $idagent){
		
		$data = array($phone, $name, $address, $lat, $lng, $idagent);
		$sql = "insert into orders(phone, name, address, lat, lng, idagent) 
								   values(?,?,?,?,?,?)";

		$this->db->query($sql, $data);

		$dbreturn = $this->db->error();

	}

	public function retrieve_orders_by_customer($phone, $status){
		
		$sql = "select orders.id, orders.phone, orders.name, orders.address, 
				orders.lat, orders.lng, order_date from orders inner join agents 
				on orders.idagent = agents.id 
				where orders.phone = ? and orders.status = ? order by order_date";
		$where = array($phone, $status);
		$result = [];
		$data = $this->db->query($sql, $where);

		$dbmessage = $this->db->error();
		$i = 0;
		foreach ($data->result() as $row)
		{
		   $detail_result = array('id' => $row->id, 'phone' => $row->phone, 
		   						  'name' => $row->name, 'address' => $row->address, 
		   						  'lat' => $row->lat, 'lng' => $row->lng);
		   $result[$i] = $detail_result;
		   
		   $i++;
		}
		$orders = array('orders'=>array_values($result));
		
		return $orders;

	}

	public function retrieve_orders_by_agent($idagent, $status){	
		
		$sql = "select orders.id, orders.phone, orders.name, orders.address, 
				orders.lat, orders.lng, order_date from orders inner join agents 
				on orders.idagent = agents.id 
				where agents.phone = ? and orders.status = ? order by order_date";
		$where = array($idagent, $status);
		$result = [];
		$data = $this->db->query($sql, $where);

		$dbmessage = $this->db->error();
		$i = 0;
		foreach ($data->result() as $row)
		{
		   $detail_result = array('order_id' => $row->id, 'phone' => $row->phone, 
		   						  'name' => $row->name, 'address' => $row->address, 
		   						  'lat' => $row->lat, 'lng' => $row->lng,
		   						  'order_date' => $row->order_date);
		   $result[$i] = $detail_result;
		   
		   $i++;
		}
		$orders = array('orders'=>array_values($result));
		
		return $orders;

	}

	public function update_order($phone, $status){
		
		$data = array('status' => $status);
		$where = "phone = '" . $phone . "'";

		$str = $this->db->update_string($data, $where);

		$dbreturn = $this->db->error();

	}
}
?>