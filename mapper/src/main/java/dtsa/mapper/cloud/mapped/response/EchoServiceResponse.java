package dtsa.mapper.cloud.mapped.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import dtsa.util.annotation.NonNull;

/**
 * 
 * @description Echo message.
 * @author Victorien ELvinger
 * @date 2014
 *
 */
public class EchoServiceResponse 
	extends ServiceResponse {

// Creation
	/**
	 * 
	 * @param aId - {@link #getId ()}
	 */
	@JsonCreator
	public EchoServiceResponse (@JsonProperty ("id") int aId) {
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
	public void accept (ServiceResponseVisitor aVisitor) {
		aVisitor.visitEcho (this);
	}
	
// Implementation
	/**
	 * @see #getId ()
	 */
	protected int id;
	
}
