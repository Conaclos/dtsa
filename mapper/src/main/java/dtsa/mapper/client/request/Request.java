package dtsa.mapper.client.request;

import dtsa.mapper.client.response.Response;
import dtsa.mapper.util.annotation.Nullable;

/**
 * 
 * @description Client request
 * @author Victorien ELvinger
 * @date 2014
 *
 */
public abstract class Request {
	
// Access
	/**
	 * @return Answer of this current request.
	 * Null if request is not already processed or if request has no answer.
	 */
	public abstract @Nullable Response response ();
	
	/**
	 * Don't copy `response'.
	 * @return Partial clone of the current instance.
	 */
	public abstract Request partialCLone ();
	
// Status	
	/**
	 * Don't use `response' to compare.
	 * @param aOther
	 * @return IS `aOther' partially equals to current instance?
	 */
	public abstract boolean partialEquals (Object aOther);
	
	/**
	 * @return Has an answer?
	 */
	public boolean hasResponse () {
		return response () != null;
	}
	
// Other
	/**
	 * Visit current request with `aProcessor'.
	 * @pattern Visitor
	 * 
	 * @param aProcessor - a visitor
	 */
	public abstract void process (RequestProcessor aProcessor);
	
}
