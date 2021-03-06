package dtsa.util.communication.base;


import dtsa.util.annotation.Nullable;

/**
 * 
 * @description Root ancestor of all requests to the server. Requests are simple objects for transmission.
 * @author Victorien ELvinger
 * @date 2014
 * 
 * @param <P> - NonNull type based on  RequestVisitor
 * @param <R> - NonNull type based on Response
 */
public abstract class Request <P extends RequestVisitor> {
	
// Access
	/**
	 * @return Answer of this current request.
	 * Null if request is not already processed or if request has no answer or if request has an exception.
	 */
	public abstract @Nullable Response <? extends ResponseVisitor> response ();
	
	/**
	 * @return Exception of this current request.
	 * Null if request is not already processed or if request has no answer or if request has a normal response.
	 */
	public abstract @Nullable ExceptionResponse <? extends ResponseVisitor> exception ();
	
	/**
	 * Don't copy `response' and `exception'.
	 * @return Partial clone of the current instance.
	 */
	public abstract Request <P> partialCLone ();
	
// Status	
	/**
	 * Don't use `response' and `exception' to compare.
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
	
	/**
	 * @return Has an exception?
	 */
	public boolean hasException () {
		return exception () != null;
	}
	
// Other
	/**
	 * Visit current request with `aProcessor'.
	 * @pattern Visitor
	 * 
	 * @param aProcessor - a visitor
	 */
	public abstract void accept (P aProcessor);
	
	/**
	 * Visit current request with `aProcessor' if `aProcessor' is of type `P'.
	 * @pattern Visitor
	 * 
	 * @param aProcessor - a visitor
	 */
	public void attemptAccept (RequestVisitor aProcessor) {
		try {
			P processor = (P) aProcessor;
			accept (processor);
		}
		catch (ClassCastException e) {}
	}
	
}
