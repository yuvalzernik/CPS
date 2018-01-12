package cps.clients.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.CompletableFuture;

import cps.clientServer.RequestResult;
import cps.clientServer.RequestsSender;
import cps.clientServer.ServerResponse;
import cps.entities.AddRealTimeParkingRequest;
import cps.entities.Customer;
import cps.entities.Parkinglot;
import cps.entities.Reservation;
import cps.entities.enums.ReservationStatus;
import cps.entities.enums.ReservationType;
import cps.utilities.Consts;
import cps.utilities.DialogBuilder;
import cps.utilities.InputValidator;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
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
    
    @FXML
    private ProgressBar prgBar;
    
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
	
	prgBar.setVisible(true);
	
	CompletableFuture.runAsync(() ->
	{
	    
	    CompletableFuture.runAsync(() ->
	    {
		RequestsSender.AddCustomerIfNotExists(customer);
	    });
	    
	    AddRealTimeParkingRequest request = new AddRealTimeParkingRequest(parkinglotName, LocalDateTime.now(),
		    LocalDateTime.of(LocalDate.now(), LocalTime.parse(departureTime.getText())), carNumber.getText(),
		    true);
	    
	    ServerResponse<AddRealTimeParkingRequest> insertCarResponse = RequestsSender.TryInsertCar(request);
	    
	    Platform.runLater(() ->
	    {
		prgBar.setVisible(false);
		
		if (insertCarResponse.GetRequestResult().equals(RequestResult.ResourceNotAvaillable))
		{
		    DialogBuilder.AlertDialog(AlertType.ERROR, null, "We are sorry. \nThe parking lot is full..", null,
			    false);
		    
		}
		else if (insertCarResponse.GetRequestResult().equals(RequestResult.Succeed))
		{
		    float paymentAmount = LocalDateTime.now().until(
			    LocalDateTime.of(LocalDate.now(), LocalTime.parse(departureTime.getText())),
			    ChronoUnit.HOURS) * parkinglot.getGuestRate();
		    
		    String reservationId = RequestsSender.Reservation(new Reservation(ReservationType.Local,
			    id.getText(), parkinglotName, carNumber.getText(), LocalDate.now(), LocalDate.now(),
			    LocalTime.now(), LocalTime.parse(departureTime.getText()), ReservationStatus.Fullfilled,
			    paymentAmount)).GetResponseObject().getOrderId();
		    
		    DialogBuilder.AlertDialog(AlertType.INFORMATION, Consts.Approved,
			    Consts.LeaveTheCarMessage + "\nYour order id is: " + reservationId, null, false);
		    
		    myControllersManager.GoToHomePage(Consts.GuestEntry);
		}
		else if (insertCarResponse.GetRequestResult().equals(RequestResult.AlredyExist))
		{
		    DialogBuilder.AlertDialog(AlertType.ERROR, null, "Your car is already in the parking lot.", null,
			    false);
		    
		}
		else
		{
		    DialogBuilder.AlertDialog(AlertType.ERROR, null, Consts.ServerProblemMessage, null, false);
		    
		}
		
	    });
	    
	});
	
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
