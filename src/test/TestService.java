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
