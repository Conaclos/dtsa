package dtsa.mapper.client.base;

import java.io.IOException;
import java.net.Socket;

import dtsa.mapper.client.request.Request;
import dtsa.mapper.client.request.StoreRequest;
import dtsa.mapper.util.annotation.Nullable;
import dtsa.mapper.util.communication.ClientSession;
import dtsa.mapper.util.communication.InputListener;
import dtsa.mapper.util.configuration.ConfigurationParsingException;
import dtsa.mapper.util.configuration.InvalidParameterException;

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
			e.printStackTrace();
		}
		catch (ConfigurationParsingException e) {
			// TODO Log it to client
			e.printStackTrace();
		}
		listener.start ();
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
		
		System.out.println ("LAUNCHING :)"); // TODO remove it
		
		try {
			next = listener.maybeNext (Timeout);
			if (next != null) {
				System.out.println (((StoreRequest) next).getPath ()); // TODO remove it
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
	
}
