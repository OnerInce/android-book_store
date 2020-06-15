<?php

require_once 'connection.php';

$sql0 = "DELETE FROM discounts WHERE discount_value = 0";
$result = mysqli_query($connection, $sql0);

$sql = "
SELECT 
    discounted.*
FROM
    (SELECT 
        b.id, title, price, image, first_name, last_name, discount_value
    FROM
        books b
    JOIN authors a ON a.id = b.author_id
    LEFT JOIN discounts d ON b.id = d.book_id
    WHERE discount_value IS NOT NULL
    ORDER BY d.discount_value DESC
    LIMIT 5) AS discounted 
UNION ALL SELECT 
    most_sales.*
FROM
    (SELECT 
        b.id, title, price, image, first_name, last_name, discount_value
    FROM
        books b
    JOIN authors a ON a.id = b.author_id
    LEFT JOIN discounts d ON b.id = d.book_id
    ORDER BY b.sales DESC
    LIMIT 5) AS most_sales 
UNION ALL SELECT 
    newest.*
FROM
    (SELECT 
        b.id, title, price, image, first_name, last_name, discount_value
    FROM
        books b
    JOIN authors a ON a.id = b.author_id
    LEFT JOIN discounts d ON b.id = d.book_id
    ORDER BY b.id DESC
    LIMIT 5) AS newest;
        ";

$result = mysqli_query($connection, $sql);
if ($result ->num_rows > 0){


    $response = array();

    while ( $row = mysqli_fetch_assoc($result) ){

        array_push($response,
            array(
                "id" => $row["id"],
                "title" => $row["title"],
                "image" => $row["image"],
                "price" => $row["price"],
                "discount" => $row["discount_value"],
                "first_name" => $row["first_name"],
                "last_name" => $row["last_name"])
        );
    }

    echo json_encode($response);

}
mysqli_close($connection);