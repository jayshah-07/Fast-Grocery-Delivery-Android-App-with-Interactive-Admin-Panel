<?php 
 
 require 'db.php';
$data = json_decode(file_get_contents('php://input'), true);
$uid = $data['uid'];
$nid = $data['nid'];

if ($uid == '' or $nid == '')
{
$returnArr = array("ResponseCode"=>"401","Result"=>"false","ResponseMsg"=>"Something Went wrong  try again !");
}
else 
{
	$sel = $con->query("select * from uread where uid=".$uid." and nid=".$nid."")->num_rows;
	if($sel != 0 )
	{
	    
	    $returnArr = array("ResponseCode"=>"401","Result"=>"false","ResponseMsg"=>"Notification Already Read!!");
	    
		
		
	}
	else 
	{
	   $con->query("insert into uread(`uid`,`nid`)values(".$uid.",".$nid.")");
	   $udata = $con->query("select * from user where id=".$uid."")->fetch_assoc();
	$date_time = $udata['rdate'];
	$new_date = date("Y-m-d",strtotime($date_time));
$remain = $con->query("select * from noti where date >='".$new_date."'")->num_rows;

	$read = $con->query("select * from uread where uid=".$uid."")->num_rows;
	$r_noti = $remain - $read;
	    
		$returnArr = array("ResponseCode"=>"200","Result"=>"true","ResponseMsg"=>"notification read Successfully!","Remain_notification"=>$r_noti);
	}
}
echo json_encode($returnArr);
mysqli_close($con);
?>