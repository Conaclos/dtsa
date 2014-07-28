package dtsa.mapper.cloud.mapped.request;

import dtsa.mapper.client.request.EchoClientRequest;
import dtsa.util.communication.base.RequestVisitor;

public abstract class MapperRequestVisitor
		implements RequestVisitor {
	
// Other
	/**
	 * Handle `aVisited'.
	 * @pattern Visitor
	 * 
	 * @param aVisited - request to process.
	 */
	public abstract void visitEcho (EchoMapperRequest aVisited);
	
}
