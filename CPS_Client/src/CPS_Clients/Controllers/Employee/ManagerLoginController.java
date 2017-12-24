package CPS_Clients.Controllers.Employee;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import CPS_Clients.ConstsEmployees;
import CPS_Clients.Controllers.BaseController;
import CPS_Utilities.Consts;
import CPS_Utilities.DialogBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Dialog;
import javafx.scene.control.Alert.AlertType;

public class ManagerLoginController extends EmployeeBaseController{
	private ArrayList<String> DisableParkingSpotInputs = new ArrayList<>();
	private ArrayList<String> RequestUpdatePricesInputs = new ArrayList<>();
	public ManagerLoginController()
	{
		super();
		DisableParkingSpotInputs.add("Parking Spot Number:");
		RequestUpdatePricesInputs.add("New guest rate:");
		RequestUpdatePricesInputs.add("New in advance rate:");
	}
	
    @FXML
    void OnInitializeParkingLot(ActionEvent event)
    {
    	//Check if empty(first time?)
    	//initialize. do we have to insert here size?
    	DialogBuilder.AlertDialog(AlertType.INFORMATION, "", ConstsEmployees.ParkingLotWasinitialized, null,false);
    	
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
    	Dialog<List<String>> dialog = DialogBuilder.InputsDialog(Consts.FillRequest, DisableParkingSpotInputs, Consts.Submit);
    	Optional<List<String>> result = dialog.showAndWait();
		/////////////////check if submit was clicked
		{
		//save in DB
		DialogBuilder.AlertDialog(AlertType.INFORMATION, "", ConstsEmployees.ParkingSpotReserved, null,false);
		}
    }

    @FXML
    void OnDisabeledParkingLot(ActionEvent event) 
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
    void OnRequestUpdatePrices(ActionEvent event) 
    {
    	Dialog<List<String>> dialog = DialogBuilder.InputsDialog(Consts.FillRequest, RequestUpdatePricesInputs, Consts.Submit);
    	Optional<List<String>> result = dialog.showAndWait();
    	result.ifPresent(inputs->
    	{
    	//send new prices to DB
    			
    		DialogBuilder.AlertDialog(AlertType.INFORMATION, "", ConstsEmployees.RequestSent, null,false);		
    	});
    }

    @FXML
    void OnProduceReport(ActionEvent event) 
    {

    }

    @FXML
    void OnBack(ActionEvent event) 
    {
    	myControllersManager.Back(PreviousScene,ConstsEmployees.ManagerLogin );
    }

}
