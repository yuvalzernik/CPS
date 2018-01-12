package cps.clientServer;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Class ServerResponse.
 *
 * @param <T> the generic type
 */
public class ServerResponse<T> implements Serializable
{
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The request result. */
    private RequestResult requestResult;
    
    /** The response object. */
    private T responseObject;
    
    /** The message. */
    private String message;
    
    /**
     * Instantiates a new server response.
     *
     * @param requestResult the request result
     * @param responseObject the response object
     * @param message the message
     */
    public ServerResponse(RequestResult requestResult, T responseObject, String message)
    {
	this.requestResult = requestResult;
	this.responseObject = responseObject;
	this.message = message;
    }
    
    /**
     * Gets the response object.
     *
     * @return the t
     */
    public T GetResponseObject()
    {
	return responseObject;
    }
    
    /**
     * Gets the request result.
     *
     * @return the request result
     */
    public RequestResult GetRequestResult()
    {
	return requestResult;
    }
    
    /**
     * Gets the message.
     *
     * @return the string
     */
    public String GetMessage()
    {
	return message;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
	return "Request result: " + requestResult + "\nResponse object:\n" + responseObject + "\nMessage: " + message;
    }
}
