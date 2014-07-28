package dtsa.util.communication.base;

import java.util.concurrent.atomic.AtomicBoolean;

import com.google.java.contract.Ensures;

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
	/**
	 * Create an active repeatable task.
	 */
	@Ensures ("! isAlive ()")
	public RepeatableTask () {
		super ();
		isActive = new AtomicBoolean (true);
		
		assert ! isAlive (): "ensure: Thread not already started.";
	}
	
// Status
	/**
	 * 
	 * @return Looping is enabled?
	 */
	public boolean isActive () {
		return isActive.get ();
	}
	
// Thread operation
	@Override
	public final void run () {		
		initialize ();
		while (isActive ()) {
			process ();
		}
	}
	
	@Ensures ("! isActive ()")
	@Override
	public void interrupt () {
		System.out.println ("interruption de " + getClass ().getName ()); // TODO remove it
		super.interrupt ();
		isActive.set (false);
		
		assert ! isActive (): "ensure: is not active.";
	}
	
// Implementation
	/**
	 * Looping is enabled?
	 */
	protected final AtomicBoolean isActive;
	
	/**
	 * Process one iteration.
	 */
	protected abstract void process ();
	
	/**
	 * Initialize states.
	 * Default: do nothing.
	 */
	protected void initialize () {}
	
}
