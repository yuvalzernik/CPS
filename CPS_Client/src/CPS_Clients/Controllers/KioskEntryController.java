package CPS_Clients.Controllers;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import CPS_Utilities.Consts;
import CPS_Utilities.DialogBuilder;
import CPS_Utilities.GuestIdentifyingInformation;
import CPS_Utilities.MemberIdentifyingInformation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Dialog;
import javafx.scene.control.Alert.AlertType;

public class KioskEntryController extends BaseController
{
    private ArrayList<String> PreOrderInputs = new ArrayList<>();
    
    private ArrayList<String> MemberInputs = new ArrayList<>();
    
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
    void OnGuestEntry(ActionEvent event) throws IOException, URISyntaxException
    {
	if (!IsParkinglotFull())
	{
	    myControllersManager.SetScene(Consts.GuestEntry, Consts.KioskEntry);
	}
	else
	{
	    DialogBuilder.AlertDialog(AlertType.ERROR, "error", "We are sorry, the parkinglot is full.", null, false);
	}
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
		DialogBuilder.AlertDialog(AlertType.INFORMATION, Consts.Approved, Consts.LeaveTheCarMessage, null,
			false);
		
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
		DialogBuilder.AlertDialog(AlertType.INFORMATION, Consts.Approved, Consts.LeaveTheCarMessage, null,
			false);
		
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
    
    private boolean IsParkinglotFull() throws IOException, URISyntaxException
    {
	// Todo:
	// check if parking is full
	
	String myParkinglotName = new String(
		Files.readAllBytes(Paths.get(getClass().getResource(Consts.ParkinglotNamePathFromController).toURI())));
	
	return false;
    }
}
