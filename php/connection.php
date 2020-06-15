<?php

define('DB_NAME','kithubdb');
define('DB_USER','admin');
define('DB_PASSWORD','1q2w3e4r');
define('DB_HOST','database-1.cwqb6euicxit.us-east-1.rds.amazonaws.com');

$connection = mysqli_connect(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);

if($connection->connect_errno >0){
    exit("Database failed.\n".$connection->connect_error);
}
mysqli_set_charset($connection,'utf8');
