<?php
if(getenv('REQUEST_METHOD')=='POST'){
    require_once 'connection.php';

    $ISBN = $_POST["ISBN"];
    $title = $_POST["title"];
    $first_name = $_POST["first_name"];
    $last_name = $_POST["last_name"];
    $stock = $_POST["stock"];
    $category_name = $_POST["category_name"];
    $book_type_name = $_POST["book_type_name"];
    $price = $_POST["price"];
    $summary = $_POST["summary"];
    $image = $_POST["image"];

    $mask = "/var/www/html/images/book/".$ISBN."_*";
    array_map("unlink",glob($mask));

    $fileName = "/var/www/html/images/book/".$ISBN."_".rand(1000,10000).".png";
    file_put_contents($fileName,base64_decode($image));

    $sql_author = "INSERT IGNORE INTO authors (first_name, last_name) VALUES ('$first_name', '$last_name')";

    $stmt = mysqli_prepare($connection, $sql_author);
    mysqli_stmt_execute($stmt);

    $sql = "INSERT INTO books (ISBN, title, author_id, stock_quantity, category_id, book_type_id, price, summary, image) 
                VALUES
                ('$ISBN', '$title', (SELECT id FROM authors WHERE first_name = '$first_name' AND last_name = '$last_name'), 
                '$stock', (SELECT id FROM categories WHERE category_name = '$category_name'), 
                (SELECT id FROM book_types WHERE book_type_name = '$book_type_name'), '$price', '$summary', '$fileName')";
    $result = array();

    $stmt = mysqli_prepare($connection, $sql);
    mysqli_stmt_execute($stmt);

    $result["success"] = mysqli_stmt_affected_rows($stmt);
    echo json_encode($result);
    mysqli_close($connection);
}
