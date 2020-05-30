<?php 
require 'db.php';
header('Content-type: text/json');
$sel = $con->query("select * from category");
$myarray = array();
while($row = $sel->fetch_assoc())
{
	$p['id'] = $row['id'];
    $p['catname'] = $row['catname'];
		$p['catimg'] = $row['catimg'];
		$p['count'] = $con->query("select * from subcategory where cat_id=".$row['id']."")->num_rows;
		$myarray[] = $p;
}
$returnArr = array("data"=>$myarray,"ResponseCode"=>"200","Result"=>"true","ResponseMsg"=>"Category List Founded!");
echo json_encode($returnArr);
?>