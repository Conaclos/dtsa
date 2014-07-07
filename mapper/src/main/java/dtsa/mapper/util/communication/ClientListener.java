package dtsa.mapper.util.communication;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import dtsa.mapper.util.annotation.Nullable;

/**
 * 
 * @description Port listening.
 * @warning Should be used in a separate and unique thread.
 * @author Victorien Elvinger
 * @date 2014/06/24
 * 
 */
public class ClientListener
		extends InputListener <Socket> {
	
// Creation
	/**
	 * Create an active listener for `aPort' if it is not already in use.
	 * @param aPort
	 *            - acceptable port
	 */
	public ClientListener (int aPort) {
		this (aPort, aPort);
	}
	
	/**
	 * Create an active listener for a port between `aLowerPort' and `aUpperPort' if it is not already in use.
	 * @param aLowerPort
	 *            - lower acceptable port
	 * @param aUpperPort
	 *            - upper acceptable port
	 */
	public ClientListener (int aLowerPort, int aUpperPort) {
		super (MaximumPendingClients);
		
		assert 1024 <= aLowerPort && aLowerPort <= 65555: "require: `aLowerPort' is an assignable port.";
		assert 1024 <= aUpperPort && aUpperPort <= 65555: "require: `aUpperPort' is an assignable port.";
		assert aLowerPort <= aUpperPort: "require: valid range";
		
		socket = maybeSocketBetween (aLowerPort, aUpperPort);
		port = socket.getLocalPort ();
		
		assert aLowerPort <= port && port <= aUpperPort: "ensure: `socket' uses a port between `aLowerPort' and `aUpperPort'";
	}
	
// Constant
	/**
	 * Maximum number of queued clients.
	 */
	public final static int MaximumPendingClients = 10;
	
// Access	
	/**
	 * Port is listening only if the listener is active.
	 * 
	 * @return Listening port.
	 */
	public int port () {
		return port;
	}
	
// Status
	@Override
	public boolean isActive () {
		return super.isActive () && socket != null;
	}
	
// Service
	/**
	 * 
	 * @param aPort
	 *            - acceptable port
	 * @return socket attached to `aPort' if `aPort' is available
	 */
	protected @Nullable ServerSocket maybeSocketOn (int aPort) {
		assert 1024 <= aPort && aPort <= 65555: "require: `aPort' is an assignable port.";
		
		ServerSocket result;
		
		try {
			result = new ServerSocket (aPort);
		}
		catch (IOException e) {
			result = null;
		}
		
		assert result == null || result.getLocalPort () == aPort: "ensure: result is null or else it uses `aPort'.";
		return result;
	}
	
	/**
	 * 
	 * @param aLowerPort
	 *            - lower acceptable port
	 * @param aUpperPort
	 *            - upper acceptable port
	 * @return socket attached to a port between `aLowerPort' and `aUpperPort' if available.
	 */
	protected @Nullable ServerSocket maybeSocketBetween (int aLowerPort, int aUpperPort) {
		assert 1024 <= aLowerPort && aLowerPort <= 65555: "require: `aLowerPort' is an assignable port.";
		assert 1024 <= aUpperPort && aUpperPort <= 65555: "require: `aUpperPort' is an assignable port.";
		assert aLowerPort <= aUpperPort: "require: valid range";
		
		ServerSocket result;
		int candidate;
		
		result = null;
		candidate = aLowerPort;
		
		do {
			result = maybeSocketOn (candidate);
			candidate++;
		}
		while (result == null || candidate <= aUpperPort);
		
		assert result == null || aLowerPort <= result.getLocalPort ()
				&& result.getLocalPort () <= aUpperPort: "ensure: result is null or else it uses a port between `aLowerPort' and `aUpperPort'";
		return result;
	}
	
// Thread operation
	@Override
	public void interrupt () {
		@Nullable ServerSocket localSocket = socket;
		
		super.interrupt ();
		try {
			if (localSocket != null) {
				localSocket.close ();
			}
		}
		catch (IOException e) {
			// TODO Logging
			e.printStackTrace();
		}
	}
	
// Implementation	
	/**
	 * Server port.
	 */
	protected int port;
	
	/**
	 * Server socket.
	 */
	protected final @Nullable ServerSocket socket;
	
	@Override
	protected @Nullable Socket last () {		
		@Nullable Socket rseult;
		
		rseult = null;
		try {
			assert socket != null: "check: `socket' exists";
			rseult = socket.accept (); // wait a client
		}
		catch (IOException e) {
			if (! isInterrupted ()) {
				// TODO Logging
				e.printStackTrace();
			}
			else {
				assert isInterrupted (): "check: `socket' was closed with `interrupt'.";
			}
		}
		
		return rseult;
	}
	
}
