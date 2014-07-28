package dtsa.mapped.client.response;

import dtsa.util.communication.base.ResponseVisitor;

public abstract interface MappedResponseVisitor
		extends ResponseVisitor {
	
// Visit
	/**
	 * Handle `aVisited'.
	 * 
	 * @pattern Visitor
	 * 
	 * @param aVisited - response to process.
	 */
	public abstract void visitProjectCompilation (ProjectCompilationMappedResponse aVisited);
	
	/**
	 * Handle `aVisited'.
	 * 
	 * @pattern Visitor
	 * 
	 * @param aVisited - response to process.
	 */
	public abstract void visitException (MappedExceptionResponse aVisited);
	
	/**
	 * 
	 * Handle `aVisited'.
	 * 
	 * @pattern Visitor
	 * 
	 * @param aVisited
	 *            - response to process.
	 */
	public abstract void visitEcho (EchoMappedResponse aVisited);
	
}
