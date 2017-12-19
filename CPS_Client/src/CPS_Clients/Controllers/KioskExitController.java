package CPS_Clients.Controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import CPS_Utilities.Consts;
import CPS_Utilities.DialogBuilder;
import CPS_Utilities.GuestIdentifyingInformation;
import CPS_Utilities.MemberIdentifyingInformation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Dialog;
import javafx.scene.control.Alert.AlertType;

public class KioskExitController extends BaseController
{
    private ArrayList<String> PreOrderInputs = new ArrayList<>();
    
    private ArrayList<String> MemberInputs = new ArrayList<>();
    
    private MemberIdentifyingInformation memberIdentifyingInformation;
    
    private GuestIdentifyingInformation guestIdentifyingInformation;
    
    private float paymentAmount;
    
    public KioskExitController()
    {
	super();
	
	PreOrderInputs.add("Order id:");
	PreOrderInputs.add("Car number:");
	
	MemberInputs.add("Subscription id:");
	MemberInputs.add("Car number:");
    }
    
    @FXML
    void OnMemberExit(ActionEvent event)
    {
	Dialog<List<String>> dialog = DialogBuilder.InputsDialog(Consts.FillRequest, MemberInputs, Consts.Submit);
	
	Optional<List<String>> result = dialog.showAndWait();
	
	result.ifPresent(inputs ->
	{
	    memberIdentifyingInformation = new MemberIdentifyingInformation(inputs.get(0), inputs.get(1));
	    
	    boolean isCarExist = CheckAndExitMember(memberIdentifyingInformation);
	    
	    if (isCarExist)
	    {
		DialogBuilder.AlertDialog(AlertType.INFORMATION, Consts.Approved, Consts.LeaveTheParkinglotMessage,
			null, false);
		
		myControllersManager.SetScene(Consts.Kiosk, null);
	    }
	    
	    // Else todo
	});
    }
    
    @FXML
    void OnGuestExit(ActionEvent event)
    {
	Dialog<List<String>> dialog = DialogBuilder.InputsDialog(Consts.FillRequest, PreOrderInputs, Consts.Submit);
	
	Optional<List<String>> result = dialog.showAndWait();
	
	result.ifPresent(inputs ->
	{
	    memberIdentifyingInformation = new MemberIdentifyingInformation(inputs.get(0), inputs.get(1));
	    
	    boolean isCarExist = CheckAndExitOrder(guestIdentifyingInformation);
	    
	    // Todo : create the partial membership object and send it to db +
	    // add it to Payment parameters.
	    
	    if (isCarExist)
	    {
		Consumer<Void> afterPayment = Void ->
		{
		    DialogBuilder.AlertDialog(AlertType.INFORMATION, Consts.Approved, Consts.LeaveTheParkinglotMessage,
			    null, false);
		    
		    myControllersManager.SetScene(Consts.Kiosk, null);
		};
		
		// myControllersManager.Payment(paymentAmount, afterPayment,
		// Consts.KioskExit);
	    }
	    
	    // Else todo
	});
    }
    
    @FXML
    void OnBack(ActionEvent event)
    {
	myControllersManager.Back(PreviousScene, Consts.KioskExit);
    }
    
    private boolean CheckAndExitOrder(GuestIdentifyingInformation guestIdentifyingInformation)
    {
	// Todo:
	// Send the obj to the server and validate and get payment amount
	
	paymentAmount = 100;
	
	return true;
    }
    
    private boolean CheckAndExitMember(MemberIdentifyingInformation memberIdentifyingInformation)
    {
	// Todo:
	// Send the obj to the server and validate..
	
	return true;
    }
}
