package dtsa.util.aws;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @description COnfiguration for an EC2 Instance.
 * @author Victorien ELvinger
 * @date 2014
 *
 */
public class EC2InstanceConfiguration {

// Creation
	/**
	 * 
	 * @param imageId - {@link #getImageId()}
	 * @param instanceType - {@link #getInstanceType()}
	 * @param securityGroup - {@link #getSecurityGroup()}
	 * @param region - {@link #getRegion()}
	 */
	@JsonCreator
	public EC2InstanceConfiguration (@JsonProperty ("imageId") String aImageId, 
			@JsonProperty ("instanceType")  String aInstanceType, @JsonProperty ("securityGroup") SecuryGroupConfiguration aSecurityGroup, 
			@JsonProperty ("region")  String aRegion) {
		
		if (aImageId == null) {
			throw new IllegalArgumentException ("'imageId' should be not null");
		}
		else if (aInstanceType == null) {
			throw new IllegalArgumentException ("'instanceType' should be not null");
		}
		else if (aSecurityGroup == null) {
			throw new IllegalArgumentException ("'securityGroup' should be not null");
		}
		else if (aRegion == null) {
			throw new IllegalArgumentException ("'region' should be not null");
		}
		
		imageId = aImageId;
		instanceType = aInstanceType;
		securityGroup = aSecurityGroup;
		region = aRegion;
		
		assert getImageId () == aImageId: "ensure: `getImageId' set with `aImageId'.";
		assert getInstanceType () == aInstanceType: "ensure: `getInstanceType' set with `aInstanceType'.";
		assert getSecurityGroup () == aSecurityGroup: "ensure: `getSecurityGroup' set with `aSecurityGroup'.";
		assert getRegion () == aRegion: "ensure: `getRegion' set with `aRegion'.";
	}
	
// Access
	/**
	 * Note: take care of the region.
	 * @return Instance image id.
	 */
	public String getImageId () {
		return imageId;
	}

	/**
	 * @return Instance Type.
	 */
	public String getInstanceType () {
		return instanceType;
	}

	/**
	 * Note: take care of the region.
	 * @return Instance image id.
	 */
	public SecuryGroupConfiguration getSecurityGroup () {
		return securityGroup;
	}

	/**
	 * @return Instance location.
	 */
	public String getRegion () {
		return region;
	}
	
// Implementation
	/**
	 * @see #getImageId ()
	 */
	protected String imageId;
	
	/**
	 * @see #getInstanceType ()
	 */
	protected String instanceType;
	
	/**
	 * @see #getSecurityGroup ()
	 */
	protected SecuryGroupConfiguration securityGroup;
	
	/**
	 * @see #getInstanceType ()
	 */
	protected String region;
	
}
