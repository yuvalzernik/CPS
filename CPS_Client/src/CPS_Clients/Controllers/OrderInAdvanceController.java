package CPS_Clients.Controllers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.function.Consumer;

import CPS_Clients.ConstsWeb;
import CPS_Utilities.Consts;
import CPS_Utilities.DialogBuilder;
import CPS_Utilities.InputValidator;
import clientServerCPS.RequestResult;
import clientServerCPS.RequestsSender;
import clientServerCPS.ServerResponse;
import entities.Customer;
import entities.FullMembership;
import entities.OrderInAdvance;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class OrderInAdvanceController extends BaseController {
    @FXML // fx:id="carNumber"
    private TextField carNumber; // Value injected by FXMLLoader

    @FXML // fx:id="parkingLot"
    private TextField parkingLot; // Value injected by FXMLLoader

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


   OrderInAdvance orderInAdvance;
    
    Customer customer;
    void initialize()
    {
    	arrivalDate.setEditable(true);
    	arrivalDate.setValue(LocalDate.now());
    	leavingDate.setEditable(true);
    	leavingDate.setValue(LocalDate.now());
    }
    
    @FXML
    void OnPayment(ActionEvent event) 
    {
    	
    	//myControllersManager.SetScene(Consts.Payment, ConstsWeb.OrderInAdvance);
    	// Todo : calc amount to pay.
    	float paymentAmount = 100;
    	
    	if (!TryConstructOrderInAdvance())
    	{
    	    return;
    	}
    	
    	Consumer<Void> afterPayment = Void ->
    	{
    	    // Todo : consider sending these requests in parallel.
    	    // + what if register succeed but add customer failed ? 
    	    
    	    ServerResponse<OrderInAdvance> OrderInAdvanceResponse = RequestsSender.OrderInAdvance(orderInAdvance);
    	    
    	    ServerResponse<Customer> AddCustomerIfNotExist = RequestsSender.AddCustomerIfNotExists(customer);
    	    
    	    if (OrderInAdvanceResponse.GetRequestResult().equals(RequestResult.Failed)
    		    || AddCustomerIfNotExist.GetRequestResult().equals(RequestResult.Failed))
    	    {
    		DialogBuilder.AlertDialog(AlertType.ERROR, null, Consts.ServerProblemMessage, null, false);
    		
    		return;
    	    }
    	    
    	    DialogBuilder.AlertDialog(AlertType.INFORMATION, Consts.Approved, Consts.ThankYouForOrderInAdvance, null,
    		    false);
    	    
    	    myControllersManager.GoToHomePage(Consts.Payment);
    	};
    	
    	myControllersManager.Payment(orderInAdvance, paymentAmount, afterPayment, ConstsWeb.OrderInAdvance);
        }
   

    @FXML
    void OnBack(ActionEvent event) {
    	myControllersManager.Back(PreviousScene,ConstsWeb.OrderInAdvance);
    }
    
    
    private boolean TryConstructOrderInAdvance()
    {
    	customer = new Customer(customerId.getText(), email.getText(), 0);

    	if (!InputValidator.OrderInAdvance(customerId.getText(),carNumber.getText(),email.getText(),arrivalDate.getValue(),leavingDate.getValue(),arrivalHour.getText(),leavingHour.getText()) || !InputValidator.Customer(customer))
    	{
    	    DialogBuilder.AlertDialog(AlertType.ERROR, null, Consts.InputsAreIncorrect, null, false);
    	    
    	    return false;
    	}
	orderInAdvance = new OrderInAdvance(customerId.getText(),parkingLot.getText(),carNumber.getText(),email.getText(),arrivalDate.getValue(),leavingDate.getValue(),LocalTime.parse(arrivalHour.getText()),LocalTime.parse(leavingHour.getText()));
	customer = new Customer(customerId.getText(), email.getText(), 0);
	
	return true;
    }

}
