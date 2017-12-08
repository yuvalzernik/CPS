package application.Controllers;

import application.ControllersManager;

public abstract class BaseController
{
    protected ControllersManager myControllersManager;
    
    protected String PreviousScene;
    
    public void SetControllerManager(ControllersManager controllersManager)
    {
	myControllersManager = controllersManager;
    }
    
    public void SetPreviousScene(String scene)
    {
	PreviousScene = scene;
    }
}