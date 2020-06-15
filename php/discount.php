<?php
if(getenv('REQUEST_METHOD')=='POST') {
    require_once 'connection.php';

    $id = $_POST["id"];
    $discount = $_POST["amount"];
    $result = array();

    $sql1 = "UPDATE IGNORE discounts SET discount_value = '$discount' WHERE book_id = '$id'";
    $stmt = mysqli_prepare($connection, $sql1);
    mysqli_stmt_execute($stmt);

    $sql2 = "INSERT INTO discounts (discount_value, book_id) VALUES ($discount, $id)";
    $stmt = mysqli_prepare($connection, $sql2);
    mysqli_stmt_execute($stmt);

    $result["success"] = mysqli_stmt_affected_rows($stmt);

    echo json_encode($result);
    mysqli_close($connection);

}