package CPS_Clients.Controllers;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import CPS_Utilities.Consts;
import CPS_Utilities.DialogBuilder;
import CPS_Utilities.InputValidator;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
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
    
    @FXML
    private ProgressBar prgBar;
    
    private Consumer<Void> afterPaymentDetailsCheck;
    
    @FXML
    void initialize()
    {
	prgBar.setVisible(false);
	orderDetails.setEditable(false);
    }
    
    void setPaymentAmount(float paymentAmount)
    {
	this.paymentAmount.setText(Float.toString(paymentAmount) + "$");
    }
    
    void SetOnSubmit(Consumer<Void> afterPaymentDetailsCheck)
    {
	this.afterPaymentDetailsCheck = afterPaymentDetailsCheck;
    }
    
    void SetOrderDetails(String order)
    {
	orderDetails.setText(order);
    }
    
    @FXML
    void OnBack(ActionEvent event)
    {
	myControllersManager.Back(PreviousScene, Consts.Payment);
    }
    
    @FXML
    void OnSubmit(ActionEvent event)
    {
	if (IsPaymentDetailsAccepted())
	{
	    
	    prgBar.setVisible(true);
	    
	    CompletableFuture.runAsync(() ->
	    {
		afterPaymentDetailsCheck.accept(null);
		
		Platform.runLater(() ->
		{
		    prgBar.setVisible(false);
		});
	    });
	    
	}
	else
	{
	    DialogBuilder.AlertDialog(AlertType.ERROR, null, Consts.InputsAreIncorrect, null, false);
	}
    }
    
    private boolean IsPaymentDetailsAccepted()
    {
	boolean result = true;
	
	if (!InputValidator.CheckVisaDate(expirationDate.getText()))
	{
	    result = false;
	    expirationDate.setStyle("-fx-background-color: tomato;");
	}
	else
	{
	    expirationDate.setStyle("-fx-background-color: white;");
	}
	
	if (!InputValidator.CreditCardNumber(creditCard.getText()))
	{
	    result = false;
	    creditCard.setStyle("-fx-background-color: tomato;");
	}
	else
	{
	    creditCard.setStyle("-fx-background-color: white;");
	}
	
	if (!InputValidator.Ccv(threeSecretNumbers.getText()))
	{
	    result = false;
	    threeSecretNumbers.setStyle("-fx-background-color: tomato;");
	}
	else
	{
	    threeSecretNumbers.setStyle("-fx-background-color: white;");
	}
	
	return result;
    }
}
