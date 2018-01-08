package CPS_Utilities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;


public class Payment_Calc {
	
	public float Guest_Payment(LocalDate arrivalDate, LocalTime arrivalHour, float parkinglotGuestRate) {
	    
	    float paymentAmount = LocalDateTime.of(arrivalDate,arrivalHour)
		    .until(LocalDateTime.now(), ChronoUnit.HOURS) * parkinglotGuestRate;
	    return paymentAmount;
	}
	
	public float FullMembershipPayment(float parkinglotPreOrderRate) {
		
		return (72*parkinglotPreOrderRate);
		
	}

	public float PartialMembershipPayment(float parkinglotPreOrderRate,int NumOfCars) {
		int hours=(NumOfCars>1?54:60);
		return ( NumOfCars * hours * parkinglotPreOrderRate);

	}
	
	public float OrderInAdvancePaymet(float parkinglotPreOrderRate, LocalDate arrivalDate,LocalDate leavingDate,LocalTime arrivalHour,LocalTime leavingHour) {
		float days = (float) (Period.between(arrivalDate, leavingDate).getDays());
		float hours = ((leavingHour).getHour()-(arrivalHour).getHour());

		return ((hours + days * 24) * parkinglotPreOrderRate);
	}

}
	
	
