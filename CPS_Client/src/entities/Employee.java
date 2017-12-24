package entities;

import java.io.Serializable;

import CPS_Clients.Controllers.Employee.EmployeeBaseController;
import entities.enums.EmployeeType;

public class Employee implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    private String employeeId;
    
    private String firstName;
    
    private String lastName;
    
    private String email;
    
    private String orgAffiliation;
    
    private String username;
    
    private String password;
    
    EmployeeType employeeType;
    
    public Employee(String employeeId, String firstName, String lastName, String email, String orgAffiliation,
	    String username, String password, EmployeeType employeeType)
    {
	this.employeeId = employeeId;
	this.firstName = firstName;
	this.lastName = lastName;
	this.email = email;
	this.orgAffiliation = orgAffiliation;
	this.username = username;
	this.password = password;
	this.employeeType = employeeType;
	
    }
    
    public String getEmployeeId()
    {
	return employeeId;
    }
    
    public String getEmail()
    {
	return email;
    }
    
    public EmployeeType getEmployeeType()
    {
	return employeeType;
    }
    
    public String getFirstName()
    {
	return firstName;
    }
    
    public String getLastName()
    {
	return lastName;
    }
    
    public String getOrgAffiliation()
    {
	return orgAffiliation;
    }
    
    public String getPassword()
    {
	return password;
    }
    
    public String getUsername()
    {
	return username;
    }
    
    @Override
    public String toString()
    {
	return "employee Id: " + employeeId + "\nfirst name: " + firstName + "\nlast name: " + lastName + "\nemail: "
		+ email + "\nOrganization affiliation: " + orgAffiliation + "\nusername: " + username + "\npassword: "
		+ password + "\nEmployee type: " + employeeType;
    }
}