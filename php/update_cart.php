<?php
if(getenv('REQUEST_METHOD')=='POST'){
    require_once 'connection.php';
    $operation = $_POST["operation"];
    $customer_id = $_POST["customer_id"];
    $book_id = $_POST["p_id"];

    if($operation == "change_quantity"){
        $quantity = $_POST["quantity"];
        $sql = "UPDATE cart_items ci 
            JOIN carts c on ci.cart_id = c.id
            JOIN books b on ci.book_id = b.id
            SET quantity = '$quantity' 
            WHERE c.customer_id = '$customer_id' AND b.id = '$book_id'";


    }elseif($operation == "remove_item"){

        $sql = "DELETE FROM cart_items
                WHERE cart_id IN (SELECT id from carts WHERE customer_id = '$customer_id') 
                AND book_id = '$book_id';";
    }
    $stmt = mysqli_prepare($connection, $sql);
    mysqli_stmt_execute($stmt);

    $result = array();
    $result["success"] = mysqli_stmt_affected_rows($stmt);
    echo json_encode($result);
    mysqli_close($connection);
}