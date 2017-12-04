package application.Controllers;

import application.ControllersManager;

public abstract class BaseController
{
    protected ControllersManager myControllersManager;
    
    public void SetControllerManager(ControllersManager controllersManager)
    {
	myControllersManager = controllersManager;
    }
}