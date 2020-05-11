<?php

include_once "db.php";
$db = new Database();

$requestMethod = $_SERVER['REQUEST_METHOD'];

if ($requestMethod == 'POST') {
    $nature = isset($_POST['nature']) ? $_POST['nature'] : null;
    $description = isset($_POST['description']) ? $_POST['description'] : null;
    $latitude = isset($_POST['latitude']) ? $_POST['latitude'] : null;
    $longitude = isset($_POST['longitude']) ? $_POST['longitude'] : null;
    $android_id = isset($_POST['android_id']) ? $_POST['android_id'] : null;
    $android_id = isset($_POST['android_id']) ? $_POST['android_id'] : null;
    $unique_id = null;
    try{
        $image = isset($_POST['image']) ? base64_decode($_POST['image']) : null;
        if($image){
            $unique_id = uniqid('', true);
            file_put_contents("../images/$unique_id.JPG", $image);
        }
    } catch (Exception $exception){
        error_log("unable to decode image");
    }
    $db->insert('incident', [
        'nature' => $nature,
        'description' => $description,
        'latitude' => $latitude,
        'longitude' => $longitude,
        'android_id' => $android_id,
        'unique_id' => $unique_id
    ]);
    echo json_encode('ok', JSON_PRETTY_PRINT);
    exit();
}

if ($requestMethod == 'GET') {
    $incidents = $db->select('incident', [], false, 'id DESC')->result_array();
    foreach ($incidents as &$incident) {
        $incident['image'] = $incident['unique_id'] ? base64_encode(file_get_contents("../images/" . $incident['unique_id'] . '.JPG')) : null;
    }
    echo json_encode($incidents, JSON_PRETTY_PRINT);
    exit();
}

if ($requestMethod == 'PUT') {
    $fd = fopen('php://input', 'r');
    $data = '';
    while ($str = fread($fd, 1024))
        $data .= $str;
    fclose($fd);
    $data_array = json_decode($data, true);
    error_log("put data : $data");
    try {
        $id = $data_array['id'];
        $incident = $db->select('incident', ['id' => $id])->row_array();
        if (!$incident) {
            echo json_encode('KO, unknown id', JSON_PRETTY_PRINT);
            exit();
        }
        $nature = isset($data_array['nature']) ? $data_array['nature'] : $incident['nature'];
        $description = isset($data_array['description']) ? $data_array['description'] : $incident['description'];
        $db->update('incident', [
            'nature' => $nature,
            'description' => $description
        ], [
            'id' => $id
        ]);
    } catch (DatabaseException $e) {
        error_log($e);
        echo json_encode('ko, contact the admin', JSON_PRETTY_PRINT);
        exit();
    }
    echo json_encode('ok', JSON_PRETTY_PRINT);

    exit();
}

if ($requestMethod == 'DELETE') {
    $fd = fopen('php://input', 'r');
    $data = '';
    while ($str = fread($fd, 1024))
        $data .= $str;
    fclose($fd);
    $data_array = json_decode($data, true);
    try {
        $id = $data_array['id'];
        $incident = $db->select('incident', ['id' => $id])->row_array();
        if (!$incident) {
            echo json_encode('KO, unknown id', JSON_PRETTY_PRINT);
            exit();
        }
        $db->delete('incident', [
            'id' => $id
        ]);
    } catch (DatabaseException $e) {
        error_log($e);
        echo json_encode('ko, contact the admin', JSON_PRETTY_PRINT);
        exit();
    }
    echo json_encode('ok', JSON_PRETTY_PRINT);
    exit();
}

