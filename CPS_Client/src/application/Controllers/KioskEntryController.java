package application.Controllers;

import java.util.ResourceBundle;
import application.Consts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class KioskEntryController extends BaseController
{
    @FXML
    private ResourceBundle resources;
    
    @FXML
    private Button enterButton;
    
    @FXML
    void OnEnter(ActionEvent event)
    {
	myControllersManager.SetScene(Consts.TestWindow);
    }
}
