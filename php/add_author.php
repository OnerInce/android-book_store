<?php
if(getenv('REQUEST_METHOD')=='POST'){
    require_once 'connection.php';

    $first_name = $_POST["first_name"];
    $last_name = $_POST["last_name"];

    $sql = "INSERT INTO authors (first_name, last_name) VALUES ('$first_name','$last_name')";
    $result = array();

    $stmt = mysqli_prepare($connection, $sql);
    mysqli_stmt_execute($stmt);

    $result["success"] = mysqli_stmt_affected_rows($stmt);
    echo json_encode($result);

}