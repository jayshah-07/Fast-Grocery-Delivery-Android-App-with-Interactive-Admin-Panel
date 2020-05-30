<?php
require 'db.php';
 header( 'Content-Type: text/html; charset=utf-8' ); 
$data = json_decode(file_get_contents('php://input'), true);

$pin = $data['pin'];
$password = $data['password'];
if ($pin =='' or $password =='')
{
$returnArr = array("ResponseCode"=>"401","Result"=>"false","ResponseMsg"=>"Something Went wrong  try again !");
}
else 
{
    
    $pin = strip_tags(mysqli_real_escape_string($con,$pin));
    $password = strip_tags(mysqli_real_escape_string($con,$password));
    
    $counter = $con->query("select * from user where pin='".$pin."'");
    
   
    
    if($counter->num_rows != 0)
    {
     $con->query("update user set password='".$password."' where pin='".$pin."'");
     $returnArr = array("ResponseCode"=>"200","Result"=>"true","ResponseMsg"=>"Password Changed Successfully!!!!!");    
    }
    else
    {
     $returnArr = array("ResponseCode"=>"401","Result"=>"false","ResponseMsg"=>"Pin Not Matched!!!!");  
    }
}

echo json_encode($returnArr);
