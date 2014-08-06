package dtsa.util.communication.listener;

import java.io.BufferedReader;
import java.io.IOException;

import dtsa.util.annotation.Nullable;
import dtsa.util.communication.converter.String2Object;

/**
 * 
 * @description Input to Object of type <G>
 * @author Victorien ELvinger
 * @date 2014
 *
 * @param <G> - NonNull type
 */
public class ConvertibleObjectListener <G>
	extends InputListener <G> {

// Creation
	public ConvertibleObjectListener (BufferedReader aInput, String2Object <G> aConverter) {
		super (DefaultCapacity);
		
		in = aInput;
		converter = aConverter;
		
		assert in == aInput: "ensure: set `in' with `aInput'";
		assert converter == aConverter: "ensure: set `converter' with `aConverter'";		
	}
	
// Constant
	/**
	 * DEfault number of maximum request queued.
	 */
	protected final static int DefaultCapacity = 10;
	
// Implementation
	/**
	 * Input.
	 */
	protected BufferedReader in;
	
	/**
	 * From string to Objects.
	 */
	protected String2Object <G> converter;
	
	@Override
	protected @Nullable G last () {
		@Nullable G result;
		@Nullable String str;
		
		result = null;
		try {
			str = in.readLine ();
			if (str != null) {
				result = converter.instance (str);
			}
		}
		catch (IOException e) {
			if (! isInterrupted ()) {
				// TODO Logging
				e.printStackTrace();
			}
			else {
				// Thread.currentThread ().interrupt ();
			}

			assert false: "stop lsitener 2";
		}
		catch (Exception e) {
			// TODO Logging
			e.printStackTrace();

			assert false: "stop lsitener";
		}
		
		return result;
	}
	
}
