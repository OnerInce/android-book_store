<?php

if (getenv('REQUEST_METHOD') == 'GET') {
    require_once 'connection.php';

    $sql = "SELECT 
    o.id,
    order_time,
    complete_name,
    total_order_price,
    o.address,
    company_name,
    COUNT(od.id) AS book_count
    FROM
    orders o
        JOIN
    order_details od ON o.id = od.order_id
        JOIN
    shippers s ON s.id = shipper_id
        JOIN
    customers c on o.customer_id = c.id
    WHERE is_confirmed = 0
    GROUP BY od.order_id";

    $response = mysqli_query($connection, $sql);

    $result = array();
    $item = array();
    if ($response->num_rows > 0) {
        while ($row = $response->fetch_assoc()) {
            $item["order_id"] = $row["id"];
            $item["order_time"] = $row["order_time"];
            $item["total_price"] = $row["total_order_price"];
            $item["address"] = $row["address"];
            $item["shipper_name"] = $row["company_name"];
            $item["book_count"] = $row["book_count"];
            $item["name"] = $row["complete_name"];

            array_push($result, $item);
            $item = array();
        }
    }
    echo(json_encode($result));
    mysqli_close($connection);

}