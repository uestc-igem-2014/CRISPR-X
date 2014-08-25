<html>
  <head>
    <title>Help</title>
    <meta charset="utf-8" />
    <link rel="shortcut icon" href="hehe.png" >
    <style>
    div{
        border:0.1em solid;
        border-radius:1em;
        -moz-border-radius:1em; /* Old Firefox */
        overflow-y:hidden;
        transition: width 2s;
        -moz-transition: width 2s;	/* Firefox 4 */
        -webkit-transition: width 2s;	/* Safari or Chrome */
        -o-transition: width 2s;	/* Opera */
        padding:1em;
        margin:1em;
        height:1em;
    }
    div:hover{
        height:6em;
    }
    .folder{
        width:60em;
        border-color:blue;
    }
    .file{
        width:50em;
        border-color:red;
    }
    .helper{
        width:50em;
        border-color:green;
    }
    .symbol{
        width:5em;
    }
    .symbol:hover{
        height:1em;
    }
    </style>
  </head>
  <body>
<?php
$fp=fopen("rescng.txt","r");
while (!feof($fp)){
    $bruce=fgets($fp);
    echo $bruce;
}
?>
  <h2>Help Page</h2>
<?php
$fp=fopen("file.txt","r");
while (!feof($fp)){
    $bruce=fgets($fp);
    $path=split("\t",$bruce);
    if(count($path)!=5) break;
    if($path[0]<0) continue;
    echo "<div class=".$path[1]." style='margin-left:".($path[0]*4)."em;'>";
    echo $path[2];
    echo "<br><br>";
    if($path[1]=="file") echo "<a href='http://immunet.cn/iGEM2014/".$path[3]."'>http://immunet.cn/iGEM2014/".$path[3]."</a><br>";
    if($path[1]=="helper") echo "<a href='http://immunet.cn/iGEM2014/".$path[3]."'>http://immunet.cn/iGEM2014/".$path[3]."</a><br>";
    echo $path[4];
    echo "</div>";
}
?>
    <h3>symbols</h3>
<table border="0">
  <tr>
    <td><div class=symbol style="border-color:blue;">Folder</div></td>
    <td><div class=symbol style="border-color:red;">File</div></td>
    <td><div class=symbol style="border-color:green;">Helper</div></td>
  </tr>
</table>

<p>You can use this page either: <a href="http://immunet.cn/iGEM2014/index_nonAnimation.php">http://immunet.cn/iGEM2014/index_nonAnimation.php</a>

  </body>
</html>

