package dtsa.mapper.util.communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @description Client session.
 * @author Victorien Elvinger
 * @date 2014/06/23
 *
 */
public abstract class ClientSession
		extends RepeatableTask {
	
// Creation
	/**
	 * 
	 * @param aSocket
	 *            - Client socket. Should be used only in this freshly created object
	 * @exception IOException
	 */
	public ClientSession (Socket aSocket) throws IOException {
		super ();
		socket = aSocket;
		rescueCreateIO (3);
		
		assert socket == aSocket: "ensure: `socket' set with `aSocket'.";
	}
	
// Thread operation
	@Override
	public void interrupt () {
		super.interrupt ();
		try {
			socket.close ();
		}
		catch (IOException e) {
			// TODO Logging
			e.printStackTrace();
		}
	}
	
// Implementation
	/**
	 * Client socket.
	 */
	protected Socket socket;
	
	/**
	 * Input of the client.
	 */
	protected BufferedReader in;
	
	/**
	 * Output to the client.
	 */
	protected PrintWriter out;
	
	/**
	 * ATtempt to reach the success of `createIO'.
	 * @param aCount - maximum number of attempt.
	 * @throws IOException
	 */
	protected void rescueCreateIO (int aCount) throws IOException {
		assert aCount > 0: "require:  aCount`' strictly positive.";
		int i;
		
		i = 0;
		while (i < aCount) {
			i++;
			try {
				createIO ();
				i = aCount;
			}
			catch (IOException e) {
				if (in != null) {
					in.close ();
				}
				
				if (out != null) {
					out.close ();
				}
				
				if (i == aCount) {
					throw e;
				}
			}
			
			assert 0 <= i && i <= aCount: "check: loop count is valid";
		}
	}
	
	/**
	 * ATtempt to get Input and Output for communication.
	 * @param aSocket
	 * @throws IOException
	 */
	protected void createIO () throws IOException {
		in = new BufferedReader (new InputStreamReader (socket.getInputStream ()));
		out = new PrintWriter (socket.getOutputStream ());
	}
	
}
