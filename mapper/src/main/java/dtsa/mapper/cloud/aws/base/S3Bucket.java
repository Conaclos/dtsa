package dtsa.mapper.cloud.aws.base;

import java.io.File;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Region;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;

import dtsa.mapper.util.annotation.Nullable;
import dtsa.mapper.util.file.DirectoryCompressionException;
import dtsa.mapper.util.file.Store;
import dtsa.mapper.util.file.UnreachablePathException;

/**
 * @author Victorien Elvinger
 * @date 2014/06/26
 *
 */
public class S3Bucket
		extends Store {
	
// Creation
	/**
	 * 
	 * @param aName
	 *            - Bucket name.
	 * @param aLocation
	 *            - Bucket location.
	 * @param aAccess
	 *            - Bucket location.
	 * @param aS3
	 *            - S3 service.
	 */
	public S3Bucket (String aName, String aLocation, @Nullable String aAccess, AmazonS3Client aS3) {
		super (aName);
		location = aLocation;
		access = aAccess;
		s3 = aS3;
		if (! s3.doesBucketExist (aName)) {
			try {
				s3.createBucket (aName, aLocation);
			}
			catch (AmazonServiceException e) {
				// TODO Logging
				e.printStackTrace ();
			}
		}
		
		assert s3 == aS3: "ensure: `s3' set with `aS3'";
		assert name == aName: "ensure: `name' set with `aName'";
		assert access == aAccess: "ensure: `access' set with `aAccess'";
		assert location == aLocation: "ensure: `location' set with `aLocation'";
	}
	
// Access
	/**
	 * @return Bucket URI
	 */
	public String URI () {
		return "https://s3-" + location + ".amazonaws.com/" + name + "/";
	}
	
	/**
	 * 
	 * @return Bucket location.
	 */
	public String location () {
		return location;
	}
	
	/**
	 * 
	 * @return URI of the last stored entity. Null if none.
	 */
	public @Nullable String lastStoredURI () {
		@Nullable String localLastStored;
		@Nullable String result;
		
		localLastStored = lastStored;
		if (localLastStored != null) {
			result =  URI () + lastStored;
		}
		else {
			result = null;
		}
		
		return result;		
	}
	
	/**
	 * 
	 * @return MD5 of the last stored entity. Null if none.
	 */
	public @Nullable String lastStoredMd5 () {
		return lastStoredMD5;
	}
	
// Change
	@Override
	public void storeFromPath (String aPath) throws UnreachablePathException, DirectoryCompressionException, AmazonServiceException {
		try {
			super.storeFromPath (aPath);
		}
		catch (Exception e) {
			if (e instanceof AmazonServiceException) {
				throw (AmazonServiceException) e;
			}
			else if (e instanceof DirectoryCompressionException) {
				throw (DirectoryCompressionException) e;
			}
			else if (e instanceof UnreachablePathException) {
				throw (UnreachablePathException) e;
			}
			else {
				assert false: "check: is AmazonServiceException or DirectoryCompressionException or UnreachablePathException";
			}
		}
	}
	
	@Override
	public void store (File aFile) throws DirectoryCompressionException, AmazonServiceException {
		try {
			super.store (aFile);
		}
		catch (Exception e) {
			if (e instanceof AmazonServiceException) {
				throw (AmazonServiceException) e;
			}
			else if (e instanceof DirectoryCompressionException) {
				throw (DirectoryCompressionException) e;
			}
			else {
				assert false: "check: is AmazonServiceException or DirectoryCompressionException";
			}
		}
	}
	
	@Override
	public void storeDirectory (File aDirectory) throws DirectoryCompressionException, AmazonServiceException {
		try {
			super.storeDirectory (aDirectory);
		}
		catch (Exception e) {
			if (e instanceof AmazonServiceException) {
				throw (AmazonServiceException) e;
			}
			else {
				assert false: "check: is AmazonServiceException";
			}
		}
	}
	
	@Override
	public void storePlainFile (File aPlainFile) throws AmazonServiceException {
		assert aPlainFile.exists (): "require: `aPlainFile' exists.";
		assert ! aPlainFile.isDirectory (): "require: `aPlainFile' denotes a plain file.";
		
		PutObjectResult objectResult;
		PutObjectRequest objectRequest;
		CannedAccessControlList permission;
		
		if (access == null) {
			permission = CannedAccessControlList.Private;
		}
		else if (access.equals ("public")) {
			permission = CannedAccessControlList.PublicRead;
		}
		else {
			permission = CannedAccessControlList.Private;
		}
		objectRequest = new PutObjectRequest (name, aPlainFile.getName (), aPlainFile).withCannedAcl (permission);
		objectResult = s3.putObject (objectRequest);
		lastStoredMD5 = objectResult.getContentMd5 ();
		lastStored = aPlainFile.getName ();
	}
	
// Implementation
	/**
	 * S3 service.
	 */
	protected final AmazonS3 s3;
	
	/**
	 * Bucket location.
	 */
	protected final String location;
	
	/**
	 * Access permission for future stored files.
	 * Null means private.
	 */
	protected final @Nullable String access;
	
	/**
	 * MD5 of the last stored entity.
	 */
	protected @Nullable String lastStoredMD5;
	
}
