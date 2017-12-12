package application.Models;

public class Customer
{
    String id;
    
    String email;
    
    float balance;
    
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
