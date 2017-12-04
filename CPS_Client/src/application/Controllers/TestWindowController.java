package application.Controllers;

import java.util.ResourceBundle;
import application.Consts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class TestWindowController extends BaseController
{
    @FXML
    private ResourceBundle resources;
    
    @FXML
    private Button backButton;
    
    @FXML
    void OnBack(ActionEvent event)
    {
	myControllersManager.SetScene(Consts.KioskEntry);
    }
}
