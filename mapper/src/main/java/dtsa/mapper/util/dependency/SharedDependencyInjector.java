package dtsa.mapper.util.dependency;

import org.picocontainer.MutablePicoContainer;
import org.picocontainer.PicoContainer;
import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.injectors.ConstructorInjection;

/**
 * 
 * @description Share a single PicoContainer instance
 * Extends this class to setup the injector, otherwise instantiate it or import statically `injector'
 * @author Victorien ELvinger
 * @date 2014/07/7
 *
 */
public class SharedDependencyInjector {
	
// Shared
	/**
	 * Dependency injector - read-only access.
	 */
	public final static PicoContainer injector;
	
	static {
		mutableInjector = new DefaultPicoContainer (new ConstructorInjection());
	}
	
// Implementation
	/**
	 * Write access of `injector'.
	 */
	protected final static MutablePicoContainer mutableInjector;
	
	static {
		injector = mutableInjector;
	}
	
}
