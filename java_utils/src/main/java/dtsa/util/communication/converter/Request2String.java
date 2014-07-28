package dtsa.util.communication.converter;

import dtsa.util.communication.base.Request;
import dtsa.util.communication.base.RequestVisitor;


public abstract interface Request2String <G extends Request <? extends RequestVisitor>>
	extends Object2String <G> {
	
}
