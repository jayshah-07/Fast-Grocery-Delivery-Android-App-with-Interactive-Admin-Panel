<?php 
require 'db.php';
$sel = $con->query("select * from timeslot");
while($row = $sel->fetch_assoc())
{
    $myarray[] = $row;
}
$returnArr = array("data"=>$myarray,"ResponseCode"=>"200","Result"=>"true","ResponseMsg"=>"Timeslot Founded!");
echo json_encode($returnArr);
?>