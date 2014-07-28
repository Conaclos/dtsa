package dtsa.mapped.base;

import dtsa.util.annotation.Nullable;

/**
 * 
 * @description Entry point definition.
 * @author Victorien ELvinger
 * @date 2014
 *
 */
public class Root {
	
	/**
	 * Define service entry point.
	 * @param args
	 */
	public static void main (String... words) {
		if ("start".equals(words [0])) {
			start (words);
		}
		else if ("stop".equals(words [0])) {
			stop (words);
		}
		(new MappedApplication ()).run ();
	}
	
	/**
	 * Start service.
	 * @param words
	 */
	public static void start (String... words) {
		MappedApplication localApp;
		
		if (app == null) {
			localApp = new MappedApplication ();
			app = localApp;
			localApp.run ();
		}
	}
	
	/**
	 * Stop service.
	 * @param words
	 */
	public static void stop (String... words) {
		@Nullable MappedApplication localApp;
		
		localApp = app;
		if (localApp != null) {
			localApp.getSession ().interrupt ();
		}
	}
	
// Implementation
	/**
	 * Application.
	 */
	protected static @Nullable MappedApplication app;
	
}
