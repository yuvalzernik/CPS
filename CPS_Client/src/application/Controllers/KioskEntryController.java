package application.Controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import application.Consts;
import application.DialogBuilder;
import application.GuestIdentifyingInformation;
import application.MemberIdentifyingInformation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Dialog;
import javafx.scene.control.Alert.AlertType;

public class KioskEntryController extends BaseController
{
    private ArrayList<String> PreOrderInputs = new ArrayList<>();
    
    private ArrayList<String> MemberInputs = new ArrayList<>();
    
    //private ArrayList<String> 
    
    private MemberIdentifyingInformation memberIdentifyingInformation;
    
    private GuestIdentifyingInformation guestIdentifyingInformation;
    
    public KioskEntryController()
    {
	super();
	
	PreOrderInputs.add("Order id:");
	PreOrderInputs.add("Car number:");
	
	MemberInputs.add("Subscription id:");
	MemberInputs.add("Car number:");
    }
    
    @FXML
    void OnGuestEntry(ActionEvent event)
    {
	System.out.println("test");
    }
    
    @FXML
    void OnPreOrderEntry(ActionEvent event)
    {
	Dialog<List<String>> dialog = DialogBuilder.InputsDialog(Consts.FillRequest, PreOrderInputs, Consts.Submit);
	
	Optional<List<String>> result = dialog.showAndWait();
	
	result.ifPresent(inputs ->
	{
	    guestIdentifyingInformation = new GuestIdentifyingInformation(inputs.get(0), inputs.get(1));
	    
	    boolean isOrderExist = CheckAndSubmitPreOrder(guestIdentifyingInformation);
		
		if (isOrderExist)
		{
		    DialogBuilder.AlertDialog(AlertType.INFORMATION, Consts.Approved, Consts.LeaveTheCarMessage, null, false);
		    
		    myControllersManager.SetScene(Consts.Kiosk, null);
		}
	});
	
	
    }
    
    @FXML
    void OnMemberEntry(ActionEvent event)
    {
	Dialog<List<String>> dialog = DialogBuilder.InputsDialog(Consts.FillRequest, MemberInputs, Consts.Submit);
	
	Optional<List<String>> result = dialog.showAndWait();
	
	result.ifPresent(inputs ->
	{
	    memberIdentifyingInformation = new MemberIdentifyingInformation(inputs.get(0), inputs.get(1));
	    
	    boolean isMemberLegal = CheckAndSubmitMember(memberIdentifyingInformation);
		
		if (isMemberLegal)
		{
		    DialogBuilder.AlertDialog(AlertType.INFORMATION, Consts.Approved, Consts.LeaveTheCarMessage, null, false);
		    
		    myControllersManager.SetScene(Consts.Kiosk, null);
		}
	});
    }
    
    @FXML
    void OnBack(ActionEvent event)
    {
	myControllersManager.Back(PreviousScene);
    }
    
    private boolean CheckAndSubmitPreOrder(GuestIdentifyingInformation guestIdentifyingInformation)
    {
	// Todo:
	// Send the obj to the server..
	
	return true;
    }
    
    private boolean CheckAndSubmitMember(MemberIdentifyingInformation memberIdentifyingInformation)
    {
	// Todo:
	// Send the obj to the server..
	
	return true;
    }
}
