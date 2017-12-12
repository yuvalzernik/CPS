package application.ClientServerCPS;

import java.io.Serializable;

public class ClientRequest<T> implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    T sentObject;
    
    public String serverDestination = "test";
}
