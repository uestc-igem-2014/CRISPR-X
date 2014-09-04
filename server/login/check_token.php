<?php

function ip2int($ip){ 
    list($ip1,$ip2,$ip3,$ip4)=explode(".",$ip); 
    return (16777216*$ip1)+(65536*$ip2)+(256*$ip3)+($ip4); 
}

require_once("../config.php");

echo "name: ".$_COOKIE["user"]."<br>";
echo "token: ".$_COOKIE["token"]."<br>";
echo "ip:".$_SERVER['REMOTE_ADDR']."<br>";
echo "ip2int: ".ip2int($_SERVER['REMOTE_ADDR'])."<br>";

$strsql="SELECT * from user_token as t join user_info as i where t.user_id=i.user_id and token_str='".$_COOKIE['token']."' and token_ip='".ip2int($_SERVER['REMOTE_ADDR'])."' and user_name='".$_COOKIE['user']."'; ";
$result=mysql_db_query($mysql_database, $strsql, $conn);

mysql_data_seek($result, 0);
if($row=mysql_fetch_row($result)){
    echo "ok";
}else{
    echo "bad";
}


mysql_free_result($result);
mysql_close($conn);

?>

