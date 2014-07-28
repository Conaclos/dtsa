package dtsa.util.communication.converter;

import dtsa.util.communication.base.Response;
import dtsa.util.communication.base.ResponseVisitor;


public abstract interface Response2String <G extends Response <? extends ResponseVisitor>> 
	extends Object2String <G> {
	
}
