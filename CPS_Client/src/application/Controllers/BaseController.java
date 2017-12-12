package application.Controllers;

import java.lang.reflect.Field;

import application.DialogBuilder;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;

public abstract class BaseController
{
    protected ControllersManager myControllersManager;
    
    protected String PreviousScene;
    
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
		}
	    }
	}
	catch (Exception e)
	{
	    e.printStackTrace();
	    System.out.println(e);
	    DialogBuilder.AlertDialog(AlertType.ERROR, "Error", "Caught Exception while trying clear fileds", null,
		    false);
	}
    }
    
    public void SetControllerManager(ControllersManager controllersManager)
    {
	myControllersManager = controllersManager;
    }
    
    public void SetPreviousScene(String scene)
    {
	PreviousScene = scene;
    }
}