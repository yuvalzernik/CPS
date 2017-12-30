package CPS_Clients.Controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import CPS_Utilities.Consts;
import CPS_Utilities.DialogBuilder;
import CPS_Utilities.InputValidator;
import clientServerCPS.RequestResult;
import clientServerCPS.RequestsSender;
import clientServerCPS.ServerResponse;
import entities.AddRealTimeParkingRequest;
import entities.Customer;
import entities.Reservation;
import entities.enums.ReservationStatus;
import entities.enums.ReservationType;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class GuestEntryController extends BaseController
{
    
    @FXML
    private TextField carNumber;
    
    @FXML
    private TextField departureTime;
    
    @FXML
    private TextField id;
    
    @FXML
    private TextField email;
    
    private String parkinglot;
    
    public GuestEntryController() throws IOException
    {
	super();
	
	parkinglot = new BufferedReader(
		new InputStreamReader(getClass().getResourceAsStream(Consts.ParkinglotNamePathFromController)))
			.readLine();
    }
    
    @FXML
    void OnBack(ActionEvent event)
    {
	myControllersManager.Back(PreviousScene, Consts.GuestEntry);
    }
    
    @FXML
    void OnSubmit(ActionEvent event)
    {
	Customer customer = new Customer(id.getText(), email.getText(), 0);
	
	RequestsSender.AddCustomerIfNotExists(customer);
	
	if (!InputValidator.Customer(customer) || !InputValidator.CarNumber(carNumber.getText())
		|| !InputValidator.CheckHourFormat(departureTime.getText()))
	{
	    DialogBuilder.AlertDialog(AlertType.ERROR, null, Consts.InputsAreIncorrect, null, false);
	    
	    return;
	}
	
	LocalTime exiTime = LocalTime.parse(departureTime.getText());
	
	if (LocalTime.now().isAfter(exiTime))
	{
	    DialogBuilder.AlertDialog(AlertType.ERROR, null, "Please check your departure time", null, false);
	    
	    return;
	}
	
	AddRealTimeParkingRequest request = new AddRealTimeParkingRequest(parkinglot, LocalDateTime.now(),
		LocalDateTime.of(LocalDate.now(), exiTime), carNumber.getText(), true);
	
	ServerResponse<AddRealTimeParkingRequest> insertCarResponse = RequestsSender.TryInsertCar(request);
	
	if (insertCarResponse.GetRequestResult().equals(RequestResult.ResourceNotAvaillable))
	{
	    DialogBuilder.AlertDialog(AlertType.ERROR, null, "We are sorry. \nThe parking lot is full..", null, false);
	    return;
	}
	else if (insertCarResponse.GetRequestResult().equals(RequestResult.Succeed))
	{
	    String reservationId = RequestsSender
		    .Reservation(new Reservation(ReservationType.Local, id.getText(), parkinglot, carNumber.getText(),
			    LocalDate.now(), LocalDate.now(), LocalTime.now(), exiTime, ReservationStatus.Fullfilled))
		    .GetResponseObject().getOrderId();
	    
	    DialogBuilder.AlertDialog(AlertType.INFORMATION, Consts.Approved, Consts.LeaveTheCarMessage + "\nYour order id is: " + reservationId, null, false);
	    
	    myControllersManager.GoToHomePage(Consts.GuestEntry);
	}
	else if (insertCarResponse.GetRequestResult().equals(RequestResult.AlredyExist))
	{
	    DialogBuilder.AlertDialog(AlertType.ERROR, null, "Your car is already in the parking lot.", null, false);
	    return;
	}
	else
	{
	    DialogBuilder.AlertDialog(AlertType.ERROR, null, Consts.ServerProblemMessage, null, false);
	    return;
	}
    }
    
}
