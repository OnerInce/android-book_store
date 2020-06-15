<?php

require_once 'connection.php';

if(getenv('REQUEST_METHOD')=='POST'){

    $key = $_POST["query"];

    if ($key == "first"){
        $query = "SELECT b.id, title, a.first_name, a.last_name, stock_quantity
                FROM books b JOIN authors a on b.author_id = a.id
                WHERE stock_quantity < 10";
    }
    else {
        $query = "SELECT b.id, title, first_name, last_name, stock_quantity
            FROM books b JOIN authors a 
            ON b.author_id = a.id
            WHERE LOWER(title) LIKE LOWER('%$key%') OR ISBN LIKE LOWER('%$key%') OR 
                  LOWER(first_name) LIKE LOWER('%$key%') OR LOWER(last_name) LIKE LOWER('%$key%')";
    }

    $result = mysqli_query($connection, $query);

    if ($result ->num_rows > 0){
        $response = array();
        while ( $row = mysqli_fetch_assoc($result) ){
            array_push($response,
                array(
                    "id" => $row["id"],
                    "title" => $row["title"],
                    "first_name" => $row["first_name"],
                    "last_name" => $row["last_name"],
                    "stock" => $row["stock_quantity"])
            );
        }
        echo json_encode($response);
    }
    else{
        print(json_encode(array("No item found that matches the query: ")));
    }
}
mysqli_close($connection);



