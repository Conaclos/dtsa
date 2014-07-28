package dtsa.mapper.cloud.aws.base;

import java.util.ArrayList;
import java.util.Collection;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.ec2.AmazonEC2Client;

import dtsa.mapper.base.ServiceConfiguration;
import dtsa.mapper.client.request.ClientRequestVisitor;
import dtsa.mapper.client.request.EchoClientRequest;
import dtsa.mapper.client.request.ProjectTestingClientRequest;
import dtsa.mapper.client.request.StartingInstancesClientRequest;
import dtsa.mapper.client.request.StoreClientRequest;
import dtsa.mapper.client.response.EchoMapperResponse;
import dtsa.mapper.client.response.MapperExceptionResponse;
import dtsa.mapper.client.response.StartingInstancesMapperResponse;
import dtsa.mapper.client.response.StoreMapperResponse;
import dtsa.mapper.cloud.mapped.base.MappedProxy;
import dtsa.mapper.cloud.mapped.base.MappedProxyFactory;
import dtsa.mapper.cloud.mapped.request.EchoMapperRequest;
import dtsa.mapper.cloud.mapped.request.ProjectCompilationMapperRequest;
import dtsa.mapper.cloud.mapped.response.EchoServiceResponse;
import dtsa.util.annotation.NonNull;
import dtsa.util.annotation.Nullable;
import dtsa.util.aws.AWS;
import dtsa.util.aws.AWSConfiguration;
import dtsa.util.aws.EC2InstancePool;
import dtsa.util.aws.S3Bucket;
import dtsa.util.configuration.UnmatchableTypeException;
import dtsa.util.configuration.UnparsableException;
import dtsa.util.file.DirectoryCompressionException;
import dtsa.util.file.UnreachablePathException;

/**
 * 
 * @description Processor for Amazon Web Service
 * @author Victorien ELvinger
 * @date 2014/06/26
 *
 */
public class AWSClientRequestVisitor
		extends AWS
		implements ClientRequestVisitor {
	
// Creation
	/**
	 * 
	 * @param aConfiguration - Server configuration
	 * @param aServiceConfiguration - Remote service configuration
	 */
	public AWSClientRequestVisitor (AWSConfiguration aConfiguration, ServiceConfiguration aServiceConfiguration, 
			MappedProxyFactory aFactory) {
		super (aConfiguration);
		serviceConfiguration = aServiceConfiguration;
		factory = aFactory;
	}
	
// Status
	/**
	 * @return Does current processor give answer for requests?
	 * Answer: true
	 */
	@Override
	public boolean isReactive () {
		return true;
	}
	
// Processing
	@Override
	public void visitStore (StoreClientRequest aRequest) {
		S3Bucket bucket;
		String name;
		
		try {
			bucket = defaultBucket ();
			name = "" + bucket.salt ();
			bucket.storeFromPathAs (aRequest.getPath (), name);
			aRequest.setResponse (new StoreMapperResponse (bucket.createURI (name)));
			System.out.println (aRequest.response ().getURI ());
		}
		catch (AmazonServiceException e) {
			aRequest.setException (new MapperExceptionResponse (e));
			// TODO create an exception processor for logging
			e.printStackTrace();
		}
		catch (UnreachablePathException e) {
			aRequest.setException (new MapperExceptionResponse (e));
			// TODO create an exception processor for logging
			e.printStackTrace();
		}
		catch (DirectoryCompressionException e) {
			aRequest.setException (new MapperExceptionResponse (e));
			// TODO create an exception processor for logging
			e.printStackTrace();
		}
		catch (UnparsableException e) {
			aRequest.setException (new MapperExceptionResponse (e));
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (UnmatchableTypeException e) {
			aRequest.setException (new MapperExceptionResponse (e));
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assert aRequest.hasResponse () || aRequest.hasException (): "ensure: `aRequest' has a response or an exception.";
	}
	
	@Override
	public void visitStartingInstances (StartingInstancesClientRequest aRequest) {
		EC2InstancePool instance;
		
		try {
			instance = newInstancePool ();
			instance.launch (aRequest.getInstanceCount ());
			aRequest.setResponse (new StartingInstancesMapperResponse (instance.count (), instance.privateKey (), instance.ids (), instance.ips ()));
		}
		catch (AmazonServiceException e) {
			aRequest.setException (new MapperExceptionResponse (e));
			// TODO create an exception processor for logging
			e.printStackTrace();
		}
		catch (UnparsableException e) {
			aRequest.setException (new MapperExceptionResponse (e));
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (UnmatchableTypeException e) {
			aRequest.setException (new MapperExceptionResponse (e));
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assert aRequest.hasResponse () || aRequest.hasException (): "ensure: `aRequest' has a response or an exception.";
	}
	
	@Override
	public void visitProjectTesting (@NonNull ProjectTestingClientRequest aVisited) {
		@Nullable Collection <? extends Object> groups;
		@Nullable StartingInstancesMapperResponse startingInstancesResponse;
		@Nullable MapperExceptionResponse exception;
		StartingInstancesClientRequest startingInstances;
		ArrayList <MappedProxy> mappeds;
		MappedProxy mapped;
		int instanceCount;
		
		groups = aVisited.getClusters ();
		if (groups != null) {
			instanceCount = groups.size ();
		}
		else {
			instanceCount = 1;
		}
		
		startingInstances = new StartingInstancesClientRequest (instanceCount);
		visitStartingInstances (startingInstances);
		
		startingInstancesResponse = startingInstances.response ();
		exception = startingInstances.exception ();
		if (startingInstancesResponse != null) {
			try {
				mappeds = new ArrayList <> (startingInstancesResponse.getIds ().size ());
				for (String id : startingInstancesResponse.getIds ()) {
					mapped = factory.apply (serviceConfiguration.getPort (), 
							new AWSInstanceAvailability (id, new AmazonEC2Client (defaultCredentials ()), 
									5000));
					mapped.start ();
					mappeds.add (mapped);
				}
				
				for (MappedProxy item : mappeds) {
					item.write (new ProjectCompilationMapperRequest (aVisited.getUri (), aVisited.getProject (),
							aVisited.getConfiguration (), aVisited.getTarget ()));
				}
				
				for (MappedProxy item : mappeds) {
					item.maybeNext (120000, 1800);
				}
			}
			catch (UnparsableException e) {
				aVisited.setException (new MapperExceptionResponse (e));
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (UnmatchableTypeException e) {
				aVisited.setException (new MapperExceptionResponse (e));
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if (exception != null) {
			aVisited.setException (exception);
		}
		else {
			assert false: "check: starting instances has an answer.";
		}
		
	}
	
	@Override
	public void visitEcho (EchoClientRequest aVisited) {
		@Nullable StartingInstancesMapperResponse startingInstancesResponse;
		StartingInstancesClientRequest startingInstances;
		@Nullable MapperExceptionResponse exception;
		ArrayList <MappedProxy> mappeds;
		EchoServiceResponse response;
		@Nullable Object temp;
		MappedProxy mapped;
		
		if (aVisited.getHop () == 1) {
			aVisited.setResponse (new EchoMapperResponse (aVisited.getId ()));
		}
		else {
			startingInstances = new StartingInstancesClientRequest (1);
			visitStartingInstances (startingInstances);
			
			startingInstancesResponse = startingInstances.response ();
			exception = startingInstances.exception ();
			if (startingInstancesResponse != null) {
				try {
					mappeds = new ArrayList <> (startingInstancesResponse.getIds ().size ());
					for (String id : startingInstancesResponse.getIds ()) {
						mapped = factory.apply (serviceConfiguration.getPort (), 
								new AWSInstanceAvailability (id, new AmazonEC2Client (defaultCredentials ()), 
										5000));
						mapped.start ();
						mappeds.add (mapped);
					}
					
					for (MappedProxy item : mappeds) {
						item.write (new EchoMapperRequest (aVisited.getId (), aVisited.getHop ()));
					}
					
					temp = null;
					for (MappedProxy item : mappeds) {
						temp = item.maybeNext (120000, 1800);
					}
					
					if (temp != null && temp.getClass () == EchoServiceResponse.class) {
						response = (EchoServiceResponse) temp;
						aVisited.setResponse (new EchoMapperResponse (response.getId ()));
					}
					else {
						aVisited.setException (new MapperExceptionResponse ("No answer"));
					}
				}
				catch (UnparsableException e) {
					aVisited.setException (new MapperExceptionResponse (e));
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch (UnmatchableTypeException e) {
					aVisited.setException (new MapperExceptionResponse (e));
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if (exception != null) {
				aVisited.setException (exception);
			}
			else {
				assert false: "check: starting instances has an answer.";
			}
		}
		
		assert aVisited.hasResponse (): "ensure: `aRequest' has a response.";
	}
	
// Implementation
	/**
	 * Service configuration.
	 */
	protected ServiceConfiguration serviceConfiguration;
	
	/**
	 * Factory of mapped proxy.
	 */
	protected MappedProxyFactory factory;
	
}
