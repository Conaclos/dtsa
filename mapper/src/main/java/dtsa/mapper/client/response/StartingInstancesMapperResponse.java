package dtsa.mapper.client.response;

import java.util.List;

import dtsa.util.annotation.NonNull;

/**
 * 
 * @description Response for starting instances request.
 * @author Victorien ELvinger
 * @date 2014/07/2
 *
 */
public class StartingInstancesMapperResponse
	extends MapperResponse {
	
// Creation	
	/**
	 * Create a normal starting instances response with `aCount' as `getCount'.
	 * 
	 * @param aCount - {@link #getCount ()}
	 * @param aSk - {@link #getSk ()}
	 * @param aIps - {@link #getIps ()}
	 */
	public StartingInstancesMapperResponse (int aCount, String aSk, List <String> aIds, List <String> aIps) {
		count = aCount;
		sk = aSk;
		ids = aIds;
		ips = aIps;
		
		assert getCount () == aCount: "ensure: `getCount' set with `aCount'";
		assert getIps () == ips: "ensure: `getIps' set with `ips'";
		assert getSk () == aSk: "ensure: `getSk' set with `aSk'";
	}

// Access
	/**
	 * Number of instances finally launched.
	 */
	public int getCount () {
		return count;
	}
	
	/**
	 * @return Secret key
	 */
	public String getSk () {
		return sk;
	}
	
	/**=
	 * @return Instance ID.
	 */
	public List <String> getIds () {
		return ids;
	}
	
	/**=
	 * @return Public IPs address of instances.
	 */
	public List <String> getIps () {
		return ips;
	}
	
// Other
	@Override
	public void accept (@NonNull MapperResponseVisitor aProcessor) {
		// TODO Auto-generated method stub
		
	}
	
// Implementation
	/**
	 * Number of instances finally launched.
	 */
	protected int count;
	
	/**
	 * @see #getSk ()
	 */
	protected String sk;
	
	/**
	 * @see #getIds ()
	 */
	protected List <String> ids;
	
	/**
	 * @see #getIps ()
	 */
	protected List <String> ips;
	
}
