package application;

import java.util.HashMap;
import java.util.List;

import application.Controllers.BaseController;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;

public class ControllersManager
{
    private Stage myStage;
    
    private HashMap<String, Scene> sceneMap = new HashMap<>();
    
    public ControllersManager(List<Pair<String, URL>> fxmlNamePathList, Stage stage) throws IOException
    {
	myStage = stage;
	
	for(Pair<String, URL> fxmlNamePath : fxmlNamePathList )
	{
	    FXMLLoader loader = new FXMLLoader(fxmlNamePath.getValue());
	    
	    Scene scene = new Scene(loader.load());
	    
	    BaseController controller = loader.getController();
	    
	    controller.SetControllerManager(this);
	    
	    sceneMap.put(fxmlNamePath.getKey(), scene);
	}
    }
    
    public void SetScene(String sceneName)
    {
	myStage.setScene(sceneMap.get(sceneName));
	myStage.show();
    }
}
