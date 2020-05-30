<?php 

require 'db.php';
$data = json_decode(file_get_contents('php://input'), true);

$uid = $data['uid'];
if($uid == '')
{
	$returnArr = array("ResponseCode"=>"401","Result"=>"false","ResponseMsg"=>"Something Went wrong  try again !");
}
else 
{ 
	$v = array();
	$cp = array();
	$d = array();
	$p = array();
$sel = $con->query("select * from banner");
while($row = $sel->fetch_assoc())
{
    $v[] = $row;
}

$sels = $con->query("select * from category limit 6");
while($rows = $sels->fetch_assoc())
{
    $p['id'] = $rows['id'];
		$p['catname'] = $rows['catname'];
		$p['catimg'] = $rows['catimg'];
		$p['count'] = $con->query("select * from subcategory where cat_id=".$rows['id']."")->num_rows;
		$cp[] = $p;
}
$result = array();
$prod = $con->query("select * from product where status=1 and popular = 1 order by id desc limit 5 ");
	while($row = $prod->fetch_assoc())
	{
		$result['id'] = $row['id'];
    $result['product_name'] = $row['pname'];
    $result['product_image'] = $row['pimg'];
    $result['seller_name'] = $row['sname'];
    $result['short_desc'] = $row['psdesc'];
    $a = explode('$;',$row['pgms']);
    //print_r($a[0]);
    $ab = explode('$;',$row['pprice']);
    $k=array();
    for($i=0;$i<count($a);$i++)
    {
        $k[$i] = array("product_type"=>$a[$i],"product_price"=>$ab[$i]);
    }
    
    $result['price'] = $k;
	$result['discount'] = $row['discount'];
    $result['stock'] = $row['stock'];
    $d[] = $result;
	}
	$udata = $con->query("select * from user where id=".$uid."")->fetch_assoc();
	$date_time = $udata['rdate'];
	$new_date = date("Y-m-d",strtotime($date_time));
$remain = $con->query("select * from noti where date >='".$new_date."'")->num_rows;

	
	$read = $con->query("select * from uread where uid=".$uid."")->num_rows;
	$r_noti = $remain - $read;
	$curr = $con->query("select * from setting")->fetch_assoc();
	$kp = array('Banner'=>$v,'Catlist'=>$cp,'Productlist'=>$d,"Remain_notification"=>$r_noti,"currency"=>$curr['currency']);
	
	$returnArr = array("ResponseCode"=>"200","Result"=>"true","ResponseMsg"=>"Data Get Successfully!","ResultData"=>$kp);
}
echo json_encode($returnArr);