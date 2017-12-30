package entities;

import java.io.Serializable;

public class RemoveCarRequest implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String parkinglot;
    
    private String carNumber;
    
    public RemoveCarRequest(String parkinglot, String carNumber)
    {
	this.parkinglot = parkinglot;
	this.carNumber = carNumber;
    }
    
    @Override
    public String toString()
    {
	return "Parking lot: " + parkinglot + "\nCar number: " + carNumber;
    }
    
    public String getCarNumber()
    {
	return carNumber;
    }
    
    public String getParkinglot()
    {
	return parkinglot;
    }
    
}
