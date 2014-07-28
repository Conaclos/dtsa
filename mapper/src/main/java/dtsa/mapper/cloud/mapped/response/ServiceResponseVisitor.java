package dtsa.mapper.cloud.mapped.response;

import dtsa.util.communication.base.ResponseVisitor;

public abstract interface ServiceResponseVisitor
		extends ResponseVisitor {
	
// Visit
	/**
	 * Handle `aVisited'.
	 * 
	 * @pattern Visitor
	 * 
	 * @param serviceExceptionResponse
	 */
	public abstract void visitException (ServiceExceptionResponse aVisited);
	
	/**
	 * Handle `aVisited'.
	 * 
	 * @pattern Visitor
	 * 
	 * @param serviceExceptionResponse
	 */
	public abstract void visitProjectCompilation (ProjectCompilationServiceResponse aVisited);
	
	/**
	 * Handle `aVisited'.
	 * 
	 * @pattern Visitor
	 * 
	 * @param serviceExceptionResponse
	 */
	public abstract void visitEcho (EchoServiceResponse aVisited);
	
}
