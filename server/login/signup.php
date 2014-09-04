<?php

if(isset($_POST['name']) && isset($_POST['pswd']) && isset($_POST['email'])){

require_once("../config.php");

$strsql="INSERT INTO user_info (uld_id,user_name,user_password,user_email) VALUES(2,'".$_POST["name"]."','".$_POST["pswd"]."','".$_POST["email"]."');";
$res=mysql_db_query($mysql_database, $strsql, $conn);

if($res){
    echo "Sign In Succeed";
}else{
    echo mysql_error();
}

mysql_free_result($result);
mysql_close($conn);  

}else{
    echo "No args";
}

?>

