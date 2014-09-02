<?php

$conn=mysql_connect("localhost","root","root");
$mysql_database="CasDB";

$strsql="SELECT t.user_id from user_token as t join user_info as i where t.user_id=i.user_id and uld_id<=2 and token_str='".$_POST['token']."'; ";
$result=mysql_db_query($mysql_database, $strsql, $conn);

if($row=mysql_fetch_row($result)){
    $user_id=$row[0];
    $result=mysql_db_query($mysql_database, "SELECT upload_ID, upload_specie FROM user_upload where user_id=".$user_id.";", $conn);
    echo "[";
    $count=0;
    while($row=mysql_fetch_row($result)){
        if($count!=0) echo ",";
        $count=$count+1;
        echo '{"id":"'.$row[0].'", "note":"'.$row[1].'"}';
    }
    echo "]";
}else{
    echo "[]";
}

mysql_free_result($result);
mysql_close($conn);

?>

