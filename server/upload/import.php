<?php

$conn=mysql_connect("localhost","root","root");
$mysql_database="CasDB";

$strsql="SELECT t.user_id from user_token as t join user_info as i where t.user_id=i.user_id and uld_id<=2 and token_str='".$_POST['token']."'; ";
$result=mysql_db_query($mysql_database, $strsql, $conn);

$ok=0;
if($row=mysql_fetch_row($result)){
    $ok=1;
}
mysql_free_result($result);
mysql_close($conn);

if($ok==0){
    echo 'N';
    return ;
}

$Prog_name = "../import";
$Prog_arg = $_POST['command'];
$Prog = $Prog_name." '".$Prog_arg."'";
$last_line = exec($Prog, $output, $returnvalue);

if($returnvalue==0) echo $output;
else echo 'N';

?>

