package cps.entities;

import java.io.Serializable;
import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class ActivityReport.
 */
public class ActivityReport implements Serializable
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The arr exercised. */
	private ArrayList<Integer> arrExercised;
	
	/** The arr cancelled. */
	private ArrayList<Integer> arrCancelled;
	
	/** The arr disabled. */
	private ArrayList<Float> arrDisabled;
	
	/** The median exercised. */
	private float medianExercised;
	
	/** The median cancelled. */
	private float medianCancelled;
	
	/** The median disabled. */
	private float medianDisabled;
	
	/** The deviation exercised. */
	private float deviationExercised;
	
	/** The deviation cancelled. */
	private float deviationCancelled;
	
	/** The deviation disabled. */
	private float deviationDisabled;
	
	/**
	 * Instantiates a new activity report.
	 *
	 * @param arrExercised the arr exercised
	 * @param arrCancelled the arr cancelled
	 * @param arrDisabled the arr disabled
	 * @param medianExercised the median exercised
	 * @param medianCancelled the median cancelled
	 * @param medianDisabled the median disabled
	 * @param deviationExercised the deviation exercised
	 * @param deviationCancelled the deviation cancelled
	 * @param deviationDisabled the deviation disabled
	 */
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
	
	/**
	 * Gets the arr exercised.
	 *
	 * @return the arr exercised
	 */
	public ArrayList<Integer> getArrExercised()
	{
		return arrExercised;
	}
	
	/**
	 * Gets the arr cancelled.
	 *
	 * @return the arr cancelled
	 */
	public ArrayList<Integer> getArrCancelled()
	{
		return arrCancelled;
	}
	
	/**
	 * Gets the arr disabled.
	 *
	 * @return the arr disabled
	 */
	public ArrayList<Float> getArrDisabled()
	{
		return arrDisabled;
	}
	
	/**
	 * Gets the median exercised.
	 *
	 * @return the median exercised
	 */
	public float getMedianExercised()
	{
		return medianExercised;
	}
	
	/**
	 * Gets the median cancelled.
	 *
	 * @return the median cancelled
	 */
	public float getMedianCancelled()
	{
		return medianCancelled;
	}
	
	/**
	 * Gets the median disabled.
	 *
	 * @return the median disabled
	 */
	public float getMedianDisabled()
	{
		return medianDisabled;
	}
	
	/**
	 * Gets the deviation exercised.
	 *
	 * @return the deviation exercised
	 */
	public float getDeviationExercised()
	{
		return deviationExercised;
	}
	
	/**
	 * Gets the deviation cancelled.
	 *
	 * @return the deviation cancelled
	 */
	public float getDeviationCancelled()
	{
		return deviationCancelled;
	}
	
	/**
	 * Gets the deviation disabled.
	 *
	 * @return the deviation disabled
	 */
	public float getDeviationDisabled()
	{
		return deviationDisabled;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
    public String toString()
    {
		return "Median exercised: " + medianExercised + "\nMedian cancelled: " + medianCancelled + "\nMedian disabled: " + medianDisabled + 
				"\nDeviation excersied: " + deviationExercised + "\nDeviation cancelled: " + deviationCancelled + "\nDeviation disabled: " + deviationDisabled;
    }
}
