package CPS_Utilities;

import java.io.Serializable;

public class LoginIdentification implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    private String Username;
    private String Password;
    
    public LoginIdentification(String _Username, String _Password)
    {
	Username = _Username;
	Password = _Password;
    }
    
    public String getUsername()
    {
	return Username;
    }
    
    public String getPassword()
    {
	return Password;
    }
    
    @Override
    public String toString()
    {
	return "username: " + Username + "\npassword: " + Password;
    }
}
