<?php 
  require 'include/header.php';
  ?>
  


  <body data-col="2-columns" class=" 2-columns ">
      <div class="layer"></div>
    <!-- ////////////////////////////////////////////////////////////////////////////-->
    <div class="wrapper">


     
      <?php include('main.php'); ?>
      <!-- Navbar (Header) Ends-->

      <div class="main-panel">
        <div class="main-content">
          <div class="content-wrapper"><!--Statistics cards Starts-->
<?php if(isset($_GET['edit'])) {
$sels = $con->query("select * from area_db where id=".$_GET['edit']."");
$sels = $sels->fetch_assoc();
?>
<div class="row">
		<div class="col-md-12">
			<div class="card">
				<div class="card-header">
					<h4 class="card-title" id="basic-layout-form">Edit Area</h4>
					
				</div>
				<div class="card-body">
					<div class="px-3">
						<form class="form" method="post" enctype="multipart/form-data">
							<div class="form-body">
								

								

								<div class="form-group">
									<label for="cname">Area Name</label>
									<input type="text" id="cname" value="<?php echo $sels['name'];?>" class="form-control"  name="cname" required >
								</div>

									<div class="form-group">
									<label for="cname">Delivery Charge(Only Digit)</label>
									<input type="text" id="dcharge" value="<?php echo $sels['dcharge'];?>" class="form-control"  name="dcharge" pattern="[0-9]+"  required >
								</div>

							<div class="form-group">
									<label for="cname">Status</label>
									<select name="status" class="form-control">
									    <option value="1" <?php if($sels['status'] == 1){echo 'selected';}?>>Publish</option>
									    <option value="0" <?php if($sels['status'] == 0){echo 'selected';}?>>Unpublish</option>
									</select>
								</div>
								
							

								
							</div>

							<div class="form-actions">
								
								<button type="submit" name="up_cat" class="btn btn-raised btn-raised btn-primary">
									<i class="fa fa-check-square-o"></i> Save
								</button>
							</div>
							
							<?php 
							if(isset($_POST['up_cat'])){
							$cname = mysqli_real_escape_string($con,$_POST['cname']);
							$status = $_POST['status'];
							$dcharge = $_POST['dcharge'];
    $con->query("update area_db set name='".$cname."',status=".$status.",dcharge=".$dcharge." where id=".$_GET['edit']."");
?>
						
							 <script type="text/javascript">
  $(document).ready(function() {
    toastr.options.timeOut = 4500; // 1.5s

    toastr.info('Area Update Successfully!!');
	setTimeout(function()
	{
		window.location.href="alist.php";
	},1500);
    
  });
  </script>
  <?php
							}
							?>
						</form>
					</div>
				</div>
			</div>
		</div>

		
	</div>
<?php } else { ?>
<div class="row">
		<div class="col-md-12">
			<div class="card">
				<div class="card-header">
					<h4 class="card-title" id="basic-layout-form">Add Area</h4>
					
				</div>
				<div class="card-body">
					<div class="px-3">
						<form class="form" method="post" enctype="multipart/form-data">
							<div class="form-body">
								

								

								<div class="form-group">
									<label for="cname">Area Name</label>
									<input type="text" id="cname" class="form-control"  name="cname" required >
								</div>
								
								<div class="form-group">
									<label for="cname">Delivery Charge(Only Digit)</label>
									<input type="text" id="dcharge"  class="form-control" pattern="[0-9]+"  name="dcharge" required >
								</div>
 
									<div class="form-group">
									<label for="cname">Status</label>
									<select name="status" class="form-control">
									    <option value="1">Publish</option>
									    <option value="0">Unpublish</option>
									</select>
								</div>


							

								
							</div>

							<div class="form-actions">
								
								<button type="submit" name="sub_cat" class="btn btn-raised btn-raised btn-primary">
									<i class="fa fa-check-square-o"></i> Save
								</button>
							</div>
							
							<?php 
							if(isset($_POST['sub_cat'])){
							$cname = mysqli_real_escape_string($con,$_POST['cname']);
							$status = $_POST['status'];
							$dcharge = $_POST['dcharge'];
$con->query("insert into area_db(`name`,`status`,`dcharge`)values('".$cname."',".$status.",".$dcharge.")");
?>
						
							 <script type="text/javascript">
  $(document).ready(function() {
    toastr.options.timeOut = 4500; // 1.5s

    toastr.info('Insert Area Successfully!!!');
   
  });
  </script>
  <?php 
							}
							?>
						</form>
					</div>
				</div>
			</div>
		</div>

		
	</div>
	<?php } ?>





          </div>
        </div>

        

      </div>
    </div>
    
   <?php 
  require 'include/js.php';
  ?>
    
 
  </body>


</html>