package client;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

import common.ChatIF;
import gui.ClientPortFrameController;


public class ClientUI extends Application implements ChatIF {
	  
	  /**
	   * The default port to connect on.
	   */
	  final public static int DEFAULT_PORT = 5555;
	  
	  //Instance variables **********************************************
	  
	  /**
	   * The instance of the client that created this ConsoleChat.
	   */
	  private ChatClient client;
	  
	public static void main(String args[]) throws Exception
	   {   
		 launch(args);
	  } // end main
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub				  		
		ClientPortFrameController aFrame = new ClientPortFrameController(); // create StudentFrame
		aFrame.start(primaryStage);
	}
	
	public void connect(String ip, int port ,ClientPortFrameController controller)
	{
		  try 
		    {
		      client = new ChatClient(ip, port, this);
		    } 
		    catch(IOException exception) 
		    {
		      System.out.println("Error: Can't setup connection!"
		                + " Terminating client.");
		      System.exit(1);
		    }
	}

	@Override
	public void display(String message) {
	    System.out.println(message);
	}
	

}
