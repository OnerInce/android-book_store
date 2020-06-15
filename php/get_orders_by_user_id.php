<?php

if (getenv('REQUEST_METHOD') == 'POST') {
    require_once 'connection.php';

    $customer_id = $_POST['customer_id'];

    $sql = "SELECT 
    o.id,
    order_time,
    b.image,
    b.title,
    od.single_price,
    COUNT(od.order_id) AS book_count
    FROM
    orders o
        JOIN
    order_details od ON o.id = od.order_id
        JOIN
    customers c on o.customer_id = c.id
        JOIN
    books b on od.book_id = b.id
    WHERE c.id = '$customer_id'
    GROUP BY od.order_id";

    $response = mysqli_query($connection, $sql);
    $result = array();
    $item = array();
    if ($response->num_rows > 0) {
        while ($row = $response->fetch_assoc()) {
            $item["order_id"] = $row["id"];
            $item["order_time"] = $row["order_time"];
            $item["image"] = $row["image"];
            $item["book_name"] = $row["title"];
            $item["single_price"] = $row["single_price"];
            $item["book_count"] = $row["book_count"];

            array_push($result, $item);
            $item = array();
        }
    }
    echo(json_encode($result));
    mysqli_close($connection);

}