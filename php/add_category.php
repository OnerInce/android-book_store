<?php
if(getenv('REQUEST_METHOD')=='POST'){
    require_once 'connection.php';

    if(isset($_POST["book_type_name"])){
        $bookType = $_POST["book_type_name"];
    }
    $category = $_POST["category_name"];


    $sqlType = "INSERT INTO book_types (book_type_name, category_id)
                VALUES ('$bookType', (SELECT id from categories WHERE category_name = '$category'))";
    $sqlCategory = "INSERT IGNORE INTO categories (category_name) VALUES ('$category')";
    $result = array();

    if(empty($bookType)){

        $stmt = mysqli_prepare($connection, $sqlCategory);
        mysqli_stmt_execute($stmt);

        $result["success"] = mysqli_stmt_affected_rows($stmt);
        echo json_encode($result);
    }
    else{
        //category
        $stmt = mysqli_prepare($connection,$sqlCategory);
        mysqli_stmt_execute($stmt);
        $stmt2 = mysqli_prepare($connection,$sqlType);
        mysqli_stmt_execute($stmt2);

        $result["success"] = mysqli_stmt_affected_rows($stmt);
        echo json_encode($result);
    }


}