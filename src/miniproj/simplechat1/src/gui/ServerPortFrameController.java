package gui;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
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

public class ServerPortFrameController  {	
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
	private Text iptext;
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
	
	private String getport() {
		return portxt.getText();			
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
			//((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			ServerUI.runServer(p,this);
		}
	}

	public void start(Stage primaryStage) throws Exception {	
		Parent root = FXMLLoader.load(getClass().getResource("/gui/ServerPort.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/gui/ServerPort.css").toExternalForm());
		primaryStage.setTitle("Server");
		primaryStage.setScene(scene);
		primaryStage.show();		
	}
	
	public void getExitBtn(ActionEvent event) throws Exception {
		System.out.println("exit Academic Tool");
		System.exit(0);			
	}
	public void getDisconnectClieentBtn(ActionEvent event) throws Exception {
		
	}
	
	public void updateClientInfo(String hostname, String ip,boolean isConnected) {
		hostnametext.setText(hostname);
		iptext.setText(ip);
		updateClientStatus(isConnected);
	}
	public void updateClientStatus(boolean isconnected) {
		String connected = "client is connected";
		String disconnected = "client is disconnected";
		if (isconnected) {statustext.setText(connected);}
		else {statustext.setText(disconnected);}
	}
}