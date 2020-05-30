<?php 
require 'db.php';
$data = json_decode(file_get_contents('php://input'), true);
if($data['uid'] == '')
{
 $returnArr = array("ResponseCode"=>"401","Result"=>"false","ResponseMsg"=>"Something Went Wrong!");    
}
else
{
    $uid =  strip_tags(mysqli_real_escape_string($con,$data['uid']));
  $sel = $con->query("select * from orders where uid=".$uid.""); 
  $g=array();
  $po= array();
  while($row = $sel->fetch_assoc())
  {
      $g['id'] = $row['id'];
      $g['oid'] = $row['oid'];
      $oid = $row['oid'];
      $g['status'] = $row['status'];
      $g['order_date'] = $row['order_date'];
	  $g['total'] = $row['total'];
      
      $po[] = $g;
      
  }
  $returnArr = array("Data"=>$po,"ResponseCode"=>"200","Result"=>"true","ResponseMsg"=>"Order History  Get Successfully!!!");
}
echo json_encode($returnArr);