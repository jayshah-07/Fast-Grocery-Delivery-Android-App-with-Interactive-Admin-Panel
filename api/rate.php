<?php 
require 'db.php';
$data = json_decode(file_get_contents('php://input'), true);
if($data['uid'] == ''  or $data['rate'] == '' or $data['oid'] == '')
{
    $returnArr = array("ResponseCode"=>"401","Result"=>"false","ResponseMsg"=>"Something Went Wrong!");
}
else
{
    $uid = strip_tags(mysqli_real_escape_string($con,$data['uid']));
    $msg = strip_tags(mysqli_real_escape_string($con,$data['msg']));
    $rate = strip_tags(mysqli_real_escape_string($con,$data['rate']));
    $oid = strip_tags(mysqli_real_escape_string($con,$data['oid']));

$con->query("insert into rate_order(`uid`,`msg`,`rate`,`oid`)values(".$uid.",'".$msg."',".$rate.",'".$oid."')");
    $con->query("update orders set rate=1 where oid='".$oid."' and uid=".$uid."");
     $returnArr = array("ResponseCode"=>"200","Result"=>"true","ResponseMsg"=>"Order Rate Submitted Successfully!");
        
}
echo json_encode($returnArr);
