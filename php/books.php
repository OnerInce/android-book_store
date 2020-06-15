<?php
if(getenv('REQUEST_METHOD')=='POST') {
    require_once 'connection.php';

    $id = $_POST["id"];
    $result = array();

    $sql1 = "SELECT b.id, ISBN, title, first_name, last_name, category_name, book_type_name, stock_quantity, price, sales, discount_value,
            SUM(r.rating) AS rate, COUNT(r.rating) AS no_people_rated, summary, image
            FROM books b
            JOIN
            authors a ON b.author_id = a.id
            JOIN
            categories c ON c.id = b.category_id
            JOIN 
            book_types bt ON bt.id = b.book_type_id
            LEFT JOIN
            reviews r ON b.id = r.book_id
            LEFT JOIN 
            discounts d ON b.id = d.book_id
            WHERE b.id = '$id'";

    $sql2 = "SELECT c.complete_name, review_text, review_date, r.rating
            FROM customers c
            JOIN reviews r on c.id = r.customer_id
            WHERE r.book_id = '$id'
            ORDER BY r.id DESC";


    $response = mysqli_query($connection, $sql1);
    $response2 = mysqli_query($connection, $sql2);
    if (mysqli_num_rows($response) > 0) {

        $result["book_info"] = $response->fetch_array(MYSQLI_ASSOC);
        $comment_array = array();

        while($record = mysqli_fetch_array($response2, MYSQLI_ASSOC)) {
            array_push($comment_array, $record);
        }

        $result["comments"] = $comment_array;

        echo json_encode($result);
        mysqli_close($connection);
    }
}