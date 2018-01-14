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
     * On member.
     *
     * @param event the event
     */
    @FXML
    void OnMember(ActionEvent event)
    {
    	myControllersManager.SetScene(Consts.MonitorAndControllMember, Consts.MonitorAndControll);
    }
    
    /**
     * On not A member.
     *
     * @param event the event
     */
    @FXML
    void OnNotAMember(ActionEvent event)
    {
    	myControllersManager.SetScene(Consts.MonitorAndControllNotMember, Consts.MonitorAndControll);
    }
    
    /**
     * On customer info.
     *
     * @param event the event
     */
    @FXML
    void OnCustomerInfo(ActionEvent event)
    {
	myControllersManager.SetScene(Consts.MonitorAndControlCustomer, Consts.MonitorAndControll);
    }
    
    /**
     * On back.
     *
     * @param event the event
     */
    @FXML
    void OnBack(ActionEvent event)
    {
	myControllersManager.Back(PreviousScene, Consts.MonitorAndControll);
    }
}
