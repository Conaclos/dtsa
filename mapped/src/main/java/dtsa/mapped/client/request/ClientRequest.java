package dtsa.mapped.client.request;

import dtsa.mapped.client.response.MappedExceptionResponse;
import dtsa.util.annotation.Nullable;
import dtsa.util.communication.base.Request;


public abstract class ClientRequest 
	extends Request <ClientRequestVisitor> {

// Access
	@Override
	public @Nullable MappedExceptionResponse exception () {
		// TODO Auto-generated method stub
		return exception;
	}

// Implementation
	protected @Nullable MappedExceptionResponse exception;
	
}
