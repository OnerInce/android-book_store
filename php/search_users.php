<?php

require_once 'connection.php';

if(getenv('REQUEST_METHOD')=='POST'){

    $key = $_POST["query"];
    $query = "SELECT id, complete_name, e_mail, phone, address, image 
            FROM customers
            WHERE LOWER(complete_name) LIKE LOWER('$key%') OR LOWER(e_mail) LIKE LOWER('$key%')
                OR LOWER(phone) LIKE LOWER('$key%')";
    $result = mysqli_query($connection, $query);
    if ($result ->num_rows > 0){
        $response = array();
        while ( $row = mysqli_fetch_assoc($result) ){
            array_push($response,
                array(
                    "id" => $row["id"],
                    "name" => $row["complete_name"],
                    "e_mail" => $row["e_mail"],
                    "phone" => $row["phone"],
                    "address" => $row["address"],
                    "image" => $row["image"])
            );
        }
        echo json_encode($response);
    }
    else{
        print(json_encode(array("No user found that matches the query: ")));
    }

}
mysqli_close($connection);

