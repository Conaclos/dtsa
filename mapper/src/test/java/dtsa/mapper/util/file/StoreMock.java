package dtsa.mapper.util.file;

import java.io.File;

import dtsa.mapper.util.annotation.NonNull;

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
	public StoreMock (@NonNull String aName) {
		super (aName);
	}

// Change
	@Override
	public void storePlainFile (@NonNull File aPlainFile) {
		// do nothing
	}
	
}
