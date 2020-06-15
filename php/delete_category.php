<?php
if(getenv('REQUEST_METHOD')=='POST'){
    require_once 'connection.php';

    if(isset($_POST["book_type_name"])){
        $bookType = $_POST["book_type_name"];
    }
    $category = $_POST["category_name"];


    $sqlType = "DELETE FROM book_types WHERE book_type_name = '$bookType'";
    $sqlCategory = "DELETE FROM categories WHERE category_name = '$category'";
    $result = array();
    if(!empty($bookType)){

        $stmt = mysqli_prepare($connection, $sqlType);
        mysqli_stmt_execute($stmt);

        $result["success"]=mysqli_stmt_affected_rows($stmt);
        echo json_encode($result);
    }
    else{
        //category
        $stmt = mysqli_prepare($connection,$sqlCategory);
        mysqli_stmt_execute($stmt);
        $result["success"] = mysqli_stmt_affected_rows($stmt);
        echo json_encode($result);
    }


}