import java.util.ArrayList;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


/**
 * A class that builds a Graphical User Interface that includes functions to choose user, read messages, send message, 
 * as well as other related functions.
 * @author Isabellewn
 */

public class MessengerGUI extends Application{
	private Messenger myMessenger;
	private BorderPane root;
	private Text statusLine;
	private TabPane tabPane;
	private TextField usernameField;
	private TextArea messageDisplay;
	private Button nextButton;
	private Button loadAllButton;
	private Button loadUnreadButton;
	private ArrayList<Message> receivedMessages;
	private int messageIndex = 0;
	private TextField receiverField;
	private TextArea writeMessageField;
	private RadioButton smileButton;
	private RadioButton writtenButton;
	private SmileMessage smileMessage;
	
	public MessengerGUI() {
		myMessenger = new Messenger();
		myMessenger.addUser("Isabelle");
		myMessenger.addUser("Leon");
		myMessenger.addUser("Mike");
		myMessenger.addUser("Emily");
		myMessenger.sendMessage("Leon", "Isabelle", "Do you want to watch a movie?");
		myMessenger.sendMessage("Isabelle", "Leon", "Ok, sounds nice!");
		myMessenger.sendMessage("Mike", "Isabelle", "Do we have class tomorrow?");
		myMessenger.sendMessage("Isabelle", "Mike", "Which course will you choose for next semester?");
		myMessenger.sendMessage("Emily", "Isabelle", "Have you finished the hw?");
		myMessenger.sendMessage("Isabelle", "Emily", "Let's go to Stanley Park on Saturday!");
	}

	@Override
	public void start(Stage primaryStage) {
		root = new BorderPane();
		
		//elements for 1st tab
		statusLine = new Text("Select A User");
		statusLine.setFont(Font.font(20));
		
		usernameField = new TextField("");
		Text enterUsername = new Text("Enter Username: "); 
		Button selectButton = new Button("Select");
		HBox firstTab = new HBox(5, enterUsername, usernameField, selectButton);
		firstTab.setPadding(new Insets(70, 20, 70, 20));
		firstTab.setAlignment(Pos.CENTER);
	
		selectButton.setOnAction(new UpdateMessageStatusHandler());
		
		//elements for 2nd tab
		messageDisplay = new TextArea("No Message Displayed");
		messageDisplay.setFont(Font.font("monospace"));
		messageDisplay.setEditable(false);
		nextButton = new Button("Next");
		nextButton.setDisable(true);
		loadAllButton = new Button("Load All Messages");
		loadUnreadButton = new Button("Load Unread Messages");
		
		HBox top = new HBox(5, messageDisplay, nextButton);
		top.setAlignment(Pos.CENTER);
		top.setPadding(new Insets(5));
		HBox bottom = new HBox(5, loadAllButton, loadUnreadButton);
		bottom.setAlignment(Pos.CENTER);
		VBox seondTab = new VBox(5, top, bottom);
		
		LoadMessageHandler loadMessage = new LoadMessageHandler();
		loadAllButton.setOnAction(loadMessage);
		loadUnreadButton.setOnAction(loadMessage);
		
		//elements for 3rd tab
		Text to = new Text("To: ");
		receiverField = new TextField("");
		HBox toReceiver = new HBox(5, to, receiverField);
		toReceiver.setPadding(new Insets(5,5,0,5));
		
		writeMessageField = new TextArea();
		writeMessageField.setMaxWidth(400);
		writeMessageField.setFont(Font.font("monospace"));
		
		Text messageType = new Text("Message Type");
		ToggleGroup toggle = new ToggleGroup();
		smileButton = new RadioButton("Smile");
		writtenButton = new RadioButton("Written");
		smileButton.setToggleGroup(toggle);
		writtenButton.setToggleGroup(toggle);
		toggle.selectToggle(writtenButton);
		Button send = new Button("send");
		HBox bottomLine = new HBox(5, messageType, smileButton, writtenButton, send);
		bottomLine.setAlignment(Pos.CENTER);
		VBox thirdTab = new VBox(5, toReceiver, writeMessageField, bottomLine);
		
		SendMessageHandler sendMessageHandler = new SendMessageHandler();
		send.setOnAction(sendMessageHandler);	
		smileButton.setOnAction(sendMessageHandler);
		writtenButton.setOnAction(sendMessageHandler);

		Tab chooseUser = new Tab("Choose User", firstTab);
		Tab readMessages = new Tab("Read Messages", seondTab);
		Tab sendMessage = new Tab("Send Message", thirdTab);
		tabPane = new TabPane();
		tabPane.getTabs().addAll(chooseUser, readMessages, sendMessage);
		
		root.setTop(statusLine);
		root.setCenter(tabPane);
		BorderPane.setAlignment(statusLine, Pos.CENTER);
		
		Scene scene = new Scene(root);
		primaryStage.setTitle("Messenger GUI");
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	private class UpdateMessageStatusHandler implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent e) {	
			if(usernameField.getText() == null) {
				throw new NullPointerException("Username can't be null");
			}
			else{
				if(!myMessenger.getUsers().contains(usernameField.getText())) {
					statusLine.setText("Incorrect Username");
				}
				else {
					for(String user : myMessenger.getUsers()) {
						if(usernameField.getText().equals(user)) {
							statusLine.setText("Current User: " + user);
						}
					}
				}
			}
		}
	}
	
	private class LoadMessageHandler implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent e) {
			if(e.getSource() == loadUnreadButton) {
				receivedMessages = myMessenger.getReceivedMessages(usernameField.getText(), Message.MessageStatus.unread);
			}
			else if(e.getSource() == loadAllButton) {
				receivedMessages = myMessenger.getReceivedMessages(usernameField.getText());
			}
			
			
			if(receivedMessages.size() == 0 || messageIndex == receivedMessages.size()-1 && messageIndex != 0) {
				messageDisplay.setText("No Message Displayed");
				statusLine.setText("0 message(s) loaded");
			}
			else if(receivedMessages.size() > 0) {
				receivedMessages.get(messageIndex).setStatus(Message.MessageStatus.read);
				messageDisplay.setText(receivedMessages.get(messageIndex).toString());
				statusLine.setText(receivedMessages.size() + " message(s) loaded");	
		
				if(receivedMessages.size() > 1) {
					nextButton.setDisable(false);
					nextButton.setOnAction(new ShowNextHandler());
				}
				
			}
		}
	}
	
	private class ShowNextHandler implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent e) {
			messageIndex ++;
			receivedMessages.get(messageIndex).setStatus(Message.MessageStatus.read);
			messageDisplay.setText(receivedMessages.get(messageIndex).toString());
			if(messageIndex == receivedMessages.size()-1) {
				nextButton.setDisable(true);
				messageIndex = 0;
			}
		}
	}
	
	private class SendMessageHandler implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent e) {
			String sender = usernameField.getText();
			String receiver = receiverField.getText();
			
			if(writtenButton.isSelected()) {
				if(receiver == null) {
					throw new NullPointerException("Receipient name can't be null");
				}
				else{
					if(!myMessenger.getUsers().contains(receiver)) {
						statusLine.setText("Receipient Username Not Found");
					}
					else {
						myMessenger.sendMessage(sender, receiver, writeMessageField.getText());
						statusLine.setText("Message Successfully Sent");
					}
				}
			}
			else if(smileButton.isSelected()) {
				if(receiver == null) {
					throw new NullPointerException("Receipient name can't be null");
				}
				else{
					if(!myMessenger.getUsers().contains(receiver)) {
						statusLine.setText("Receipient Username Not Found");
					}
					else {
						smileMessage = new SmileMessage(sender, receiver);
						writeMessageField.setText(smileMessage.getText());
						myMessenger.sendSmile(sender, receiver);
						statusLine.setText("Smile Successfully Sent");
					}
				}
				
			}
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
