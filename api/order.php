<?php 
require 'db.php';
$data = json_decode(file_get_contents('php://input'), true);
if($data['uid'] == '')
{
 $returnArr = array("ResponseCode"=>"401","Result"=>"false","ResponseMsg"=>"Something Went Wrong!");    
}
else
{
    
$uid =  $data['uid'];
$ddate = $data['ddate'];
$a = explode('/',$ddate);
$ddate = $a[2].'-'.$a[1].'-'.$a[0];
$timesloat = $data['timesloat'];
$oid ='#'.uniqid();
$pname = $data['pname'];
$status = 'pending';
$p_method = $data['p_method'];
date_default_timezone_set('Asia/Kolkata');
$timestamp = date("Y-m-d");

$total = $data['total'];
$e= array();
$p = array();
$w=array();
$pp = array();
$q = array();
for($i=0;$i<count($pname);$i++)
{
$e[] = $pname[$i]['title'];
$p[] = $pname[$i]['pid'];
$w[] = $pname[$i]['weight'];
$pp[] = $pname[$i]['cost'];
$q[] = $pname[$i]['qty'];
}
$pname = implode('$;',$e);
$pid = implode('$;',$p);
$ptype = implode('$;',$w);
$pprice = implode('$;',$pp);
$qty = implode('$;',$q);

$con->query("insert into orders(`oid`,`uid`,`pname`,`pid`,`ptype`,`pprice`,`ddate`,`timesloat`,`order_date`,`status`,`qty`,`total`,`p_method`)values('".$oid."',".$uid.",'".$pname."','".$pid."','".$ptype."','".$pprice."','".$ddate."','".$timesloat."','".$timestamp."','".$status."','".$qty."',".$total.",'".$p_method."')");
$returnArr = array("ResponseCode"=>"200","Result"=>"true","ResponseMsg"=>"Order Place Successfully!!!");
}

echo json_encode($returnArr);