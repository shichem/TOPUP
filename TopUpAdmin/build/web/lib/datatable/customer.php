

<?php
error_reporting(0);
// for test
/*$page_file_temp = $_SERVER["PHP_SELF"];

$page_directory = dirname($page_file_temp);

$array = explode("/",$page_directory);*/

session_start();

if(!isset($_SESSION['user']) && empty($_SESSION['user'])) {
	header('Location: ./index.php');
}



$client =json_decode($_SESSION["user"]);
$array1 = $client->siteid;

//error_reporting(0);

//Get the MAC addresses of AP and user


        $ch = curl_init();
        // set url
        curl_setopt($ch, CURLOPT_URL, "http://23.154.2.71/CRMWifiPortail/getInfoClient.php?siteid=".$client->siteid);     
        //return the transfer as a string
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
        // $output contains the output string
        $output = curl_exec($ch);
        // close curl resource to free up system resources
        curl_close($ch);    
        
     
      $obj =  json_decode($output);
      
      //var_dump($obj);
// include language configuration file based on selected language

   //echo $obj->lang;
  
   $lang = substr($_SERVER['HTTP_ACCEPT_LANGUAGE'], 0, 2);
    $acceptLang = ['fr', 'en']; 
    $lang = in_array($lang, $acceptLang) ? $lang : 'en';
  
	require_once("./lang/lang.".$lang.".php");
 
 
  function base(){
 echo str_replace("index.php","",$_SERVER["PHP_SELF"]);
 }
?>

<html lang="<?php echo $lang; ?>">

<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title><?php echo $language["registre"]  ;?> !</title>

 
  <link rel="stylesheet" href="static/style.css" />
  <link rel="stylesheet" href="static/normalize.css" />
  <link rel="stylesheet" href="static/notiflix-2.4.0.min.css" />

  <style>
   
  </style>
</head>
<!DOCTYPE html>

<body>
  <div class="container">
    <main class="row">
      <div class="column  client-logo">
      
      
        <img src="<?php echo $obj->logo_url;  ?>" />
      
      </div>
      <div class="column form-side">
        <div class="main-title1">
          <img src="static/icons/bookmark.png" />
        
        </div> 
        <form method="post" id="form1" action="addCustomer.php" >

        <div class="form-group">
            <input class="name-input" type="text" id="fullname" name="fullname" autocomplete="off" required placeholder="<?php echo $language['name_placeholder'] ;?>" 
             data-valueMissing="<?php echo $language['name_valueMissing'] ;?>"
             />
          </div>
        <?php 
       
        ?>
        
          <div class="form-group">
            <input class="email-input" type="email" id="email" name="email" autocomplete="off" placeholder="<?php echo $language['email_placeholder'] ;?>" 
            required data-typeMismatch="<?php echo $language['email_typeMismatch'] ;?>" 
             data-valueMissing="<?php echo $language['email_valueMissing'] ;?>"
             />
          </div>
          <?php 
        
          ?>
          
          <div class="form-group">
            <input class="phone-input" type="text" id="phone-number" name="phone-number" autocomplete="off" required placeholder="<?php echo $language['phone_placeholder'] ;?>" 
             data-valueMissing="<?php echo $language['phone_valueMissing'] ;?>"
             />
          </div>
          <?php 
          
          ?>
         
          <button type="submit"><?php echo $language['registre'] ;?></button>


        </form>
      </div>
    </main>
    <footer class="footer">
     <?php echo $language['service'] ;?>
      <a href="http://transattelecom.ca" target="_blank"><img src="static/images/provider-logo.png" /></a>
    </footer>
  </div>

  <!-- The Modal -->
  <div id="modal" class="modal">
    <!-- Modal content -->
    <div class="modal-content">
      <span class="close">&times;</span>
      <p><?php 
       require_once( $language['termsURL']) ;
      ?></p>
    </div>
  </div>
  <script src="static/valid-form.min.js"></script>
  <script src="static/app.js"></script>
  <script src="static/notiflix-2.4.0.min.js"></script>
  <script src="static/notiflix-aio-2.4.0.min.js"></script>
  <?php 
if(isset($_SESSION['addCustomer']) && !empty($_SESSION['addCustomer'])) {
  unset($_SESSION['addCustomer']);

  echo "<script>
  
  Notiflix.Report.Success('Success','".$language['addSucces']."','ok');
  </script>";
  
  }
?>
</body>

</html>