<?php
if(getenv('REQUEST_METHOD')=='POST') {
    require_once 'connection.php';

    $id = $_POST["id"];
    $result = array();

    $sql = "SELECT address
            FROM customers
            WHERE id = '$id'";


    $response = mysqli_query($connection, $sql);
    if (mysqli_num_rows($response) > 0) {

        while ( $row = mysqli_fetch_assoc($response) ){

            $result["address"] = $row["address"];
        }

        echo json_encode($result);
        mysqli_close($connection);
    }
}