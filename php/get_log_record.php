<?php
if(getenv('REQUEST_METHOD')=='POST') {
    require_once 'connection.php';

    $time = $_POST["time"];
    $operation = $_POST["operation"];

    if (strpos($time, 'saat') == true) {
        $new_time = 1;
    }

    elseif (strpos($time, 'hafta') == true) {
        $new_time = 7;
    }

    elseif (strpos($time, 'ay') == true) {
        $new_time = 30;
    }

    elseif (strpos($time, 'yıl') == true) {
        $new_time = 365;
    }

    elseif (strpos($time, 'zamanlar') == true) {
        $new_time = 100000;
    }


    if($operation == "Giriş yapma" or $operation == "Çıkış yapma" or $operation == "Kaydolma" or $operation == "Kullanıcı bilgileri güncelleme"){
        $sql = "SELECT c.id, complete_name, phone, e_mail, action_time, address
                FROM user_logs ul
                JOIN customers c on ul.customer_id = c.id
                WHERE ul.event_name = '$operation' AND DATE_SUB(CURDATE(), INTERVAL $new_time DAY) <= action_time";

        $response = mysqli_query($connection, $sql);
        $result = array();

        while($row = mysqli_fetch_array($response))
        {
            $temp = array();
            $temp['customer_id'] = $row['id'];
            $temp['e_mail'] = $row['e_mail'];
            $temp['complete_name'] = $row['complete_name'];
            $temp["phone"] = $row['phone'];
            $temp["action_time"] = $row['action_time'];
            $temp["address"] = $row['address'];
            array_push($result, $temp);
        }
    }
    elseif($operation == "Sepet İşlemleri"){
        $sql = "SELECT c.id, title, complete_name, event_name, action_time
                FROM cart_logs cl
                JOIN customers c on cl.customer_id = c.id
                JOIN books b on cl.book_id = b.id
                WHERE DATE_SUB(CURDATE(), INTERVAL $new_time DAY) <= action_time";

        $response = mysqli_query($connection, $sql);
        $result = array();
        while($row = mysqli_fetch_array($response))
        {
            $temp = array();

            $temp['customer_id'] = $row['id'];
            $temp['complete_name'] = $row['complete_name'];
            $temp['title'] = $row['title'];
            $temp["event_name"] = $row['event_name'];
            $temp["action_time"] = $row['action_time'];
            array_push($result, $temp);
        }
    }
    elseif($operation == "Sipariş verme"){
        $sql = "SELECT o.id, complete_name, company_name, o.total_order_price, action_time, COUNT(book_id) AS nof_books
                FROM order_logs ol
                JOIN orders o on ol.order_id = o.id
                JOIN order_details od on o.id = od.order_id
                JOIN customers c on o.customer_id = c.id
                JOIN shippers s on o.shipper_id = s.id
                JOIN books b on od.book_id = b.id
                WHERE DATE_SUB(CURDATE(), INTERVAL $new_time DAY) <= action_time
                GROUP BY o.id";

        $response = mysqli_query($connection, $sql);
        $result = array();
        while($row = mysqli_fetch_array($response))
        {
            $temp = array();

            $temp['order_id'] = $row['id'];
            $temp['company_name'] = $row['company_name'];
            $temp['complete_name'] = $row['complete_name'];
            $temp['total_price'] = $row['total_order_price'];
            $temp["nof_books"] = $row['nof_books'];
            $temp["action_time"] = $row['action_time'];
            array_push($result, $temp);
        }
    }

    elseif($operation == "Yorum yapma"){
        $sql = "SELECT r.id, complete_name, title, action_time, review_text, r.rating
                FROM review_logs rl
                JOIN reviews r on r.id = rl.review_id
                JOIN customers c on r.customer_id = c.id
                JOIN books b on r.book_id = b.id
                WHERE DATE_SUB(CURDATE(), INTERVAL $new_time DAY) <= action_time";

        $response = mysqli_query($connection, $sql);
        $result = array();
        while($row = mysqli_fetch_array($response))
        {
            $temp = array();

            $temp['review_id'] = $row['id'];
            $temp['title'] = $row['title'];
            $temp['complete_name'] = $row['complete_name'];
            $temp['review_text'] = $row['review_text'];
            $temp["rating"] = $row['rating'];
            $temp["action_time"] = $row['action_time'];
            array_push($result, $temp);
        }
    }
    echo json_encode($result);
    mysqli_close($connection);

}