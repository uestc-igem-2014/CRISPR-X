<html>
<body>

<?php

if (isset($_COOKIE["user"])){
    echo "Welcome " . $_COOKIE["user"] . "!<br />";
    echo "<a href='logout.php?urlback=index.php'>logout</a> ";
    echo "<a href='check_token.php?urlback=index.php'>check token</a>";
}
else{
?>
Welcome guest!<br />
<form action="./" method="post">
  <p>username: <input type="text" name="name" /></p>
  <p>password: <input type="password" name="pswd" /></p>
  <input type="submit" value="Submit" />
</form>
<?php  
}
?>
</body>
</html>

