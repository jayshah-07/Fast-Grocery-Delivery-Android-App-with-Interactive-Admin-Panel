<?php 

require 'include/dbconfig.php';

$cid = $_POST['catid'];

$c = $con->query("select * from subcategory where cat_id=".$cid."");
?>
<option value="">Select A Subcategory</option>
<?php 

while($row = $c->fetch_assoc())
{
	?>
	<option value="<?php echo $row['id'];?>"><?php echo $row['name'];?></option>
	<?php 
}