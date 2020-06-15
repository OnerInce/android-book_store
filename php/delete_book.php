<?php
if(getenv('REQUEST_METHOD')=='POST') {
    require_once 'connection.php';

    $book_id = $_POST["book_id"];

    $sql = "DELETE FROM books WHERE id = '$book_id'";
    $stmt = mysqli_prepare($connection, $sql);
    mysqli_stmt_execute($stmt);

    $result["success"] = mysqli_stmt_affected_rows($stmt);
    echo json_encode($result);
}