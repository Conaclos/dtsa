package dtsa.mapper.client.request;

import dtsa.mapper.client.response.MapperExceptionResponse;
import dtsa.util.annotation.Nullable;
import dtsa.util.communication.base.Request;

/**
 * 
 * @description Root ancestor of all client requests to Mapper. Requests are simple objects for transmission.
 * @author Victorien ELvinger
 * @date 2014
 *
 */
public abstract class ClientRequest 
	extends Request <ClientRequestVisitor> {
	
// Access
	@Override
	public @Nullable MapperExceptionResponse exception () {
		return exception;
	}
	
// Implementation
	/**
	 * @see #exception ()
	 */
	protected @Nullable MapperExceptionResponse exception;
	
}
