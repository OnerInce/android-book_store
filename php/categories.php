<?php

require_once 'connection.php';

$sql = "SELECT category_name, book_type_name FROM categories c LEFT JOIN book_types b ON c.id = b.category_id";
$result = mysqli_query($connection, $sql);
//print_r($result);
$categoryArray = array();
$returnArray = array();

$currentCategory = "";
if ($result->num_rows > 0) {
    while($row = $result->fetch_assoc()) {
        if($currentCategory == ""){
            $currentCategory = $row["category_name"];
            array_push($categoryArray,$row["book_type_name"]);
            continue;
        }
        elseif($currentCategory == $row["category_name"]){

            array_push($categoryArray,$row["book_type_name"]);
        }
        else{
            $returnArray[$currentCategory] = $categoryArray;

            $categoryArray = array();
            $currentCategory = $row["category_name"];
            array_push($categoryArray, $row["book_type_name"]);
        }
    }
    $returnArray[$currentCategory] = $categoryArray;
    echo json_encode($returnArray);
} else {
    echo "0";
}
mysqli_close($connection);