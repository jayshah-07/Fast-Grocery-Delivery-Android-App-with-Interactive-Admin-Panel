 <?php 
  require 'include/header.php';
  $getkey = mysqli_fetch_assoc(mysqli_query($con,"select * from setting"));
define('ONE_KEY',$getkey['one_key']);
define('ONE_HASH',$getkey['one_hash']);
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

<section id="dom">
    <div class="row">
        <div class="col-12">
            <div class="card">
                <div class="card-header">
                    <h4 class="card-title">Notification List</h4>
                </div>
                <div class="card-body collapse show">
                    <div class="card-block card-dashboard">
                       
                        <table class="table table-striped table-bordered dom-jQuery-events">
                            <thead>
                                <tr>
								 <th>Sr No.</th>
                                    <th>Title</th>
                                     <th>Message</th>
                                    <th>Image</th>
                                    <th>Action</th>

                                </tr>
                            </thead>
                            <tbody>
                                <?php 
                                $sel = $con->query("select * from template order by id desc");
                                $i=0;
                                while($row = $sel->fetch_assoc())
                                {
                                    $i= $i + 1;
                                ?>
                                <tr>
                                    
                                    <td><?php echo $i; ?></td>
                                    <td><?php echo $row['title'];?></td>
                                     <td style="    min-width: 100px;
    word-break: break-word;"><?php echo $row['message'];?></td>
									  <td><?php if($row['url'] == 'no_url') { echo $row['url'];}else {?>
                                       <img src="<?php echo $row['url'];?>" width="100" height="100"/>
                                       <?php }?></td>
                                  
									<td style="    display: flex;">
									<form method="post">
									<input type="hidden" name="nid" value="<?php echo $row['id'];?>"/>
									<button class="primary" type="submit" style="    border: none;
    background: transparent;"><i class="ft-bell font-medium-3"></i></button>
									</form>
									
									
										
									<a class="primary"  href="template.php?edit=<?php echo $row['id'];?>" data-original-title="" title="">
                                            <i class="ft-edit font-medium-3"></i>
                                        </a>
										&nbsp;&nbsp;
									<a class="danger" href="?dele=<?php echo $row['id'];?>" data-original-title="" title="">
                                            <i class="ft-trash font-medium-3"></i>
                                        </a>
										
										</td>
                                   
                                </tr>
                               <?php } ?>
                            </tbody>
                            
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<?php 
if(isset($_GET['dele']))
{
$con->query("delete from template where id=".$_GET['dele']."");
?>
	 <script type="text/javascript">
  $(document).ready(function() {
    toastr.options.timeOut = 4500; // 1.5s

    toastr.error('selected notification deleted successfully.');
    setTimeout(function()
	{
		window.location.href="templatelist.php";
	},1500);
  });
  </script>
  <?php
}
?>

<?php 
                        if(isset($_POST['nid']))
                        {
							 
                         $udata = mysqli_fetch_assoc(mysqli_query($con,"select * from template where id=".$_POST['nid'].""));
                         $msg = mysqli_real_escape_string($con,$udata['message']);
                         $title = mysqli_real_escape_string($con,$udata['title']);
                         $url = $udata['url'];
                         
                         $content = array(
			"en" => $title
			);
		
		$fields = array(
			'app_id' => ONE_KEY,
			'included_segments' => array('Active Users'),
			'data' => array("url" =>$url,"message"=>$msg),
			'contents' => $content
		);
		
		$fields = json_encode($fields);
    //	print("\nJSON sent:\n");
    //	print($fields);
		
		$ch = curl_init();
		curl_setopt($ch, CURLOPT_URL, "https://onesignal.com/api/v1/notifications");
		curl_setopt($ch, CURLOPT_HTTPHEADER, array('Content-Type: application/json; charset=utf-8',
												   'Authorization: Basic '.ONE_HASH));
		curl_setopt($ch, CURLOPT_RETURNTRANSFER, TRUE);
		curl_setopt($ch, CURLOPT_HEADER, FALSE);
		curl_setopt($ch, CURLOPT_POST, TRUE);
		curl_setopt($ch, CURLOPT_POSTFIELDS, $fields);
		curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, FALSE);

		$response = curl_exec($ch);
		curl_close($ch);
		
		date_default_timezone_set('Asia/Kolkata');
        $timestamp = date("Y-m-d");
		mysqli_query($con,"insert into noti(`msg`,`date`,`title`,`img`)values('".$msg."','".$timestamp."','".$title."','".$url."')");
		 ?>
        <script type="text/javascript">
  $(document).ready(function() {
    toastr.options.timeOut = 4500; // 1.5s

    toastr.info('Notification Send Successfully!!!');
   setTimeout(function()
	{
		window.location.href="templatelist.php";
	},1500);
  });
  </script>
        <?php 
                        }
						?>



          </div>
        </div>

      

      </div>
    </div>
    
    <?php 
  require 'include/js.php';
  ?>
  </body>


</html>