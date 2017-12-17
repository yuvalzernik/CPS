/**
 * Sample Skeleton for 'MonitorAndControllNotMember.fxml' Controller Class
 */

package CPS_Clients.Controllers;

import CPS_Utilities.Consts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class MonitorAndControllNotMemberController extends BaseController{

    @FXML // fx:id="Order_ID"
    private TextField Order_ID; // Value injected by FXMLLoader

    @FXML // fx:id="TextOrderDeatiles"
    private TextArea TextOrderDeatiles; // Value injected by FXMLLoader

    @FXML
    void OnSubmit(ActionEvent event) {

    }

    @FXML
    void OnBack(ActionEvent event) {
    	myControllersManager.Back(PreviousScene,Consts.MonitorAndControllNotMember);
    }

}
 