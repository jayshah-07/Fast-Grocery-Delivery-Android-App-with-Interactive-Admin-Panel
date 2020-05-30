<?php 
require 'db.php';
header('Content-type: text/json');
$sel = $con->query("select * from area_db where status=1");
while($row = $sel->fetch_assoc())
{
    $myarray[] = $row;
}
$returnArr = array("data"=>$myarray,"ResponseCode"=>"200","Result"=>"true","ResponseMsg"=>"Area List Founded!");
echo json_encode($returnArr);
?>