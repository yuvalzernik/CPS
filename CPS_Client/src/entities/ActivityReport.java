package entities;

import java.io.Serializable;
import java.util.ArrayList;

public class ActivityReport implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private ArrayList<Integer> arrExercised;
	
	private ArrayList<Integer> arrCancelled;
	
	private ArrayList<Float> arrDisabled;
	
	private float medianExercised;
	
	private float medianCancelled;
	
	private float medianDisabled;
	
	private float deviationExercised;
	
	private float deviationCancelled;
	
	private float deviationDisabled;
	
	public ActivityReport(ArrayList<Integer> arrExercised, ArrayList<Integer> arrCancelled, ArrayList<Float> arrDisabled,
		float medianExercised, float medianCancelled, float medianDisabled, float deviationExercised, float deviationCancelled,
		float deviationDisabled)
	{
		this.arrExercised=arrExercised;
		
		this.arrCancelled=arrCancelled;
		
		this.arrDisabled=arrDisabled;
		
		this.medianExercised=medianExercised;
		
		this.medianCancelled=medianCancelled;
		
		this.medianDisabled=medianDisabled;
		
		this.deviationExercised=deviationExercised;
		
		this.deviationCancelled=deviationCancelled;
		
		this.deviationDisabled=deviationDisabled;
	}
	
	public ArrayList<Integer> getArrExercised()
	{
		return arrExercised;
	}
	
	public ArrayList<Integer> getArrCancelled()
	{
		return arrCancelled;
	}
	
	public ArrayList<Float> getArrDisabled()
	{
		return arrDisabled;
	}
	
	public float getMedianExercised()
	{
		return medianExercised;
	}
	
	public float getMedianCancelled()
	{
		return medianCancelled;
	}
	
	public float getMedianDisabled()
	{
		return medianDisabled;
	}
	
	public float getDeviationExercised()
	{
		return deviationExercised;
	}
	
	public float getDeviationCancelled()
	{
		return deviationCancelled;
	}
	
	public float getDeviationDisabled()
	{
		return deviationDisabled;
	}
	
	@Override
    public String toString()
    {
		return "Median exercised: " + medianExercised + "\nMedian cancelled: " + medianCancelled + "\nMedian disabled: " + medianDisabled + 
				"\nDeviation excersied: " + deviationExercised + "\nDeviation cancelled: " + deviationCancelled + "\nDeviation disabled: " + deviationDisabled;
    }
}
