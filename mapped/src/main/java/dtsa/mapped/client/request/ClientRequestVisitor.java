package dtsa.mapped.client.request;

import dtsa.util.communication.base.RequestVisitor;


public interface ClientRequestVisitor 
	extends RequestVisitor {
	
// Visit
	/**
	 * Handle `aVisited'
	 * 
	 * @pattern visitor
	 * 
	 * @param aVisited - request to processs.
	 */
	public abstract void visitProjectCompilation (ProjectCompilationClientRequest aVisited);
	
	/**
	 * Handle `aVisited'.
	 * @pattern Visitor
	 * 
	 * @param aVisited - request to process.
	 */
	public abstract void visitEcho (EchoClientRequest aVisited);
	
}
