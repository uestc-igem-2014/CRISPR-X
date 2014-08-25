<?php
$conn=mysql_connect("localhost","igem","uestc2014!");
$mysql_database="CasDB";

$result=mysql_db_query($mysql_database, "SELECT request_txt FROM user_request WHERE request_ID=".$_POST["id"]." and request_txt is not null;", $conn);
if($row=mysql_fetch_row($result)){
    echo $row[0];
}else{
    mysql_free_result($result);
    $result=mysql_db_query($mysql_database, "SELECT request_txt FROM user_request WHERE request_ID=".$_POST["id"].";", $conn);
    if($row=mysql_fetch_row($result)){
        echo '{"status":2,"message":"not finished yet"}';
    }else{
        echo '{"status":1,"message":"no this request ID"}';
    }
}
mysql_free_result($result);
mysql_close($conn);
?>

