package dtsa.util.communication.converter;

import dtsa.util.communication.base.Request;
import dtsa.util.communication.base.RequestVisitor;

public abstract interface String2Request <G extends Request <? extends RequestVisitor>>
		extends String2Object <G> {
	
}
