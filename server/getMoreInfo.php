<?php 
 if (isset($_GET['sequence'])) {
 	$resStr = system("python getMoreInfo.py ".$_GET['sequence'], $retval);
 	// echo $resStr;
 	if ($resStr == "" || $retval != 0) {
 		echo '{"err": "no rights to execute the script."}';
 	}
 } else {
 	echo '{"err": "no valid arg"}';
 }
?>