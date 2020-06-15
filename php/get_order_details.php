<?php

if (getenv('REQUEST_METHOD') == 'POST') {
    require_once 'connection.php';

    $order_id = $_POST['order_id'];

    $sql = "SELECT 
    title,
    quantity,
    single_price,
    discount_value
    FROM
    order_details od
    JOIN books b on od.book_id = b.id
    JOIN orders o on o.id = od.order_id
    JOIN customers c on c.id = o.customer_id
    LEFT JOIN discounts d on b.id = d.book_id
    WHERE o.id = '$order_id'
    GROUP BY od.id;";

    $response = mysqli_query($connection, $sql);

    $result = array();
    $item = array();
    if ($response->num_rows > 0) {
        while ($row = $response->fetch_assoc()) {
            $item["title"] = $row["title"];
            $item["quantity"] = $row["quantity"];
            $item["single_price"] = $row["single_price"];
            $item["discount"] = $row["discount_value"];

            array_push($result, $item);
            $item = array();
        }
    }
    echo(json_encode($result));
    mysqli_close($connection);

}