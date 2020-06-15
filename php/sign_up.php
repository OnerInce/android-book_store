<?php
if(getenv('REQUEST_METHOD')=='POST'){
    require_once 'connection.php';

    $complete_name = $_POST["name"];
    $phone = $_POST["phone"];
    $email = $_POST["mail"];
    $password = $_POST["password"];

    $sql = "INSERT INTO customers (complete_name, phone, e_mail, user_password) 
                VALUES ('$complete_name', '$phone' ,'$email', '$password')";
    $stmt = mysqli_prepare($connection, $sql);
    mysqli_stmt_execute($stmt);

    $getID_sql = "SELECT id, complete_name, is_admin FROM customers WHERE e_mail = '$email'";
    $response = mysqli_query($connection, $getID_sql);
    $row = mysqli_fetch_assoc($response);

    $result = array();
    $result["success"] = mysqli_stmt_affected_rows($stmt);
    $result["id"] = $row['id'];
    $result["complete_name"] = $row['complete_name'];
    $result["is_admin"] = $row['is_admin'];
    echo json_encode($result);

}