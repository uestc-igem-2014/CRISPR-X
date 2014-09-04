<?php

require_once("../config.php");

$strsql="SELECT t.user_id from user_token as t join user_info as i where t.user_id=i.user_id and uld_id<=2 and token_str='".$_POST['token']."'; ";
$result=mysql_db_query($mysql_database, $strsql, $conn);

mysql_data_seek($result, 0);
if($row=mysql_fetch_row($result)){
    echo '{"status":0}';
}else{
    echo '{"status":1, "message":"Authentication failed"}';
}

mysql_free_result($result);
mysql_close($conn);

?>

