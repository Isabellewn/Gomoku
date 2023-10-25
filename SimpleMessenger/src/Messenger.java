import java.util.ArrayList;
/**
 * A class that simulates a Messenger that contains methods to add users, send messages and get received messages.
 * @author Isabelle
 *
 */

public class Messenger {
	private ArrayList<String> users;
	private ArrayList<Message> messages;
	
	/**
	 * The constructor of the Messenger class that takes no parameters
	 */
	public Messenger() {
		this.users = new ArrayList<String>();
		this.messages = new ArrayList<Message>();
	}
	
	/**
	 * Adds users to the users list of the Messenger
	 * @param username: the name of the user in String
	 */
	public void addUser(String username) {
		if(username == null) {
			throw new NullPointerException("username cannot be null");
		}
		if(username == "") {
			throw new IllegalArgumentException("username cannot be empty");
		}
		for(String name: users) {
			if(name.equals(username)) {
				users.add(null);
			}
		}
		users.add(username);
	}
	
	/**
	 * Sends a new message from one user to another
	 * @param sender: name of the sender
	 * @param receiver: name of the recipient
	 * @param text: text to be sent
	 */
	public void sendMessage(String sender, String receiver, String text) {
		Message currentMessage = new Message(sender, receiver, text);
		if(users.contains(sender) && users.contains(receiver)) {
			this.messages.add(currentMessage);
		}
		else if(sender == null || receiver == null) {
			throw new NullPointerException("username cannot be null");
		}
		else {
			throw new IllegalArgumentException("username not found");
		}
	}
	
	/**
	 * Sends a SmileMessage from one user to another
	 * @param from: name of the sender
	 * @param to: name of the recipient
	 */
	public void sendSmile(String from, String to) {
		SmileMessage currentSmile = new SmileMessage(from, to);
		if(users.contains(from) && users.contains(to)) {
			this.messages.add(currentSmile);
		}
		else if(from == null || to == null) {
			throw new NullPointerException("username cannot be null");
		}
		else {
			throw new IllegalArgumentException("username not found");
		}
	}
	
	/**
	 * Sends a reply to a received message after reading it
	 * @param original: the received message
	 * @param text: the reply text
	 */
	public void sendReply(Message original, String text) {
		Reply currentReply = new Reply(original.getReceiver(), original.getSender(), text, original);
		if(text == "") {
			throw new IllegalArgumentException("text cannot be empty");
		}
		if(text == null || original == null) {
			throw new NullPointerException("text or orignal message cannot be null");
		}
		this.messages.add(currentReply);
	}

	/**
	 * Gets all the received messages given a user's name
	 * @param username: name of a user
	 * @return all matching received messages in an ArrayList of Message
	 */
	public ArrayList<Message> getReceivedMessages(String username){
		if(username == null) {
			throw new NullPointerException("username cannot be null");
		}
		if(username == "") {
			throw new IllegalArgumentException("username cannot be empty");
		}
		if(users.contains(username) == false) {
			throw new IllegalArgumentException("username not found");
		}
		
		ArrayList<Message> receivedMessages = new ArrayList<Message>();
		for(int i = 0; i< this.messages.size(); i++) {
			if(this.messages.get(i).getReceiver().equals(username)) {
				receivedMessages.add(this.messages.get(i));
			}
		}
		return receivedMessages;
	}
	
	/**
	 * Gets all the received messages given a user's name and the status of the message
	 * @param username: name of a user
	 * @param status: status of the message
	 * @return all matching received messages in an ArrayList of Message
	 */
	public ArrayList<Message> getReceivedMessages(String username, Message.MessageStatus status){
		if(username == null) {
			throw new NullPointerException("username cannot be null");
		}
		if(username == "") {
			throw new IllegalArgumentException("username cannot be empty");
		}
		if(users.contains(username) == false) {
			throw new IllegalArgumentException("username not found");
		}
		if(status == null) {
			throw new NullPointerException("status cannot be null");
		}
		
		ArrayList<Message> receivedMessages = new ArrayList<Message>();
		
		if(status == Message.MessageStatus.unread) {
			for(int i = 0; i < this.messages.size(); i++) {
				if(this.messages.get(i).getReceiver().equals(username) 
				&& this.messages.get(i).getStatus() == Message.MessageStatus.unread) {
					receivedMessages.add(this.messages.get(i));	
				}	
			}	
		}
		
		if(status == Message.MessageStatus.read) {
			for(int i = 0; i < this.messages.size(); i++) {
				if(this.messages.get(i).getReceiver().equals(username) 
				&& this.messages.get(i).getStatus() == Message.MessageStatus.read) {
					receivedMessages.add(this.messages.get(i));	
				}	
			}	
		}
		
		if(status == Message.MessageStatus.trash) {
			for(int i = 0; i < this.messages.size(); i++) {
				if(this.messages.get(i).getReceiver().equals(username)  
				&& this.messages.get(i).getStatus() == Message.MessageStatus.trash) {
					receivedMessages.add(this.messages.get(i));	
				}	
			}	
		}
			
		return receivedMessages;
	}
	
	/**
	 * Gets all the users' names of the Messenger class
	 * @return the user's names in an ArrayList
	 */
	public ArrayList<String> getUsers(){
		return this.users;
	}
	
	/**
	 * Gets all the messages of the Messenger class
	 * @return the messages in an ArrayList
	 */
	public ArrayList<Message> getMessages(){
		return this.messages;
	}

}
