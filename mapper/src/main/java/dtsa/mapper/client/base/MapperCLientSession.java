package dtsa.mapper.client.base;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

import dtsa.mapper.client.request.Request;
import dtsa.mapper.client.request.RequestProcessor;
import dtsa.mapper.client.response.Response;
import dtsa.mapper.client.response.ResponseProcessor;
import dtsa.mapper.util.annotation.Nullable;
import dtsa.mapper.util.communication.ClientSession;
import dtsa.mapper.util.communication.InputListener;
import dtsa.mapper.util.configuration.ConfigurationParsingException;
import dtsa.mapper.util.configuration.InvalidParameterException;
import static dtsa.mapper.util.dependency.SharedDependencyInjector.injector;

/**
 * @description CLient session implementation.
 * @author Victorien Elvinger
 * @date 2014/06/25
 *
 */
public class MapperCLientSession
		extends ClientSession {
	
// Creation
	/**
	 * 
	 * @param aSocket
	 *            - Client socket. Should be used only in this freshly created object
	 * @exception IOException
	 */
	public MapperCLientSession (Socket aSocket) throws IOException {
		super (aSocket);
		try {
			listener = new RequestListener (in);
		}
		catch (InvalidParameterException e) {
			// TODO Log it to client
			e.printStackTrace ();
		}
		catch (ConfigurationParsingException e) {
			// TODO Log it to client
			e.printStackTrace ();
		}
		listener.start ();
		
		requestVisitors = injector.getComponents (RequestProcessor.class);
		assert requestVisitors != null: "check: `requestVisitors' exists.";
		
		responseVisitors = injector.getComponents (ResponseProcessor.class);
		assert responseVisitors != null: "check: `responseVisitors' exists.";
		
		assert requestVisitors.stream ().allMatch ( (@Nullable RequestProcessor p) -> p != null): "ensure: All item of `requestVisitors' are not null.";
		assert responseVisitors.stream ().allMatch ( (@Nullable ResponseProcessor p) -> p != null): "ensure: All item of `responseVisitors' are not null.";
	}
	
// Constant
	/**
	 * Timeout in seconds to wait the next request.
	 */
	public final static int Timeout = 10;
	
// Thread operation
	@Override
	public void interrupt () {
		super.interrupt ();
		listener.interrupt ();
	}
	
// Change
	@Override
	protected void process () {
		@Nullable Request next;
		@Nullable Response maybeAnswer;
		
		System.out.println ("LAUNCHING :)"); // TODO remove it
		
		try {
			next = listener.maybeNext (Timeout);
			if (next != null) {
				processRequest (next);
			}
			else {
				System.out.println ("Fin du temps"); // TODO remove it
				interrupt ();
			}
		}
		catch (InterruptedException e) {
			Thread.currentThread ().interrupt ();
		}
	}
	
// Implementation
	/**
	 * Listener for requests.
	 */
	protected InputListener <Request> listener;
	
	/**
	 * Request visitors.
	 */
	protected List <RequestProcessor> requestVisitors;
	
	/**
	 * Response visitors.
	 */
	protected List <ResponseProcessor> responseVisitors;
	
	/**
	 * Process `aVisited' with each processor of `requestVisitors'.
	 * And if there is a response, then process it via `processResponse'.
	 * 
	 * @param aVisited
	 */
	protected void processRequest (Request aVisited) {
		@Nullable Response maybeANswer;
		
		for (RequestProcessor p : requestVisitors) {
			assert p != null: "check: `p' exists.";
			aVisited.process (p);
			maybeANswer = aVisited.response ();
			if (maybeANswer != null) {
				processResponse (maybeANswer);
			}
		}
	}
	
	/**
	 * Process `aVisited' with each processor of `responseVisitors'.
	 * 
	 * @param aVisited
	 */
	protected void processResponse (Response aVisited) {
		for (ResponseProcessor p : responseVisitors) {
			assert p != null: "check: `p' exists.";
			aVisited.process (p);
		}
	}
	
}
