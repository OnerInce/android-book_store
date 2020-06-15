<?php
if(getenv('REQUEST_METHOD')=='POST') {
    require_once 'connection.php';

    $id = $_POST["id"];
    $result = array();

    $sql = "SELECT title, review_text, review_date, r.rating
            FROM books b
            JOIN reviews r on b.id = r.book_id
            WHERE r.customer_id = '$id'
            ORDER BY r.review_date DESC";


    $response = mysqli_query($connection, $sql);
    if (mysqli_num_rows($response) > 0) {

        while ( $row = mysqli_fetch_assoc($response) ){

            array_push($result,
                array(
                    "title" => $row["title"],
                    "review_text" => $row["review_text"],
                    "review_date" => $row["review_date"],
                    "rating" => $row["rating"])
            );
        }

        echo json_encode($result);
        mysqli_close($connection);
    }
}