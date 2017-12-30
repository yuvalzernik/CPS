package CPS_Utilities;

public class CPS_Tracer
{
    public static void TraceInformation(String message)
    {
	System.out.println();
	System.out.println("=========================================================");
	System.out.println("Information:");
	System.out.println(message);
	System.out.println("=========================================================");
	System.out.println();
    }
    
    public static void TraceError(String message)
    {
	System.out.println();
	System.out.println("=========================================================");
	System.out.println("Error:");
	System.out.println(message);
	System.out.println("=========================================================");
	System.out.println();
    }
    
    public static void TraceError(String message, Throwable e)
    {
	System.out.println();
	System.out.println("=========================================================");
	System.out.println("Error:");
	System.out.println(message);
	System.out.println("Exception: " + e);
	System.out.println("=========================================================");
	System.out.println();
    }
}
