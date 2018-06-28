<html>
<head> <title>Buy T-shirts Online </title></head>

<body>
<h1> Buy T-shirts Online </h1>

<?php

	if(isset($_GET['search']))
	{
		echo "Sorry database does not exists";
	}

	if (isset($_GET['size']))
	{		
		$size=$_GET['size'];
	}
	else{
		$size='';
	}
	
	$color=$_GET['color'];
	
	if (isset($_GET['extra1']))
	{		
		$extra1=$_GET['extra1'];
	}
	else{
		$extra1='';
	}
	
	if (isset($_GET['extra2']))
	{		
		$extra2=$_GET['extra2'];
	}
	else{
		$extra2='';
	}
	
	$name1=$_GET['Fname'];
	$name2=$_GET['Lname'];
	$address1=$_GET['add1'];
	$address2=$_GET['add2'];
	$address3=$_GET['add3'];
	$comments=$_GET['Comments'];
	
	echo "<h2> Thank you ".$Fname." for using the online Buying system</h2>";
	echo "<p>Selected t-shirt size is ".$size."</p>";
	echo "<p>Selected t-shirt color is ".$color."</p>";
	echo "<p>Selected extra items are ".$extra1. " ".$extra2."</p>";
	echo "<p>The items will be delivered to ".$Fname." ".$Lname.", ".$add1.", ".$add2.", ".$add3."</p>";
	echo "<p>Thank you for your comments!<br> Your comments were ".$Comments."</p>";
	
?>
</body>

</html>