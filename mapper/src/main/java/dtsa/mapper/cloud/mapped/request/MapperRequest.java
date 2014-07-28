package dtsa.mapper.cloud.mapped.request;

import dtsa.mapper.cloud.mapped.response.ServiceExceptionResponse;
import dtsa.mapper.cloud.mapped.response.ServiceResponse;
import dtsa.util.annotation.Nullable;
import dtsa.util.communication.base.Request;


public abstract class MapperRequest 
	extends Request <MapperRequestVisitor> {

	@Override
	public abstract @Nullable ServiceResponse response ();

	@Override
	public @Nullable ServiceExceptionResponse exception () {
		// TODO Auto-generated method stub
		return exception;
	}
	
// Implementation
	/**
	 * @see #exception ()
	 */
	protected @Nullable ServiceExceptionResponse exception;
	
}
