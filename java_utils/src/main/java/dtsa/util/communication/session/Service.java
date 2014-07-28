package dtsa.util.communication.session;

import dtsa.util.communication.base.Request;
import dtsa.util.communication.base.RequestVisitor;


public abstract class Service <R extends Request <? extends RequestVisitor>> {
	
// 
// Access
	public abstract void acept (R aRequest);
	
}
