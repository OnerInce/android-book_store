<?php
if(getenv('REQUEST_METHOD')=='POST') {
    require_once 'connection.php';
    $book_id = $_POST["book_id"];
    $customer_id = $_POST["customer_id"];

    $sql1 = "INSERT IGNORE INTO carts (customer_id) VALUES ('$customer_id')";

    $sql2 = "INSERT INTO cart_items (book_id, cart_id) VALUES ('$book_id',
            (SELECT id from carts WHERE customer_id = '$customer_id'))";

    $sql3 = "UPDATE cart_items SET quantity = quantity + 1 WHERE book_id = '$book_id'";
    
    $stmt = mysqli_prepare($connection,$sql1);
    mysqli_stmt_execute($stmt);
    $stmt2 = mysqli_prepare($connection,$sql2);
    mysqli_stmt_execute($stmt2);
    $stmt3 = mysqli_prepare($connection,$sql3);
    mysqli_stmt_execute($stmt3);
    
    $result = array();
    $result["success"] = mysqli_stmt_affected_rows($stmt);
    echo json_encode($result);
}