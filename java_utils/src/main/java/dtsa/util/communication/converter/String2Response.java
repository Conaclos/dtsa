package dtsa.util.communication.converter;

import dtsa.util.communication.base.Response;
import dtsa.util.communication.base.ResponseVisitor;


public abstract interface String2Response <G extends Response <? extends ResponseVisitor>>
		extends String2Object <G> {
	
}
