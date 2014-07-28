package dtsa.util.aws;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.java.contract.Ensures;
import com.google.java.contract.Requires;

/**
 * 
 * @description S3 configuration
 * @author Victorien ELvinger
 * @date 2014
 *
 */
public class S3BucketConfiguration {
	
// Creation
	/**
	 * 
	 * @param aBucket - {@link #getBucket()}
	 * @param aRegion - {@link #getRegion()}
	 * @param aAccess - #getAccess ()
	 * 
	 * @throws IllegalArgumentException if one argument is null.
	 */
	@JsonCreator
	@Requires ({
		"aBucket != null",
		"aRegion != null",
		"aAccess != null"
	})
	@Ensures ({
		"getBucket () == aBucket",
		"getRegion () == aRegion",
		"getAccess () == aAccess"
	})
	public S3BucketConfiguration (@JsonProperty ("bucket") String aBucket, 
			@JsonProperty ("region") String aRegion, 
			@JsonProperty ("access") String aAccess) {
		
		if (aBucket == null) {
			throw new IllegalArgumentException ("'bucket' should be not null");
		}
		else if (aRegion == null) {
			throw new IllegalArgumentException ("'region' should be not null");
		}
		else if (aAccess == null) {
			throw new IllegalArgumentException ("'access' should be not null");
		}
		
		bucket = aBucket;
		region = aRegion;
		access = aAccess;
		
		assert getBucket () == aBucket: "ensure: `getBucket' set with `aBucket'.";
		assert getRegion () == aRegion: "ensure: `getRegion' set with `aRegion'.";
		assert getAccess () == aAccess: "ensure: `getAccess' set with `aAccess'.";
	}
	
// Access
	/**
	 * @return Bucket name.
	 */
	public String getBucket () {
		return bucket;
	}

	/**
	 * @return Bucket location.
	 */
	public String getRegion () {
		return region;
	}

	/**
	 * @return Bucket visibility.
	 */
	public String getAccess () {
		return access;
	}
	
// Implementation
	/**
	 * @see #getBucket ()
	 */
	protected String bucket;
	
	/**
	 * @see #getRegion ()
	 */
	protected String region;
	
	/**
	 * @see #getAccess ()
	 */
	protected String access;
	
}
