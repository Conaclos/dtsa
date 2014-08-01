package dtsa.mapper.client.response;

import dtsa.util.communication.base.ResponseVisitor;

public abstract interface MapperResponseVisitor
		extends ResponseVisitor {
	
// Change
	/**
	 * Handle `aVisited'.
	 * 
	 * @pattern Visitor
	 * 
	 * @param aVisited
	 *            - response to process.
	 */
	public abstract void visitStore (StoreMapperResponse aVisited);
	
	/**
	 * 
	 * Handle `aVisited'.
	 * 
	 * @pattern Visitor
	 * 
	 * @param aVisited
	 *            - response to process.
	 */
	public abstract void visitProjectTesting (DistributedProjectTestingMapperResponse aVisited);
	
	/**
	 * 
	 * Handle `aVisited'.
	 * 
	 * @pattern Visitor
	 * 
	 * @param aVisited
	 *            - response to process.
	 */
	public abstract void visitException (MapperExceptionResponse aVisited);
	
	/**
	 * 
	 * Handle `aVisited'.
	 * 
	 * @pattern Visitor
	 * 
	 * @param aVisited
	 *            - response to process.
	 */
	public abstract void visitEcho (EchoMapperResponse aVisited);
	
}
