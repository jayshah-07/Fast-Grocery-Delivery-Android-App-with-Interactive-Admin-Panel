<?php 
  require 'include/header.php';
  ?>
  <body data-col="2-columns" class=" 2-columns ">
      <div class="layer"></div>
    <!-- ////////////////////////////////////////////////////////////////////////////-->
    <div class="wrapper">


      <!-- main menu-->
      <!--.main-menu(class="#{menuColor} #{menuOpenType}", class=(menuShadow == true ? 'menu-shadow' : ''))-->
      <?php include('main.php'); ?>
      <!-- Navbar (Header) Ends-->

      <div class="main-panel">
        <div class="main-content">
          <div class="content-wrapper"><!--Statistics cards Starts-->
 
<div class="row">
		<div class="col-md-12">
			<div class="card">
				<div class="card-header">
					<h4 class="card-title" id="basic-layout-form">Update Setting</h4>
					
				</div>
				<div class="card-body">
					<div class="px-3">
						<form class="form" method="post" enctype="multipart/form-data">
							<div class="form-body">
								

								<?php 
								$getkey = $con->query("select * from setting")->fetch_assoc();
								?>

								<div class="form-group">
									<label for="cname">Onesignal Id</label>
									<input type="text" id="cname" class="form-control"  name="oid" value="<?php echo $getkey['one_key'];?>" required >
								</div>
                                <div class="form-group">
									<label for="cname">Onesignal Hash</label>
									<input type="text" id="cname" class="form-control"  name="ohash" value="<?php echo $getkey['one_hash'];?>" required >
								</div>
								<div class="form-group">
									<label for="cname">Msg91 Api Key</label>
									<input type="text" id="cname" class="form-control"  name="mkey" value="<?php echo $getkey['otp_key'];?>" required >
								</div>
								
								<div class="form-group">
									<label for="cname">Currency</label>
									<input type="text" id="cname" class="form-control"  name="currency" value="<?php echo $getkey['currency'];?>" required >
								</div>
									


							

								
							</div>

							<div class="form-actions">
								
								<button type="submit" name="sub_cat" class="btn btn-raised btn-raised btn-primary">
									<i class="fa fa-check-square-o"></i> Update Setting
								</button>
							</div>
							
							<?php 
							if(isset($_POST['sub_cat'])){
							$oid = $_POST['oid'];
							$ohash = $_POST['ohash'];
							$mkey = $_POST['mkey'];
							$currency = mysqli_real_escape_string($con,$_POST['currency']);
							
$con->query("update setting set one_key='".$oid."',one_hash='".$ohash."',otp_key='".$mkey."',currency='".$currency."' where id=1");

?>
						
							 <script type="text/javascript">
  $(document).ready(function() {
    toastr.options.timeOut = 4500; // 1.5s
    toastr.info('Update Settings Successfully!!!');
    setTimeout(function()
	{
		window.location.href="setting.php";
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
	





          </div>
        </div>

        

      </div>
    </div>
   
   <?php 
  require 'include/js.php';
  ?>
    
 
  </body>


</html>