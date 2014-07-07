package dtsa.mapper.client.base;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

import dtsa.mapper.client.request.Request;
import dtsa.mapper.util.annotation.Nullable;
import dtsa.mapper.util.communication.InputListener;
import dtsa.mapper.util.configuration.ConfigurationParsingException;
import dtsa.mapper.util.configuration.InvalidParameterException;
import dtsa.mapper.util.json.InvalidJSONException;
import dtsa.mapper.util.json.TypeJsonManager;
import dtsa.mapper.util.json.UnrecordedLabelException;
import static dtsa.mapper.util.configuration.SharedLabeledConfigurations.configurations;

/**
 * 
 * @description Listen network and queue authorized requests.
 * @author Victorien ELvinger
 * @date 2014/07/2
 *
 */
public class RequestListener 
	extends InputListener <Request> {
	
// Creation
	/**
	 * Create a request listener for `aInput'.
	 * @param aInput
	 * @throws InvalidParameterException
	 * @throws ConfigurationParsingException
	 */
	public RequestListener (BufferedReader aInput) throws InvalidParameterException, ConfigurationParsingException {
		super (MaximumPendingRequests);
		
		in = aInput;
		manager = new TypeJsonManager <> ();
		initializeJsonManager ();
		
		assert queue.isEmpty (): "ensure: no request queued.";
	}
	
// Constant
	/**
	 * Label for global AWS configuration.
	 */
	public final static String AWSConfigurationLabel = "dtsa_request";
	
	/**
	 * Maximum number of queued requests.
	 */
	public final static int MaximumPendingRequests = 10;
	
// Thread operation
	@Override
	public void interrupt () {
		super.interrupt ();
		try {
			in.close ();
		}
		catch (IOException e) {
			// TODO Logging
			e.printStackTrace();
		}
	}
	
// Implementation
	/**
	 * Input of the client.
	 */
	protected final BufferedReader in;
	
	/**
	 * JSON to Request manager
	 */
	protected final TypeJsonManager <Request> manager;
	
	@Override
	protected @Nullable Request last () {
		@Nullable Request result;
		@Nullable String str;
		
		result = null;
		try {
			str = in.readLine ();
			if (str != null) {
				result = manager.instance (str);
			}
		}
		catch (IOException e) {
			if (! isInterrupted ()) {
				// TODO Logging
				e.printStackTrace();
			}
			else {
				Thread.currentThread ().interrupt ();
			}
		}
		catch (InvalidJSONException e) {
			// TODO Logging
			e.printStackTrace();
		}
		catch (UnrecordedLabelException e) {
			// TODO Logging
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * Use request configuration file to create mapping between types and labels.
	 * @throws InvalidParameterException 
	 * @throws ConfigurationParsingException 
	 */
	protected void initializeJsonManager () throws InvalidParameterException, ConfigurationParsingException {
		@Nullable Map <String, String> props;
		@Nullable Class <? extends Request> c;
		String key;
		
		props = configurations.labeled (AWSConfigurationLabel);
		if (props != null) {
			for (Map.Entry <String, String> pair : props.entrySet ()) {
				key = pair.getKey ();
				try {
					c = (Class <? extends Request>) Class.forName (pair.getValue ());
					assert key != null: "check: Key exists";
					assert c != null: "check: Class exists.";
					manager.add (key, c);
				}
				catch (ClassNotFoundException | ClassCastException e) {
					throw new InvalidParameterException (AWSConfigurationLabel, key);
				}
			}
		}
	}
	
}
