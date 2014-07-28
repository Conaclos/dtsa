package dtsa.mapper.client.response;

/**
 * 
 * @description Echo message.
 * @author Victorien ELvinger
 * @date 2014
 *
 */
public class EchoMapperResponse 
	extends MapperResponse {

// Creation
	/**
	 * 
	 * @param aId - {@link #getId ()}
	 */
	public EchoMapperResponse (int aId) {
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
	public void accept (MapperResponseVisitor aVisitor) {
		aVisitor.visitEcho (this);
	}
	
// Implementation
	/**
	 * @see #getId ()
	 */
	protected int id;
	
}
