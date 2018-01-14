package cps.clients.controllers;

import cps.utilities.Consts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

// TODO: Auto-generated Javadoc
/**
 * The Class MonitorAndControllController.
 */
public class MonitorAndControllController extends BaseController
{
    
    /** The Headline. */
    @FXML
    private Label Headline;
    
    /**
     * Client clicks on member button.
     *
     * @param event the event
     */
    @FXML
    void OnMember(ActionEvent event)
    {
    	myControllersManager.SetScene(Consts.MonitorAndControllMember, Consts.MonitorAndControll);
    }
    
    /**
     *Client clicks on not a member button.
     *
     * @param event the event
     */
    @FXML
    void OnNotAMember(ActionEvent event)
    {
    	myControllersManager.SetScene(Consts.MonitorAndControllNotMember, Consts.MonitorAndControll);
    }
    
    /**
     * Client clicks on customer info button.
     *
     * @param event the event
     */
    @FXML
    void OnCustomerInfo(ActionEvent event)
    {
	myControllersManager.SetScene(Consts.MonitorAndControlCustomer, Consts.MonitorAndControll);
    }
    
    /**
     * Client clicks on back button.
     *
     * @param event the event
     */
    @FXML
    void OnBack(ActionEvent event)
    {
	myControllersManager.Back(PreviousScene, Consts.MonitorAndControll);
    }
}
