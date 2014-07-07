package dtsa.mapper.client.response;



public abstract interface ResponseProcessor {
// Change
	/**
	 * Handle `aRequest'.
	 * @pattern Visitor
	 * 
	 * @param aResponse - request to process.
	 */
	public abstract void processStore (StoreResponse aResponse);
	
}
