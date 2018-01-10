package CPS_Clients.Controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import CPS_Utilities.Consts;
import CPS_Utilities.DialogBuilder;
import clientServerCPS.RequestResult;
import clientServerCPS.RequestsSender;
import clientServerCPS.ServerResponse;
import entities.AddRealTimeParkingRequest;
import entities.FullMembership;
import entities.PartialMembership;
import entities.Reservation;
import entities.enums.ReservationStatus;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Dialog;
import javafx.scene.control.Alert.AlertType;

public class KioskEntryController extends BaseController
{
    private ArrayList<String> PreOrderInputs = new ArrayList<>();
    
    private ArrayList<String> MemberInputs = new ArrayList<>();
    
    private ArrayList<String> subscriptionTypes = new ArrayList<>();
    
    private String parkinglot;
    
    public KioskEntryController() throws IOException
    {
	super();
	
	PreOrderInputs.add("Order id:");
	PreOrderInputs.add("Car number:");
	
	MemberInputs.add("Subscription id:");
	MemberInputs.add("Car number:");
	
	subscriptionTypes.add(Consts.FullMembership);
	subscriptionTypes.add(Consts.PartialMembership);
	
	parkinglot = new BufferedReader(
		new InputStreamReader(getClass().getResourceAsStream(Consts.ParkinglotNamePathFromController)))
			.readLine();
    }
    
    @FXML
    void OnGuestEntry(ActionEvent event) throws IOException, URISyntaxException
    {
	myControllersManager.SetScene(Consts.GuestEntry, Consts.KioskEntry);
    }
    
    @FXML
    void OnPreOrderEntry(ActionEvent event)
    {
	Dialog<List<String>> dialog = DialogBuilder.InputsDialog(Consts.FillRequest, PreOrderInputs, Consts.Submit);
	
	Optional<List<String>> result = dialog.showAndWait();
	
	result.ifPresent(inputs ->
	{
	    ServerResponse<Reservation> reservationResponse = RequestsSender.GetReservation(inputs.get(0));
	    
	    Reservation reservation = reservationResponse.GetResponseObject();
	    
	    if (reservationResponse.GetRequestResult().equals(RequestResult.Failed))
	    {
		DialogBuilder.AlertDialog(AlertType.ERROR, null, Consts.ServerProblemMessage, null, false);
		return;
	    }
	    
	    if (reservationResponse.GetRequestResult().equals(RequestResult.NotFound)
		    || !inputs.get(1).equals(reservationResponse.GetResponseObject().getCarNumber())
		    || !reservationResponse.GetResponseObject().getReservationStatus()
			    .equals(ReservationStatus.NotStarted))
	    {
		DialogBuilder.AlertDialog(AlertType.ERROR, null, "No reservation with these details was found.", null,
			false);
		return;
	    }
	    
	    if (reservationResponse.GetRequestResult().equals(RequestResult.Succeed))
	    {
		if (!reservation.getArrivalDate().equals(LocalDate.now())
			|| !reservation.getArrivalHour().isAfter(LocalTime.now()))
		{
		    DialogBuilder.AlertDialog(AlertType.ERROR, null,
			    "You have a reservation, but not for now. \nYou can check your reservation details on the main page.",
			    null, false);
		    return;
		}
	    }
	    
	    if (reservationResponse.GetRequestResult().equals(RequestResult.Succeed)
		    && inputs.get(1).equals(reservationResponse.GetResponseObject().getCarNumber()))
	    {
		AddRealTimeParkingRequest request = new AddRealTimeParkingRequest(parkinglot, LocalDateTime.now(),
			LocalDateTime.of(reservation.getLeavingDate(), reservation.getLeavingHour()),
			reservation.getCarNumber(), false);
		
		ServerResponse<AddRealTimeParkingRequest> insertResponse = RequestsSender.TryInsertCar(request);
		
		if (insertResponse.GetRequestResult().equals(RequestResult.Succeed))
		{
		    
		    DialogBuilder.AlertDialog(AlertType.INFORMATION, Consts.Approved, Consts.LeaveTheCarMessage, null,
			    false);
		    
		    myControllersManager.GoToHomePage(Consts.KioskEntry);
		}
		else
		{
		    DialogBuilder.AlertDialog(AlertType.ERROR, null, Consts.ServerProblemMessage, null, false);
		    return;
		}
	    }
	});
    }
    
    @FXML
    void OnMemberEntry(ActionEvent event)
    {
	String buttonResult = DialogBuilder.AlertDialog(AlertType.NONE, "Register", "Please choose a subscription type",
		subscriptionTypes, true);
	
	switch (buttonResult)
	{
	case Consts.FullMembership:
	    HandleFullMember();
	    break;
	
	case Consts.PartialMembership:
	    HandlePartialMember();
	    break;
	
	default:
	    break;
	}
    }
    
    @FXML
    void OnBack(ActionEvent event)
    {
	myControllersManager.Back(PreviousScene, Consts.KioskEntry);
    }
    
    private void HandleFullMember()
    {
	Dialog<List<String>> dialog = DialogBuilder.InputsDialog(Consts.FillRequest, MemberInputs, Consts.Submit);
	
	Optional<List<String>> result = dialog.showAndWait();
	
	result.ifPresent(inputs ->
	{
	    ServerResponse<FullMembership> fullMmembershipResponse = RequestsSender.GetFullMembership(inputs.get(0));
	    
	    FullMembership fullMembership = fullMmembershipResponse.GetResponseObject();
	    
	    if (fullMmembershipResponse.GetRequestResult().equals(RequestResult.Failed))
	    
	    {
		DialogBuilder.AlertDialog(AlertType.ERROR, null, Consts.ServerProblemMessage, null, false);
		return;
	    }
	    
	    if (fullMmembershipResponse.GetRequestResult().equals(RequestResult.NotFound)
		    || !fullMmembershipResponse.GetResponseObject().GetCarNumber().equals(inputs.get(1)))
	    
	    {
		DialogBuilder.AlertDialog(AlertType.ERROR, null, "No membership with these details was found.", null,
			false);
		return;
	    }
	    
	    if (fullMmembershipResponse.GetRequestResult().equals(RequestResult.Succeed)
		    && LocalDate.now().isAfter(fullMembership.getExpiryDate()))
	    {
		DialogBuilder.AlertDialog(AlertType.ERROR, null,
			"Your membership has expired. \nYou can renew on the main page -> Monitor and Control.", null,
			false);
		return;
	    }
	    
	    if (fullMmembershipResponse.GetRequestResult().equals(RequestResult.Succeed))
	    {
		LocalDateTime expiry = LocalDateTime.of(fullMembership.getExpiryDate(), LocalTime.parse("00:00"));
		
		LocalDateTime parkingExitDateTime = LocalDateTime.now().plusDays(14).isAfter(expiry) ? expiry
			: LocalDateTime.now().plusDays(14);
		
		AddRealTimeParkingRequest request = new AddRealTimeParkingRequest(parkinglot, LocalDateTime.now(),
			parkingExitDateTime, fullMembership.GetCarNumber(), false);
		
		ServerResponse<AddRealTimeParkingRequest> insertResponse = RequestsSender.TryInsertCar(request);
		
		if (insertResponse.GetRequestResult().equals(RequestResult.Succeed))
		{
		    
		    DialogBuilder.AlertDialog(AlertType.INFORMATION, Consts.Approved, Consts.LeaveTheCarMessage, null,
			    false);
		    
		    myControllersManager.GoToHomePage(Consts.KioskEntry);
		}
		else if (insertResponse.GetRequestResult().equals(RequestResult.AlredyExist))
		{
		    DialogBuilder.AlertDialog(AlertType.ERROR, null, "Your car is already in the parking lot.", null,
			    false);
		    return;
		}
		else
		{
		    DialogBuilder.AlertDialog(AlertType.ERROR, null, Consts.ServerProblemMessage, null, false);
		    return;
		}
	    }
	});
    }
    
    private void HandlePartialMember()
    {
	Dialog<List<String>> dialog = DialogBuilder.InputsDialog(Consts.FillRequest, MemberInputs, Consts.Submit);
	
	Optional<List<String>> result = dialog.showAndWait();
	
	result.ifPresent(inputs ->
	{
	    ServerResponse<PartialMembership> partialMembershipResponse = RequestsSender
		    .GetPartialMembership(inputs.get(0));
	    
	    String carNumber = inputs.get(1);
	    
	    PartialMembership partialMembership = partialMembershipResponse.GetResponseObject();
	    
	    if (partialMembershipResponse.GetRequestResult().equals(RequestResult.Failed))
	    
	    {
		DialogBuilder.AlertDialog(AlertType.ERROR, null, Consts.ServerProblemMessage, null, false);
		return;
	    }
	    
	    if (partialMembershipResponse.GetRequestResult().equals(RequestResult.NotFound)
		    || (partialMembershipResponse.GetRequestResult().equals(RequestResult.Succeed)
			    && !partialMembership.GetCarList().contains(carNumber)))
	    
	    {
		DialogBuilder.AlertDialog(AlertType.ERROR, null, "No membership with these details was found.", null,
			false);
		return;
	    }
	    
	    if (partialMembershipResponse.GetRequestResult().equals(RequestResult.Succeed)
		    && LocalDate.now().isAfter(partialMembership.getExpiryDate()))
	    {
		DialogBuilder.AlertDialog(AlertType.ERROR, null,
			"Your membership has expired. \nYou can renew on the main page -> Monitor and Control.", null,
			false);
		return;
	    }
	    
	    if (partialMembershipResponse.GetRequestResult().equals(RequestResult.Succeed))
	    {
		AddRealTimeParkingRequest request = new AddRealTimeParkingRequest(parkinglot, LocalDateTime.now(),
			LocalDateTime.of(LocalDate.now(), partialMembership.GetExitTime()), inputs.get(1), false);
		
		ServerResponse<AddRealTimeParkingRequest> insertResponse = RequestsSender.TryInsertCar(request);
		
		if (insertResponse.GetRequestResult().equals(RequestResult.Succeed))
		{
		    
		    DialogBuilder.AlertDialog(AlertType.INFORMATION, Consts.Approved, Consts.LeaveTheCarMessage, null,
			    false);
		    
		    myControllersManager.GoToHomePage(Consts.KioskEntry);
		}
		else if (insertResponse.GetRequestResult().equals(RequestResult.AlredyExist))
		{
		    DialogBuilder.AlertDialog(AlertType.ERROR, null, "Your car is already in the parking lot.", null,
			    false);
		    return;
		}
		else
		{
		    DialogBuilder.AlertDialog(AlertType.ERROR, null, Consts.ServerProblemMessage, null, false);
		    return;
		}
	    }
	});
    }
}
