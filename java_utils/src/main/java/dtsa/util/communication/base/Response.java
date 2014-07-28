package dtsa.util.communication.base;

import com.google.java.contract.Requires;

/**
 * 
 * @description Root ancestor of all server response. Response are simple objects for reception.
 * @author Victorien ELvinger
 * @date 2014
 *
 * @param <P> - NonNull type based on ResponseVisitor.
 * 
 */
public abstract class Response <P extends ResponseVisitor> {
	
// Other
	/**
	 * Visit current response with `aVisitor'.
	 * @pattern Visitor
	 * 
	 * @param aVisitor - a visitor
	 */
	public abstract void accept (P aVisitor);
	
	/**
	 * Visit current response with `aVisitor' if `aProcessor' is of type `P'.
	 * @pattern Visitor
	 * 
	 * @param aVisitor - a visitor
	 */
	@Requires ("aProcessor != null")
	public void attemptAccept (ResponseVisitor aVisitor) {
		try {
			P processor = (P) aVisitor;
			accept (processor);
		}
		catch (ClassCastException e) {}
	}
	
}
