package gui;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ClientUI;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import server.EchoServer;
import server.ServerUI;

public class ClientPortFrameController  {	
	String temp="";
	@FXML
	private Button btnExit = null;
	@FXML
	private Button btnDone = null;
	@FXML
	private Label lbllist;
	@FXML
	private Text hostnametext;
	@FXML
	private TextField iptext;
	@FXML
	private TextField portxt;
    @FXML
    private Label hostnameLabel;
    @FXML
    private Label ipLabel;
    @FXML
    private Label statusLabel;
    @FXML
	private Text statustext;
	
	ObservableList<String> list;
	
	ClientUI client;
	
	private String getport() {
		return portxt.getText();			
	}
	
	private String getIp() {
		return iptext.getText();			
	}
	
	
	public ClientPortFrameController() {
		client = new ClientUI();
	}
	
	public void initialize() {
	   
	}
	public void Done(ActionEvent event) throws Exception {
		String p;
		p=getport();
		if(p.trim().isEmpty()) {
			System.out.println("You must enter a port number");
					
		}
		else
		{
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			client.connect(getIp() ,Integer.parseInt(getport()) , this);
		}
	}

	public void start(Stage primaryStage) throws Exception {	
		Parent root = FXMLLoader.load(getClass().getResource("/gui/ClientPort.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/gui/ClientPort.css").toExternalForm());
		primaryStage.setTitle("Client");
		primaryStage.setScene(scene);
		primaryStage.show();		
	}
	
	public void getExitBtn(ActionEvent event) throws Exception {
		System.out.println("exit Academic Tool");
	    System.exit(0);			
	}

	
}