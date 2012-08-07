package bledo.rpc.json;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bledo.rpc.json.ProxyService.MethodDefinition;

/**
 * Servlet implementation class TestJsonRpc
 */
@WebServlet("/TestJsonRpc")
public class RpcServlet extends HttpServlet
{
	final Logger log = LoggerFactory.getLogger(RpcServlet.class);
	private static final long serialVersionUID = 1L;
	protected ProxyService proxy;
       
    public void init()
    {
		proxy = new ProxyService(this);
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
    	log.info("doGet called");
		StringBuilder sb = new StringBuilder();
		List<MethodDefinition> defList = proxy.getMethodDefinitios();
		sb.append("<html><body>");
		if (defList.size() > 0)
		{
			sb.append("<ul>");
			for (MethodDefinition def : defList)
			{
				sb.append("<li>");
				sb.append(def.toString());
				sb.append("</li>");
			}
			sb.append("</ul>");
		}
		else
		{
			sb.append("<b>No methods found</b>");
		}
		sb.append("<body></html>");
		response.getWriter().print(sb.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
    	log.info("doPost called");
		int sPathLen = req.getContextPath().length();
		String path = req.getRequestURI();
		path = path.substring(sPathLen);
		
		String jsonInput = IOUtils.toString(req.getInputStream());
		RpcResponse rpcResponder = getRpcResponder(path, jsonInput);
		
		resp.setContentType("application/json");
		rpcResponder.respond( resp.getWriter() );
	}
	
	protected RpcResponse getRpcResponder(String path, String jsonInput)
	{
		// request object
		RpcRequest request = null;
		try {
			request = new RpcRequest(jsonInput);
		} catch (RpcException e) {
			log.error("{}", e);
			return new ErrorResponse(-32700, "Parse error");
		}
		
		// check method
		if (!proxy.methodExists(request.getMethod())) {
			return new ErrorResponse(request.getId(), -32601, "Method not found");
		}
		
		// response
		try {
			Object result = proxy.call(request.getMethod(), request.getParams());
			return new BaseRpcResponse(request.getId(), result);
		} catch (RpcException e) {
			return new ErrorResponse(request.getId(), -32000, "Server error");
		}
	}

}
