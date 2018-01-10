package entities;

import java.io.Serializable;
import java.util.ArrayList;

public class Customer implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    private String id;
    
    private String email;
    
    private float balance;
    
    private ArrayList<String> reservations;
    
    private ArrayList<String> subscriptions;
    
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
    
    public ArrayList<String> getSubscriptions()
    {
	return subscriptions;
    }
    
    public ArrayList<String> getReservations()
    {
	return reservations;
    }
    
    public void setSubscriptions(ArrayList<String> subscriptions)
    {
	this.subscriptions = subscriptions;
    }
    
    public void setReservations(ArrayList<String> reservations)
    {
	this.reservations = reservations;
    }
    
    @Override
    public String toString()
    {
	return "Id: " + id + "\nEmail: " + email + "\nBalance: " + balance + "\n\nYour Reservations:\n" + reservations
		+ "\n\nYour subscriptions:\n" + subscriptions;
    }
}
