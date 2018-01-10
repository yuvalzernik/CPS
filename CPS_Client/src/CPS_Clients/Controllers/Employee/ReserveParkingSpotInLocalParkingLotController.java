package CPS_Clients.Controllers.Employee;

import java.time.LocalDate;
import java.time.LocalTime;
import CPS_Clients.ConstsEmployees;
import CPS_Utilities.Consts;
import CPS_Utilities.DialogBuilder;
import CPS_Utilities.InputValidator;
import clientServerCPS.RequestResult;
import clientServerCPS.RequestsSender;
import clientServerCPS.ServerResponse;
import entities.Customer;
import entities.Reservation;
import entities.enums.ReservationStatus;
import entities.enums.ReservationType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ReserveParkingSpotInLocalParkingLotController extends EmployeeBaseController
{
    @FXML // fx:id="carNumber"
    private TextField carNumber; // Value injected by FXMLLoader
    
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
    @FXML
    private Label Headline;
    Reservation reservation;
    
    Customer customer;
    
    void initialize()
    {
	arrivalDate.setEditable(true);
	arrivalDate.setValue(LocalDate.now());
	leavingDate.setEditable(true);
	leavingDate.setValue(LocalDate.now());
    }
    
    @FXML
    void OnSubmit(ActionEvent event)
    {
	
	// float paymentAmount = 0;
	
	if (!TryConstructOrderInAdvance())
	{
	    return;
	}
	ServerResponse<Reservation> OrderInAdvanceResponse = RequestsSender.Reservation(reservation);
	ServerResponse<Customer> AddCustomerIfNotExist = RequestsSender.AddCustomerIfNotExists(customer);
	
	if (OrderInAdvanceResponse.GetRequestResult().equals(RequestResult.Failed)
		|| AddCustomerIfNotExist.GetRequestResult().equals(RequestResult.Failed))
	{
	    DialogBuilder.AlertDialog(AlertType.ERROR, null, Consts.ServerProblemMessage, null, false);
	    return;
	}
	
	DialogBuilder.AlertDialog(AlertType.INFORMATION, Consts.Approved, ConstsEmployees.ReservationSubmitted, null,
		false);
	myControllersManager.Back(PreviousScene, ConstsEmployees.ReserveParkingSpot);
	
    }
    
    @FXML
    void OnBack(ActionEvent event)
    {
	myControllersManager.Back(PreviousScene, ConstsEmployees.ReserveParkingSpot);
    }
    
    private boolean TryConstructOrderInAdvance()
    {
	
	customer = new Customer(customerId.getText(), email.getText(), 0);
	
	if (!InputValidator.OrderInAdvance(carNumber.getText(), arrivalDate.getValue(), leavingDate.getValue(),
		arrivalHour.getText(), leavingHour.getText()) || !InputValidator.Customer(customer))
	{
	    DialogBuilder.AlertDialog(AlertType.ERROR, null, Consts.InputsAreIncorrect, null, false);
	    
	    return false;
	}
	
	reservation = new Reservation(ReservationType.Employee, customerId.getText(), MyEmployee.getOrgAffiliation(),
		carNumber.getText(), arrivalDate.getValue(), leavingDate.getValue(),
		LocalTime.parse(arrivalHour.getText()), LocalTime.parse(leavingHour.getText()),
		ReservationStatus.NotStarted, 0);
	
	customer = new Customer(customerId.getText(), email.getText(), 0);
	
	return true;
    }
    
}
