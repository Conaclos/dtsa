package dtsa.mapper.client.request;



public abstract interface RequestProcessor {

// Status
	/**
	 * @return Does current processor give answer for requests?
	 */
	public abstract boolean isReactive ();
	
// Other
	/**
	 * Handle `aRequest'.
	 * @pattern Visitor
	 * 
	 * @param aRequest - request to process.
	 */
	public abstract void processStore (StoreRequest aRequest);
	
	/**
	 * Handle `aRequest'.
	 * @pattern Visitor
	 * 
	 * @param aRequest - request to process.
	 */
	public abstract void processStartingInstances (StartingInstancesRequest aRequest);
	
}
