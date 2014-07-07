package dtsa.mapper.util.file;

import java.io.File;
import java.util.Calendar;
import java.lang.String;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import dtsa.mapper.util.annotation.Nullable;
import net.lingala.zip4j.exception.ZipException;

/**
 * @description Storing utils and store interface.
 * @author Victorien Elvinger
 * @date 2014/06/26
 *
 */
public abstract class Store {
	
// Initialization
	/**
	 * STore with `aName' as `name'
	 * 
	 * @param aName
	 *            - store name.
	 */
	public Store (String aName) {
		name = aName;
		
		assert name () == aName: "ensure: `name' set with `aName'";
	}
	
// Constant
	protected final static ZipParameters zipParameters;
	
	static {
		zipParameters = new ZipParameters ();
		zipParameters.setCompressionMethod (Zip4jConstants.COMP_DEFLATE);
		zipParameters.setCompressionLevel (Zip4jConstants.DEFLATE_LEVEL_ULTRA);
		
		assert zipParameters.getCompressionMethod () == Zip4jConstants.COMP_DEFLATE: "check: compression enabled and encryption disabled.";
		assert zipParameters.getCompressionLevel () == Zip4jConstants.DEFLATE_LEVEL_ULTRA: "check: highest compression level.";
	}
	
// Access
	/**
	 * @return Store name
	 */
	public String name () {
		return name;
	}
	
	/**
	 * 
	 * @return Path of the last stored entity, relative to store root. Null if none.
	 */
	public @Nullable String lastStored () {
		return lastStored;
	}
	
	/**
	 * 
	 */
	@Override
	public int hashCode () {
		return name.hashCode ();
	}
	
// Status
	/**
	 * @param o
	 *            - object to compare
	 * @return Is `o' denoting the same store?
	 */
	@Override
	public boolean equals (@Nullable Object o) {
		return o instanceof Store && name.equals (((Store) o).name);
	}
	
// Change
	/**
	 * Store file at `aPath'
	 * 
	 * @param aPath
	 *            - file or directory path
	 * @throws UnreachablePathException
	 *             - triggered if `aPath' is unreachable
	 * @throws DirectoryCompressionException
	 *             - triggered when the directory compression failed
	 * @throws Exception
	 *             - triggered if an issue occurs for storing.
	 */
	public void storeFromPath (String aPath) throws UnreachablePathException, DirectoryCompressionException, Exception {
		File f = new File (aPath);
		
		if (f.exists ()) {
			store (f);
		}
		else {
			throw new UnreachablePathException (aPath);
		}
	}
	
	/**
	 * Store `aFile'.
	 * 
	 * @param aFile
	 *            - file or directory.
	 * @throws DirectoryCompressionException
	 *             - triggered when the directory compression failed
	 * @throws Exception
	 *             - triggered if an issue occurs for storing.
	 */
	public void store (File aFile) throws DirectoryCompressionException, Exception {
		assert aFile.exists (): "require: `aPath' exists.";
		
		if (aFile.isDirectory ()) {
			storeDirectory (aFile);
		}
		else {
			storePlainFile (aFile);
		}
	}
	
	/**
	 * Compress `aDirectory' as a zip file and store the obtained plain file.
	 * 
	 * @param aDirectory
	 *            - Directory to store.
	 * @throws DirectoryCompressionException
	 *             - triggered when the directory compression failed
	 * @throws Exception
	 *             - triggered if an issue occurs for storing.
	 */
	public void storeDirectory (File aDirectory) throws DirectoryCompressionException, Exception,
			DirectoryCompressionException {
		assert aDirectory.exists (): "require: `aDirectory' exists.";
		assert aDirectory.isDirectory (): "require: `aDirectory' denotes a directory.";
		
		ZipFile zipFile;
		@Nullable File zippedDirectory;
		
		long milliseconds = Calendar.getInstance ().getTime ().getTime ();
		
		try {
			zipFile = new ZipFile (aDirectory.getPath () + "_" + milliseconds + ".zip");
			zipFile.addFolder (aDirectory, zipParameters);
			zippedDirectory = zipFile.getFile ();
			if (zippedDirectory != null) {
				storePlainFile (zippedDirectory);
			}
			else {
				assert false: "check: Zip file exists";
			}
		}
		catch (ZipException e) {
			@Nullable String path = aDirectory.getPath ();
			
			if (path != null) {
				throw new DirectoryCompressionException (path);
			}
			else {
				assert false: "check: directory path exists";
			}
		}
	}
	
	/**
	 * Store `aPlainFile'
	 * 
	 * @param aPlainFile
	 *            - Plain file to store.
	 * @throws Exception
	 *             - exceptions for specific implementation.
	 */
	public abstract void storePlainFile (File aPlainFile) throws Exception;
	
// Removal
	/**
	 * Set `lastStored' with null pointer.
	 */
	public void resetLastStored () {
		lastStored = null;
		
		assert lastStored () == null: "ensure: `lastStored' is null.";
	}
	
// Implementation
	/**
	 * Store name.
	 */
	protected final String name;
	
	/**
	 * Path of the last stored entity, relative to store root.
	 */
	protected @Nullable String lastStored;
	
}
