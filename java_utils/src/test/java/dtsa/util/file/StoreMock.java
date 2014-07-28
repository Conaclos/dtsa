package dtsa.util.file;

import java.io.File;

import dtsa.util.annotation.NonNull;

/**
 * 
 * @description Mock for testing Store facility.
 * @author Victorien ELvinger
 * @date 2014/07/1
 *
 */
public class StoreMock 
	extends Store {

// Creation
	/**
	 * STore with `aName' as `name'
	 * @param aName - store name.
	 */
	public StoreMock (String aName) {
		super (aName);
	}

// Change
	@Override
	public void storeTo (String aName, File aDirectory)
			throws UnreachableObjectException, Exception {
		// do nothing
	}

	@Override
	public void storePlainFileAs (File aPlainFile, String aName) throws Exception {
		// do nothing
	}

// Removal
	@Override
	public void remove (@NonNull String aName) {
		// do nothing
	}
	
}
