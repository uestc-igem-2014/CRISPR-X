<?php

require_once("../config.php");

$strsql="SELECT t.user_id from user_token as t join user_info as i where t.user_id=i.user_id and uld_id<=2 and token_str='".$_POST['token']."'; ";
$result=mysql_db_query($mysql_database, $strsql, $conn);

if($row=mysql_fetch_row($result)){
    $user_id=$row[0];
    $result=mysql_db_query($mysql_database, "INSERT INTO user_upload(user_id,upload_specie) VALUES(".$user_id.", '".$_POST['note']."');", $conn);
    if(!$result){
        print mysql_error();
        return ;
    }
	$result=mysql_db_query($mysql_database, "SELECT LAST_INSERT_ID();", $conn);
	if($row=mysql_fetch_row($result)){
        $filename=$row[0];
        $fp=fopen($_SERVER['DOCUMENT_ROOT']."/iGEM2014/uploaddata/".$filename,"w");
        fwrite($fp,$_POST['file']."\n\n");
        fclose($fp);
        echo "A";
    }else{
        echo "N";
    }
}else{
    echo "N";
}

mysql_free_result($result);
mysql_close($conn);

?>

