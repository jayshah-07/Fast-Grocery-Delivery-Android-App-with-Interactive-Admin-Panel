<?php 
require 'db.php';
$data = json_decode(file_get_contents('php://input'), true);
if($data['uid'] == '' or $data['oid'] == '')
{
    $returnArr = array("ResponseCode"=>"401","Result"=>"false","ResponseMsg"=>"Something Went Wrong!");
}
else
{
    $uid = strip_tags(mysqli_real_escape_string($con,$data['uid']));
    $oid = strip_tags(mysqli_real_escape_string($con,$data['oid']));
    $con->query("update orders set status='cancelled' where  id=".$oid." and uid=".$uid."");
    $returnArr = array("ResponseCode"=>"200","Result"=>"true","ResponseMsg"=>"Order Cancle Successfully!");
    
}
echo json_encode($returnArr);