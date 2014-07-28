package dtsa.util.communication.writer;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import dtsa.util.communication.base.RepeatableTask;

/**
 * 
 * @description Consumer.
 * @author Victorien ELvinger
 * @date 2014
 *
 * @param <I> NonNull Input type.
 */
public abstract class OutputWriter <I> 
	extends RepeatableTask {
	
// Creation
	/**
	 * Create an output writer with a storage capacity of `aCapcity'.
	 * @param aCapcity
	 */
	public OutputWriter (int aCapcity) {
		super ();
		queue = new ArrayBlockingQueue <> (aCapcity);
		
		assert queue.isEmpty (): "ensure: no queued input.";
	}
	
// Other	
	/**
	 * Transmitt `aObject'.
	 * @param aObject
	 * @throws InterruptedException
	 */
	public void write (I aObject) throws InterruptedException {
		queue.put (aObject);
	}
	
// Implementation	
	/**
	 * Request queue.
	 */
	protected final BlockingQueue <I> queue;
	
	/**
	 * Listen and queue valid inputs.
	 */
	@Override
	protected final void process () {
		try {
			I o = queue.take ();
			print (o);
		}
		catch (InterruptedException e) {
			e.printStackTrace ();
			assert false: "stop writer";
		}
	}
	
	/**
	 * Print  `aObject'.
	 * @param aObject
	 */
	protected abstract void print (I aObject);
	
}
