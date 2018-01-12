package cps.clients.controllers;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import cps.utilities.Consts;
import cps.utilities.DialogBuilder;
import cps.utilities.InputValidator;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

// TODO: Auto-generated Javadoc
/**
 * The Class PaymentController.
 */
public class PaymentController extends BaseController
{
    
    /** The three secret numbers. */
    @FXML
    private TextField threeSecretNumbers;
    
    /** The order details. */
    @FXML
    private TextArea orderDetails;
    
    /** The credit card. */
    @FXML
    private TextField creditCard;
    
    /** The payment amount. */
    @FXML
    private Label paymentAmount;
    
    /** The expiration date. */
    @FXML
    private TextField expirationDate;
    
    /** The prg bar. */
    @FXML
    private ProgressBar prgBar;
    
    /** The after payment details check. */
    private Consumer<Void> afterPaymentDetailsCheck;
    
    /**
     * Initialize.
     */
    @FXML
    void initialize()
    {
	prgBar.setVisible(false);
	orderDetails.setEditable(false);
    }
    
    /**
     * Sets the payment amount.
     *
     * @param paymentAmount the new payment amount
     */
    void setPaymentAmount(float paymentAmount)
    {
	this.paymentAmount.setText(Float.toString(paymentAmount) + "$");
    }
    
    /**
     * Sets the on submit.
     *
     * @param afterPaymentDetailsCheck the after payment details check
     */
    void SetOnSubmit(Consumer<Void> afterPaymentDetailsCheck)
    {
	this.afterPaymentDetailsCheck = afterPaymentDetailsCheck;
    }
    
    /**
     * Sets the order details.
     *
     * @param order the order
     */
    void SetOrderDetails(String order)
    {
	orderDetails.setText(order);
    }
    
    /**
     * On back.
     *
     * @param event the event
     */
    @FXML
    void OnBack(ActionEvent event)
    {
	myControllersManager.Back(PreviousScene, Consts.Payment);
    }
    
    /**
     * On submit.
     *
     * @param event the event
     */
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
    
    /**
     * Checks if is payment details accepted.
     *
     * @return true, if successful
     */
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
