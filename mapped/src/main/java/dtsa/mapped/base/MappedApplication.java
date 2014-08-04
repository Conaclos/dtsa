package dtsa.mapped.base;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.picocontainer.MutablePicoContainer;

import dtsa.mapped.back.local.base.LocalClientRequestVisitor;
import dtsa.mapped.back.local.base.LocalConfiguration;
import dtsa.mapped.client.request.ProjectCompilationClientRequest;
import dtsa.mapped.client.request.EchoClientRequest;
import dtsa.mapped.client.request.ProjectTestingClientRequest;
import dtsa.mapped.client.response.EchoMappedResponse;
import dtsa.mapped.client.response.MappedExceptionResponse;
import dtsa.mapped.client.response.ProjectCompilationMappedResponse;
import dtsa.mapped.client.response.ProjectTestingMappedResponse;
import dtsa.util.json.LabeledJson2Request;
import dtsa.util.json.LabeledJson2Response;
import dtsa.util.json.Request2LabeledJson;
import dtsa.util.json.Response2LabeledJson;
import dtsa.util.communication.base.Request;
import dtsa.util.communication.base.RequestVisitor;
import dtsa.util.communication.base.Response;
import dtsa.util.communication.base.ResponseVisitor;
import dtsa.util.communication.session.DefaultApplication;
import dtsa.util.configuration.UnmatchableTypeException;
import dtsa.util.configuration.UnparsableException;

/**
 * 
 * @description Retrieve configurations and setup dependencies.
 * @author Victorien ELvinger
 * @date 2014
 *
 */
public class MappedApplication
		extends DefaultApplication {
	
// Constant
	/**
	 * Server configuration filename.
	 */
	public final static String LocalConfiguration = "local.json";
	
// Implementation	
	/**
	 * Default dependency injector.
	 */
	@Override
	protected MutablePicoContainer defaultDependencyInjector () {
		MutablePicoContainer mutableInjector;
		LabeledJson2Request <Request <? extends RequestVisitor>> json2Request;
		Response2LabeledJson <Response <? extends ResponseVisitor>> response2Json;
		Request2LabeledJson <Request <? extends RequestVisitor>> request2Json;
		LabeledJson2Response <Response <? extends ResponseVisitor>> json2Response;
		LocalConfiguration localConfig;
		Logger logger;
		
		mutableInjector = super.defaultDependencyInjector ();
		
		try {
			localConfig = configurations.labeled (LocalConfiguration, LocalConfiguration.class);
			mutableInjector.addComponent (localConfig);
		}
		catch (UnparsableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (UnmatchableTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		// Client request and server response
		json2Request = new LabeledJson2Request <> ();
		json2Request.add ("echo", EchoClientRequest.class);
		json2Request.add ("compilation", ProjectCompilationClientRequest.class);
		json2Request.add ("testing", ProjectTestingClientRequest.class);
		mutableInjector.addComponent (json2Request);

		response2Json = new Response2LabeledJson <> ();
		response2Json.add ("echo", EchoMappedResponse.class);
		response2Json.add ("compilation", ProjectCompilationMappedResponse.class);
		response2Json.add ("testing", ProjectTestingMappedResponse.class);
		response2Json.add ("exception", MappedExceptionResponse.class);
		mutableInjector.addComponent (response2Json);
		
		
		mutableInjector.addComponent (LocalClientRequestVisitor.class);
		mutableInjector.addComponent (MockMappedResponseVisitor.class);
		
		// Logger
		logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
		logger.setLevel (Level.INFO);
		
		
		return mutableInjector;
	}
	
}
