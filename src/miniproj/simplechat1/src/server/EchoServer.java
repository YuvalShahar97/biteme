// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 
package server;
import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import gui.ServerPortFrameController;
import javafx.beans.value.ObservableValue;
import ocsf.server.*;

/**
 * This class overrides some of the methods in the abstract 
 * superclass in order to give more functionality to the server.
 *
 */

public class EchoServer extends AbstractServer {
    private ServerPortFrameController controller;
  //Class variables *************************************************
  
  /**
   * The default port to listen on.
   */
  final public static int DEFAULT_PORT = 5555;
  private DataBaseControl dbConnection = new DataBaseControl();
  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the echo server.
   *
   * @param port The port number to connect on.
   */
  public EchoServer(int port, ServerPortFrameController controller) {
      super(port);
      this.controller = controller;
  }
  
  @Override
  protected void clientConnected(ConnectionToClient client) {
      System.out.println("A new client is connected to the server.");
      updateClientInfo(client,true);
  }
  @Override
  protected void clientDisconnected(ConnectionToClient client) {
	  System.out.println("A client is disconnected from the server.");
	  updateClientInfo(client,false);
  }

  //Instance methods ************************************************
  
  /**
   * This method handles any messages received from the client.
   *
   * @param msg The message received from the client.
   * @param client The connection from which the message originated.
   */
  public void handleMessageFromClient(Object msg, ConnectionToClient client)
  {
    System.out.println("Message received: " + msg + " from " + client);
    this.sendToAllClients(msg);
    if (msg instanceof String) {
      String message = (String) msg;
      if (message.equals("Show") || message.equals("show")) {
        this.showOrderTableToClient(client);
      } else if (message.startsWith("update") || message.startsWith("Update")) {
        this.updateTotalPriceAndOrderAddress(msg,client);
      }
    }
  }
  private void updateClientInfo(ConnectionToClient client,boolean isConnected) {
	  String hostname = client.getInetAddress().getHostName();
      System.out.println("client hostname is: " + hostname);
      String ip = client.getInetAddress().getHostAddress();
      System.out.println("client ip address is: " + ip);
      controller.updateClientInfo(hostname,ip,isConnected);
  }

  public void showOrderTableToClient(ConnectionToClient client) {
	  String orderTable = dbConnection.getOrderTable();
      try {
        client.sendToClient(orderTable);
      } catch (IOException e) {
        e.printStackTrace();
      }
  }
  
  public void updateTotalPriceAndOrderAddress(Object msg, ConnectionToClient client) {
	  String[] parts = ((String) msg).split(";");
      if (parts.length == 4) {
        int orderNumber = Integer.parseInt(parts[1]);
        int totalPrice = Integer.parseInt(parts[2]);
        String orderAddress = parts[3];
        boolean success = dbConnection.updateOrder(orderNumber, totalPrice, orderAddress);
        try {
          if (success) {
            client.sendToClient("Order updated successfully.");
          } else {
            client.sendToClient("This order does not exist, try again!");
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
      } else {
        try {
          client.sendToClient("Invalid update command.");
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
  }
  /**
   * This method overrides the one in the superclass.  Called
   * when the server starts listening for connections.
   */
  protected void serverStarted()
  {
    System.out.println("Server listening for connections on port " + getPort());
  }
  
  /**
   * This method overrides the one in the superclass.  Called
   * when the server stops listening for connections.
   */
  protected void serverStopped()
  {
    System.out.println("Server has stopped listening for connections.");
  }
  
  //Class methods ***************************************************
  
  /**
   * This method is responsible for the creation of 
   * the server instance (there is no UI in this phase).
   *
   * @param args[0] The port number to listen on.  Defaults to 5555 
   *          if no argument is entered.
   */
//  public static void main(String[] args) 
//  {
//    int port = 0; //Port to listen on
//    try
//    {
//      port = Integer.parseInt(args[0]); //Get port from command line
//    }
//    catch(Throwable t)
//    {
//      port = DEFAULT_PORT; //Set port to 5555
//    }
//	
//    EchoServer sv = new EchoServer(port, controller);
//    
//    try 
//    {
//      sv.listen(); //Start listening for connections
//    } 
//    catch (Exception ex) 
//    {
//      System.out.println("ERROR - Could not listen for clients!");
//    }
//  }
}
//End of EchoServer class
