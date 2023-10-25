/**
 * The Reply class is the child class of the Message class. It sends reply to a message.
 * @author Isabellewn
 *
 */
public class Reply extends Message{
	private Message message;
	
	public Reply(String sender, String receiver, String text, Message message) {
		super(sender, receiver, text);
		this.message = message;
	}
	
	/**
	 * Gets the information of Reply class by overriding the toString() method in the Message class. 
	 * It contains the name of the sender, receiver and the reply text.
	 */
	@Override
	public String toString() {
		String result =  super.toString();
		result += "\n-------------Replying to---------------";
		result += "\n" + this.message.toString();
		return result;
	}
}
