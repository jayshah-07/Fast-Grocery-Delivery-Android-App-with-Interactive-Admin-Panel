<?php 
require 'db.php';
$data = json_decode(file_get_contents('php://input'), true);
if($data['email'] == '')
{
    $returnArr = array("ResponseCode"=>"401","Result"=>"false","ResponseMsg"=>"Something Went Wrong!");
}
else
{
    
    $search = $con->query("select * from user where mobile='".$data['email']."'");
    if($search->num_rows != 0)
    {
        $pin = rand(1000,10000);
        $up = mysqli_query($con,"update user set pin='".$pin."' where mobile='".$data['email']."'");
        
       $getkey = $con->query("select * from setting");
    $key = $getkey->fetch_assoc();
// use wordwrap() if lines are longer than 70 characters

$curl = curl_init();

curl_setopt_array($curl, array(
  CURLOPT_URL => "https://control.msg91.com/api/sendotp.php?otp=".$pin."&authkey=".$key['otp_key']."&mobile=".$data['email']."",
  CURLOPT_RETURNTRANSFER => true,
  CURLOPT_ENCODING => "",
  CURLOPT_MAXREDIRS => 10,
  CURLOPT_TIMEOUT => 30,
  CURLOPT_HTTP_VERSION => CURL_HTTP_VERSION_1_1,
  CURLOPT_CUSTOMREQUEST => "POST",
  CURLOPT_POSTFIELDS => "",
  CURLOPT_SSL_VERIFYHOST => 0,
  CURLOPT_SSL_VERIFYPEER => 0,
));

$response = curl_exec($curl);
$err = curl_error($curl);

curl_close($curl);


 $returnArr = array("ResponseCode"=>"200","Result"=>"true","ResponseMsg"=>"Sms Send Successfully. Please Have a look on Sms!!");
    }
    else
  {
      $returnArr = array("ResponseCode"=>"401","Result"=>"false","ResponseMsg"=>"Mobile Number Not Found. Please Give Registered Mobile Number!!");
  }
}
echo json_encode($returnArr);
?>