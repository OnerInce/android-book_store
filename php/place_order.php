<?php
if(getenv('REQUEST_METHOD')=='POST'){
    require_once 'connection.php';

    $data = json_decode(file_get_contents('php://input'), true);

    $customer_id = $data["customerID"];
    $shipper = $data["shipper"];
    $total_price = $data["totalOrderPrice"];
    $address = $data["address"];

    $sql_order = "INSERT INTO orders (customer_id, shipper_id, address, total_order_price) VALUES ('$customer_id', 
                    (SELECT id from shippers WHERE company_name = '$shipper'), '$address', '$total_price')";

    $stmt = mysqli_prepare($connection, $sql_order);
    mysqli_stmt_execute($stmt);
    $last_id = $stmt->insert_id;

    foreach ($data["orders"] as $order) {
        $book_id = $order["book_id"];
        $quantity = $order["quantity"];
        $single_price = $order["single_price"];

        $sql_order_item = "INSERT INTO order_details (book_id, order_id, single_price, quantity) VALUES ('$book_id', 
                    '$last_id', '$single_price', '$quantity')";
        $stmt2 = mysqli_prepare($connection, $sql_order_item);
        mysqli_stmt_execute($stmt2);

        //satılan kitabın satış sayısı
        $sql_sell = "UPDATE books SET sales = sales + '$quantity' WHERE id = '$book_id'";
        mysqli_query($connection, $sql_sell);
    }

    //sipariş tamamlandıktan sonra bu sepetin silinmesi
    $sql_delete_cart = $sql = "DELETE FROM carts WHERE customer_id = '$customer_id'";
    mysqli_query($connection, $sql_delete_cart);

    $result = array();

    $result["success"] = mysqli_stmt_affected_rows($stmt2);
    echo json_encode($result);
    mysqli_close($connection);
}