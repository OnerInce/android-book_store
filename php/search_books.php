<?php

require_once 'connection.php';

if(getenv('REQUEST_METHOD')=='POST'){

    $key = $_POST["query"];
    $query = "SELECT b.id, title, ISBN, first_name, last_name, image, price, discount_value
            FROM books b JOIN authors a 
            ON b.author_id = a.id
            LEFT JOIN discounts d on b.id = d.book_id
            WHERE LOWER(title) LIKE LOWER('$key%') OR ISBN LIKE LOWER('$key%') OR LOWER(first_name) LIKE LOWER('$key%') OR LOWER(last_name) LIKE LOWER('$key%')
                ";
    $result = mysqli_query($connection, $query);

    if ($result ->num_rows > 0){

        $response = array();

        while ( $row = mysqli_fetch_assoc($result) ){

            array_push($response,
                array(
                    "id" => $row["id"],
                    "title" => $row["title"],
                    "image" => $row["image_path"],
                    "price" => $row["price"],
                    "first_name" => $row["first_name"],
                    "last_name" => $row["last_name"],
                    "discount_value" => $row["discount_value"])
            );
        }

        echo json_encode($response);

    }
    else{

        print(json_encode(array("No item found that matches the query: ")));

    }

}
mysqli_close($connection);

?>

