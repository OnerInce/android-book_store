<?php
if(getenv('REQUEST_METHOD')=='POST') {
    require_once 'connection.php';

    $user_id = $_POST["user_id"];

    $sql = "SELECT 
            o.id,
            order_time,
            total_order_price,
            address,
            company_name,
            is_confirmed,
            COUNT(od.id) AS book_count
        FROM
            orders o
                JOIN
            order_details od ON o.id = od.order_id
                JOIN
            shippers s ON s.id = shipper_id
        WHERE o.customer_id = '$user_id'
        GROUP BY od.order_id;";

    $response = mysqli_query($connection, $sql);

    $result = array();
    $cart_item = array();
    if ($response->num_rows > 0) {
        while ($row = $response->fetch_assoc()) {
            $cart_item["order_id"] = $row["id"];
            $cart_item["order_time"] = $row["order_time"];
            $cart_item["total_price"] = $row["total_order_price"];
            $cart_item["address"] = $row["address"];
            $cart_item["shipper_name"] = $row["company_name"];
            $cart_item["book_count"] = $row["book_count"];
            $cart_item["status"] = $row["is_confirmed"];

            array_push($result, $cart_item);
            $cart_item = array();
        }
    }
    echo(json_encode($result));
    mysqli_close($connection);

}