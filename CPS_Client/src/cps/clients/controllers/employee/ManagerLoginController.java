package cps.clients.controllers.employee;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import cps.clientServer.RequestResult;
import cps.clientServer.RequestsSender;
import cps.clientServer.ServerResponse;
import cps.entities.ChangeParkingSpotStatusRequest;
import cps.entities.ChangeParkinglotStatusRequest;
import cps.entities.ChangeRatesRequest;
import cps.entities.ParkingSpot;
import cps.entities.Parkinglot;
import cps.entities.enums.ParkingSpotStatus;
import cps.entities.enums.ParkinglotStatus;
import cps.utilities.Consts;
import cps.utilities.ConstsEmployees;
import cps.utilities.DialogBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;

// TODO: Auto-generated Javadoc
/**
 * The Class ManagerLoginController.
 */
public class ManagerLoginController extends EmployeeBaseController{
	
	/** The Disable parking spot inputs. */
	private ArrayList<String> DisableParkingSpotInputs = new ArrayList<>();
	
	/** The Request update prices inputs. */
	private ArrayList<String> RequestUpdatePricesInputs = new ArrayList<>();
	
	/** The Initialize parking spot inputs. */
	private ArrayList<String> InitializeParkingSpotInputs = new ArrayList<>();
	
	/**
	 * Instantiates a new manager login controller.
	 */
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

    /** The Headline. */
    @FXML
    private Label Headline;
    
    /**
     * On initialize parking lot.
     *
     * @param event the event
     */
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

    /**
     * On rigister disabled parking lot.
     *
     * @param event the event
     */
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

    /**
     * On reserve parking spot.
     *
     * @param event the event
     */
    @FXML
    void OnReserveParkingSpot(ActionEvent event) 
    {
    	myControllersManager.SetScene(ConstsEmployees.ReserveParkingSpotInLocalParkingLot, ConstsEmployees.ManagerLogin);
    }
    
    /**
     * On register disabeled parking spot.
     *
     * @param event the event
     */
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

    /**
     * On request update prices.
     *
     * @param event the event
     */
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

    /**
     * On produce report.
     *
     * @param event the event
     */
    @FXML
    void OnProduceReport(ActionEvent event) 
    {
    	myControllersManager.SetScene(ConstsEmployees.ManagerProduceReport, ConstsEmployees.ManagerLogin);
    }
    
    /**
     * On undisable parking lot.
     *
     * @param event the event
     */
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
    
    /**
     * On undisable parking spot.
     *
     * @param event the event
     */
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

    /**
     * On back.
     *
     * @param event the event
     */
    @FXML
    void OnBack(ActionEvent event) 
    {
	LogOut();
	
    	myControllersManager.Back(PreviousScene,ConstsEmployees.ManagerLogin );
    }

}
