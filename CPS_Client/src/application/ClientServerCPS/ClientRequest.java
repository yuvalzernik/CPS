package application.ClientServerCPS;

public class ClientRequest<T> extends ClientRequestBase
{    
    private static final long serialVersionUID = 1L;
    
    private T sentObject;
            
    public ClientRequest(T sentObject, String serverDestination)
    {
	super(serverDestination);
	
	this.sentObject = sentObject;
    }
    
    public T GetSentObject()
    {
	return sentObject;
    }
}
