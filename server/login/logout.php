<?php
    require_once("../config.php");
    
    $strsql="DELETE FROM user_token WHERE token_str='".$_COOKIE["token"]."';";
    mysql_db_query($mysql_database, $strsql, $conn);
    
    mysql_close($conn);
    
    //setcookie("user", "", time()-1);
    //setcookie("token", "", time()-1);
    
?>

