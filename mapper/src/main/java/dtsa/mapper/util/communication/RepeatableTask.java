package dtsa.mapper.util.communication;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 
 * @description Task repeated.
 * @author Victorien ELvinger
 * @date 2014/07/2
 *
 */
public abstract class RepeatableTask 
	extends Thread {
	
// Creation
	public RepeatableTask () {
		super ();
		isActive = new AtomicBoolean (true);
	}
	
// Status
	/**
	 * 
	 * @return Repetition is active?
	 */
	public boolean isActive () {
		return isActive.get ();
	}
	
// Thread operation
	@Override
	public final void run () {		
		while (isActive ()) {
			process ();
		}
	}
	
	@Override
	public void interrupt () {
		System.out.println ("interruption de " + getClass ().getName ()); // TODO remove it
		super.interrupt ();
		isActive.set (false);
		
		assert ! isActive (): "ensure: is not active";
	}
	
// Implementation
	/**
	 * Repetition is active?
	 */
	protected final AtomicBoolean isActive;
	
	/**
	 * Process one iteration.
	 */
	protected abstract void process ();
	
}
