<?php
ignore_user_abort(TRUE);
set_time_limit(60*60*2);
ob_end_clean();

if (isset($_GET['specie']) && ( isset($_GET['location']) || isset($_GET['gene']) || isset($_GET['blast']) ) && isset($_GET['pam']) && isset($_GET['rfc'])){
    $conn=mysql_connect("YOUR_SERVER","YOUR_MYSQL_USER","YOUR_MYSQL_PASSWD");
    $mysql_database="YOUR_DB_NAME";
    
    $user_id=2;
    if(isset($_GET['token'])){
	    $result=mysql_db_query($mysql_database, "SELECT user_id FROM user_token WHERE token_str='".$_GET['token']."';", $conn);
	    if($row=mysql_fetch_row($result)){
            $user_id=$row[0];
        }
    }

    $result=mysql_db_query($mysql_database, "INSERT INTO user_request(user_id) VALUES(".$user_id.");", $conn);
    if(!$result){
        print mysql_error();
        return ;
    }
	$result=mysql_db_query($mysql_database, "SELECT LAST_INSERT_ID();", $conn);
	if($row=mysql_fetch_row($result)){
        print $row[0];
    }
    print 'Q';
    print str_pad("", 15);
    @ob_flush();
    flush();  
	
	if(isset($_GET['location'])){
		$Prog_arg = '{"specie":"'.$_GET['specie'].'","location":"'.$_GET['location'].'","pam":"'.$_GET['pam'].'","rfc":"'.$_GET['rfc'].'"';
	}else if(isset($_GET['blast'])){
        $file=fopen("blast/seq.txt","w");
        fwrite($file,$_GET['blast']);
        fclose($file);
	    $fp=fopen("Database/".$_GET["specie"]."/list.txt",'r');
        while (!feof($fp)){
            $bruce=fgets($fp);
            if(strlen($bruce)<3) break;
            $path=split("\t",$bruce);

            $Prog="blast/bin/formatdb -i 'Database/".$_GET['specie']."/$path[1]' -p F";
            exec($Prog);
            $Prog="blast/bin/blastall -p blastn -d 'Database/".$_GET['specie']."/$path[1]' -i blast/seq.txt -o blast/out".$path[1].".txt";
            exec($Prog);
            unset($output1);
            exec("blast/a.out 'blast/out".$path[1].".txt'", $output1, $returnvalue1);

            if($output1[0]>0 && $output1[0]==strlen($_GET['blast'])){
        		$Prog_arg = '{"specie":"'.$_GET['specie'].'","location":"'.$path[0].":".$output1[1].'","pam":"'.$_GET['pam'].'","rfc":"'.$_GET['rfc'].'"';
                break;
            }
        }
        exec("rm blast/out*.txt");
	}else if(isset($_GET['gene'])){
		$Prog_arg = '{"specie":"'.$_GET['specie'].'","gene":"'.$_GET['gene'].'","pam":"'.$_GET['pam'].'","rfc":"'.$_GET['rfc'].'"';
	}else{
	    echo "Error: no arg";
	    return ;
	}
	if(isset($_GET['r1'])){
	    $Prog_arg=$Prog_arg.',"r1":'.$_GET['r1'];
	}
	if(isset($_GET['region'])){
	    $Prog_arg=$Prog_arg.',"region":"'.$_GET['r1'].'"';
	}
	$Prog_arg=$Prog_arg."}";
	
	$Prog_name = "./main";
	$Prog = $Prog_name." '".$Prog_arg."'";
	$last_line = exec($Prog_name, $output, $returnvalue);

	if($returnvalue!=0){
	    $result=mysql_db_query($mysql_database, "UPDATE user_request SET request_txt='{\"status\":1,\"message\":\"run time error\"}' WHERE request_ID=".$row[0].";", $conn);
	    return ;
	}

	$result=mysql_db_query($mysql_database, "UPDATE user_request SET request_txt='".$output[0]."' WHERE request_ID=".$row[0].";", $conn);
	if(!$result){
		print mysql_error();
		return ;
	}
    mysql_close($conn);

}
else{
	echo "Error: no arg";
}
?>

