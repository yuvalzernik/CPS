package entities;

import java.io.Serializable;

import entities.enums.ParkinglotStatus;

public class SetParkingotStatusRequest implements Serializable
{
    private String parkinglotName;
    
    private ParkinglotStatus parkinglotStatus;
    
    public SetParkingotStatusRequest(String parkinglotName, ParkinglotStatus parkinglotStatus)
    {
	// TODO Auto-generated constructor stub
    }
}
