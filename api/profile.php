<?php 
require 'db.php';
header('Content-type: text/json');
$data = json_decode(file_get_contents('php://input'), true);
if($data['name'] == '' or $data['email'] == '' or $data['mobile'] == '' or $data['imei']==''  or $data['landmark'] == '' or $data['password'] == '' or $data['hno'] == '' or $data['society'] == '' or $data['area'] == '' or $data['pincode'] == '')
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
$uid =  strip_tags(mysqli_real_escape_string($con,$data['uid']));
$checkimei = mysqli_num_rows(mysqli_query($con,"select * from user where  `id`=".$uid.""));

if($checkimei != 0)
    {
		if($uid == 29)
        {
        date_default_timezone_set('Asia/Kolkata');
        $timestamp = date("Y-m-d H:i:s");
       
            $c = mysqli_fetch_assoc(mysqli_query($con,"select * from user where id='".$uid."'"));
            $dc = mysqli_fetch_assoc(mysqli_query($con,"select * from area_db where name='".$c['area']."'"));
        $returnArr = array("user"=>$c,"d_charge"=>$dc['dcharge'],"ResponseCode"=>"200","Result"=>"true","ResponseMsg"=>"its Test User So Not Change Data!!!");
        }
		else 
        {
        date_default_timezone_set('Asia/Kolkata');
        $timestamp = date("Y-m-d H:i:s");
       $con->query("update user set name='".$name."',hno='".$hno."',society='".$society."',area='".$area."',pincode='".$pincode."',email='".$email."',mobile='".$mobile."',landmark='".$landmark."',password='".$password."' where id=".$uid."");
            $c = $con->query("select * from user where id='".$uid."'");
            $c = $c->fetch_assoc();
            $dc = $con->query("select * from area_db where name='".$c['area']."'");
            $dc = $dc->fetch_assoc();
        $returnArr = array("user"=>$c,"d_charge"=>$dc['dcharge'],"ResponseCode"=>"200","Result"=>"true","ResponseMsg"=>"Profile Update successfully!");
        
    }
	}
    else
    {
      $returnArr = array("ResponseCode"=>"401","Result"=>"false","ResponseMsg"=>"Request To Update Own Device!!!!");  
    }
    
}

echo json_encode($returnArr);