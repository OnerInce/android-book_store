<?php
if(getenv('REQUEST_METHOD')=='POST'){
    require_once 'connection.php';

    $order_id = $_POST["order_id"];
    $decision = $_POST["decision"];

    if($decision == '1'){
        $sql = "UPDATE orders SET is_confirmed = 1 WHERE id = '$order_id'";
    }
    else{
        $sql = "UPDATE orders SET is_confirmed = -1 WHERE id = '$order_id'";
    }

    $stmt = mysqli_prepare($connection, $sql);
    mysqli_stmt_execute($stmt);

    $result = array();
    $result["success"] = mysqli_stmt_affected_rows($stmt);

    echo json_encode($result);

}