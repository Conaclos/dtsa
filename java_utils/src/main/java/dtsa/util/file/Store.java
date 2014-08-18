package dtsa.util.file;

import java.io.File;
import java.util.Calendar;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import dtsa.util.annotation.Nullable;
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
	 * Store with `aId' as `id'.
	 * 
	 * @param aId
	 *            - store id.
	 */
	public Store (String aId) {
		assert aId.length () > 0: "require: `aName' is not empty.";
		
		id = aId;
		
		assert id () == aId: "ensure: `name' set with `aId'";
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
	public String id () {
		return id;
	}
	
	/**
	 * Hash code.
	 */
	@Override
	public int hashCode () {
		return id.hashCode ();
	}
	
	/**
	 * Can be used to avoid name conflict
	 * 
	 * @return random salt.
	 */
	public long salt () {
		return Calendar.getInstance ().getTime ().getTime ();
	}
	
// Status
	/**
	 * @param o
	 *            - object to compare
	 * @return Is `o' denoting the same store?
	 */
	@Override
	public boolean equals (@Nullable Object o) {
		return o instanceof Store && id.equals (((Store) o).id);
	}
	
// Change (remote and local)
	/**
	 * Store file at `aPath' with its name.
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
	public void storeFromPath (String aPath) throws UnreachablePathException,
			DirectoryCompressionException, Exception {
		File f = new File (aPath);
		
		if (f.exists ()) {
			store (f);
		}
		else {
			throw new UnreachablePathException (aPath);
		}
	}
	
	/**
	 * Store file at `aPath' as `aName'.
	 * 
	 * @param aPath
	 *            - file or directory path
	 * @param aName
	 *            - name for file in the store
	 * @throws UnreachablePathException
	 * @throws DirectoryCompressionException
	 * @throws Exception
	 */
	public void storeFromPathAs (String aPath, String aName) throws UnreachablePathException,
			DirectoryCompressionException, Exception {
		assert aName.length () > 0: "require: `aName' is not empty.";
		
		File f = new File (aPath);
		
		if (f.exists ()) {
			storeAs (f, aName);
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
		
		storeAs (aFile, aFile.getName ());
	}
	
	/**
	 * Store file at `aPath' as `aName'.
	 * 
	 * @param aFile
	 *            - file or directory path
	 * @param aName
	 *            - name for file in the store
	 * @throws UnreachablePathException
	 * @throws DirectoryCompressionException
	 * @throws Exception
	 */
	public void storeAs (File aFile, String aName) throws DirectoryCompressionException, Exception {
		assert aFile.exists (): "require: `aPath' exists.";
		assert aName.length () > 0: "require: `aName' is not empty.";
		
		if (aFile.isDirectory ()) {
			storeDirectoryAs (aFile, aName);
		}
		else {
			storePlainFileAs (aFile, aName);
		}
	}
	
	/**
	 * Compress `aDirectory' as a zip file and store the obtained plain file.
	 * 
	 * @param aDirectory
	 *            - Directory to store.
	 * @param aName
	 *            - name for file in the store
	 * @throws DirectoryCompressionException
	 *             - triggered when the directory compression failed
	 * @throws Exception
	 *             - triggered if an issue occurs for storing.
	 */
	public void storeDirectoryAs (File aDirectory, String aName) throws DirectoryCompressionException, Exception,
			DirectoryCompressionException {
		assert aDirectory.exists (): "require: `aDirectory' exists.";
		assert aDirectory.isDirectory (): "require: `aDirectory' denotes a directory.";
		
		ZipFile zipFile;
		@Nullable File zippedDirectory;
		
		try {
			zipFile = new ZipFile (aDirectory.getParent () + File.separator + aName + ".zip");
			zipFile.addFolder (aDirectory, zipParameters);
			zippedDirectory = zipFile.getFile ();
			if (zippedDirectory != null) {
				storePlainFileAs (zippedDirectory, aName);
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
	 * @param aName
	 *            - name for file in the store
	 * @throws Exception
	 *             - exceptions for specific implementation.
	 */
	public abstract void storePlainFileAs (File aPlainFile, String aName) throws Exception;
	
// Change (local)
	/**
	 * Store `aName' locally to `aPath'.
	 * 
	 * @param aName - entity name
	 * @param aPath - local directory
	 * @param aPath
	 * @throws UnreachablePathException
	 * @throws DirectoryCompressionException
	 * @throws Exception
	 */
	public void storeToPath (String aName, String aPath) throws UnreachablePathException, UntwinableObjectException, UnreachableObjectException,
			Exception {
		File f = new File (aPath);
		
		if (f.exists () && f.isDirectory ()) {
			storeTo (aName, f);
		}
		else {
			throw new UnreachablePathException (aPath);
		}
	}
	
	/**
	 * Store `aName' locally to `aDirectory'.
	 * 
	 * @param aName - entity name
	 * @param aDirectory - local directory
	 * @throws Exception
	 */
	public abstract void storeTo (String aName, File aDirectory) throws UnreachableObjectException, UntwinableObjectException, Exception;
	
// Removal
	/**
	 * @param aName - entity to remove
	 */
	public abstract void remove (String aName);
	
// Implementation
	/**
	 * Store id.
	 */
	protected String id;
	
}
