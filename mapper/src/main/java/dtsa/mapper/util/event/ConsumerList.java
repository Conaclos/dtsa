package dtsa.mapper.util.event;

import java.util.LinkedList;
import java.util.function.Consumer;

/**
 * @description Consumer sequence. USe for event handling.
 * @author Victorien Elvinger
 * @date 2014/06/25
 *
 * @param <T> - Type of data to consume
 */
public class ConsumerList <T> {
	
// Creation
	/**
	 * Create an empty consumer list
	 */
	public ConsumerList () {
		representation = new LinkedList <> ();
		
		assert isEmpty (): "ensure: no items";
	}
	
// Acces
	/**
	 * @return Count of actions.
	 */
	public synchronized int size () {
		return representation.size ();
	}
	
// Status
	/**
	 * @return Is there no items?
	 */
	public synchronized boolean isEmpty () {
		return size () == 0;
	}
	
// Change
	/**
	 * Add a new action.
	 * @param aConsumer - action.
	 */
	public synchronized void add (Consumer<T> aConsumer) {
		representation.add (aConsumer);
		
		assert ! isEmpty (): "ensure: is not empty";
	}
	
// Process
	/**
	 * Run each action in a separate thread.
	 * @param aData - Data to consume
	 */
	public synchronized void separateRun (T aData) {
		for (Consumer <T> action : representation) {
			(new Thread (() -> action.accept (aData))).start ();
		}
	}
	
	/**
	 * Run sequentially each actions.
	 * @param aData - Data to consume
	 */
	public synchronized void run (T aData) {
		for (Consumer <T> action : representation) {
			action.accept (aData);
		}
	}
	
// Implementation
	/**
	 * List of actions.
	 */
	protected final LinkedList <Consumer <T>> representation;
	
}
