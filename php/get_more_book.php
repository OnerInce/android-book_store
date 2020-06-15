<?php

if(getenv('REQUEST_METHOD')=='POST') {
    require_once 'connection.php';
    $type = $_POST["type"];

    if($type == "En"){
        $sql = "SELECT 
        b.id, title, price, image, first_name, last_name, discount_value
    FROM
        books b
    JOIN authors a ON a.id = b.author_id
    LEFT JOIN discounts d ON b.id = d.book_id
    WHERE discount_value IS NOT NULL
    ORDER BY d.discount_value DESC   
    LIMIT 20";
    }
    elseif ($type == "Çok"){
        $sql = "SELECT 
        b.id, title, price, image, first_name, last_name
    FROM
        books b
    JOIN authors a ON a.id = b.author_id
    LEFT JOIN discounts d ON b.id = d.book_id
    ORDER BY b.sales DESC
    LIMIT 20";
    }
    elseif ($type == "Yeni"){
        $sql = "SELECT 
        b.id, title, price, image, first_name, last_name
    FROM
        books b
    JOIN authors a ON a.id = b.author_id
    LEFT JOIN discounts d ON b.id = d.book_id
    ORDER BY b.id DESC
    LIMIT 20";
    }

    $result = mysqli_query($connection, $sql);
    if ($result ->num_rows > 0){
        $response = array();
        while ( $row = mysqli_fetch_assoc($result) ){
            if($type == "En"){
            array_push($response,
                array(
                    "id" => $row["id"],
                    "title" => $row["title"],
                    "image" => $row["image"],
                    "price" => $row["price"],
                    "first_name" => $row["first_name"],
                    "last_name" => $row["last_name"],
                    "discount" => $row["discount_value"])
            );}
            else{
            array_push($response,
                array(
                    "id" => $row["id"],
                    "title" => $row["title"],
                    "image" => $row["image"],
                    "price" => $row["price"],
                    "first_name" => $row["first_name"],
                    "last_name" => $row["last_name"],
                    "discount" => "")
            );
            }
        }
    }

    echo json_encode($response);
    mysqli_close($connection);

}