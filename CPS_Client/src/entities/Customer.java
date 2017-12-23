package entities;

import java.io.Serializable;

public class Customer implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String id;
    
    private String email;
    
    private float balance;
    
    public Customer(String id, String email, float balance)
    {
	this.id = id;
	this.email = email;
	this.balance = balance;
    }
    
    public String GetId()
    {
	return id;
    }
    
    public String GetEmail()
    {
	return email;
    }
    
    public float GetBalance()
    {
	return balance;
    }
    
    @Override
    public String toString()
    {
	return "Id: " + id + "\nEmail: " + email + "\nBalance: " + balance;
    }
}
