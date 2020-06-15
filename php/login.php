<?php

if (getenv('REQUEST_METHOD')=='POST') {

    $email = $_POST['e_mail'];
    $password = $_POST['user_password'];

    require_once 'connection.php';
    $sql = "SELECT * FROM customers WHERE e_mail='$email' and user_password = '$password'";
    $response = mysqli_query($connection, $sql);

    $result = array();
    if ( mysqli_num_rows($response) > 0 ) {

        $row = mysqli_fetch_assoc($response);

        $result['e_mail'] = $row['e_mail'];
        $result['id'] = $row['id'];
        $id_log = $result['id'];
        $result['success'] = "1";
        $result["is_admin"] = $row['is_admin'];
        $result["complete_name"] = $row['complete_name'];

        $sql_log = "INSERT INTO user_logs (event_name, customer_id) VALUES('Giriş yapma',  '$id_log')";
        $response2 = mysqli_query($connection, $sql_log);
    }
    else{
        $result['success'] = "0";
    }

    header("Content-Type: application/json");
    echo json_encode($result);
    mysqli_close($connection);
}

?>