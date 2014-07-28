package dtsa.mapped.client.response;

/**
 * 
 * @description Echo message.
 * @author Victorien ELvinger
 * @date 2014
 *
 */
public class EchoMappedResponse 
	extends MappedResponse {

// Creation
	/**
	 * 
	 * @param aId - {@link #getId ()}
	 */
	public EchoMappedResponse (int aId) {
		id = aId;
		
		assert id == aId: "ensure: set `id' with `aId'.";
	}
	
// Access
	/**
	 * @return Message id.
	 */
	public int getId () {
		return id;
	}
	
// Other
	@Override
	public void accept (MappedResponseVisitor aVisitor) {
		aVisitor.visitEcho (this);
	}
	
// Implementation
	/**
	 * @see #getId ()
	 */
	protected int id;
	
}
