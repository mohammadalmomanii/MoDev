<?php

header('Content-Type: application/json');

function makeCurlRequest($url, $method = 'GET', $postData = []) {
    $ch = curl_init();

    curl_setopt($ch, CURLOPT_URL, $url);

    if ($method === 'POST') {
        curl_setopt($ch, CURLOPT_POST, 1);
        curl_setopt($ch, CURLOPT_POSTFIELDS, http_build_query($postData));
    }

    curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
    curl_setopt($ch, CURLOPT_SSL_VERIFYHOST, false);

    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);

    $response = curl_exec($ch);

    if (curl_errno($ch)) {
        return ['error' => curl_error($ch)."missing URL"];
    }

    curl_close($ch);

    return json_decode($response, true);
}

$requests = $_POST['requests'] ?? null;

if (empty($requests)) {
    echo json_encode(['error' => 'No requests provided']);
    exit;
}

$requests = json_decode($requests, true);

if (json_last_error() !== JSON_ERROR_NONE || !is_array($requests)) {
    echo json_encode(['error' => 'Invalid JSON input']);
    exit;
}

$responses = [];

foreach ($requests as $request) {
    $url = $request['url'] ?? null;
    $method = $request['method'] ?? 'GET';
    $postData = $request['postData'] ?? [];

    if (empty($url)) {
        $responses[] = ['error' => 'URL is missing'];
        continue;
    }

    $response = makeCurlRequest($url, $method, $postData);

    $responses[$request['apiName']] = $response;
}

echo json_encode($responses);
?>
