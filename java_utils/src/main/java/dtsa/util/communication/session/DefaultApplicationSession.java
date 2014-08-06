package dtsa.util.communication.session;

import java.net.Socket;
import java.util.function.Function;

import dtsa.util.annotation.Nullable;
import dtsa.util.communication.base.RepeatableTask;
import dtsa.util.communication.listener.ClientListener;
import dtsa.util.communication.listener.InputListener;
import dtsa.util.log.Logger;

/**
 * @description Application session with a client listener.
 * @author Victorien Elvinger
 * @ 2014/06/26
 *
 */
public class DefaultApplicationSession 
	extends RepeatableTask {
	
// Creation
	/**
	 * @param aListener - Client Listener
	 * @param aLogger - Logger
	 * @param aInjector - dependency injector
	 */
	public DefaultApplicationSession (ClientListener aListener, Function <Socket, ServerSession> aSssionFactory, Logger aLogger) {
		listener = aListener;
		sessionFactory = aSssionFactory;
		logger = aLogger;
		
		listener.start ();
		
		assert listener == aListener: "ensure: `listener' set with `aListener'";
		assert logger == aLogger: "ensure: `logger' set with `aLogger'";
		assert sessionFactory == aSssionFactory: "ensure: `sessionFactory' set with `aSssionFactory'";
		assert listener.isAlive (): "ensure: listener launched";
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
	 * Logger.
	 */
	protected Logger logger;
	
	/**
	 * Dependency injector for instantiate type or provide singletons.
	 */
	protected Function <Socket, ServerSession> sessionFactory;
	
	/**
	 * Create a client session with `launchSession' for each new clients.
	 */
	@Override
	protected void process () {
		Socket s;
		Thread t;
		
		try {
			s = listener.next ();
			t = new Thread (() -> launchSession (s));
			t.start ();
		}
		catch (InterruptedException e) {
			Thread.currentThread ().interrupt ();
			logger.logException (e);
		}
	}
	
	/**
	 * Create a new client session with `aSocket' and run it.
	 * 
	 * @param aSocket - client socket
	 */
	protected void launchSession (Socket aScoket) {
		@Nullable ServerSession maybe;
		
		maybe = sessionFactory.apply (aScoket);
		if (maybe != null) {
			maybe.run ();
		}
		else {
			assert false: "Check: Session created";
		}
	}
	
}
