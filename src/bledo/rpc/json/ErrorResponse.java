package bledo.rpc.json;

import java.io.PrintWriter;


public class ErrorResponse implements RpcResponse
{
	protected String id;
	protected String msg;
	protected int code;
	public ErrorResponse(int code, String msg)
	{
		this.id = null;
		this.code = code;
		this.msg = msg;
	}
	
	public ErrorResponse(String id, int code, String msg)
	{
		this.id = id;
		this.code = code;
		this.msg = msg;
	}
	
	public void respond(PrintWriter writer)
	{
		String lmsg = org.apache.commons.lang.StringEscapeUtils.escapeJavaScript(msg);
		String lid = org.apache.commons.lang.StringEscapeUtils.escapeJavaScript(id);
		
		String resp;
		if (id == null)
		{
			resp = "{\"jsonrpc\":\"2.0\",\"error\":{\"code\":"+code+",\"message\":\""+ lmsg +"\"}}";
		}
		else
		{
			resp = "{\"jsonrpc\":\"2.0\", \"id\":\""+ lid +"\", \"error\":{\"code\":"+code+",\"message\":\""+ lmsg +"\"}}";
		}
		
		writer.write(resp);
	}
}
