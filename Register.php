<?php
	$servername = "localhost";
	$username = "watchew";
	$password = "enghacks";
	$dbname = "nutrition";

	$conn = mysqli_connect($servername, $username, $password, $dbname);
	if($conn->connect_error) {
		die("Connection failed: " . $conn->connect_error);
	}

	$name = $_POST["name"];
	$password = $_POST["password"];
	$email = $_POST["email"];

	if($conn->query("CREATE TABLE users (name varchar(16), email varchar(16),
		password varchar(16))") === TRUE){
		echo "Succcess<br>";
	}
	else {
		echo "Sum Ting Wong<br>";
	}

	if($conn->query("INSERT INTO users (name, email, password)
		VALUES ($name, $email, $password)") === TRUE){
		echo "Success<br>";
	} else {
		echo "Sum Ting Wong<br>";
	}

	//mysqli_stmt_bind_param($statement, "sss", $name, $email, $password);
	mysqli_stmt_execute($statement);
	mysqli_stmt_close($statement);

	mysqli_close($conn);

?>