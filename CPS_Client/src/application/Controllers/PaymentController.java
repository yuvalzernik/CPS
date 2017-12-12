package application.Controllers;

import java.util.function.Consumer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class PaymentController extends BaseController
{
    @FXML
    private TextField threeSecretNumbers;
    
    @FXML
    private TextArea orderDetails;
    
    @FXML
    private TextField creditCard;
    
    @FXML
    private Label paymentAmount;
    
    @FXML
    private TextField expirationDate;
    
    private Consumer<Void> afterPaymentDetailsCheck;
    
    void setPaymentAmount(float paymentAmount)
    {
	this.paymentAmount.setText(Float.toString(paymentAmount) + "$");
    }
    
    void SetOnSubmit(Consumer<Void> afterPaymentDetailsCheck)
    {
	this.afterPaymentDetailsCheck = afterPaymentDetailsCheck;
    }
    
    @FXML
    void OnBack(ActionEvent event)
    {
	myControllersManager.Back(PreviousScene);
    }
    
    @FXML
    void OnSubmit(ActionEvent event)
    {
	if (IsPaymentDetailsAccepted())
	{
	    afterPaymentDetailsCheck.accept(null);
	}
	else
	{
	    // Todo : generate appropriate message
	}
    }
    
    private boolean IsPaymentDetailsAccepted()
    {
	// TODO real cehck;
	
	return true;
    }
}
