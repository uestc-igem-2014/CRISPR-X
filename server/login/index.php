<?php

function ip2int($ip){ 
    list($ip1,$ip2,$ip3,$ip4)=explode(".",$ip); 
    return (16777216*$ip1)+(65536*$ip2)+(256*$ip3)+($ip4); 
}

require_once("../config.php");

$strsql="SELECT user_id FROM `user_info` WHERE uld_id<=2 and user_name='".$_POST['name']."' and user_password='".$_POST['pswd']."'";
$result=mysql_db_query($mysql_database, $strsql, $conn);

mysql_data_seek($result, 0);

if($row=mysql_fetch_row($result)){
    $user_id=$row[0];
    $token=md5($user_id."|".time()."|".$_SERVER['REMOTE_ADDR'],false);
    $sql="INSERT INTO user_token (token_str,user_id,token_ip) VALUES('".$token."',".$user_id.",".ip2int($_SERVER['REMOTE_ADDR']).");";
    mysql_db_query($mysql_database,$sql,$conn);
    echo $token;
}else{
    echo '-';
}

mysql_free_result($result);
mysql_close($conn);  

?>

