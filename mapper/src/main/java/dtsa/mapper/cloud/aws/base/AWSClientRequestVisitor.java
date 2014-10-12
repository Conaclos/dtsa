package dtsa.mapper.cloud.aws.base;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.lingala.zip4j.core.ZipFile;

import com.amazonaws.AmazonServiceException;

import dtsa.mapped.client.request.ProjectTestingClientRequest;
import dtsa.mapped.client.response.EchoMappedResponse;
import dtsa.mapped.client.response.MappedExceptionResponse;
import dtsa.mapped.client.response.ProjectTestingMappedResponse;
import dtsa.mapper.base.ServiceConfiguration;
import dtsa.mapper.client.request.ClientRequestVisitor;
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
import dtsa.mapper.cloud.mapped.base.MappedProxy;
import dtsa.mapper.cloud.mapped.base.MappedProxyFactory;
import dtsa.util.annotation.Nullable;
import dtsa.util.aws.AWS;
import dtsa.util.aws.AWSConfiguration;
import dtsa.util.aws.EC2InstancePool;
import dtsa.util.aws.S3Bucket;
import dtsa.util.aws.S3ObjectURI;
import dtsa.util.communication.base.Response;
import dtsa.util.communication.base.ResponseVisitor;
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

// Constants
	/**
	 * Interesting result directory for DTSA.
	 */
	public final static String resultDirectory = "auto_test/log/";

	/**
	 * Interesting result files for DTSA.
	 */
	public final static String [] resultFiles = new String [] {
		"serialization.txt",
		"statistics.txt",
		"error.log"
	};

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


	/**
	 * Handle `aVisited'.
	 * @pattern Visitor
	 *
	 * @param aVisited - request to process.
	 */
	@Override
	public void visitRetrieve (RetrieveClientRequest aVisited) {
		String [] partitionedPath;
		ReadableByteChannel rbc;
		FileOutputStream fos;
		S3ObjectURI object;
		ZipFile zip;
		String name, uri, resultUri;
		File f;
		URL url;

		rbc = null;
		fos = null;
		try {
			uri = directoryUriFrom (aVisited.getPath ());

			if (aVisited.getSource ().startsWith (S3ObjectURI.protocol)) {
				object = new S3ObjectURI (aVisited.getSource ());

				name = object.getId ();
				defaultBucket ().storeToPath (name, uri);

				zip = new ZipFile (uri + name);
				resultUri = directoryUriFrom (uri + "unzipped_" + name);
				f = new File (resultUri);
				f.mkdir ();
				zip.extractAll (resultUri);

				f = new File (uri + name);
				assert ! f.isDirectory (): "check: " + aVisited.getPath () + name + " is not a directory";
				f.delete ();
			}
			else {
				url = new URL (aVisited.getSource ());
				partitionedPath = url.getPath ().split ("/");
				name = partitionedPath [partitionedPath.length - 1];

				rbc = Channels.newChannel (url.openStream ());
				fos = new FileOutputStream (aVisited.getPath () + name);
				fos.getChannel ().transferFrom (rbc, 0, Long.MAX_VALUE);

				if (! url.getProtocol ().equals ("file")) {
					zip = new ZipFile (uri + name);
					resultUri = directoryUriFrom (uri + "unzipped_" + name);
					zip.extractAll (resultUri);

					f = new File (uri + name);
					assert ! f.isDirectory (): "check: " + aVisited.getPath () + name + " is not a directory";
					f.delete ();
				}
				else {
					resultUri = uri;
				}
			}

			aVisited.setResponse (new RetrieveMapperResponse (resultUri));
		}
		catch (Exception e) {
			aVisited.setException (new MapperExceptionResponse (e));
		}
		finally {
			if (rbc != null) {
				try {
					rbc.close ();
				}
				catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (fos != null) {
				try {
					fos.close ();
				}
				catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void visitResultMerging (ResultMergingClientRequest aVisited) {
		ArrayList <RetrieveMapperResponse> responses = new ArrayList <> (aVisited.getUris ().length);
		RetrieveClientRequest temp;
		ArrayList <File> files = new ArrayList <> (aVisited.getUris ().length);
		String dirUri;
		File f;

		try {
			for (String uri : aVisited.getUris ()) {
				temp = new RetrieveClientRequest (aVisited.getPath (), uri);
				visitRetrieve (temp);
				if (temp.response () != null) {
					responses.add (temp.response ());
				}
				else if (temp.exception () != null) {
					aVisited.setException (temp.exception ());
					break;
				}
				else {
					assert false: "check: has response or exception.";
				}
			}
/*
			while (responses.size () > 10) {
				responses.remove (responses.size () - 1);
				System.out.println ("removing");
			}
*/
			if (! aVisited.hasException ()) {
				dirUri = directoryUriFrom (aVisited.getPath ()) + resultDirectory;
				f = new File (dirUri);
				if (! f.exists ()) {
					f.mkdir ();
				}

				for (String relativeFileNames : resultFiles) {
					for (RetrieveMapperResponse r : responses) {
						files.add (new File (r.getUri () + resultDirectory + relativeFileNames));
					}

					mergeFiles (new File (dirUri + relativeFileNames), files);
					files.clear ();
				}

				for (RetrieveMapperResponse r : responses) {
					f = new File (r.getUri ());
					if (f.exists ()) {
						f.delete ();
					}
				}

				aVisited.setResponse (new RetrieveMapperResponse (directoryUriFrom (directoryUriFrom (aVisited.getPath ()))));
			}
		}
		catch (Exception e) {
			e.printStackTrace ();
			aVisited.setException (new MapperExceptionResponse (e));
		}
	}

	@Override
	public void visitStartingInstances (StartingInstancesClientRequest aRequest) {
		EC2InstancePool pool;
		List <String> ips;

		try {
			pool = newInstancePool ();
			pool.launch (aRequest.getInstanceCount ());

			do {
				Thread.sleep (8000);
				ips = pool.ips ();
			} while (ips.contains (null));

			aRequest.setResponse (new StartingInstancesMapperResponse (pool.count (), pool.privateKey (), pool.ids (), ips));
		}
		catch (Exception e) {
			aRequest.setException (new MapperExceptionResponse (e));
		}

		assert aRequest.hasResponse () || aRequest.hasException (): "ensure: `aRequest' has a response or an exception.";
	}

	@Override
	public void visitProjectTesting (DistributedProjectTestingClientRequest aVisited) {
		@Nullable String [][] groups;
		@Nullable StartingInstancesMapperResponse startingInstancesResponse;
		@Nullable MapperExceptionResponse exception;
		StartingInstancesClientRequest startingInstances;
		ArrayList <Response <? extends ResponseVisitor>> responses;
		MappedProxy mapped;
		int instanceCount;

		groups = aVisited.getClusters ();
		if (groups != null) {
			instanceCount = groups.length;
		}
		else {
			instanceCount = 1;
		}

		startingInstances = new StartingInstancesClientRequest (instanceCount);
		visitStartingInstances (startingInstances);

		startingInstancesResponse = startingInstances.response ();
		exception = startingInstances.exception ();
		if (startingInstancesResponse != null) {
			@Nullable EC2InstancePool pool;

			pool = null;
			try {
				ArrayList <MappedProxy> mappeds = new ArrayList <> (startingInstancesResponse.getIds ().size ());

				for (String ip : startingInstancesResponse.getIps ()) {
					mapped = factory.apply (serviceConfiguration.getPort (),
							new AWSInstanceAvailability (ip));
					mapped.start ();
					mappeds.add (mapped);
				}

				int i;
				i = mappeds.size ();
				for (MappedProxy item : mappeds) {
					i--;
					item.write (new ProjectTestingClientRequest (aVisited.getUri (), aVisited.getProject (),
							aVisited.getConfiguration (), aVisited.getTarget (), aVisited.getTimeout (), groups [i]));
				}

				Response <? extends ResponseVisitor> temp;
				long time = aVisited.getTimeout ()*60 + 25*60;
				responses = new ArrayList <> (mappeds.size ());
				for (MappedProxy item : mappeds) {
					temp = item.maybeNext (120000, time);
					if (temp != null) {
						responses.add (temp);
						time = 60; // Wait just for the first instance
					}
					else {
						System.out.println ("error");
					}
				}

				i = responses.size ();
				String [] uris = new String [i];
				MappedExceptionResponse anormal;
				ProjectTestingMappedResponse normal;
				for (Response <? extends ResponseVisitor> item : responses) {
					i--;
					if (item.getClass () == ProjectTestingMappedResponse.class) {
						normal = (ProjectTestingMappedResponse) item;
						uris [i] = normal.getUri ();
					}
					else if (item.getClass () == MappedExceptionResponse.class) {
						anormal = (MappedExceptionResponse) item;
						System.out.println (anormal.getMessage ());
					}
					else {
						assert false: "check: response unexpected";
					}
				}

				aVisited.setResponse (new DistributedProjectTestingMapperResponse (uris));
			}
			catch (Exception e) {
				aVisited.setException (new MapperExceptionResponse (e));
			}
			finally {
				try {
					if (pool == null) {
						pool = newInstancePool ();
						pool.setIds (startingInstancesResponse.getIds ());
					}
					// pool.terminate ();
				}
				catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
		EchoMappedResponse response;
		@Nullable Object temp;
		MappedProxy mapped;

		startingInstancesResponse = null;
		if (aVisited.getHop () == 1) {
			System.out.println ("[Mapper] echo");
			aVisited.setResponse (new EchoMapperResponse (aVisited.getId ()));
		}
		else {
			startingInstances = new StartingInstancesClientRequest (1);
			visitStartingInstances (startingInstances);

			startingInstancesResponse = startingInstances.response ();
			exception = startingInstances.exception ();
			if (startingInstancesResponse != null) {
				@Nullable EC2InstancePool pool;

				pool = null;
				try {
					ArrayList <MappedProxy> mappeds = new ArrayList <> (startingInstancesResponse.getIds ().size ());

					for (String ip : startingInstancesResponse.getIps ()) {
						mapped = factory.apply (serviceConfiguration.getPort (),
								new AWSInstanceAvailability (ip));
						mapped.start ();
						mappeds.add (mapped);
					}

					for (MappedProxy item : mappeds) {
						item.write (new dtsa.mapped.client.request.EchoClientRequest (aVisited.getId (), aVisited.getHop () - 1));
					}

					temp = null;
					for (MappedProxy item : mappeds) {
						temp = item.maybeNext (120000, 1800);
					}

					if (temp != null && temp.getClass () == EchoMappedResponse.class) {
						response = (EchoMappedResponse) temp;
						aVisited.setResponse (new EchoMapperResponse (response.getId ()));
					}
					else {
						aVisited.setException (new MapperExceptionResponse ("No answer"));
					}
				}
				catch (Exception e) {
					aVisited.setException (new MapperExceptionResponse (e));
				}
				finally {
					try {
						if (pool == null) {
							pool = newInstancePool ();
							pool.setIds (startingInstancesResponse.getIds ());
						}
						pool.terminate ();
					}
					catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
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

	/**
	 * Normalize `aUri' with Unix separator
	 * and adding a separator at the end if not present.
	 * @param aUri
	 * @return
	 */
	protected String directoryUriFrom (String aUri) {
		String result;

		result = aUri.replace ("\\", "/");
		if (! aUri.endsWith ("/")) {
			result = result + "/";
		}

		return result;
	}

	/**
	 * Merge `aFiles' in  `aMerged'.
	 * @param aMerged
	 * @param aFiles
	 * @throws IOException
	 */
	public static void mergeFiles (File aMerged, Collection <File> aFiles) throws IOException {
		@Nullable BufferedWriter out;
		@Nullable BufferedReader in;
		@Nullable String line;
		FileInputStream fis;
		FileWriter fstream;

		out = null;
		in = null;

		try {
			fstream = new FileWriter(aMerged, true);
			out = new BufferedWriter(fstream);

			for (File f : aFiles) {
				fis = new FileInputStream (f);
				in = new BufferedReader (new InputStreamReader (fis));

				line = in.readLine ();
				while (line != null) {
					out.write(line);
					out.newLine();
					line = in.readLine ();
				}

				in.close();
				in = null;
			}

			out.close();
		}
		finally {
			if (out != null) {
				try {
					out.close ();
				}
				catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (in != null) {
				try {
					in.close ();
				}
				catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
