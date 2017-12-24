package entities;

import java.io.Serializable;

public class ParkingSpot implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    private int width_X;
    
    private int height_Y;
    
    private int depth_Z;
    
    public ParkingSpot(int width_X, int height_Y, int depth_Z)
    {
	this.width_X = width_X;
	this.height_Y = height_Y;
	this.depth_Z = depth_Z;
    }
    
    @Override
    public String toString()
    {
	return width_X + "," + height_Y + "," + depth_Z;
    }
    
    @Override
    public boolean equals(Object obj)
    {
	if(!(obj instanceof ParkingSpot))
	    return false;
	
	ParkingSpot parkingSpot = (ParkingSpot)obj;
	
	return (this.width_X == parkingSpot.getWidth_X() && this.height_Y == parkingSpot.getHeight_Y()
		&& this.depth_Z == parkingSpot.getDepth_Z());
    }
    
    static public ParkingSpot Parse(String str)
    {
	String[] dimentions = str.split(",");
	
	int x = Integer.parseInt(dimentions[0]);
	int y = Integer.parseInt(dimentions[1]);
	int z = Integer.parseInt(dimentions[2]);
	
	return new ParkingSpot(x, y, z);
    }
    
    public int getDepth_Z()
    {
	return depth_Z;
    }
    
    public int getHeight_Y()
    {
	return height_Y;
    }
    
    public int getWidth_X()
    {
	return width_X;
    }
}
