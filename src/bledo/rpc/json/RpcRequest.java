package bledo.rpc.json;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RpcRequest
{
	protected String method;
	protected String id;
	protected List<Object> params = new ArrayList<Object>();
	
	public RpcRequest(String jsonStr) throws RpcException
	{
		JSONObject json= null;
		try {
			// request object
			json = new JSONObject(jsonStr);
			
			method = json.getString("method");
			id = json.getString("id");
			JSONArray _params = json.getJSONArray("params");
			int len = _params.length();
			for (int i = 0; i < len; i++)
			{
				params.add(_params.get(i));
			}
		} catch (JSONException e) {
			throw new RpcException(e);
		}
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Object> getParams() {
		return params;
	}

	public void setParams(List<Object> params) {
		this.params = params;
	}
}
