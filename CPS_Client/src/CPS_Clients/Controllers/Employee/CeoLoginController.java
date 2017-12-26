package CPS_Clients.Controllers.Employee;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import CPS_Clients.ConstsEmployees;
import CPS_Utilities.Consts;
import CPS_Utilities.DialogBuilder;
import clientServerCPS.RequestResult;
import clientServerCPS.RequestsSender;
import clientServerCPS.ServerResponse;
import entities.Parkinglot;
import entities.enums.ParkinglotStatus;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Dialog;
import javafx.scene.control.Alert.AlertType;

public class CeoLoginController extends EmployeeBaseController{
	private ArrayList<String> DisableParkingSpotInputs = new ArrayList<>();
	private ArrayList<String> InitializeParkingSpotInputs = new ArrayList<>();
	public CeoLoginController()
	{
		super();
		DisableParkingSpotInputs.add("Parking Spot Number:");
		InitializeParkingSpotInputs.add("Parking Lot Name:");
		InitializeParkingSpotInputs.add("Width:");
	}
	
 
    
    @FXML
    void OnInitializeParkingLot(ActionEvent event)
    {
    	Dialog<List<String>> dialog = DialogBuilder.InputsDialog(Consts.FillRequest, InitializeParkingSpotInputs, Consts.Submit);
    	Optional<List<String>> result = dialog.showAndWait();
    	result.ifPresent(inputs->
	    {
	    	//String parkinglotName=MyEmployee.getOrgAffiliation();
	    	String parkinglotName=inputs.get(0);
	    	String s_width=inputs.get(1);
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
    void OnRigisterDisabledParkingLot(ActionEvent event) 
    {
    	String result =DialogBuilder.AlertDialog(AlertType.CONFIRMATION,"" , ConstsEmployees.ConfirmParkingLotDisabled, null,false);
    	if (result.equals("OK"))
    	{
    		//submit disabled parking lot in DB
    		DialogBuilder.AlertDialog(AlertType.INFORMATION, "", ConstsEmployees.ParkingLotDisabled, null,false);
    	}
    }

    @FXML
    void OnReserveParkingSpot(ActionEvent event) 
    {
    	/*
    	Dialog<List<String>> dialog = DialogBuilder.InputsDialog(Consts.FillRequest, DisableParkingSpotInputs, Consts.Submit);
    	Optional<List<String>> result = dialog.showAndWait();
    	result.ifPresent(inputs ->
		{
		//save in DB
		DialogBuilder.AlertDialog(AlertType.INFORMATION, "", ConstsEmployees.ParkingSpotReserved, null,false);
		});
		*/
    	myControllersManager.SetScene(ConstsEmployees.ReserveParkingSpot, ConstsEmployees.CeoLogin);
    	
    }

    @FXML
    void OnDisabeledParkingLot(ActionEvent event) 
    {
    	Dialog<List<String>> dialog = DialogBuilder.InputsDialog(Consts.FillRequest, DisableParkingSpotInputs, Consts.Submit);
    	//Optional<List<String>> result = dialog.showAndWait();
    	/////////////////check if submit was clicked
    	{
    	//save in DB
    	DialogBuilder.AlertDialog(AlertType.INFORMATION, "", ConstsEmployees.ParkingSpotDisabled, null,false);
    	}
    }

    @FXML
    void OnUpdatePrices(ActionEvent event) 
    {

    	//myTable.setItems(oRequests);
    	// myTable.setItems(oRequests);
         //table.getColumns().addAll(firstNameCol, lastNameCol, emailCol);
    	myControllersManager.SetScene(ConstsEmployees.ManageRequestRateChange, ConstsEmployees.CeoLogin);

    }

    @FXML
    void OnProduceReport(ActionEvent event) 
    {

    }
    
    @FXML
    void OnUndisableParkingLot(ActionEvent event)
    {
    	
    }
    @FXML
    void OnUndisableParkingSpot(ActionEvent event)
    {
    	
    }
    @FXML
    void OnBack(ActionEvent event) 
    {
    	myControllersManager.Back(PreviousScene,ConstsEmployees.CeoLogin );
    }

}
