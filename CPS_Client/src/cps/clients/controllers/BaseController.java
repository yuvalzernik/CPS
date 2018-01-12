package cps.clients.controllers;

import java.lang.reflect.Field;

import cps.utilities.DialogBuilder;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

// TODO: Auto-generated Javadoc
/**
 * The Class BaseController.
 */
public abstract class BaseController
{
    
    /** The my controllers manager. */
    protected ControllersManager myControllersManager;
    
    /** The Previous scene. */
    protected String PreviousScene;
    
    /**
     * Clear text fields.
     */
    void ClearTextFields()
    {
	try
	{
	    Field[] fields = getClass().getDeclaredFields();
	    for (Field f : fields)
	    {
		f.setAccessible(true);
		if (f.getType().equals(TextField.class))
		{
		    ((TextField) f.get(this)).clear();
		    ((TextField) f.get(this)).setStyle("-fx-background-color: white;");
		}
		else if(f.getType().equals(TextArea.class)) {
			((TextArea) f.get(this)).clear();
		}
	    }
	}
	catch (Exception e)
	{
	    e.printStackTrace();
	    System.out.println(e);
	    DialogBuilder.AlertDialog(AlertType.ERROR, "Error", "Caught exception while trying to clear fields", null,
		    false);
	}
    }
    
    /**
     * Sets the controller manager.
     *
     * @param controllersManager the controllers manager
     */
    public void SetControllerManager(ControllersManager controllersManager)
    {
	myControllersManager = controllersManager;
    }
    
    /**
     * Sets the previous scene.
     *
     * @param scene the scene
     */
    public void SetPreviousScene(String scene)
    {
	PreviousScene = scene;
    }
}