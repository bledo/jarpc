# JSON RPC Implementation For Java Servlets


## Sample Usage:

### Test Servlet:

	package test;

	import bledo.rpc.json.JsonRpc;
	import bledo.rpc.json.RpcServlet;
	import javax.servlet.annotation.WebServlet;

	@WebServlet("/TestService")
	public class TestService extends RpcServlet
	{
		private static final long serialVersionUID = 1L;
		
		@JsonRpc
		public String helloWorld(String name)
		{
			return "Hello " + name;
		}
	}


### PHP Client
test.php

	<?php
	$req = array(
		'id'=>'1',
		'jsonrpc' => '2.0',
		'method' => 'helloWorld',
		'params' => array('World')
	);
	$c = curl_init('http://localhost:8080/TestService');
	curl_setopt($c,  CURLOPT_RETURNTRANSFER, true);
	curl_setopt($c,  CURLOPT_POST, true);
	curl_setopt($c,  CURLOPT_POSTFIELDS, json_encode($req));
	echo curl_exec($c)."\n";
	curl_close($c);


### List of methods:
Navigate to http://localhost:8080/TestService

	* java.lang.String helloWorld(java.lang.String)



	

### Execute:
	$ php test.php
	{"id":"1","result":"Hello World","jsonrpc":"2.0"}



