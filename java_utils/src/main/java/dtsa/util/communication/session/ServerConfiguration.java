package dtsa.util.communication.session;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @description Server configuration.
 * @author Victorien ELvinger
 * @date 2014
 *
 */
public class ServerConfiguration {
	
// Creation
	/**
	 * 
	 * @param aPort
	 *            - {@link #getPort()}
	 * @param aExceptionLogging
	 *            - {@link #isExceptionLogging()}
	 */
	@JsonCreator
	public ServerConfiguration (@JsonProperty ("port") int aPort, 
			@JsonProperty ("exceptionLogging") boolean aExceptionLogging,
			@JsonProperty ("client") ServerSessionConfiguration aClientConfiguration) {
		
		assert 1024 <= aPort && aPort <= 65555: "require: `aPort' is an assignable port.";
		
		if (! (1024 <= aPort && aPort <= 65555)) {
			throw new IllegalArgumentException ("'port' should be included between 1024 and 65555");
		}
		else if (aClientConfiguration == null) {
			throw new IllegalArgumentException ("''client' should be not null");
		}
		
		port = aPort;
		exceptionLogging = aExceptionLogging;
		clientConfiguration = aClientConfiguration;
		
		assert getPort () == aPort: "ensure: `getPort' set with `aPort'";
		assert isExceptionLogging () == aExceptionLogging: "ensure: `getExceptionLogging' set with `aExceptionLogging'";
		assert getClientConfiguration () == aClientConfiguration: "ensure: `getClientConfiguration' set with `aClientConfiguration'";
	}
	
// Access
	/**
	 * 
	 * @return Server port.
	 */
	public int getPort () {
		return port;
	}
	
	/**
	 * 
	 * @return Is logging of exception enabled?
	 */
	public boolean isExceptionLogging () {
		return exceptionLogging;
	}

	/**
	 * 
	 * @return Configuration for client sessions.
	 */
	public ServerSessionConfiguration getClientConfiguration () {
		return clientConfiguration;
	}
	
// Implementation
	/**
	 * @see #getTimeout ()
	 */
	protected int port;
	
	/**
	 * @see #isExceptionLogging()
	 */
	protected boolean exceptionLogging;
	
	/**
	 * @see #getClientConfiguration()
	 */
	protected ServerSessionConfiguration clientConfiguration;
	
}
