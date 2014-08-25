<?php

function ip2int($ip){ 
    list($ip1,$ip2,$ip3,$ip4)=explode(".",$ip); 
    return (16777216*$ip1)+(65536*$ip2)+(256*$ip3)+($ip4); 
}

$conn=mysql_connect("localhost","igem","uestc2014!");
$mysql_database="CasDB";

$strsql="SELECT user_id, user_email FROM `user_info` WHERE uld_id=2 and user_name='".$_POST['name']."' and user_password='".$_POST['pswd']."'";
$result=mysql_db_query($mysql_database, $strsql, $conn);

mysql_data_seek($result, 0);
if($row=mysql_fetch_row($result)){
    $new_pswd=$_POST['pswd'];
    if(isset($_POST['newpswd'])){
        $new_pswd=$_POST['newpswd'];
    }
    $new_email=$row[1];
    if(isset($_POST['newemail'])){
        $new_email=$_POST['newemail'];
    }

    $sql="UPDATE user_info SET user_password='".$new_pswd."', user_email='".$new_email."' WHERE user_id=".$row[0].";";
    mysql_db_query($mysql_database,$sql,$conn);
    echo '+';
}else{
    echo '-';
}

mysql_free_result($result);
mysql_close($conn);  

?>

