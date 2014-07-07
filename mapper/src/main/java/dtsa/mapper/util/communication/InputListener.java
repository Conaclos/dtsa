package dtsa.mapper.util.communication;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import dtsa.mapper.util.annotation.Nullable;

/**
 * 
 * @description Listener producing inputs.
 * @author Victorien ELvinger
 * @date 2014
 *
 * @param <I> Input type
 */
public abstract class InputListener <I> 
	extends RepeatableTask {
	
// Creation
	/**
	 * Create an input listener with a storage capacity of `aCapcity'.
	 * @param aCapcity
	 */
	public InputListener (int aCapcity) {
		super ();
		queue = new ArrayBlockingQueue <> (aCapcity);
		
		assert queue.isEmpty (): "ensure: no queued input.";
	}
	
// Access
	/**
	 * Get and remove the oldest input.
	 * If no input is available, then wait one until `aTimeout'.
	 * @Concurrent
	 * @SideEffect
	 * 
	 * @return Oldest input or null if `timeout' elapses before an input is available.
	 */
	public @Nullable I maybeNext (int aTimeout) throws InterruptedException {
		return queue.poll (aTimeout, TimeUnit.SECONDS);
	}
	
	/**
	 * Get and remove the oldest input.
	 * If no input is available, then wait one.
	 * @Concurrent
	 * @SideEffect
	 * 
	 * @return Oldest input.
	 */
	public I next () throws InterruptedException {
		return queue.take ();
	}
	
// Implementation	
	/**
	 * Request queue.
	 */
	protected final BlockingQueue <I> queue;
	
	/**
	 * Listen and retrieve last input.
	 * During input listening an `java.io.IOException' can be triggered 
	 * due to the call of `close' by `interrupt'.
	 * 
	 * @SideEffect
	 * @return last input or null if the input is invalid.
	 */
	protected abstract I last ();
	
	/**
	 * Listen and queue valid inputs.
	 */
	@Override
	protected final void process () {
		@Nullable I localLast;
		
		try {
			localLast = last ();
			if (localLast != null) {
				queue.put (localLast);
			}
		}
		catch (InterruptedException e) {
			assert ! isInterrupted (): "check: current htread is not interrupted";
			Thread.currentThread ().interrupt ();
			assert isInterrupted (): "check: current htread is interrupted";
		}
	}
	
}
