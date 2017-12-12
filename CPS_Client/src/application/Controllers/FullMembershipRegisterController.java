package application.Controllers;

import java.time.LocalDate;
import java.util.Date;
import java.util.function.Consumer;

import javax.xml.bind.Validator;

import application.Consts;
import application.DialogBuilder;
import application.InputValidator;
import application.Models.Customer;
import application.Models.FullMembership;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class FullMembershipRegisterController extends BaseController
{
    @FXML
    private DatePicker startingDatePicker;
    
    @FXML
    private TextField carNumber;
    
    @FXML
    private TextField id;
    
    @FXML
    private TextField email;
    
    FullMembership fullMembership;
    
    Customer customer;
    
    @FXML
    void initialize()
    {
	startingDatePicker.setEditable(true);
	startingDatePicker.setValue(LocalDate.now());
    }
    
    @FXML
    void OnBack(ActionEvent event)
    {
	myControllersManager.Back(PreviousScene);
    }
    
    @FXML
    void OnSubmitAndPay(ActionEvent event)
    {
	// Todo : calc amount to pay.
	float paymentAmount = 100;
	
	if (!TryConstructFullMembership())
	{
	    return;
	}
	
	Consumer<Void> afterPayment = Void ->
	{
	    DialogBuilder.AlertDialog(AlertType.INFORMATION, Consts.Approved, Consts.ThankYouForRegistering, null,
		    false);
	    
	    myControllersManager.SetScene(Consts.Kiosk, null);
	};
	
	myControllersManager.Payment(paymentAmount, afterPayment, Consts.FullMembershipRegister);
    }
    
    private boolean TryConstructFullMembership()
    {
	fullMembership = new FullMembership(id.getText(), startingDatePicker.getValue(),
		startingDatePicker.getValue().plusDays(21), carNumber.getText());
	
	customer = new Customer(id.getText(), email.getText(), 0);
	
	if(!InputValidator.FullMembership(fullMembership) || !InputValidator.Customer(customer))
	{
	    DialogBuilder.AlertDialog(AlertType.ERROR, null, Consts.InputsAreIncorrect, null,
		    false);
	    
	    return false;
	}
	
	return true;
    }
}
