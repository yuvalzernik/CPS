package CPS_Clients.Controllers.Employee;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import clientServerCPS.RequestResult;
import clientServerCPS.RequestsSender;
import CPS_Clients.ConstsEmployees;
import CPS_Utilities.Consts;
import CPS_Utilities.DialogBuilder;
import clientServerCPS.ServerResponse;
import entities.ChangeParkingSpotStatusRequest;
import entities.ChangeParkinglotStatusRequest;
import entities.ParkingSpot;
import entities.Parkinglot;
import entities.enums.ParkingSpotStatus;
import entities.enums.ParkinglotStatus;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;

public class ParkingLotWorkerEnteryController extends EmployeeBaseController {
	
	private ArrayList<String> InitializeParkingSpotInputs = new ArrayList<>();
	private ArrayList<String> DisableParkingSpotInputs = new ArrayList<>();
	public ParkingLotWorkerEnteryController()
	{
		super();
		DisableParkingSpotInputs.add("Parking spot width:");
		DisableParkingSpotInputs.add("Parking spot height:");
		DisableParkingSpotInputs.add("Parking spot depth:");
		InitializeParkingSpotInputs.add("Parking lot width:");
	}
    @FXML
    private Label Headline;
    @FXML
    void OnInitializeParkingLot(ActionEvent event) 
    {
    	Dialog<List<String>> dialog = DialogBuilder.InputsDialog(Consts.FillRequest, InitializeParkingSpotInputs, Consts.Submit);
    	Optional<List<String>> result = dialog.showAndWait();
    	result.ifPresent(inputs->
	    {
	    	String parkinglotName=MyEmployee.getOrgAffiliation();
	    	boolean IsValid= Pattern.matches("[0-9]+",inputs.get(0)) && 4 <= Integer.parseInt(inputs.get(0)) && Integer.parseInt(inputs.get(0))<=8 ;
	    	if (IsValid) 
	    	{
				Parkinglot parkinglot=new Parkinglot(parkinglotName, Integer.parseInt(inputs.get(0)), ParkinglotStatus.Open, 5, 4);
		    	ServerResponse<Parkinglot>ParkinglotRes= RequestsSender.AddParkinglot(parkinglot);
		    	if(ParkinglotRes.GetRequestResult().equals(RequestResult.Succeed))
		        	DialogBuilder.AlertDialog(AlertType.INFORMATION, "", ConstsEmployees.ParkingLotWasinitialized, null,false);
		    	else 
		    	{
		        	DialogBuilder.AlertDialog(AlertType.ERROR, "", ConstsEmployees.ParkingLotWasAlreadyinitialized, null,false);
		        	
				}
	    	}
	    	else 
	    		DialogBuilder.AlertDialog(AlertType.ERROR, "", ConstsEmployees.ParkingLotSizeError, null,false);
    	});
    }

    @FXML
    void OnRegisterDisabeledParkingLot(ActionEvent event) 
    {
    	String result =DialogBuilder.AlertDialog(AlertType.CONFIRMATION,"" , ConstsEmployees.ConfirmParkingLotDisabled, null,false);
    	if (result.equals("OK"))
    	{
    		String parkinglotName=MyEmployee.getOrgAffiliation();
    		ServerResponse<Parkinglot>ParkinglotRes= RequestsSender.GetParkinglot(parkinglotName);
    		Parkinglot parkinglot=ParkinglotRes.GetResponseObject();
    		ParkinglotStatus status=parkinglot.getStatus();
    		if(status.equals(ParkinglotStatus.OutOfOrder))
	    		DialogBuilder.AlertDialog(AlertType.ERROR, "", ConstsEmployees.AlreadyDisabled, null,false);

    		else 
    		{
	    		ChangeParkinglotStatusRequest changeParkinglotStatusRequest = new ChangeParkinglotStatusRequest(parkinglotName, ParkinglotStatus.OutOfOrder);
	    		ServerResponse<ChangeParkinglotStatusRequest>ParkinglotDisableRes= RequestsSender.ChangeParkinglotStatus(changeParkinglotStatusRequest);
	    		    		
		    	if(ParkinglotDisableRes.GetRequestResult().equals(RequestResult.Succeed))
		    	{
	    		DialogBuilder.AlertDialog(AlertType.INFORMATION, "", ConstsEmployees.ParkingLotDisabled, null,false);
		    	}
		    	else 
		    	{
		    		DialogBuilder.AlertDialog(AlertType.ERROR, "", Consts.ServerProblemMessage, null,false);
		    	}
    		}
    	}
    }

    @FXML
    void OnReserveParkingSpot(ActionEvent event) 
    {
    	myControllersManager.SetScene(ConstsEmployees.ReserveParkingSpotInLocalParkingLot, ConstsEmployees.ParkingLotWorkerEntery);
    }

    @FXML
    void OnRegisterDisabeledParkingSpot(ActionEvent event) 
    {
    	Dialog<List<String>> dialog = DialogBuilder.InputsDialog(Consts.FillRequest, DisableParkingSpotInputs, Consts.Submit);
    	Optional<List<String>> result = dialog.showAndWait();
    	result.ifPresent(inputs->
    	{
    		String parkinglotName=MyEmployee.getOrgAffiliation();
    		ServerResponse<Parkinglot>ParkinglotRes= RequestsSender.GetParkinglot(parkinglotName);
    		Parkinglot parkinglot=ParkinglotRes.GetResponseObject();
    		ParkinglotStatus status=parkinglot.getStatus();
    		if(status.equals(ParkinglotStatus.OutOfOrder))
	    		DialogBuilder.AlertDialog(AlertType.ERROR, "", ConstsEmployees.ParkingLotIsDisabled, null,false);
    		else 
    		{
    			boolean temp1= Pattern.matches("[0-9]+", inputs.get(0)) && parkinglot.getWidth() >= Integer.parseInt(inputs.get(0)) && 0<Integer.parseInt(inputs.get(0));
    			boolean temp2= Pattern.matches("[0-9]+", inputs.get(1)) && parkinglot.getHeight() >= Integer.parseInt(inputs.get(1)) && 0<Integer.parseInt(inputs.get(1));
    			boolean temp3= Pattern.matches("[0-9]+", inputs.get(2)) && parkinglot.getDepth() >= Integer.parseInt(inputs.get(2)) && 0<Integer.parseInt(inputs.get(2));
    			if (temp1 && temp2 && temp3) 
    			{
	    			ParkingSpot parkingspot= new ParkingSpot(Integer.parseInt(inputs.get(0)),Integer.parseInt(inputs.get(1)),Integer.parseInt(inputs.get(2)));
	    			ChangeParkingSpotStatusRequest changeParkingSpotStatusRequest= new ChangeParkingSpotStatusRequest(parkingspot,parkinglotName,ParkingSpotStatus.Disabled);
		    		ServerResponse<ChangeParkingSpotStatusRequest> ChangeParkingSpotStatusRes= RequestsSender.ChangeParkingSpotStatus(changeParkingSpotStatusRequest);
		    		if(ChangeParkingSpotStatusRes.GetRequestResult().equals(RequestResult.Succeed))
		    			DialogBuilder.AlertDialog(AlertType.INFORMATION, "", ConstsEmployees.ParkingSpotDisabled, null,false);
		    		else if(ChangeParkingSpotStatusRes.GetRequestResult().equals(RequestResult.NotFound))
		    			DialogBuilder.AlertDialog(AlertType.ERROR, "", ConstsEmployees.ParkingSpotAlreadyDisabled, null,false);
		    		else DialogBuilder.AlertDialog(AlertType.ERROR, "", Consts.ServerProblemMessage, null,false);

    			}
    			else DialogBuilder.AlertDialog(AlertType.ERROR, "", ConstsEmployees.FieldWrong, null,false);
    		}
    	});
    }
    @FXML
    void OnUndisableParkingLot(ActionEvent event)
    {
    	String result =DialogBuilder.AlertDialog(AlertType.CONFIRMATION,"" , ConstsEmployees.ConfirmParkingLotUnDisabled, null,false);
    	if (result.equals("OK"))
    	{
    		String parkinglotName=MyEmployee.getOrgAffiliation();
    		ServerResponse<Parkinglot>ParkinglotRes= RequestsSender.GetParkinglot(parkinglotName);
    		Parkinglot parkinglot=ParkinglotRes.GetResponseObject();
    		ParkinglotStatus status=parkinglot.getStatus();
    		if(status.equals(ParkinglotStatus.Open))
	    		DialogBuilder.AlertDialog(AlertType.ERROR, "", ConstsEmployees.Alreadyinabled, null,false);

    		else 
    		{
	    		ChangeParkinglotStatusRequest changeParkinglotStatusRequest = new ChangeParkinglotStatusRequest(parkinglotName, ParkinglotStatus.Open);
	    		ServerResponse<ChangeParkinglotStatusRequest>ParkinglotDisableRes= RequestsSender.ChangeParkinglotStatus(changeParkinglotStatusRequest);
	    		    		
		    	if(ParkinglotDisableRes.GetRequestResult().equals(RequestResult.Succeed))
		    	{
	    		DialogBuilder.AlertDialog(AlertType.INFORMATION, "", ConstsEmployees.ParkingLotIsInabled, null,false);
		    	}
		    	else 
		    	{
		    		DialogBuilder.AlertDialog(AlertType.ERROR, "", Consts.ServerProblemMessage, null,false);
		    	}
    		}
    	}
    }
    @FXML
    void OnUndisableParkingSpot(ActionEvent event)
    {
    	Dialog<List<String>> dialog = DialogBuilder.InputsDialog(Consts.FillRequest, DisableParkingSpotInputs, Consts.Submit);
    	Optional<List<String>> result = dialog.showAndWait();
    	result.ifPresent(inputs->
    	{
    		String parkinglotName=MyEmployee.getOrgAffiliation();
    		ServerResponse<Parkinglot>ParkinglotRes= RequestsSender.GetParkinglot(parkinglotName);
    		Parkinglot parkinglot=ParkinglotRes.GetResponseObject();
    		ParkinglotStatus status=parkinglot.getStatus();
    		if(status.equals(ParkinglotStatus.OutOfOrder))
	    		DialogBuilder.AlertDialog(AlertType.ERROR, "", ConstsEmployees.ParkingLotIsDisabled, null,false);
    		else 
    		{

    			boolean temp1= Pattern.matches("[0-9]+", inputs.get(0)) && parkinglot.getWidth() >= Integer.parseInt(inputs.get(0)) && 0<Integer.parseInt(inputs.get(0));
    			boolean temp2= Pattern.matches("[0-9]+", inputs.get(1)) && parkinglot.getHeight() >= Integer.parseInt(inputs.get(1)) && 0<Integer.parseInt(inputs.get(1));
    			boolean temp3= Pattern.matches("[0-9]+", inputs.get(2)) && parkinglot.getDepth() >= Integer.parseInt(inputs.get(2)) && 0<Integer.parseInt(inputs.get(2));
    			if (temp1 && temp2 && temp3) 
    			{
	    			ParkingSpot parkingspot= new ParkingSpot(Integer.parseInt(inputs.get(0)),Integer.parseInt(inputs.get(1)),Integer.parseInt(inputs.get(2)));
	        		ChangeParkingSpotStatusRequest changeParkingSpotStatusRequest= new ChangeParkingSpotStatusRequest(parkingspot,parkinglotName,ParkingSpotStatus.Active);
	        		ServerResponse<ChangeParkingSpotStatusRequest> ChangeParkingSpotStatusRes= RequestsSender.ChangeParkingSpotStatus(changeParkingSpotStatusRequest);
	        		if(ChangeParkingSpotStatusRes.GetRequestResult().equals(RequestResult.Succeed))
	        			DialogBuilder.AlertDialog(AlertType.INFORMATION, "", ConstsEmployees.parkingSpotInabled, null,false);
	        		else if(ChangeParkingSpotStatusRes.GetRequestResult().equals(RequestResult.NotFound))
					DialogBuilder.AlertDialog(AlertType.ERROR, "", ConstsEmployees.ParkingSpotAlreadyEnabled, null,false);
	        		else DialogBuilder.AlertDialog(AlertType.ERROR, "", Consts.ServerProblemMessage, null,false);
    			}
        		else DialogBuilder.AlertDialog(AlertType.ERROR, "", ConstsEmployees.FieldWrong, null,false);
    			
    		}
    	});
    }

    @FXML
    void onBack(ActionEvent event) 
    {
	LogOut();
	
    	myControllersManager.Back(PreviousScene,ConstsEmployees.ParkingLotWorkerEntery );
    }

}
