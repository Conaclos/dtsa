package dtsa.util.aws;

import java.io.File;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;

import dtsa.util.file.DirectoryCompressionException;
import dtsa.util.file.Store;
import dtsa.util.file.UnreachableObjectException;
import dtsa.util.file.UnreachablePathException;
import dtsa.util.file.UntwinableObjectException;

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
	public S3Bucket (S3BucketConfiguration aConfiguration, AmazonS3Client aS3) throws AmazonServiceException {
		super (aConfiguration.getBucket ());
		location = aConfiguration.getRegion ();
		isPublic = aConfiguration.isPublic ();
		s3 = aS3;
		if (! s3.doesBucketExist (id)) {
			s3.createBucket (id, location);
		}

		assert id == aConfiguration.getBucket (): "ensure: `name' set with `aName'";
		assert s3 == aS3: "ensure: `s3' set with `aS3'";
		assert isPublic == aConfiguration.isPublic (): "ensure: `access' set with `aAccess'";
		assert location == aConfiguration.getRegion (): "ensure: `location' set with `aLocation'";
	}

// Access
	/**
	 *
	 * @return Bucket location.
	 */
	public String location () {
		return location;
	}

	/**
	 * Note: Generate a new URI for `aName'.
	 * @side-effect
	 *
	 * @return RI of `aName'.
	 * @throws AmazonServiceException
	 */
	public String createURI (String aName) throws AmazonServiceException {
		String result;

		if (isPublic) {
			result = s3.generatePresignedUrl (new GeneratePresignedUrlRequest (id, aName)).toString ();
		}
		else {
			result = (new S3ObjectURI (location, id, aName)).toString ();
		}

		return result;
	}

// Change (remote and local)
	@Override
	public void storeFromPath (String aPath) throws UnreachablePathException, DirectoryCompressionException, AmazonServiceException {
		try {
			super.storeFromPath (aPath);
		}
		catch (UnreachablePathException | DirectoryCompressionException e) {
			throw e;
		}
		catch (Exception e) {
			if (e instanceof AmazonServiceException) {
				throw (AmazonServiceException) e;
			}
			else {
				assert false: "check: is AmazonServiceException or DirectoryCompressionException or UnreachablePathException";
			}
		}
	}
	@Override
	public void storeFromPathAs (String aPath, String aName) throws UnreachablePathException, DirectoryCompressionException, AmazonServiceException {
		try {
			super.storeFromPathAs (aPath, aName);
		}
		catch (UnreachablePathException | DirectoryCompressionException e) {
			throw e;
		}
		catch (Exception e) {
			if (e instanceof AmazonServiceException) {
				throw (AmazonServiceException) e;
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
		catch (DirectoryCompressionException e) {
			throw e;
		}
		catch (Exception e) {
			if (e instanceof AmazonServiceException) {
				throw (AmazonServiceException) e;
			}
			else {
				assert false: "check: is AmazonServiceException or DirectoryCompressionException";
			}
		}
	}

	@Override
	public void storeAs (File aFile, String aName) throws DirectoryCompressionException, AmazonServiceException {
		try {
			super.storeAs (aFile, aName);
		}
		catch (DirectoryCompressionException e) {
			throw e;
		}
		catch (Exception e) {
			if (e instanceof AmazonServiceException) {
				throw (AmazonServiceException) e;
			}
			else {
				assert false: "check: is AmazonServiceException or DirectoryCompressionException";
			}
		}
	}

	@Override
	public void storeDirectoryAs (File aDirectory, String aName) throws DirectoryCompressionException, AmazonServiceException {
		try {
			super.storeDirectoryAs (aDirectory, aName);
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
	public void storePlainFileAs (File aPlainFile, String aName) throws AmazonServiceException {
		assert aPlainFile.exists (): "require: `aPlainFile' exists.";
		assert ! aPlainFile.isDirectory (): "require: `aPlainFile' denotes a plain file.";

		PutObjectResult objectResult;
		PutObjectRequest objectRequest;
		CannedAccessControlList permission;

		if (isPublic) {
			permission = CannedAccessControlList.PublicRead;
		}
		else {
			permission = CannedAccessControlList.Private;
		}
		objectRequest = new PutObjectRequest (id, aName, aPlainFile).withCannedAcl (permission);
		objectResult = s3.putObject (objectRequest);
	}

// Change (local)
	@Override
	public void storeToPath (String aName, String aPath) throws UnreachablePathException, UnreachableObjectException, UntwinableObjectException, AmazonServiceException {
		try {
			super.storeToPath (aName, aPath);
		}
		catch (UnreachablePathException | UntwinableObjectException | UnreachableObjectException e) {
			throw e;
		}
		catch (Exception e) {
			if (e instanceof AmazonServiceException) {
				throw (AmazonServiceException) e;
			}
			else {
				assert false: "check: is AmazonServiceException or DirectoryCompressionException or UnreachablePathException";
			}
		}
	}

	/**
	 *
	 * @param aName
	 * @return
	 */
	@Override
	public void storeTo (String aName, File aDirectory) throws UnreachableObjectException, UntwinableObjectException, AmazonServiceException {
		assert aDirectory.exists (): "require: `aDirectory' exists.";
		assert aDirectory.isDirectory (): "require: `aDirectory' denotes a directory." + aDirectory.getPath ();

		s3.getObject (new GetObjectRequest (id, aName), new File (aDirectory, aName));
	}

// Removal
	@Override
	public void remove (String aName) throws AmazonServiceException {
		s3.deleteObject (id, aName);
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
	protected final boolean isPublic;

}
