package CPS_Clients.Controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import CPS_Clients.ConstsWeb;
import CPS_Utilities.Consts;
import CPS_Utilities.DialogBuilder;
import clientServerCPS.RequestResult;
import clientServerCPS.RequestsSender;
import clientServerCPS.ServerResponse;
import entities.CreditCustomerRequest;
import entities.Reservation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;

public class WebController extends BaseController
{
    
    private ArrayList<String> subscriptionTypes = new ArrayList<>();
    
    public WebController()
    {
	subscriptionTypes.add(Consts.FullMembership);
	subscriptionTypes.add(Consts.PartialMembership);
    }
    
    @FXML
    private Label Headline;
    
    @FXML
    void OnComplaines(ActionEvent event)
    {
	myControllersManager.SetScene(ConstsWeb.Complaints, ConstsWeb.Web);
    }
    
    @FXML
    void OnRegister(ActionEvent event)
    {
	String buttonResult = DialogBuilder.AlertDialog(AlertType.NONE, "Register", "Please choose a subscription type",
		subscriptionTypes, true);
	
	switch (buttonResult)
	{
	case Consts.FullMembership:
	    myControllersManager.SetScene(Consts.FullMembershipRegister, ConstsWeb.Web);
	    break;
	
	case Consts.PartialMembership:
	    myControllersManager.SetScene(Consts.PartialMembershipRegister, ConstsWeb.Web);
	    break;
	
	default:
	    break;
	}
	
    }
    
    @FXML
    void OnMonitorAndControll(ActionEvent event)
    {
	myControllersManager.SetScene(Consts.MonitorAndControll, ConstsWeb.Web);
    }
    
    @FXML
    void OnOrderInAdvance(ActionEvent event)
    {
	myControllersManager.SetScene(ConstsWeb.OrderInAdvance, ConstsWeb.Web);
	
    }
    
    @FXML
    void OnCancelOrder(ActionEvent event)
    {
	ArrayList<String> inputs = new ArrayList<>();
	
	inputs.add("Order ID:");
	
	Dialog<List<String>> dialog = DialogBuilder.InputsDialog("Cancel Order", inputs, Consts.Submit);
	
	dialog.setHeaderText("Please fill the order's ID to cancel");
	
	Optional<List<String>> result = dialog.showAndWait();
	
	result.ifPresent(reservationId ->
	{
	    ServerResponse<Reservation> serverResponse = RequestsSender.GetReservation(reservationId.get(0));
	    
	    if (serverResponse.GetRequestResult().equals(RequestResult.Failed))
	    {
		DialogBuilder.AlertDialog(AlertType.ERROR, null, Consts.ServerProblemMessage, null, false);
		return;
	    }
	    
	    if (serverResponse.GetRequestResult().equals(RequestResult.NotFound))
	    {
		DialogBuilder.AlertDialog(AlertType.ERROR, null, "Reservation not found.", null, false);
		return;
	    }
	    
	    if (serverResponse.GetRequestResult().equals(RequestResult.Succeed))
	    {
		Reservation reservation = serverResponse.GetResponseObject();
		
		LocalDateTime reservationDateTime = LocalDateTime.of(reservation.getArrivalDate(),
			reservation.getArrivalHour());
		
		if (reservationDateTime.isAfter(LocalDateTime.now().plusHours(3)))
		{
		    DialogBuilder.AlertDialog(AlertType.INFORMATION, null,
			    "Your reservation has been successfully cancelled.\nYour account got credited with 90% of the order's fee.\n"
				    + (reservation.getPrice() / 10) * 9 + "$",
			    null, false);
		    
		    CompletableFuture.runAsync(() ->
		    {
			RequestsSender.CloseReservation(reservation.getOrderId());
			RequestsSender.CreditCustomer(new CreditCustomerRequest(reservation.getCustomerId(),
				(reservation.getPrice() / 10) * 9));
		    });
		    return;
		}
		
		if (reservationDateTime.isAfter(LocalDateTime.now().plusHours(1)))
		{
		    DialogBuilder.AlertDialog(AlertType.INFORMATION, null,
			    "Your reservation has been successfully cancelled.\nYour account got credited with 50% of the order's fee.\n"
				    + reservation.getPrice() / 2 + "$",
			    null, false);
		    
		    CompletableFuture.runAsync(() ->
		    {
			RequestsSender.CloseReservation(reservation.getOrderId());
			RequestsSender.CreditCustomer(
				new CreditCustomerRequest(reservation.getCustomerId(), reservation.getPrice() / 2));
		    });
		    return;
		}
		
		else
		{
		    DialogBuilder.AlertDialog(AlertType.INFORMATION, null,
			    "We cancelled your reservation.\nUnfortunately we cant credit your account."
				    + reservation.getPrice() / 10 + "$",
			    null, false);
		    
		    CompletableFuture.runAsync(() ->
		    {
			RequestsSender.CloseReservation(reservation.getOrderId());
		    });
		    return;
		}
	    }
	});
    }
}
