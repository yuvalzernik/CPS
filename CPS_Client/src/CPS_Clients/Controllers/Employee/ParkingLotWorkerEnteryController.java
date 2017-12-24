package CPS_Clients.Controllers.Employee;



//import java.awt.Checkbox;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import clientServerCPS.RequestResult;
import clientServerCPS.RequestsSender;
import CPS_Clients.ConstsEmployees;
import CPS_Clients.Controllers.BaseController;
//import CPS_Clients.ConstsWeb;
import CPS_Utilities.Consts;
import CPS_Utilities.DialogBuilder;
import clientServerCPS.ServerResponse;
import entities.Parkinglot;
import entities.enums.ParkinglotStatus;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Dialog;
import javafx.scene.control.Alert.AlertType;

public class ParkingLotWorkerEnteryController extends EmployeeBaseController {
	
	private ArrayList<String> InitializeParkingSpotInputs = new ArrayList<>();
	private ArrayList<String> DisableParkingSpotInputs = new ArrayList<>();
	public ParkingLotWorkerEnteryController()
	{
		super();
		//DisableParkingSpotInputs.add("Parking Lot Name:");
		DisableParkingSpotInputs.add("Parking Spot Number:");
		
		//InitializeParkingSpotInputs.add("Parking Lot Name:");
		InitializeParkingSpotInputs.add("Parking Lot Width:");
	}
	
    @FXML
    void OnInitializeParkingLot(ActionEvent event) 
    {
    	Dialog<List<String>> dialog = DialogBuilder.InputsDialog(Consts.FillRequest, InitializeParkingSpotInputs, Consts.Submit);
    	Optional<List<String>> result = dialog.showAndWait();
    	result.ifPresent(inputs->
	    {
	    	String parkinglotName=MyEmployee.getOrgAffiliation();
	    	//String parkinglotName=inputs.get(0);
	    	String s_width=inputs.get(0);
	    	int width=Integer.parseInt(s_width);
	    	if (width>=4 && width<=8) 
	    	{
				Parkinglot parkinglot=new Parkinglot(parkinglotName, width, ParkinglotStatus.Open, 5, 4);
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
    		ServerResponse<ArrayList<Parkinglot>> ParkinglotsRes= RequestsSender.GetAllParkinglots();
    		ArrayList<Parkinglot> Parkinglots = ParkinglotsRes.GetResponseObject();
    		//submit disabled parking lot in DB
    		DialogBuilder.AlertDialog(AlertType.INFORMATION, "", ConstsEmployees.ParkingLotDisabled, null,false);
    	}
    }

    @FXML
    void OnReserveParkingSpot(ActionEvent event) 
    {
    	Dialog<List<String>> dialog = DialogBuilder.InputsDialog(Consts.FillRequest, DisableParkingSpotInputs, Consts.Submit);
    	Optional<List<String>> result = dialog.showAndWait();
    	result.ifPresent(inputs->
	    {
	    //check if not already reserved or disabeled or exist or 
		//save in DB
		DialogBuilder.AlertDialog(AlertType.INFORMATION, "", ConstsEmployees.ParkingSpotReserved, null,false);
	    });
    	
    }

    @FXML
    void OnRegisterDisabeledParkingSpot(ActionEvent event) 
    {
    	Dialog<List<String>> dialog = DialogBuilder.InputsDialog(Consts.FillRequest, DisableParkingSpotInputs, Consts.Submit);
    	Optional<List<String>> result = dialog.showAndWait();
    	/////////////////check if submit was clicked
    	{
    	//save in DB
    	DialogBuilder.AlertDialog(AlertType.INFORMATION, "", ConstsEmployees.ParkingSpotDisabled, null,false);
    	}
    }

    @FXML
    void onBack(ActionEvent event) 
    {
    	myControllersManager.Back(PreviousScene,ConstsEmployees.ParkingLotWorkerEntery );
    }

}
