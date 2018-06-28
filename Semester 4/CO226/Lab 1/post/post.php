<html>
<head> <title>Buy T-shirts Online </title></head>

<body>
<h1> Buy T-shirts Online </h1>

<?php
	
	if (isset($_POST['size'])){		
		$size=$_POST['size'];
	}
	else{
		$size='';
	}
	
	$color=$_POST['color'];
	
	if (isset($_POST['extra1'])){		
		$extra1=$_POST['extra1'];
	}
	else{
		$extra1='';
	}
	
	if (isset($_POST['extra2'])){		
		$extra2=$_POST['extra2'];
	}
	else{
		$extra2='';
	}
	
	$name1=$_POST['firstname'];
	$name2=$_POST['lastname'];
	$address1=$_POST['address1'];
	$address2=$_POST['address2'];
	$address3=$_POST['address3'];
	$comments=$_POST['comments'];

	echo "<h2> Thank you ".$name1." for using the online Buying system</h2>";
	echo "<p>Selected t-shirt size is ".$size."</p>";
	echo "<p>Selected t-shirt color is ".$color."</p>";
	echo "<p>Selected extra items are ".$extra1. " ".$extra2."</p>";
	echo "<p>The items will be delivered to ".$name1." ".$name2.", ".$address1.", ".$address2.", ".$address3."</p>";
	echo "<p>Thank you for your comments!<br> Your comments were ".$comments."</p>";
	
?>
</body>

</html>