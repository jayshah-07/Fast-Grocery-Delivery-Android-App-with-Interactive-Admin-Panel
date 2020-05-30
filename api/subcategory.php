<?php 
require 'db.php';
$data = json_decode(file_get_contents('php://input'), true);
if($data['category_id'] == '')
{
    $returnArr = array("ResponseCode"=>"401","Result"=>"false","ResponseMsg"=>"Something Went Wrong!");
}
else
{
	$cat_id = $data['category_id'];
	
$sel = $con->query("select * from subcategory where cat_id=".$cat_id."");
$count = $con->query("select * from subcategory where cat_id=".$cat_id."")->num_rows;
if($count != 0)
{
	$myarray = array();
while($row = $sel->fetch_assoc())
{
     $p['id'] = $row['id'];
	  $p['cat_id'] = $row['cat_id'];
		$p['name'] = $row['name'];
		$p['img'] = $row['img'];
		$p['count'] = $con->query("select * from product where sid=".$row['id']."")->num_rows;
		$myarray[] = $p;
}
$returnArr = array("data"=>$myarray,"ResponseCode"=>"200","Result"=>"true","ResponseMsg"=>"Subcategory List Founded!");
}
else 
{
$returnArr = array("ResponseCode"=>"401","Result"=>"false","ResponseMsg"=>"Category Not Found!!!");	
}
}
echo json_encode($returnArr);
?>