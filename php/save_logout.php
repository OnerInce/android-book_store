<?php

if (getenv('REQUEST_METHOD') == 'POST') {
    require_once 'connection.php';

    $id = $_POST["id"];

    $sql = "INSERT INTO user_logs (event_name, customer_id) VALUES('Çıkış yapma',  '$id')";

    $response = mysqli_query($connection, $sql);
    $result = array();

    if (mysqli_num_rows($response) >= 0) {
        $result['success'] = "1";
    }
    echo(json_encode($result));
    mysqli_close($connection);

}