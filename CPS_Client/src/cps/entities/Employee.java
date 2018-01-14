package cps.entities;

import java.io.Serializable;

import cps.entities.enums.EmployeeType;
import cps.entities.enums.LogedStatus;

// TODO: Auto-generated Javadoc
/**
 * The Class Employee.
 */
public class Employee implements Serializable
{
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The employee id. */
    private String employeeId;
    
    /** The first name. */
    private String firstName;
    
    /** The last name. */
    private String lastName;
    
    /** The email. */
    private String email;
    
    /** The org affiliation. */
    private String orgAffiliation;
    
    /** The username. */
    private String username;
    
    /** The password. */
    private String password;
    
    /** The employee type. */
    EmployeeType employeeType;
    
    /** The loged status. */
    LogedStatus logedStatus;
    
    /**
     * Instantiates a new employee.
     *
     * @param employeeId the employee id
     * @param firstName the first name
     * @param lastName the last name
     * @param email the email
     * @param orgAffiliation the org affiliation
     * @param username the username
     * @param password the password
     * @param employeeType the employee type
     * @param logedStatus the loged status
     */
    public Employee(String employeeId, String firstName, String lastName, String email, String orgAffiliation,
	    String username, String password, EmployeeType employeeType, LogedStatus logedStatus)
    {
	this.employeeId = employeeId;
	this.firstName = firstName;
	this.lastName = lastName;
	this.email = email;
	this.orgAffiliation = orgAffiliation;
	this.username = username;
	this.password = password;
	this.employeeType = employeeType;
	this.logedStatus = logedStatus;
	
    }
    
    /**
     * Gets the loged status.
     *
     * @return the loged status
     */
    public LogedStatus getLogedStatus()
    {
	return logedStatus;
    }
    
    /**
     * Gets the employee id.
     *
     * @return the employee id
     */
    public String getEmployeeId()
    {
	return employeeId;
    }
    
    /**
     * Gets the email.
     *
     * @return the email
     */
    public String getEmail()
    {
	return email;
    }
    
    /**
     * Gets the employee type.
     *
     * @return the employee type
     */
    public EmployeeType getEmployeeType()
    {
	return employeeType;
    }
    
    /**
     * Gets the first name.
     *
     * @return the first name
     */
    public String getFirstName()
    {
	return firstName;
    }
    
    /**
     * Gets the last name.
     *
     * @return the last name
     */
    public String getLastName()
    {
	return lastName;
    }
    
    /**
     * Gets the org affiliation.
     *
     * @return the org affiliation
     */
    public String getOrgAffiliation()
    {
	return orgAffiliation;
    }
    
    /**
     * Gets the password.
     *
     * @return the password
     */
    public String getPassword()
    {
	return password;
    }
    
    /**
     * Gets the username.
     *
     * @return the username
     */
    public String getUsername()
    {
	return username;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
	return "employee Id: " + employeeId + "\nfirst name: " + firstName + "\nlast name: " + lastName + "\nemail: "
		+ email + "\nOrganization affiliation: " + orgAffiliation + "\nusername: " + username + "\npassword: "
		+ password + "\nEmployee type: " + employeeType;
    }
}