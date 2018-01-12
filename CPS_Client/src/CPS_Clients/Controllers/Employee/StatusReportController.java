package CPS_Clients.Controllers.Employee;

import java.io.File;
import java.util.concurrent.CompletableFuture;

import CPS_Clients.ConstsEmployees;
import CPS_Utilities.Consts;
import CPS_Utilities.DialogBuilder;
import CPS_Utilities.Pdf_Builder;
import clientServerCPS.RequestResult;
import clientServerCPS.RequestsSender;
import clientServerCPS.ServerResponse;
import entities.StatusReport;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;

public class StatusReportController extends EmployeeBaseController
{
    
    @FXML
    private TextField directory;
    
    @FXML
    private ProgressBar prgBar;
    
    @FXML
    void OnBrowse(ActionEvent event)
    {
	DirectoryChooser chooser = new DirectoryChooser();
	
	File libDirectory = chooser.showDialog(null);
	
	if (libDirectory == null) return;
	
	directory.setText(libDirectory.getAbsolutePath());
    }
    
    @FXML
    void OnBack(ActionEvent event)
    {
	myControllersManager.Back(PreviousScene, ConstsEmployees.StatusReport);
    }
    
    @FXML
    void OnSave(ActionEvent event)
    {
	if (directory.getText().equals(""))
	{
	    DialogBuilder.AlertDialog(AlertType.ERROR, "", ConstsEmployees.ChoosePathAgain, null, false);
	    return;
	}
	
	prgBar.setVisible(true);
	
	CompletableFuture.runAsync(() ->
	{
	    ServerResponse<StatusReport> serverResponse = RequestsSender.GetStatusReport();
	    
	    Platform.runLater(() ->
	    {
		prgBar.setVisible(false);
		
		if (serverResponse.GetRequestResult().equals(RequestResult.Succeed))
		{
		    Pdf_Builder pdf = new Pdf_Builder(directory.getText());
		    
		    pdf.build(serverResponse.GetResponseObject());
		    
		    DialogBuilder.AlertDialog(AlertType.INFORMATION, "", ConstsEmployees.StatusReportSaved, null,
			    false);
		}
		else
		{
		    DialogBuilder.AlertDialog(AlertType.ERROR, "", Consts.ServerProblemMessage, null, false);
		}
		
	    });
	    
	});
	
    }
    
}
