package clientServerCPS;

import java.io.Serializable;

public class ServerResponse<T> implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    private RequestResult requestResult;
    
    private T responseObject;
    
    private String message;
    
    public ServerResponse(RequestResult requestResult, T responseObject, String message)
    {
	this.requestResult = requestResult;
	this.responseObject = responseObject;
	this.message = message;
    }
    
    public T GetResponseObject()
    {
	return responseObject;
    }
    
    public RequestResult GetRequestResult()
    {
	return requestResult;
    }
    
    public String GetMessage()
    {
	return message;
    }
    
    @Override
    public String toString()
    {
	return "Request result: " + requestResult + "\nResponse object:\n" + responseObject + "\nMessage: " + message;
    }
}
