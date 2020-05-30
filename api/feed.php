<?php 
require 'db.php';
$data = json_decode(file_get_contents('php://input'), true);
if($data['uid'] == '' or $data['msg'] == '' or $data['rate'] == '')
{
    $returnArr = array("ResponseCode"=>"401","Result"=>"false","ResponseMsg"=>"Something Went Wrong!");
}
else
{
    $uid = strip_tags(mysqli_real_escape_string($con,$data['uid']));
    $msg = strip_tags(mysqli_real_escape_string($con,$data['msg']));
    $rate = strip_tags(mysqli_real_escape_string($con,$data['rate']));

    $con->query("insert into feedback(`uid`,`msg`,`rate`)values(".$uid.",'".$msg."',".$rate.")");
     $returnArr = array("user"=>$c,"ResponseCode"=>"200","Result"=>"true","ResponseMsg"=>"Feedback Submitted Successfully!");
        
}
echo json_encode($returnArr);
