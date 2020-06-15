<?php
if(getenv('REQUEST_METHOD')=='POST') {
    require_once 'connection.php';
    $book_id = $_POST["book_id"];
    $customer_id = $_POST["customer_id"];
    $rate = $_POST["rate"];
    $comment = $_POST["comment"];

    $sql1 = "INSERT IGNORE INTO reviews (book_id, customer_id, review_text, rating) VALUES ('$book_id', '$customer_id'
              ,'$comment', '$rate')";

    $stmt = mysqli_prepare($connection,$sql1);
    mysqli_stmt_execute($stmt);

    $result = array();

    if(mysqli_stmt_affected_rows($stmt) > 0){
        $result["success"] = 1;
    }

    else{
        $result["success"] = 0;
    }

    echo json_encode($result);
}