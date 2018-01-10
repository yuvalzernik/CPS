package CPS_Clients.Controllers.Employee;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import CPS_Clients.ConstsEmployees;
import CPS_Utilities.Consts;
import CPS_Utilities.DialogBuilder;
import clientServerCPS.RequestResult;
import clientServerCPS.RequestsSender;
import clientServerCPS.ServerResponse;
import entities.ChangeParkingSpotStatusRequest;
import entities.ChangeParkinglotStatusRequest;
import entities.ChangeRatesRequest;
import entities.ParkingSpot;
import entities.Parkinglot;
import entities.enums.ParkingSpotStatus;
import entities.enums.ParkinglotStatus;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;

public class ManagerLoginController extends EmployeeBaseController{
	private ArrayList<String> DisableParkingSpotInputs = new ArrayList<>();
	private ArrayList<String> RequestUpdatePricesInputs = new ArrayList<>();
	private ArrayList<String> InitializeParkingSpotInputs = new ArrayList<>();
	public ManagerLoginController()
	{
		super();
		DisableParkingSpotInputs.add("Parking spot width:");
		DisableParkingSpotInputs.add("Parking spot height:");
		DisableParkingSpotInputs.add("Parking spot depth:");
		
		InitializeParkingSpotInputs.add("Parking lot width:");
		
		RequestUpdatePricesInputs.add("New guest rate:");
		RequestUpdatePricesInputs.add("New in-advance rate:");
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
	    	//parkinglotName= parkinglotName.toLowerCase();
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
    void OnRigisterDisabledParkingLot(ActionEvent event) 
    {
    	String result =DialogBuilder.AlertDialog(AlertType.CONFIRMATION,"" , ConstsEmployees.ConfirmParkingLotDisabled, null,false);
    	if (result.equals("OK"))
    	{
    		String parkinglotName=MyEmployee.getOrgAffiliation();
    		//parkinglotName= parkinglotName.toLowerCase();
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
    	myControllersManager.SetScene(ConstsEmployees.ReserveParkingSpotInLocalParkingLot, ConstsEmployees.ManagerLogin);
    }
    
    @FXML
    void OnRegisterDisabeledParkingSpot(ActionEvent event) 
    {
    	Dialog<List<String>> dialog = DialogBuilder.InputsDialog(Consts.FillRequest, DisableParkingSpotInputs, Consts.Submit);
    	Optional<List<String>> result = dialog.showAndWait();
    	result.ifPresent(inputs->
    	{
    		String parkinglotName=MyEmployee.getOrgAffiliation();
    		//parkinglotName= parkinglotName.toLowerCase();
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
    void OnRequestUpdatePrices(ActionEvent event) 
    {
    	Dialog<List<String>> dialog = DialogBuilder.InputsDialog(Consts.FillRequest, RequestUpdatePricesInputs, Consts.Submit);
    	Optional<List<String>> result = dialog.showAndWait();
    	result.ifPresent(inputs->
    	{
    	//send new prices to DB
    		ChangeRatesRequest changeRatesRequest= new ChangeRatesRequest(MyEmployee.getOrgAffiliation(),Float.parseFloat( inputs.get(0)), Float.parseFloat(inputs.get(1)));
    		ServerResponse<ChangeRatesRequest> RequestRes=RequestsSender.AddChangeRatesRequest(changeRatesRequest);
    		if(RequestRes.GetRequestResult().equals(RequestResult.Succeed))
    			DialogBuilder.AlertDialog(AlertType.INFORMATION, "", ConstsEmployees.RequestSent, null,false);
    		else 
    			DialogBuilder.AlertDialog(AlertType.ERROR, "", ConstsEmployees.RequestDoNotSent, null,false);
    	});
    }

    @FXML
    void OnProduceReport(ActionEvent event) 
    {
    	myControllersManager.SetScene(ConstsEmployees.ManagerProduceReport, ConstsEmployees.ManagerLogin);
    }
    @FXML
    void OnUndisableParkingLot(ActionEvent event)
    {
    	String result =DialogBuilder.AlertDialog(AlertType.CONFIRMATION,"" , ConstsEmployees.ConfirmParkingLotUnDisabled, null,false);
    	if (result.equals("OK"))
    	{
    		String parkinglotName=MyEmployee.getOrgAffiliation();
    		//parkinglotName= parkinglotName.toLowerCase();
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
    		//parkinglotName= parkinglotName.toLowerCase();
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
    void OnBack(ActionEvent event) 
    {
	LogOut();
	
    	myControllersManager.Back(PreviousScene,ConstsEmployees.ManagerLogin );
    }

}
