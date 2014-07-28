package dtsa.util.json;

import dtsa.util.communication.base.Response;
import dtsa.util.communication.base.ResponseVisitor;
import dtsa.util.communication.converter.String2Response;


public class LabeledJson2Response <G extends Response <? extends ResponseVisitor>>
	extends JsonLabeling <G>
	implements String2Response <G> {
	
// Creation
	/**
	 * Create a default manager.
	 */
	public LabeledJson2Response () {
		super ();
	}
	
}
