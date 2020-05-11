<?php

include_once "api/db.php";
$db = new Database();
$incidents = $db->select('incident', [], false, 'id DESC')->result_array();
echo json_encode($incidents, JSON_PRETTY_PRINT);

?>

