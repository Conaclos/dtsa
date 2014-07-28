package dtsa.mapper.base;

import dtsa.util.communication.base.Request;
import dtsa.util.communication.base.Response;
import dtsa.util.communication.base.RequestVisitor;
import dtsa.util.communication.base.ResponseVisitor;
import dtsa.util.communication.converter.Request2String;
import dtsa.util.communication.converter.String2Response;

public class ServiceFactory {
	
// Creation
	public ServiceFactory (Request2String <Request <? extends RequestVisitor>> aWriter,
			String2Response <Response <? extends ResponseVisitor>> aListener,
			ServiceConfiguration aConfiguration) {
		
		writer = aWriter;
		listener = aListener;
		configuration = aConfiguration;
	}
	
// Access
	public void apply () {
		
	}
	
// Implementation
	/**
	 * Writer.
	 */
	protected Request2String <Request <? extends RequestVisitor>> writer;
	
	/**
	 * Listener.
	 */
	protected String2Response <Response <? extends ResponseVisitor>> listener;
	
	/**
	 * Service configuration.
	 */
	protected ServiceConfiguration configuration;
	
}
