package CPS_Utilities;

public class LoginIdentification {

	private 	String Username;
	private	String Password;
	

	public LoginIdentification(String _Username, String _Password)
	{
		Username=_Username;
		Password=_Password;
	}
	String getUsername(){return Username;};
	String getPassword(){return Password;};
}
