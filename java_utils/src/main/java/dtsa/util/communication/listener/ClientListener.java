package dtsa.util.communication.listener;

import java.net.Socket;

/**
 * 
 * @description Listener producing client sockets.
 * @author Victorien ELvinger
 * @date 2014
 *
 * @param <I> NonNull Input type.
 */
public abstract class ClientListener
extends InputListener <Socket> {

// Creation
	public ClientListener (int aCapcity) {
		super (aCapcity);
	}
	
}
