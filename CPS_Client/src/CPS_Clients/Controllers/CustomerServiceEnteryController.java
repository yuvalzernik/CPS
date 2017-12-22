package CPS_Clients.Controllers;

import CPS_Clients.ConstsEmployees;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class CustomerServiceEnteryController  extends BaseController{

    @FXML
    void OnManageComplaints(ActionEvent event) 
    {

    }

    @FXML
    void OnSaveParkingSpot(ActionEvent event) 
    {
    	
    }
    @FXML
    void OnBack(ActionEvent event) 
    {
    	myControllersManager.Back(PreviousScene,ConstsEmployees.ParkingLotWorkerEntery );
    }
}
