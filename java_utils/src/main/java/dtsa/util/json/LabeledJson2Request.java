package dtsa.util.json;

import dtsa.util.communication.base.Request;
import dtsa.util.communication.base.RequestVisitor;
import dtsa.util.communication.converter.String2Request;


public class LabeledJson2Request <G extends Request <? extends RequestVisitor>>
	extends JsonLabeling <G>
	implements String2Request <G> {
	
// Creation
	/**
	 * Create a default manager.
	 */
	public LabeledJson2Request () {
		super ();
	}
	
}
