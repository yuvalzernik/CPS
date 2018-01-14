package cps.clients.controllers.employee;

import cps.utilities.ConstsEmployees;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

// TODO: Auto-generated Javadoc
/**
 * The Class CustomerServiceEnteryController.
 */
public class CustomerServiceEnteryController  extends EmployeeBaseController{

	/** The Headline. */
	@FXML
    private Label Headline;
	
    /**
     * On manage complaints.
     *
     * @param event the event
     */
    @FXML
    void OnManageComplaints(ActionEvent event) 
    {
    	myControllersManager.SetScene(ConstsEmployees.ManageComplaints, ConstsEmployees.CustomerServiceEntery);
    }

    /**
     * On save parking spot.
     *
     * @param event the event
     */
    @FXML
    void OnSaveParkingSpot(ActionEvent event) 
    {
    	myControllersManager.SetScene(ConstsEmployees.ReserveParkingSpot, ConstsEmployees.CustomerServiceEntery);

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
	
    	myControllersManager.Back(PreviousScene,ConstsEmployees.ParkingLotWorkerEntery );
    }
    
    /**
     * On manage customer.
     *
     * @param event the event
     */
    @FXML
    void OnManageCustomer(ActionEvent event) 
    {
    	myControllersManager.SetScene(ConstsEmployees.ManageCustomer, ConstsEmployees.CustomerServiceEntery);

    }
    
}
