package cps.entities;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Class LoginIdentification.
 */
public class LoginIdentification implements Serializable
{
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The Username. */
    private String Username;
    
    /** The Password. */
    private String Password;
    
    /**
     * Instantiates a new login identification.
     *
     * @param _Username the username
     * @param _Password the password
     */
    public LoginIdentification(String _Username, String _Password)
    {
	Username = _Username;
	Password = _Password;
    }
    
    /**
     * Gets the username.
     *
     * @return the username
     */
    public String getUsername()
    {
	return Username;
    }
    
    /**
     * Gets the password.
     *
     * @return the password
     */
    public String getPassword()
    {
	return Password;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
	return "username: " + Username + "\npassword: " + Password;
    }
}
