/**
 * The SmileMessage class is the child of the Message class. It sends a smile message to another user.
 * @author Isabellewn
 *
 */

public class SmileMessage extends Message{
	
	public SmileMessage(String sender, String receiver) {
		super(sender, receiver, "*^ o ^*"+"\n  ^_^");
	}
	
	/**
	 * Gets the preview of the SmileMessage by overriding the getPreview() method in the Message class.
	 * It contains the name of the sender, receiver and a simple smile in one line.
	 */
	@Override
	public String getPreview() {
		text = ":)";
		return super.getPreview(); 
	}
	

}
