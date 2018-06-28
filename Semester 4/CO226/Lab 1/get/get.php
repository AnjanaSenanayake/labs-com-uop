<!DOCTYPE html>
<html>
<head> <title>Buy T-shirts Online </title></head>

<body>
<h1> Buy T-shirts Online </h1>

<?php
	
	echo "<h2> Thank you ".$_GET['Fname']." for using the online Buying system</h2>";
	
	if (isset($_GET['size']))
	{		
		echo "<p>Selected t-shirt size is ".$_GET['size']."</p>";
	}
	else
	{
		echo "<p>Selected t-shirt size is ""</p>";
	}

	echo "<p>Selected t-shirt color is ".$_GET['colour']."</p>";
	
	if (isset($_GET['extra1']))
	{		
		echo "<p>Selected extra items are ".$_GET['extra1']."</p>";
	}
	else
	{
		echo "<p>Selected extra items are ""</p>";
	}
	
	if (isset($_GET['extra2']))
	{
		echo "<p>Selected extra items are ".$_GET['extra2']."</p>";
	}
	else
	{
		echo "<p>Selected extra items are ""</p>";
	}

	echo "<p>The items will be delivered to </p>"

	$Lname=$_GET['Lname'];
	$add1=$_GET['add1'];
	$add2=$_GET['add2'];
	$add3=$_GET['add3'];
	$comments=$_GET['comments'];
	

	echo .$name1. ,<br /> 
	echo .$name2.",<br />";
	echo .$address1.",<br />";
	echo .$address2.",<br />";
	echo .$address3."</p>";
	echo "<p>Thank you for your comments!<br> Your comments were ".$comments."</p>";
	
?>
</body>

</html>