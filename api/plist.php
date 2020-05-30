<?php 
require 'db.php';
$data = json_decode(file_get_contents('php://input'), true);
if($data['uid'] == '')
{ 
 $returnArr = array("ResponseCode"=>"401","Result"=>"false","ResponseMsg"=>"Something Went Wrong!");    
}
else
{
    $id = strip_tags(mysqli_real_escape_string($con,$data['id']));
 $uid =  strip_tags(mysqli_real_escape_string($con,$data['uid']));
 
  $sel = $con->query("select * from orders where uid=".$uid." and id=".$id."");
  
  
  $result = array();
  $pp = array();
  while($row = $sel->fetch_assoc())
    {
 $oid = $row['oid']; 
    $a = explode('$;',$row['pname']);    
    $b =  explode('$;',$row['pprice']);
    $c = explode('$;',$row['ptype']);
    $d = explode('$;',$row['qty']);
    $e = explode('$;',$row['pid']);
     $k=array();
    for($i=0;$i<count($a);$i++)
    {
        $getimage = $con->query("select * from product where id=".$e[$i]."");
        $getimage = $getimage->fetch_assoc();
        $k[$i] = array("product_name"=>$a[$i],"product_price"=>number_format((float)$b[$i], 2, '.', ''),"product_weight"=>$c[$i],"product_qty"=>$d[$i],"product_image"=>$getimage['pimg'],"discount"=>$getimage['discount']);
    }
    
    
    if($row['p_method'] == 'Pickup myself' and $row['status'] != 'completed' and $row['status'] != 'cancelled')
    {
        $status = $row['p_method'];
    }
    else 
    {
    $status =$row['status'];
    }
	$p_method = $row['p_method'];
    $total =$row['total'] ;
    $odate = $row['order_date'];
    $timesloat = $row['timesloat'];
    //$result['total'] = $row['total'];
    //$result['status'] = $row['status'];
    //$result['order_date'] = $row['order_date'];
    //$result['timesloat'] = $row['timesloat'];
    //$pp[] = $result;
    }
   
    $orate = $con->query("select * from rate_order where oid='".$oid."'");
      if($orate->num_rows != 0)
      {
          $rate = 'Yes';
      }
      else 
      {
         $rate = 'No'; 
      }
      
            $c = $con->query("select * from user where id='".$uid."'");
      $c = $c->fetch_assoc();
$dc = $con->query("select * from area_db where name='".$c['area']."'");
            $dc = $dc->fetch_assoc();
			
			if($p_method == 'Pickup myself')
			{
				$px = 0;
			}
			else 
			{
				
			$px = $dc['dcharge'];
			}
    $returnArr = array("productinfo"=>$k,"orderid"=>$oid,"total_amt"=>$total,"status"=>$status,"order_date"=>$odate,"timesloat"=>$timesloat,"Israted"=>$rate,"d_charge"=>$px,"ResponseCode"=>"200","Result"=>"true","ResponseMsg"=>"Order Product Get successfully!");
}
echo json_encode($returnArr);