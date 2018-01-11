package clientServerCPS;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Random;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import entities.AddRealTimeParkingRequest;
import entities.FullMembership;
import entities.PartialMembership;
import junit.framework.TestCase;
import entities.RemoveCarRequest;
import entities.Reservation;
import entities.enums.ReservationStatus;
import entities.enums.ReservationType;


public class UnitTests  extends TestCase{

	@Before
	public void setUp() throws Exception {
		new RequestsSender("127.0.0.1");
	}
	
	@Test
	public void testRealTimeParking() {
		ServerResponse<AddRealTimeParkingRequest> serverResponse = null;
		ServerResponse<RemoveCarRequest> serverGetResponse;
		RemoveCarRequest[] removeCars = new RemoveCarRequest[50];
		int count = 0;
		for (int i = 0; i < 20; i++)
		{
			String carNum = Integer.toString(new Random().nextInt(90000000) + 10000000);
		    AddRealTimeParkingRequest request = new AddRealTimeParkingRequest("Test lot", LocalDateTime.now(),
			    LocalDateTime.now().plusHours(5), carNum, true);
		    
		    serverResponse = RequestsSender.TryInsertCar(request);
		    boolean temp = ((serverResponse.GetRequestResult().equals(RequestResult.Succeed)) || (serverResponse.GetRequestResult().equals(RequestResult.ResourceNotAvaillable)) || (serverResponse.GetRequestResult().equals(RequestResult.AlredyExist)));
		    Assert.assertTrue(temp);
		    if(serverResponse.GetRequestResult().equals(RequestResult.Succeed))
		    {
		    	removeCars[i]=new RemoveCarRequest("Test lot", carNum);
		    	count++;
		    	//TODO check thats you can get the object
		    	//serverGetResponse = RequestsSender.
		    			//.GetFullMembership(serverResponse.GetResponseObject().GetSubscriptionId());		
		    		
		    	//Assert.assertEquals(serverGetResponse.GetRequestResult(), RequestResult.Succeed);
		    }	
		}
		for (int i = 0; i < count; i++)
		{
			serverGetResponse = RequestsSender.RemoveCar(removeCars[i]);
			Assert.assertEquals(serverGetResponse.GetRequestResult(), RequestResult.Succeed);
		}
	}
	
	@Test
	public void testOneTimeReservation() {
		String id = Integer.toString(new Random().nextInt(1000000) + 3000000);
		String carNum = Integer.toString(new Random().nextInt(90000000) + 10000000);
		Reservation reservation = new Reservation(ReservationType.Web, id, "Test lot", carNum, LocalDate.now().plusDays(30),
			LocalDate.now().plusDays(31), LocalTime.parse("11:11"), LocalTime.parse("11:11"), ReservationStatus.NotStarted, 20);
		
		ServerResponse<Reservation> serverResponse = RequestsSender.Reservation(reservation);
		
		Assert.assertEquals(serverResponse.GetRequestResult(), RequestResult.Succeed);
		
		ServerResponse<Reservation> serverGetResponse = RequestsSender
			.GetReservation(serverResponse.GetResponseObject().getOrderId());
		
		Assert.assertEquals(serverGetResponse.GetRequestResult(), RequestResult.Succeed);
	}
	@Test
	public void testPartialMembership(){
		String id = Integer.toString(new Random().nextInt(1000000) + 3000000);
		String carNum1 = Integer.toString(new Random().nextInt(90000000) + 10000000);
		String carNum2 = Integer.toString(new Random().nextInt(90000000) + 10000000);

		ArrayList<String> carList = new ArrayList<>();
		carList.add(carNum1);
		carList.add(carNum2);
		
		PartialMembership partialMembership = new PartialMembership(id, LocalDate.now(), LocalDate.now().plusDays(10),
			"Test lot", carList, LocalTime.now());
		
		ServerResponse<PartialMembership> serverResponse = RequestsSender.RegisterPartialMembership(partialMembership);
		
		Assert.assertEquals(serverResponse.GetRequestResult(), RequestResult.Succeed);
		
		ServerResponse<PartialMembership> serverGetRespone = RequestsSender
			.GetPartialMembership(serverResponse.GetResponseObject().GetSubscriptionId());
		
		Assert.assertEquals(serverGetRespone.GetRequestResult(), RequestResult.Succeed);		
	    }
	@Test
	public void testFullMembership() {
		String id = Integer.toString(new Random().nextInt(1000000) + 3000000);
		String carNum = Integer.toString(new Random().nextInt(90000000) + 10000000);

		FullMembership fullMembership = new FullMembership(id, LocalDate.now(), LocalDate.now().minusDays(10), carNum);
		
		ServerResponse<FullMembership> serverResponse = RequestsSender.RegisterFullMembership(fullMembership);
		
		Assert.assertEquals(serverResponse.GetRequestResult(), RequestResult.Succeed);

		ServerResponse<FullMembership> serverGetResponse = RequestsSender
			.GetFullMembership(serverResponse.GetResponseObject().GetSubscriptionId());		
		
		Assert.assertEquals(serverGetResponse.GetRequestResult(), RequestResult.Succeed);
	}
	
	
}
