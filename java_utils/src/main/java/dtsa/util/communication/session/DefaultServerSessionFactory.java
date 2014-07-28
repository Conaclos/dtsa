package dtsa.util.communication.session;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.function.Function;

import com.google.java.contract.Ensures;
import com.google.java.contract.Requires;

import dtsa.util.annotation.Nullable;
import dtsa.util.communication.base.Request;
import dtsa.util.communication.base.RequestVisitor;
import dtsa.util.communication.base.Response;
import dtsa.util.communication.base.ResponseVisitor;
import dtsa.util.communication.converter.Object2String;
import dtsa.util.communication.converter.Response2String;
import dtsa.util.communication.converter.String2Object;
import dtsa.util.communication.converter.String2Request;
import dtsa.util.communication.listener.ConvertibleObjectListener;
import dtsa.util.communication.writer.ConvertibleObjectWriter;
import dtsa.util.log.Logger;

/**
 * 
 * @description Client Session Factory
 * @author Victorien ELvinger
 * @date 2014
 *
 */
public class DefaultServerSessionFactory 
	implements Function <Socket, ServerSession> {
	
// Creation
	public DefaultServerSessionFactory (Logger aLogger, ServerSessionConfiguration aClientConfiguration, 
			String2Request <Request <? extends RequestVisitor>> aRequestConverter, Response2String <Response <? extends ResponseVisitor>> aResponseConverter, 
			RequestVisitor [] aRequestProcessors, ResponseVisitor [] aResponseProcessors) {
		
		logger = aLogger;
		clientConfiguration = aClientConfiguration;
		requestProcessors = aRequestProcessors;
		responseProcessors = aResponseProcessors;
		requestConverter = aRequestConverter;
		responseConverter = aResponseConverter;
	}
	
// Access
	/**
	 * Use static parameters and  `aSocket' to build a new session.
	 * @param aSocket - dynamic parameter
	 * @return
	 */
	@Override
	@Requires ("aSocket != null")
	@Ensures ("result != null")
	public @Nullable ServerSession apply (Socket aSocket) {
		ConvertibleObjectListener <Request <? extends RequestVisitor>> listener;
		ConvertibleObjectWriter <Response <? extends ResponseVisitor>> writer;
		ServerSession result;
		BufferedReader in;
		BufferedWriter out;
		
		result = null;
		try {
			in = new BufferedReader (new InputStreamReader (aSocket.getInputStream ()));
			listener = new ConvertibleObjectListener <> (in, requestConverter);
			
			out = new BufferedWriter (new OutputStreamWriter (aSocket.getOutputStream ()));
			writer = new ConvertibleObjectWriter <> (out, responseConverter);
			
			result = new DefaultServerSession (aSocket, logger, clientConfiguration, listener, writer, requestProcessors, responseProcessors);
		}
		catch (IOException e) {
			// TODO Logging
			e.printStackTrace();
		}
		
		return result;
	}
	
// Implementation
	/**
	 * Logger.
	 */
	protected Logger logger;
	
	/**
	 * Client session configuration.
	 */
	protected ServerSessionConfiguration clientConfiguration;
	
	/**
	 * Request processors.
	 */
	protected RequestVisitor [] requestProcessors;
	
	/**
	 * Response Processors
	 */
	protected ResponseVisitor [] responseProcessors;
	
	/**
	 * Converter.
	 */
	protected String2Object <Request <? extends RequestVisitor>> requestConverter;
	
	/**
	 * Converter.
	 */
	protected Object2String <Response <? extends ResponseVisitor>> responseConverter;
	
}
