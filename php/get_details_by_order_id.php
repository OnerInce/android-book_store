<?php

if (getenv('REQUEST_METHOD') == 'POST') {
    require_once 'connection.php';

    $order_id = $_POST['order_id'];

    $sql = "SELECT 
    b.title, od.single_price, b.image,
    od.quantity,
    o.address,
    s.company_name,
    o.is_confirmed
    FROM
    orders o
        JOIN
    order_details od ON o.id = od.order_id
        JOIN
    books b on od.book_id = b.id
        JOIN
    shippers s on o.shipper_id = s.id
    WHERE od.order_id = '$order_id'";

    $response = mysqli_query($connection, $sql);
    $result = array();
    $result["books"] = array();
    $item = array();
    if ($response->num_rows > 0) {
        $row = $response->fetch_assoc();
        $result["address"] = $row["address"];
        $result["shipper"] = $row["company_name"];
        $result["is_confirmed"] = $row["is_confirmed"];
        while (true) {
            $item["book_name"] = $row["title"];
            $item["price"] = $row["single_price"];
            $item["quantity"] = $row["quantity"];
            $item["image"] = $row["image"];
            array_push($result["books"], $item);
            $item = array();
            if($row = $response->fetch_assoc()) continue;
            else break;
        }

    }
    echo(json_encode($result));
    mysqli_close($connection);

}