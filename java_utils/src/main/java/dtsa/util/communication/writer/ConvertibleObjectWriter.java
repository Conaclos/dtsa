package dtsa.util.communication.writer;

import java.io.BufferedWriter;
import java.io.IOException;

import dtsa.util.communication.converter.Object2String;


public class ConvertibleObjectWriter <G> 
	extends OutputWriter <G> {
	
// Creation
	public ConvertibleObjectWriter (BufferedWriter aOutput, Object2String <G> aConverter) {		
		super (20);
		
		out = aOutput;
		converter = aConverter;
		
		assert out == aOutput: "ensure: set `out' with `aOutput'";
		assert converter == aConverter: "ensure: set `converter' with `aConverter'";		
	}
	
// Cosntant
	/**
	 * Maximum number of output to handle.
	 */
	protected final static int DefaultCapacity = 20;
	
// Implementation
	/**
	 * Output.
	 */
	protected BufferedWriter out;
	
	/**
	 * From string to Objects.
	 */
	protected Object2String <G> converter;

	@Override
	protected void print (G aObject) {
		try {
			out.write (converter.value (aObject) + "\n");
			out.flush ();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
