package dtsa.util.json;


public class UnrecordedLabelException 
	extends Exception {

// Creation
	/**
	 * Create with `aLabel' as `getLabel'.
	 * 
	 * @param aPath - Label unrecognized.
	 */
	public UnrecordedLabelException (String aLabel) {		
		super (String.format (message, aLabel));
		label = aLabel;
		
		assert getLabel () == aLabel: "ensure: `getLabel' set with `aLabel'";
	}

//Constant
	/**
	 * Serial version.
	 */
	protected static final long serialVersionUID = 1L;
	
	/**
	 * User message.
	 */
	protected final static String message = "Label '%s' is not recorded.";
	
//Access
	/**
	 * @return Label unrecognized.
	 */
	public String getLabel () {
		return label;
	}
	
//Implementation
	/**
	 * Label unrecognized.
	 */
	protected final String label;
	
}
