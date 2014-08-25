<?php

class node{
    public $request_id;
    public $status;
}
$ns=array();

$conn=mysql_connect("localhost","igem","uestc2014!");
$mysql_database="CasDB";

$user_id=2;
$max=10;
if(isset($_POST['token'])){
    $result=mysql_db_query($mysql_database, "SELECT user_id FROM user_token WHERE token_str='".$_POST['token']."';", $conn);
    if($row=mysql_fetch_row($result)){
        $user_id=$row[0];
    }
}

if(true || $user_id!=2){
    $result=mysql_db_query($mysql_database, "SELECT request_id,left(request_txt,3) FROM user_request WHERE user_ID=".$user_id." ORDER BY request_id desc;", $conn);
    $i=0;
    while($row=mysql_fetch_row($result)){
        $ns[$i]=new node;
        $ns[$i]->request_id=$row[0];
        if($row[1]===null) $ns[$i]->status="2";
        else $ns[$i]->status="0";
        $i++;
        if($i>=$max) break;
    }

    echo json_encode($ns);
}else{
    echo "[]";
}
mysql_free_result($result);
mysql_close($conn);
?>

