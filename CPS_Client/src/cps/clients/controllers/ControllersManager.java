package cps.clients.controllers;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

import cps.clientServer.RequestsSender;
import cps.utilities.Consts;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;

// TODO: Auto-generated Javadoc
/**
 * The Class ControllersManager.
 */
public class ControllersManager
{
    
    /** The home page. */
    private String homePage;
    
    /** The my stage. */
    private Stage myStage;
    
    /** The scene map. */
    private HashMap<String, Scene> sceneMap = new HashMap<>();
    
    /** The controller map. */
    private HashMap<String, BaseController> controllerMap = new HashMap<>();
    
    /**
     * Instantiates a new controllers manager.
     *
     * @param fxmlNamePathList the fxml name path list
     * @param stage the stage
     * @param homepage the homepage
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws URISyntaxException the URI syntax exception
     */
    public ControllersManager(List<Pair<String, URL>> fxmlNamePathList, Stage stage, String homepage)
	    throws IOException, URISyntaxException
    {
	new RequestsSender();
	
	this.homePage = homepage;
	
	myStage = stage;
	
	for (Pair<String, URL> fxmlNamePath : fxmlNamePathList)
	{
	    FXMLLoader loader = new FXMLLoader(fxmlNamePath.getValue());
	    
	    Scene scene = new Scene(loader.load());
	    
	    scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
	    
	    BaseController controller = loader.getController();
	    
	    controller.SetControllerManager(this);
	    
	    controllerMap.put(fxmlNamePath.getKey(), controller);
	    sceneMap.put(fxmlNamePath.getKey(), scene);
	}
    }
    
    /**
     * My set scene.
     *
     * @param sceneName the scene name
     */
    private void MySetScene(String sceneName)
    {
	myStage.setScene(sceneMap.get(sceneName));
	
	myStage.setTitle(sceneName);
	
	myStage.show();
    }
    
    /**
     * Sets the scene.
     *
     * @param sceneName the scene name
     * @param previousScene the previous scene
     */
    public void SetScene(String sceneName, String previousScene)
    {
	controllerMap.get(sceneName).SetPreviousScene(previousScene);
	
	MySetScene(sceneName);
    }
    
    /**
     * Back.
     *
     * @param sceneName the scene name
     * @param currentScene the current scene
     */
    public void Back(String sceneName, String currentScene)
    {
	controllerMap.get(currentScene).ClearTextFields();
	MySetScene(sceneName);
    }
    
    /**
     * Go to home page.
     *
     * @param currentScene the current scene
     */
    public void GoToHomePage(String currentScene)
    {
	if (currentScene != null)
	{
	    controllerMap.get(currentScene).ClearTextFields();
	}
	MySetScene(homePage);
    }
    
    /**
     * Payment.
     *
     * @param object the object
     * @param amountToPay the amount to pay
     * @param afterPaymentDetailsCheck the after payment details check
     * @param PreviousScene the previous scene
     */
    public void Payment(Object object, float amountToPay, Consumer<Void> afterPaymentDetailsCheck, String PreviousScene)
    {
	((PaymentController) controllerMap.get(Consts.Payment)).setPaymentAmount(amountToPay);
	((PaymentController) controllerMap.get(Consts.Payment)).SetOnSubmit(afterPaymentDetailsCheck);
	((PaymentController) controllerMap.get(Consts.Payment)).SetOrderDetails(object.toString());
	
	controllerMap.get(PreviousScene).ClearTextFields();
	
	SetScene(Consts.Payment, PreviousScene);
    }
    
    /**
     * Gets the scene.
     *
     * @param name the name
     * @return the scene
     */
    public Scene getScene(String name)
    {
	if (sceneMap.containsKey(name))
	{
	    return sceneMap.get(name);
	}
	return null;
    }
    
    /**
     * Gets the stage.
     *
     * @return the stage
     */
    public Stage getStage()
    {
	return myStage;
    }
    
    /**
     * Wait crusor.
     *
     * @param scene the scene
     */
    public void WaitCrusor(String scene)
    {
	Scene currentScene = sceneMap.get(scene);
	
	currentScene.setCursor(Cursor.WAIT);
	
    }
    
    /**
     * Close wait crusor.
     *
     * @param scene the scene
     */
    public void CloseWaitCrusor(String scene)
    {
	Scene currentScene = sceneMap.get(scene);
	
	currentScene.setCursor(Cursor.DEFAULT);
    }
}
