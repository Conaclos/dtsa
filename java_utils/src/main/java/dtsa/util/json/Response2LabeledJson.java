package dtsa.util.json;

import dtsa.util.communication.base.Response;
import dtsa.util.communication.base.ResponseVisitor;
import dtsa.util.communication.converter.Response2String;


public class Response2LabeledJson <G extends Response <? extends ResponseVisitor>>
	extends JsonLabeling <G>
	implements Response2String <G> {
	
// Creation
	/**
	 * Create a default manager.
	 */
	public Response2LabeledJson () {
		super ();
	}
	
}
