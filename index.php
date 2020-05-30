<?php 
require 'include/front_header.php';
?>
  <body data-col="1-column" class=" 1-column  blank-page blank-page">
      <div class="layer"></div>
    <!-- ////////////////////////////////////////////////////////////////////////////-->
    <div class="wrapper nav-collapsed menu-collapsed">
      <div class="main-panel">
        <div class="main-content">
          <div class="content-wrapper"><!--Login Page Starts-->
<section id="login">
    <div class="container-fluid">
        <div class="row full-height-vh">
            <div class="col-12 d-flex align-items-center justify-content-center">
                <div class="card gradient-indigo-purple text-center width-400">
                    <div class="card-img overlap">
                        <img alt="element 06" class="mb-1" src="app-assets/img/logo.png" width="100">
                    </div>
                    <div class="card-body">
                        <div class="card-block">
                            <h2 class="white">Login</h2>
                            <form method="post">
                                <div class="form-group">
                                    <div class="col-md-12">
                                        <input type="text" class="form-control" name="inputEmail" id="inputEmail" placeholder="Email" required >
                                    </div>
                                </div>

                                <div class="form-group">
                                    <div class="col-md-12">
                                        <input type="password" class="form-control" name="inputPass" id="inputPass" placeholder="Password" required>
                                    </div>
                                </div>

                               

                                <div class="form-group">
                                    <div class="col-md-12">
                                        <button type="submit" name="sub_log" class="btn btn-pink btn-block btn-primary">Login</button>
                                       
                                    </div> 
                                </div>
                            </form>
                        </div>
                       
                    </div>
                     
                    <?php
                    if(isset($_POST['sub_log']))
                    {
                   
                    $email = $_POST['inputEmail'];
                    $pass = $_POST['inputPass'];
                    
                    $sel = $con->query("select * from admin where username='".$email."' and password='".$pass."'")->num_rows;
                    
                    if($sel != 0)
                    {
                    $_SESSION['username'] = $email;
                    ?>
                    <script>
                    window.location.href="dashboard.php";
                    </script>
                    <?php 
                    }
                    else 
                    {
                    ?>
                    <script>
                    alert('email address and password wrong!');
                    </script>
                    <?php 
                    }
                    }
                    ?>
                </div>
            </div>
        </div>
    </div>
</section>

          </div>
        </div>
      </div>
    </div>
    <!-- ////////////////////////////////////////////////////////////////////////////-->

    
   <?php 
   require 'include/js.php';
   ?>
   
  </body>


</html>