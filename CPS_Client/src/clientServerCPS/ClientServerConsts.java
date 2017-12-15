package clientServerCPS;

// These consts are used in the server side to determine what method should be called.
// For each const there should be one method in the ServerRequestHandler with the exact name.


public class ClientServerConsts
{
    public static final int PORT = 5555;
    
    public static final String RegisterFullMembership = "RegisterFullMembership";
    
    public static final String AddCustomerIfNotExist = "AddCustomerIfNotExist";
}
