package dtsa.util.communication.session;


import java.io.IOException;
import java.net.Socket;

import com.google.java.contract.Requires;

import dtsa.util.communication.base.RepeatableTask;

/**
 * @description Client session.
 * @author Victorien Elvinger
 * @date 2014/06/23
 *
 */
public abstract class ServerSession
		extends RepeatableTask {
	
// Creation
	@Requires ({
		"aSocket != null"
	})
	public ServerSession (Socket aSocket) {
		super ();
		
		socket = aSocket;
		
		assert socket == aSocket: "ensure: `socket' set with `aSocket'.";
	}
	
// Thread operation
	@Override
	public void interrupt () {
		super.interrupt ();
		try {
			socket.close ();
		}
		catch (IOException e) {}
	}

// Implementation
	/**
	 * Client socket.
	 */
	protected Socket socket;
	
}
