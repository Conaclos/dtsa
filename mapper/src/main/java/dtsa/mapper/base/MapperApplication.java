package dtsa.mapper.base;

import com.amazonaws.Request;

import dtsa.mapper.cloud.aws.base.AWSRequestProcessor;
import dtsa.mapper.util.communication.ApplicationSession;
import dtsa.mapper.util.dependency.SharedDependencyInjector;
import dtsa.mapper.util.json.TypeJsonManager;

/**
 * 
 * @description Application setup.
 * @author Victorien ELvinger
 * @date 2014
 *
 */
public class MapperApplication
		extends SharedDependencyInjector
		implements Runnable {
	
// Creation
	/**
	 * Create a session and setup dependencies.
	 */
	public MapperApplication (String... args) {
		session = new MapperApplicationSession ();
		
		mutableInjector.addComponent (AWSRequestProcessor.class);
		mutableInjector.addComponent (new TypeJsonManager <Request> ());
	}
	
// Other
	@Override
	public void run () {
		session.run ();
	}
	
// Implementation
	/**
	 * Application session.
	 */
	protected ApplicationSession session;
	
}
