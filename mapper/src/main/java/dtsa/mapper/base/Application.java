package dtsa.mapper.base;

import java.io.IOException;
import java.net.Socket;

import dtsa.mapper.client.base.MapperCLientSession;
import dtsa.mapper.util.communication.ApplicationSession;
import dtsa.mapper.util.communication.ClientListener;

/**
 * 
 * @author Victorien Elvinger
 * @date 2014/06/25
 *
 */
public class Application
	extends ApplicationSession
		implements Runnable {
	
// Creation
	public Application () {
		super (new ClientListener (DefaultPort));
	}
	
// Configuration
	/**
	 * Default server port.
	 */
	public final static int DefaultPort = 2014;
	
// Implementation	
	@Override
	public void launchSession (Socket aSocket) {
		try {
			final MapperCLientSession session = new MapperCLientSession (aSocket);
			session.run ();
		}
		catch (IOException e) {
			// TODO Logging
			e.printStackTrace ();
		}
	}
	
}
