package CPS_Clients.Controllers;

import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

import CPS_Utilities.Consts;
import clientServerCPS.RequestsSender;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;

public class ControllersManager
{
    private String homePage;
    
    private Stage myStage;
    
    private HashMap<String, Scene> sceneMap = new HashMap<>();
    
    private HashMap<String, BaseController> controllerMap = new HashMap<>();
    
    public ControllersManager(List<Pair<String, URL>> fxmlNamePathList, Stage stage, String homepage) throws IOException, URISyntaxException
    {
	new RequestsSender();
		
	this.homePage = homepage;
	
	myStage = stage;
	
	for (Pair<String, URL> fxmlNamePath : fxmlNamePathList)
	{
	    FXMLLoader loader = new FXMLLoader(fxmlNamePath.getValue());
	    
	    Scene scene = new Scene(loader.load());
	    
	    scene.getStylesheets().add(getClass().getResource("..\\style.css").toExternalForm());
	    
	    BaseController controller = loader.getController();
	    
	    controller.SetControllerManager(this);
	    
	    controllerMap.put(fxmlNamePath.getKey(), controller);
	    sceneMap.put(fxmlNamePath.getKey(), scene);
	}
    }
    
    private void MySetScene(String sceneName)
    {
	myStage.setScene(sceneMap.get(sceneName));
	
	myStage.setTitle(sceneName);
	
	myStage.show();
    }
    
    public void SetScene(String sceneName, String previousScene)
    {
	controllerMap.get(sceneName).SetPreviousScene(previousScene);
	
	MySetScene(sceneName);
    }
    
    public void Back(String sceneName, String currentScene)
    {
	controllerMap.get(currentScene).ClearTextFields();
	MySetScene(sceneName);
    }
    
    public void GoToHomePage(String currentScene)
    {
	if (currentScene != null)
	{
	    controllerMap.get(currentScene).ClearTextFields();
	}
	MySetScene(homePage);
    }
    
    public void Payment(Object object, float amountToPay, Consumer<Void> afterPaymentDetailsCheck, String PreviousScene)
    {
	((PaymentController) controllerMap.get(Consts.Payment)).setPaymentAmount(amountToPay);
	((PaymentController) controllerMap.get(Consts.Payment)).SetOnSubmit(afterPaymentDetailsCheck);
	((PaymentController) controllerMap.get(Consts.Payment)).SetOrderDetails(object.toString());
	
	controllerMap.get(PreviousScene).ClearTextFields();
	
	SetScene(Consts.Payment, PreviousScene);
    }
}
