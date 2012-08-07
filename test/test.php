<?php
$req = array(
	'id'=>'1',
	'jsonrpc' => '2.0',
	'method'=>'helloWorld',
	'params'=>array('World')
	);
$c = curl_init('http://localhost:8080/TestService');
curl_setopt($c,  CURLOPT_RETURNTRANSFER, true);
curl_setopt($c,  CURLOPT_POST, true);
curl_setopt($c,  CURLOPT_POSTFIELDS, json_encode($req));
echo curl_exec($c)."\n";
curl_close($c);

