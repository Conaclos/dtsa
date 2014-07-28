package dtsa.util.communication.base;


/**
 * 
 * @description Root ancestor of all visitors of request.
 * @author Victorien ELvinger
 * @date 2014
 *
 */
public abstract interface RequestVisitor {

// Status
	/**
	 * @return Does current processor give answer for requests?
	 */
	public abstract boolean isReactive ();
	
}
