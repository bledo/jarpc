package bledo.rpc.json;

import java.io.PrintWriter;

import org.json.JSONException;
import org.json.JSONObject;

public class BaseRpcResponse implements RpcResponse
{
	protected String id;
	protected Object result;
	
	public BaseRpcResponse(String id, Object result)
	{
		this.id = id;
		this.result = result;
	}
	
	public void respond(PrintWriter writer)
	{
		// create response
		try {
			JSONObject json = new JSONObject();
			json.put("jsonrpc", "2.0");
			json.put("id", id);
			json.put("result", result);
			
			writer.write( json.toString()  );
				
		} catch (JSONException e) {
			(new ErrorResponse(id, -32603, "Server error")).respond(writer);
		}
		
	}
}
