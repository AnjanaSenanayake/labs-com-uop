<html>
 <head>
  <title>Employee Infromation System</title>
 </head>
 <body>
  
  <h1>Employee Infromation System</h1>
  <?php
  

   
   $fname=$_POST['fname'];
   $lname=$_POST['lname'];
   $address1=$_POST['address'];
   $comment=$_POST['comment'];   


   
	
	
	echo "<p>Thank you, ".$fname." for your register for our department.</p>";
	
	 echo " your letters will be sent to :</p><i>".$fname." ".$lname.",</i><br /><i>".$address1."</i><br/>";
	 
	 echo "<p>Thank you for submitting your comments. We appreciate it. You said:</p><i>".$comment."</i>";
	
	

	
	
  ?>
 </body>
</html>