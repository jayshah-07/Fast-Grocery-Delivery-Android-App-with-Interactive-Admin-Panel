<?php 
require 'db.php';
$data = json_decode(file_get_contents('php://input'), true);
if($data['name'] == '' or $data['email'] == '' or $data['mobile'] == '' or $data['imei']=='' or $data['hno'] == '' or $data['society'] == '' or $data['area'] == '' or $data['pincode'] == ''  or $data['landmark'] == '' or $data['password'] == '')
{
    $returnArr = array("ResponseCode"=>"401","Result"=>"false","ResponseMsg"=>"Something Went Wrong!");
}
else
{
    
    $name = strip_tags(mysqli_real_escape_string($con,$data['name']));
    $email = strip_tags(mysqli_real_escape_string($con,$data['email']));
    $mobile = strip_tags(mysqli_real_escape_string($con,$data['mobile']));
    $imei = strip_tags(mysqli_real_escape_string($con,$data['imei']));
    
    
    $hno = strip_tags(mysqli_real_escape_string($con,$data['hno']));
    $society = strip_tags(mysqli_real_escape_string($con,$data['society']));
    $area = strip_tags(mysqli_real_escape_string($con,$data['area']));
    $pincode = strip_tags(mysqli_real_escape_string($con,$data['pincode']));
    
    
    $landmark = strip_tags(mysqli_real_escape_string($con,$data['landmark']));
     $password = strip_tags(mysqli_real_escape_string($con,$data['password']));
     
     
     
    $checkmob = $con->query("select * from user where mobile='".$mobile."'");
    $checkemail = $con->query("select * from user where mobile='".$email."'");
   
    if($checkmob->num_rows != 0)
    {
        $returnArr = array("ResponseCode"=>"401","Result"=>"false","ResponseMsg"=>"Mobile Number Already Used!");
    }
     else if($checkemail->num_rows != 0)
    {
        $returnArr = array("ResponseCode"=>"401","Result"=>"false","ResponseMsg"=>"Email Address Already Used!");
    }
    else
    {
        date_default_timezone_set('Asia/Kolkata');
        $timestamp = date("Y-m-d H:i:s");
        
        $con->query("insert into user(`name`,`hno`,`society`,`area`,`pincode`,`imei`,`email`,`mobile`,`landmark`,`rdate`,`password`)values('".$name."','".$hno."','".$society."','".$area."','".$pincode."','".$imei."','".$email."','".$mobile."','".$landmark."','".$timestamp."','".$password."')");
    
        $returnArr = array("ResponseCode"=>"200","Result"=>"true","ResponseMsg"=>"Registration successfully!");
    }
    
    
}

echo json_encode($returnArr);