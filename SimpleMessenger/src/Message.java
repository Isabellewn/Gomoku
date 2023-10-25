
/**
 * A class that simulates a message sent between two people. 
 * @author Isabelle
 *
 */
public class Message {
	public enum MessageStatus{
		unread, read, trash
	}
	
	protected String text;  
	private String sender;
	private String receiver;
	private MessageStatus status;
	public static int numberOfMessages = 0;
	public static int totalLengthOfMessages = 0;
	
	/**
	 * The constructor of the Message class that takes sender, receiver, text and status as parameter
	 * @param sender: sender's name
	 * @param receiver: recipient's name
	 * @param text: text sent
	 * @param status: the status of the message(read, unread, trash)
	 */
	public Message(String sender, String receiver, String text, MessageStatus status) {
		if(text == "") {
			throw new IllegalArgumentException("text cannot be empty");
		}
		if(text == null) {
			throw new NullPointerException("text cannot be null");
		}
		
		if(sender == "" || receiver == "") {
			throw new IllegalArgumentException("username cannot be empty");
		}
		
		if(sender == null || receiver == null) {
			throw new NullPointerException("username cannot be null");
		}
		
		if(status == null) {
			throw new NullPointerException("message status cannot be null");
		}
		
		this.text = text;
		this.sender = sender;
		this.receiver = receiver;
		this.status = status;
		numberOfMessages ++;
		totalLengthOfMessages += text.length();
	}
	
	/**
	 * The 2nd constructor of the Message class that takes sender, receiver and text as parameter
	 * @param sender: sender's name
	 * @param receiver: receipient's name
	 * @param text: the text sent
	 */
	public Message(String sender, String receiver, String text) {
		this(sender, receiver, text, MessageStatus.unread);
	}
	
	/**
	 * Gets the information of the whole message, including sender's name, receipient's name, text and status of the message
	 * @return the information in String
	 */
	public String toString() {
		String result = "Sender: " + this.sender + "\nReceiver: " + this.receiver + "\nStatus: " + this.status;
		if(this.text.length() < 50) {
			result +=  "\n" + this.text + "\n";
		}
		else {
			for(int i=0; i<this.text.length(); i++) {
				if(i % 50 == 0 && (this.text.length()-i >= 50)) {
					result += "\n" + this.text.substring(i, i+30);
				}
				if(i % 50 == 0 && (this.text.length()-i < 50)) {
					result += "\n" + this.text.substring(i) + "\n";
				}
			}
		}
		return result;
	}
	
	/**
	 * Gets the preview of the message, including sender's name, receipient's name, the beginning of the text in one line
	 * @return the preview in String
	 */
	public String getPreview() {
		String result = this.status + ", " + this.sender + ". ";
		if(this.text.length()<30) {
			result += this.text;
		}
		else {
			result += this.text.substring(0,30);
		}
		
		return result;
	}

	/**
	 * Gets the text of the Message
	 * @return the text in String
	 */
	public String getText() {
		return text;
	}

	/**
	 * Gets the sender's name
	 * @return the sender's name in String
	 */
	public String getSender() {
		return sender;
	}

	/**
	 * Gets the receipient's name
	 * @return the receipient's name in String
	 */
	public String getReceiver() {
		return receiver;
	}

	/**
	 * Gets the status of the message
	 * @return the status in enum
	 */
	public MessageStatus getStatus() {
		return status;
	}

	/**
	 * Sets the status of the message after reading the message
	 * @param status: the status of the message in enum(read, unread, trash)
	 */
	public void setStatus(MessageStatus status) {
		if(status == null) {
			throw new NullPointerException("status cannot be null");
		}
		this.status = status;
	}
	
	/**
	 * Gets the total number of messages sent
	 * @return: the number of messages in integer
	 */
	public static int getNumberOfMessages() {
		return numberOfMessages;
	}

	/**
	 * Gets the total length of characters of all messages
	 * @return: the length in integer
	 */
	public static int getTotalLengthOfMessages() {
		return totalLengthOfMessages;
	}
	

}
