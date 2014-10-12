package dtsa.mapper.base;

import org.picocontainer.MutablePicoContainer;

import dtsa.mapper.client.base.MockMapperResponseVisitor;
import dtsa.mapper.client.request.DistributedProjectTestingClientRequest;
import dtsa.mapper.client.request.EchoClientRequest;
import dtsa.mapper.client.request.ResultMergingClientRequest;
import dtsa.mapper.client.request.RetrieveClientRequest;
import dtsa.mapper.client.request.StartingInstancesClientRequest;
import dtsa.mapper.client.request.StoreClientRequest;
import dtsa.mapper.client.response.DistributedProjectTestingMapperResponse;
import dtsa.mapper.client.response.EchoMapperResponse;
import dtsa.mapper.client.response.MapperExceptionResponse;
import dtsa.mapper.client.response.RetrieveMapperResponse;
import dtsa.mapper.client.response.StartingInstancesMapperResponse;
import dtsa.mapper.client.response.StoreMapperResponse;
import dtsa.mapper.cloud.aws.base.AWSClientRequestVisitor;
import dtsa.mapper.cloud.mapped.base.DefaultMappedProxyFactory;
import dtsa.util.aws.AWSConfiguration;
import dtsa.util.communication.base.Request;
import dtsa.util.communication.base.RequestVisitor;
import dtsa.util.communication.base.Response;
import dtsa.util.communication.base.ResponseVisitor;
import dtsa.util.communication.session.DefaultApplication;
import dtsa.util.configuration.UnmatchableTypeException;
import dtsa.util.configuration.UnparsableException;
import dtsa.util.json.LabeledJson2Request;
import dtsa.util.json.LabeledJson2Response;
import dtsa.util.json.Request2LabeledJson;
import dtsa.util.json.Response2LabeledJson;

/**
 *
 * @description Application setup.
 * @author Victorien ELvinger
 * @date 2014
 *
 */
public class MapperApplication
	extends DefaultApplication {

// Constant
	/**
	 * AWS configuration filename.
	 */
	public final static String AWSConfiguration = "aws.json";

	/**
	 * SErvice configuration filename.
	 */
	public final static String ServiceConfiguration = "service.json";

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
		AWSConfiguration awsCnfig;
		ServiceConfiguration serviceConfig;

		mutableInjector = super.defaultDependencyInjector ();

		// Client request and server response
		json2Request = new LabeledJson2Request <> ();
		json2Request.add ("echo", EchoClientRequest.class);
		json2Request.add ("store", StoreClientRequest.class);
		json2Request.add ("retrieve", RetrieveClientRequest.class);
		json2Request.add ("merge", ResultMergingClientRequest.class);
		json2Request.add ("starting_instances", StartingInstancesClientRequest.class);
		json2Request.add ("distributed_testing", DistributedProjectTestingClientRequest.class);
		mutableInjector.addComponent (json2Request);

		response2Json = new Response2LabeledJson <> ();
		response2Json.add ("echo", EchoMapperResponse.class);
		response2Json.add ("store", StoreMapperResponse.class);
		response2Json.add ("retrieve", RetrieveMapperResponse.class);
		response2Json.add ("starting_instances", StartingInstancesMapperResponse.class);
		response2Json.add ("distributed_testing", DistributedProjectTestingMapperResponse.class);
		response2Json.add ("exception", MapperExceptionResponse.class);
		mutableInjector.addComponent (response2Json);

		// Server request and Service response
		request2Json = new Request2LabeledJson <> ();
		request2Json.add ("echo", dtsa.mapped.client.request.EchoClientRequest.class);
		request2Json.add ("compilation", dtsa.mapped.client.request.ProjectCompilationClientRequest.class);
		request2Json.add ("testing", dtsa.mapped.client.request.ProjectTestingClientRequest.class);
		mutableInjector.addComponent (request2Json);

		json2Response = new LabeledJson2Response <> ();
		json2Response.add ("echo", dtsa.mapped.client.response.EchoMappedResponse.class);
		json2Response.add ("compilation", dtsa.mapped.client.response.ProjectCompilationMappedResponse.class);
		json2Response.add ("testing", dtsa.mapped.client.response.ProjectTestingMappedResponse.class);
		json2Response.add ("exception", dtsa.mapped.client.response.MappedExceptionResponse.class);
		mutableInjector.addComponent (json2Response);

		// Extra configurations
		try {
			awsCnfig = configurations.labeled (AWSConfiguration, AWSConfiguration.class);
			mutableInjector.addComponent (awsCnfig);

			serviceConfig = configurations.labeled (ServiceConfiguration, ServiceConfiguration.class);
			mutableInjector.addComponent (serviceConfig);

			// AWS Client Request Visitor
			mutableInjector.addComponent (AWSClientRequestVisitor.class);
			mutableInjector.addComponent (MockMapperResponseVisitor.class);

			mutableInjector.addComponent (DefaultMappedProxyFactory.class);
		}
		catch (UnparsableException | UnmatchableTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return mutableInjector;
	}

}
