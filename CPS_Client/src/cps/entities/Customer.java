package cps.entities;

import java.io.Serializable;
import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class Customer.
 */
public class Customer implements Serializable
{
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The id. */
    private String id;
    
    /** The email. */
    private String email;
    
    /** The balance. */
    private float balance;
    
    /** The reservations. */
    private ArrayList<String> reservations;
    
    /** The subscriptions. */
    private ArrayList<String> subscriptions;
    
    /**
     * Instantiates a new customer.
     *
     * @param id the id
     * @param email the email
     * @param balance the balance
     */
    public Customer(String id, String email, float balance)
    {
	this.id = id;
	this.email = email;
	this.balance = balance;
    }
    
    /**
     * Gets the id.
     *
     * @return the string
     */
    public String GetId()
    {
	return id;
    }
    
    /**
     * Gets the email.
     *
     * @return the string
     */
    public String GetEmail()
    {
	return email;
    }
    
    /**
     * Gets the balance.
     *
     * @return the float
     */
    public float GetBalance()
    {
	return balance;
    }
    
    /**
     * Gets the subscriptions.
     *
     * @return the subscriptions
     */
    public ArrayList<String> getSubscriptions()
    {
	return subscriptions;
    }
    
    /**
     * Gets the reservations.
     *
     * @return the reservations
     */
    public ArrayList<String> getReservations()
    {
	return reservations;
    }
    
    /**
     * Sets the subscriptions.
     *
     * @param subscriptions the new subscriptions
     */
    public void setSubscriptions(ArrayList<String> subscriptions)
    {
	this.subscriptions = subscriptions;
    }
    
    /**
     * Sets the reservations.
     *
     * @param reservations the new reservations
     */
    public void setReservations(ArrayList<String> reservations)
    {
	this.reservations = reservations;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
	return "Id: " + id + "\nEmail: " + email + "\nBalance: " + balance + "\n\nYour Reservations:\n" + reservations
		+ "\n\nYour subscriptions:\n" + subscriptions;
    }
}
