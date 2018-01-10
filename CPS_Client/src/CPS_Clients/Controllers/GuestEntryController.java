package CPS_Clients.Controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import CPS_Utilities.Consts;
import CPS_Utilities.DialogBuilder;
import CPS_Utilities.InputValidator;
import clientServerCPS.RequestResult;
import clientServerCPS.RequestsSender;
import clientServerCPS.ServerResponse;
import entities.AddRealTimeParkingRequest;
import entities.Customer;
import entities.Parkinglot;
import entities.Reservation;
import entities.enums.ReservationStatus;
import entities.enums.ReservationType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class GuestEntryController extends BaseController
{
    @FXML
    private TextField carNumber;
    
    @FXML
    private TextField departureTime;
    
    @FXML
    private Label Headline;
    
    @FXML
    private TextField id;
    
    @FXML
    private TextField email;
    
    private String parkinglotName;
    
    private Parkinglot parkinglot;
    
    public GuestEntryController() throws IOException
    {
	super();
	
	parkinglotName = new BufferedReader(
		new InputStreamReader(getClass().getResourceAsStream(Consts.ParkinglotNamePathFromController)))
			.readLine();
	
	parkinglot = RequestsSender.GetParkinglot(parkinglotName).GetResponseObject();
    }
    
    @FXML
    void OnBack(ActionEvent event)
    {
	myControllersManager.Back(PreviousScene, Consts.GuestEntry);
    }
    
    @FXML
    void OnSubmit(ActionEvent event)
    {
	
	if (!IsInputLegal())
	{
	    DialogBuilder.AlertDialog(AlertType.ERROR, null, Consts.InputsAreIncorrect, null, false);
	    return;
	}
	
	Customer customer = new Customer(id.getText(), email.getText(), 0);
	
	RequestsSender.AddCustomerIfNotExists(customer);
	
	AddRealTimeParkingRequest request = new AddRealTimeParkingRequest(parkinglotName, LocalDateTime.now(),
		LocalDateTime.of(LocalDate.now(), LocalTime.parse(departureTime.getText())), carNumber.getText(), true);
	
	ServerResponse<AddRealTimeParkingRequest> insertCarResponse = RequestsSender.TryInsertCar(request);
	
	if (insertCarResponse.GetRequestResult().equals(RequestResult.ResourceNotAvaillable))
	{
	    DialogBuilder.AlertDialog(AlertType.ERROR, null, "We are sorry. \nThe parking lot is full..", null, false);
	    return;
	}
	else if (insertCarResponse.GetRequestResult().equals(RequestResult.Succeed))
	{
	    float paymentAmount = LocalDateTime.now().until(
		    LocalDateTime.of(LocalDate.now(), LocalTime.parse(departureTime.getText())), ChronoUnit.HOURS)
		    * parkinglot.getGuestRate();
	    
	    String reservationId = RequestsSender.Reservation(new Reservation(ReservationType.Local, id.getText(),
		    parkinglotName, carNumber.getText(), LocalDate.now(), LocalDate.now(), LocalTime.now(),
		    LocalTime.parse(departureTime.getText()), ReservationStatus.Fullfilled, paymentAmount))
		    .GetResponseObject().getOrderId();
	    
	    DialogBuilder.AlertDialog(AlertType.INFORMATION, Consts.Approved,
		    Consts.LeaveTheCarMessage + "\nYour order id is: " + reservationId, null, false);
	    
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
    
    private boolean IsInputLegal()
    {
	boolean result = true;
	
	if (!InputValidator.CarNumber(carNumber.getText()))
	{
	    result = false;
	    carNumber.setStyle("-fx-background-color: tomato;");
	}
	else
	{
	    carNumber.setStyle("-fx-background-color: white;");
	}
	
	if (!InputValidator.Email(email.getText()))
	{
	    result = false;
	    email.setStyle("-fx-background-color: tomato;");
	}
	else
	{
	    email.setStyle("-fx-background-color: white;");
	}
	
	if (!InputValidator.Id(id.getText()))
	{
	    result = false;
	    id.setStyle("-fx-background-color: tomato;");
	}
	else
	{
	    id.setStyle("-fx-background-color: white;");
	}
	
	if (!InputValidator.CheckHourFormat(departureTime.getText()))
	{
	    result = false;
	    departureTime.setStyle("-fx-background-color: tomato;");
	}
	else if (LocalTime.now().isAfter(LocalTime.parse(departureTime.getText())))
	{
	    result = false;
	    departureTime.setStyle("-fx-background-color: tomato;");
	}
	else
	{
	    departureTime.setStyle("-fx-background-color: white;");
	}
	
	return result;
    }
}
