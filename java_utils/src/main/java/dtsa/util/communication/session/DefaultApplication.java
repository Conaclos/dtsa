package dtsa.util.communication.session;

import org.picocontainer.PicoContainer;
import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.injectors.ConstructorInjection;

import dtsa.util.annotation.Nullable;
import dtsa.util.configuration.TypedResources;
import dtsa.util.configuration.UnmatchableTypeException;
import dtsa.util.configuration.UnparsableException;
import dtsa.util.log.Logger;
import dtsa.util.log.MuteLogger;
import dtsa.util.log.SimpleLogger;
import dtsa.util.communication.listener.DefaultClientListener;
import dtsa.util.communication.listener.ConvertibleObjectListener;
import dtsa.util.communication.session.DefaultApplicationSession;
import dtsa.util.communication.session.ServerConfiguration;
import dtsa.util.communication.writer.ConvertibleObjectWriter;

/**
 * 
 * @description Retrieve configurations and setup dependencies.
 * @author Victorien ELvinger
 * @date 2014
 *
 */
public abstract class DefaultApplication
		implements Runnable {
	
// Creation
	public DefaultApplication () {
		configurations = new TypedResources (ResourceConfiguration);
		logger = new SimpleLogger ();
		injector = defaultDependencyInjector ();
	}
	
// Constant
	/**
	 * Directory for configuration files.
	 */
	public final static String ResourceConfiguration = "/configurations/";
	
	/**
	 * Server configuration filename.
	 */
	public final static String ServerConfiguration = "server.json";

// Access
	/**
	 * @return Application Session.
	 *
	 */
	public @Nullable DefaultApplicationSession getSession () {
		return session;
	}
	
// Other
	@Override
	public void run () {
		@Nullable DefaultApplicationSession localSession = injector.getComponent (DefaultApplicationSession.class);
		
		if (localSession != null) {
			session = localSession;
			localSession.run ();
		}
		else {
			assert false: "check: MappedApplicationSession.class is injectable.";
		}
	}
	
// Implementation
	/**
	 * @see #getSession ()
	 */
	protected @Nullable DefaultApplicationSession session;
	
	/**
	 * Configuration retriever.
	 */
	protected TypedResources configurations;
	
	/**
	 * Logger.
	 */
	protected Logger logger;
	
	/**
	 * Dependency injector for instantiate type or provide singletons.
	 */
	protected PicoContainer injector;
	
	/**
	 * Default dependency injector.
	 */
	protected MutablePicoContainer defaultDependencyInjector () {
		ServerConfiguration serverConfig;
		MutablePicoContainer mutableInjector;
		
		mutableInjector = new DefaultPicoContainer (new ConstructorInjection());
		mutableInjector.addComponent (PicoContainer.class, mutableInjector); // Read-only access
		
		try {
			serverConfig = configurations.labeled (ServerConfiguration, ServerConfiguration.class);
			mutableInjector.addComponent (serverConfig);
			mutableInjector.addComponent (serverConfig.getClientConfiguration ());
			
			// Exception handling finished.
			mutableInjector.addComponent (configurations);
			
			if (serverConfig.isExceptionLogging ()) {
				mutableInjector.addComponent (Logger.class, new SimpleLogger ());
			}
			else {
				mutableInjector.addComponent (Logger.class, new MuteLogger ());				
			}
			
			mutableInjector.addComponent (new DefaultClientListener (serverConfig.getPort ()));

			mutableInjector.addComponent (DefaultApplicationSession.class);
			mutableInjector.addComponent (DefaultServerSessionFactory.class);
			
			mutableInjector.addComponent (ConvertibleObjectListener.class);
			mutableInjector.addComponent (ConvertibleObjectWriter.class);
		}
		catch (UnparsableException e) {
			logger.logException (e);
		}
		catch (UnmatchableTypeException e) {
			logger.logException (e);
		}
		
		return mutableInjector;
	}
	
}
