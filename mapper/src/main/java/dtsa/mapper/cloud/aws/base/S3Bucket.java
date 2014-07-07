package dtsa.mapper.cloud.aws.base;

import java.io.File;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Region;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;

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
	 * @param aS3
	 *            - S3 service.
	 */
	public S3Bucket (String aName, Region aLocation, AmazonS3Client aS3) {
		super (aName);
		location = aLocation;
		s3 = aS3;
		s3.setRegion (aLocation);
		if (! s3.doesBucketExist (aName)) {
			try {
				s3.createBucket (aName);
			}
			catch (AmazonServiceException e) {
				// TODO Logging
				e.printStackTrace ();
			}
		}
		
		assert s3 == aS3: "ensure: `s3' set with `aS3'";
		assert name == aName: "ensure: `name' set with `aName'";
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
		
		s3.setRegion (location);
		s3.putObject (new PutObjectRequest (name, aPlainFile.getName (), aPlainFile));
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
	protected final Region location;
	
}
