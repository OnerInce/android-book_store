<?php

if(getenv('REQUEST_METHOD')=='POST') {
    require_once 'connection.php';
    $category = $_POST["category_name"];
    $bookType = $_POST["book_type_name"];

    $sql = "SELECT 
        b.id, b.title, b.price, b.image, a.first_name, a.last_name, d.discount_value
    FROM
        books AS b
    LEFT JOIN categories AS c ON b.category_id = c.id
    LEFT JOIN book_types AS bt ON b.book_type_id = bt.id
    LEFT JOIN authors a on b.author_id = a.id 
    LEFT JOIN discounts d on b.id = d.book_id
    WHERE c.category_name = '$category' AND bt.book_type_name =  '$bookType'";

    $result = mysqli_query($connection, $sql);
    if ($result ->num_rows > 0){
        $response = array();
        while ( $row = mysqli_fetch_assoc($result) ){
            if($row["discount_value"] != null){
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