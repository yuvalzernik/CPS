package CPS_Clients.Controllers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.util.function.Consumer;

import CPS_Utilities.Consts;
import CPS_Utilities.DialogBuilder;
import CPS_Utilities.InputValidator;
import clientServerCPS.RequestResult;
import clientServerCPS.RequestsSender;
import clientServerCPS.ServerResponse;
import entities.Customer;
import entities.FullMembership;
import entities.Parkinglot;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class FullMembershipRegisterController extends BaseController
{
    @FXML
    private DatePicker startingDatePicker;
    
    @FXML
    private TextField carNumber;
    
    @FXML
    private Label Headline;
    
    @FXML
    private TextField id;
    
    @FXML
    private TextField email;
    
    FullMembership fullMembership;
    
    Customer customer;
    private final int rate=5;
    
    @FXML
    void initialize()
    {
	startingDatePicker.setEditable(true);
	startingDatePicker.setValue(LocalDate.now());
    }
    
    @FXML
    void OnBack(ActionEvent event)
    {
	myControllersManager.Back(PreviousScene, Consts.FullMembershipRegister);
	
    }
    
    @FXML
    void OnSubmitAndPay(ActionEvent event)
    {

	float paymentAmount = 72*rate;
	
	if (!TryConstructFullMembership())
	{
	    return;
	}
	
	Consumer<Void> afterPayment = Void ->
	{
	    ServerResponse<FullMembership> registerFullMembershipResponse = RequestsSender
		    .RegisterFullMembership(fullMembership);
	    
	    ServerResponse<Customer> AddCustomerIfNotExist = RequestsSender.AddCustomerIfNotExists(customer);
	    
	    if (registerFullMembershipResponse.GetRequestResult().equals(RequestResult.Failed)
		    || AddCustomerIfNotExist.GetRequestResult().equals(RequestResult.Failed))
	    {
		DialogBuilder.AlertDialog(AlertType.ERROR, null, Consts.ServerProblemMessage, null, false);
		
		return;
	    }
	    
	    DialogBuilder.AlertDialog(AlertType.INFORMATION, Consts.Approved, Consts.ThankYouForRegistering +"\n Your subscription ID : "+registerFullMembershipResponse.GetResponseObject().GetSubscriptionId(), null,
		    false);
	    
	    myControllersManager.GoToHomePage(Consts.Payment);
	};
	
	myControllersManager.Payment(fullMembership, paymentAmount, afterPayment, Consts.FullMembershipRegister);
    }
    
    private boolean TryConstructFullMembership()
    {
	fullMembership = new FullMembership(id.getText(), startingDatePicker.getValue(),
		startingDatePicker.getValue().plusDays(28), carNumber.getText());
	
	customer = new Customer(id.getText(), email.getText(), 0);
	
	if (!InputValidator.FullMembership(fullMembership) || !InputValidator.Customer(customer))
	{
	    DialogBuilder.AlertDialog(AlertType.ERROR, null, Consts.InputsAreIncorrect, null, false);
	    
	    return false;
	}
	
	return true;
    }
}
