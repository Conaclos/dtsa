package dtsa.util.json;

import dtsa.util.communication.base.Request;
import dtsa.util.communication.base.RequestVisitor;
import dtsa.util.communication.converter.Request2String;


public class Request2LabeledJson <G extends Request <? extends RequestVisitor>>
	extends JsonLabeling <G>
	implements Request2String <G> {
	
// Creation
	/**
	 * Create a default manager.
	 */
	public Request2LabeledJson () {
		super ();
	}
	
}
