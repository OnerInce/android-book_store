<?php

if(getenv('REQUEST_METHOD')=='POST') {
    require_once 'connection.php';

    $operation = $_POST["operation"]; //1-->get info    2-->update info
    $id = $_POST["id"];
    if($operation == "1"){

        $sql = "SELECT * FROM customers WHERE id='$id'";

        $response = mysqli_query($connection, $sql);

        if (mysqli_num_rows($response) > 0) {
            echo json_encode(mysqli_fetch_assoc($response));
            mysqli_close($connection);
        }
    }
    elseif($operation == "2"){
        $result = array();
        $complete_name = $_POST["complete_name"];
        $phone = $_POST["phone"];
        $mail = $_POST["e_mail"];
        $address = $_POST["address"];
        $password = $_POST["user_password"];
        $image = $_POST["encodedImage"];

        if(!file_exists("/var/www/html/images/user")){
            mkdir("/var/www/html/images/user",0777,true);
        }
        $mask = "/var/www/html/images/user/".$id."_*";
        array_map("unlink",glob($mask));

        if ($image == "empty") {
            $sql = "UPDATE customers SET complete_name='$complete_name',phone = '$phone', e_mail = '$mail', user_password = '$password',
                                    address = '$address' WHERE id='$id'";
            mysqli_query($connection, $sql);
            $result["imageChanged"] = "0";
            echo  json_encode($result);
        }
        else{
            $fileName = "/var/www/html/images/user/".$id."_".rand(1000,10000).".png";
            file_put_contents($fileName,base64_decode($image));
            $sql = "UPDATE customers SET complete_name='$complete_name',phone = '$phone', e_mail = '$mail', user_password = '$password',
                                    address = '$address' , image = '$fileName' WHERE id='$id'";
            mysqli_query($connection, $sql);
            $result["newFilePath"] = $fileName;
            $result["imageChanged"] = "1";
            echo  json_encode($result);
        }

        mysqli_close($connection);

    }


}
