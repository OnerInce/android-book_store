<?php
if(getenv('REQUEST_METHOD')=='POST') {
    require_once 'connection.php';

    $customer_id = $_POST["customer_id"];

    $sql = "SELECT ci.book_id, title, price, quantity, image, discount_value FROM
            cart_items ci JOIN carts c on ci.cart_id = c.id
            JOIN books b on ci.book_id = b.id
            LEFT JOIN discounts d on b.id = d.book_id
            WHERE c.customer_id = '$customer_id'
    ";
    $response = mysqli_query($connection, $sql);

    $result = array();
    $cart_item = array();
    if ($response->num_rows > 0) {
        while ($row = $response->fetch_assoc()) {
            $cart_item["title"] = $row["title"];
            $cart_item["price"] = $row["price"];
            $cart_item["quantity"] = $row["quantity"];
            $cart_item["image"] = $row["image"];
            $cart_item["discount"] = $row["discount_value"];
            $cart_item["p_id"] = $row["book_id"];
            
            array_push($result, $cart_item);
            $cart_item = array();
        }
    }
    echo(json_encode($result));
    mysqli_close($connection);

}