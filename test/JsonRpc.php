<?php
class JsonRpc
{
	protected $url;
	protected $c;
	protected $i = 0;

	public function __construct($url)
	{
		$this->url = $url;

		$this->c = curl_init($url);
		curl_setopt($this->c,  CURLOPT_RETURNTRANSFER, true);
		curl_setopt($this->c,  CURLOPT_POST, true);
	}

	public function __call($method, $args)
	{
		$this->i++;
		$req = array( 'id' => $this->i, 'jsonrpc' => '2.0', 'method'=> $method, 'params'=>$args);
		curl_setopt($this->c,  CURLOPT_POSTFIELDS, json_encode($req));
		$json = curl_exec($this->c);
		$arr = json_decode($json, true);
		if (!$arr) {
			throw new \Exception('Unable to parse server response');
		}

		if (!empty($arr['error']))
		{
			throw new Exception($arr['error']['message'], $arr['error']['code']);
		}

		return $arr['result'];
	}

	public function close()
	{
		curl_close($this->c);
	}
}
