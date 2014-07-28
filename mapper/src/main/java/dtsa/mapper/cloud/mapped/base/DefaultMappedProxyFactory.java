package dtsa.mapper.cloud.mapped.base;

import java.io.BufferedReader;
import java.io.BufferedWriter;

import dtsa.util.communication.base.RequestVisitor;
import dtsa.util.communication.base.Request;
import dtsa.util.communication.base.Response;
import dtsa.util.communication.base.ResponseVisitor;
import dtsa.util.communication.converter.Object2String;
import dtsa.util.communication.converter.Request2String;
import dtsa.util.communication.converter.String2Object;
import dtsa.util.communication.converter.String2Response;
import dtsa.util.communication.listener.ConvertibleObjectListener;
import dtsa.util.communication.writer.ConvertibleObjectWriter;

/**
 * 
 * @description Provider of Mapped Proxy.
 * @author Victorien ELvinger
 * @date 2014
 *
 */
public class DefaultMappedProxyFactory 
	implements MappedProxyFactory {

// Creation
	public DefaultMappedProxyFactory (Request2String <Request <? extends RequestVisitor>> aRequestConverter, 
			String2Response <Response <? extends ResponseVisitor>> aResponseConverter) {
		
		requestConverter = aRequestConverter.clone ();
		responseConverter = aResponseConverter.clone ();
	}
	
// Access
	@Override
	public MappedProxy apply (int aPort, ServiceAvailability aAvailabilty) {
		return new MappedProxy (aPort, aAvailabilty, 
				(BufferedReader aIn) -> new ConvertibleObjectListener <Response <? extends ResponseVisitor>> (aIn, responseConverter), 
				(BufferedWriter aOut) -> new ConvertibleObjectWriter <Request <? extends RequestVisitor>> (aOut, requestConverter));
	}
	
// Implementation
	/**
	 * Converter.
	 */
	protected Object2String <Request <? extends RequestVisitor>> requestConverter;
	
	/**
	 * Converter.
	 */
	protected String2Object <Response <? extends ResponseVisitor>> responseConverter;
	
}
