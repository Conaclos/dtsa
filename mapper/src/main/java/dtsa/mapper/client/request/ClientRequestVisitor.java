package dtsa.mapper.client.request;

import dtsa.util.communication.base.RequestVisitor;



public abstract interface ClientRequestVisitor 
	extends RequestVisitor {
	
// Other
	/**
	 * Handle `aVisited'.
	 * @pattern Visitor
	 * 
	 * @param aVisited - request to process.
	 */
	public abstract void visitStore (StoreClientRequest aVisited);
	
	/**
	 * Handle `aVisited'.
	 * @pattern Visitor
	 * 
	 * @param aRequest - request to process.
	 */
	public abstract void visitStartingInstances (StartingInstancesClientRequest aVisited);
	
	/**
	 * Handle `aVisited'.
	 * @pattern Visitor
	 * 
	 * @param aVisited - request to process.
	 */
	public abstract void visitProjectTesting (ProjectTestingClientRequest aVisited);
	
	/**
	 * Handle `aVisited'.
	 * @pattern Visitor
	 * 
	 * @param aVisited - request to process.
	 */
	public abstract void visitEcho (EchoClientRequest aVisited);
	
}
