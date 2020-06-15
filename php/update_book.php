<?php
if(getenv('REQUEST_METHOD')=='POST') {
    require_once 'connection.php';

    $id = $_POST["id"];
    $operation = $_POST["operation"];
    $result = array();
    if($operation == "get_info"){
        $sql = "SELECT b.id, ISBN, title, first_name, last_name, category_name, book_type_name, price, 
            summary, image
            FROM books b
            JOIN
            authors a ON b.author_id = a.id
            JOIN
            categories c ON c.id = b.category_id
            JOIN 
            book_types bt ON bt.id = b.book_type_id
            WHERE b.id = '$id'";



        $response = mysqli_query($connection, $sql);
        if (mysqli_num_rows($response) > 0) {
            $result["book_info"] = $response->fetch_array(MYSQLI_ASSOC);
            echo json_encode($result);
        }
    }
    elseif ($operation == "update_info"){
        $ISBN = $_POST["ISBN"];
        $title = $_POST["title"];
        $first_name = $_POST["first_name"];
        $last_name = $_POST["last_name"];
        $category_name = $_POST["category_name"];
        $book_type_name = $_POST["book_type_name"];
        $price = $_POST["price"];
        $summary = $_POST["summary"];
        $image = $_POST["image"];
        $is_image_changed = $_POST["image_changed"];

        if($is_image_changed == "1"){
            $mask = "/var/www/html/images/book/".$ISBN."_*";
            array_map("unlink",glob($mask));

            $fileName = "/var/www/html/images/book/".$ISBN."_".rand(1000,10000).".png";
            file_put_contents($fileName,base64_decode($image));
            $image = $fileName;
        }

        $sql = "UPDATE books b 
            JOIN 
            authors a ON b.author_id = a.id
            JOIN
            categories c ON c.id = b.category_id
            JOIN 
            book_types bt ON bt.id = b.book_type_id
            SET ISBN = '$ISBN', title = \"$title\", a.first_name = '$first_name', a.last_name = '$last_name',
                c.category_name = '$category_name', bt.book_type_name = '$book_type_name',
                price = '$price', summary = \"$summary\", image = '$image'
            WHERE b.id = '$id'";
        $stmt = mysqli_prepare($connection, $sql);
        mysqli_stmt_execute($stmt);

        $result["success"] = mysqli_stmt_affected_rows($stmt);
        echo json_encode($result);

    }
    elseif ($operation == "update_stock"){
        $new_stock = $_POST["new_stock"];
        $sql = "UPDATE books SET stock_quantity = stock_quantity + '$new_stock' WHERE id = '$id'";
        $stmt = mysqli_prepare($connection, $sql);
        mysqli_stmt_execute($stmt);

        $result["success"] = mysqli_stmt_affected_rows($stmt);
        echo json_encode($result);
    }
    mysqli_close($connection);

}