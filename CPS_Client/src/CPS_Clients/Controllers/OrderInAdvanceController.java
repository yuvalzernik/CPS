package CPS_Clients.Controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.function.Consumer;
import CPS_Clients.ConstsWeb;
import CPS_Utilities.Consts;
import CPS_Utilities.DialogBuilder;
import CPS_Utilities.InputValidator;
import clientServerCPS.RequestResult;
import clientServerCPS.RequestsSender;
import clientServerCPS.ServerResponse;
import entities.Customer;
import entities.Parkinglot;
import entities.Reservation;
import entities.enums.ReservationStatus;
import entities.enums.ReservationType;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.MenuButton;

public class OrderInAdvanceController extends BaseController
{
    @FXML // fx:id="carNumber"
    private TextField carNumber; // Value injected by FXMLLoader
    
    @FXML // fx:id="parkingLot"
    private MenuButton parkingLot; // Value injected by FXMLLoader
    
    @FXML
    private Label Headline;
    
    @FXML // fx:id="arrivalHour"
    private TextField arrivalHour; // Value injected by FXMLLoader
    
    @FXML // fx:id="leavingHour"
    private TextField leavingHour; // Value injected by FXMLLoader
    
    @FXML // fx:id="customerId"
    private TextField customerId; // Value injected by FXMLLoader
    
    @FXML // fx:id="email"
    private TextField email; // Value injected by FXMLLoader
    
    @FXML // fx:id="arrivalDate"
    private DatePicker arrivalDate; // Value injected by FXMLLoader
    
    @FXML // fx:id="leavingDate"
    private DatePicker leavingDate; // Value injected by FXMLLoader
    
    ArrayList<Parkinglot> parkinglist = new ArrayList<Parkinglot>();
    String parking_Lot;
    
    Reservation reservation;
    
    Customer customer;
    
    @FXML
    void initialize()
    {
	arrivalDate.setEditable(true);
	arrivalDate.setValue(LocalDate.now());
	leavingDate.setEditable(true);
	leavingDate.setValue(LocalDate.now());
	ServerResponse<ArrayList<Parkinglot>> initListParkinglot = RequestsSender.GetAllParkinglots();
	int length = initListParkinglot.GetResponseObject().size();
	for (int i = 0; i < length; i++)
	{
	    MenuItem item = new MenuItem(initListParkinglot.GetResponseObject().get(i).getParkinglotName());
	    item.setOnAction(a ->
	    {
		parking_Lot = (item.getText());
		parkingLot.setText(parking_Lot);
	    });
	    parkingLot.getItems().add(item);
	    parkinglist.add(initListParkinglot.GetResponseObject().get(i));
	}
    }
    
    @FXML
    void OnPayment(ActionEvent event)
    {
	
	if (!TryConstructOrderInAdvance())
	{
	    return;
	}
		
	Consumer<Void> afterPayment = Void ->
	{
	    ServerResponse<Reservation> OrderInAdvanceResponse = RequestsSender.Reservation(reservation);
	    
	    ServerResponse<Customer> AddCustomerIfNotExist = RequestsSender.AddCustomerIfNotExists(customer);
	    
	    Platform.runLater(() ->
	    {
		if (OrderInAdvanceResponse.GetRequestResult().equals(RequestResult.Failed)
			|| AddCustomerIfNotExist.GetRequestResult().equals(RequestResult.Failed))
		{
		    DialogBuilder.AlertDialog(AlertType.ERROR, null, Consts.ServerProblemMessage, null, false);
		    
		    return;
		}
		
		DialogBuilder.AlertDialog(AlertType.INFORMATION, Consts.Approved, Consts.ThankYouForOrderInAdvance
			+ "\n Your order ID : " + OrderInAdvanceResponse.GetResponseObject().getOrderId(), null, false);
		
		myControllersManager.GoToHomePage(Consts.Payment);
	    });
	};
	
	myControllersManager.Payment(reservation, reservation.getPrice(), afterPayment, ConstsWeb.OrderInAdvance);
	arrivalDate.setValue(LocalDate.now());
	leavingDate.setValue(LocalDate.now());
	
    }
    
    @FXML
    void OnBack(ActionEvent event)
    {
	arrivalDate.setValue(LocalDate.now());
	leavingDate.setValue(LocalDate.now());
	myControllersManager.Back(PreviousScene, ConstsWeb.OrderInAdvance);
    }
    
    private boolean TryConstructOrderInAdvance()
    {
	
	if (!IsInputLegal())
	{
	    DialogBuilder.AlertDialog(AlertType.ERROR, null, Consts.InputsAreIncorrect, null, false);
	    
	    return false;
	}
	
	reservation = new Reservation(ReservationType.Web, customerId.getText(), parking_Lot, carNumber.getText(),
		arrivalDate.getValue(), leavingDate.getValue(), LocalTime.parse(arrivalHour.getText()),
		LocalTime.parse(leavingHour.getText()), ReservationStatus.NotStarted, AmountToPay());
	
	customer = new Customer(customerId.getText(), email.getText(), 0);
	
	return true;
    }
    
    private float AmountToPay()
    {
	float rate = 0;
	float days = (float) (Period.between(arrivalDate.getValue(), leavingDate.getValue()).getDays());
	float hours = (LocalTime.parse(leavingHour.getText()).getHour()
		- LocalTime.parse(arrivalHour.getText()).getHour());
	for (Parkinglot parkinglot : parkinglist)
	{
	    if (parkinglot.getParkinglotName().equals(parking_Lot)) rate = parkinglot.getInAdvanceRate();
	}
	return ((hours + days * 24) * rate);
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
	
	if (!InputValidator.Id(customerId.getText()))
	{
	    result = false;
	    customerId.setStyle("-fx-background-color: tomato;");
	}
	else
	{
	    customerId.setStyle("-fx-background-color: white;");
	}
	
	if (!InputValidator.StartingDate(arrivalDate.getValue()))
	{
	    result = false;
	    arrivalDate.setStyle("-fx-background-color: tomato;");
	}
	else
	{
	    arrivalDate.setStyle("-fx-background-color: white;");
	}
	
	if (!InputValidator.CheckHourFormat(arrivalHour.getText()))
	{
	    result = false;
	    arrivalHour.setStyle("-fx-background-color: tomato;");
	}
	else
	{
	    arrivalHour.setStyle("-fx-background-color: white;");
	}
	
	if (!InputValidator.StartingDate(leavingDate.getValue()))
	{
	    result = false;
	    leavingDate.setStyle("-fx-background-color: tomato;");
	}
	else
	{
	    leavingDate.setStyle("-fx-background-color: white;");
	}
	
	if (!InputValidator.CheckHourFormat(leavingHour.getText()))
	{
	    result = false;
	    leavingHour.setStyle("-fx-background-color: tomato;");
	}
	else
	{
	    leavingHour.setStyle("-fx-background-color: white;");
	}
	
	if (InputValidator.CheckHourFormat(leavingHour.getText()) && InputValidator.StartingDate(leavingDate.getValue())
		&& InputValidator.CheckHourFormat(arrivalHour.getText())
		&& InputValidator.StartingDate(arrivalDate.getValue()))
	{
	    LocalDateTime arrival = LocalDateTime.of(arrivalDate.getValue(), LocalTime.parse(arrivalHour.getText()));
	    LocalDateTime leaving = LocalDateTime.of(leavingDate.getValue(), LocalTime.parse(leavingHour.getText()));
	    
	    if (arrival.isAfter(leaving))
	    {
		result = false;
		leavingHour.setStyle("-fx-background-color: tomato;");
		leavingDate.setStyle("-fx-background-color: tomato;");
	    }
	    else
	    {
		leavingHour.setStyle("-fx-background-color: white;");
		leavingDate.setStyle("-fx-background-color: white;");
	    }
	}
	
	if (parking_Lot == null)
	{
	    result = false;
	    parkingLot.setStyle("-fx-background-color: tomato;");
	}
	else
	{
	    parkingLot.setStyle("-fx-background-color: white;");
	}
	
	return result;
    }
    
}
