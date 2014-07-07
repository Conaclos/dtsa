package dtsa.mapper.util.communication;

import java.net.Socket;

/**
 * @description Application session witha client listener.
 * @author Victorien Elvinger
 * @ 2014/06/26
 *
 */
public abstract class ApplicationSession 
	extends RepeatableTask {
	
// Creation
	public ApplicationSession (InputListener <Socket> aListener) {
		listener = aListener;
		listener.start ();
	}
	
// Thread operation
	@Override
	public void interrupt () {
		super.interrupt ();
		listener.interrupt ();
	}
	
// Implementation
	/**
	 * CLient listener.
	 */
	protected final InputListener <Socket> listener;
	
	/**
	 * Run a typical application session.
	 */
	@Override
	protected void process () {
		try {
			Socket s = listener.next ();
			new Thread (() -> launchSession (s)).start ();
		}
		catch (InterruptedException e) {
			Thread.currentThread ().interrupt ();
			// TODO Logging
			e.printStackTrace();
		}
	}
	
	/**
	 * Create a new session with `aSocket' and run it.
	 * 
	 * @param aSocket
	 */
	protected abstract void launchSession (Socket aScoket);
	
}
