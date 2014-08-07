package dtsa.mapped.back.local.base;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Arrays;

import net.lingala.zip4j.core.ZipFile;
import dtsa.mapped.client.request.ClientRequestVisitor;
import dtsa.mapped.client.request.EchoClientRequest;
import dtsa.mapped.client.request.ProjectCompilationClientRequest;
import dtsa.mapped.client.request.ProjectTestingClientRequest;
import dtsa.mapped.client.response.EchoMappedResponse;
import dtsa.mapped.client.response.MappedExceptionResponse;
import dtsa.mapped.client.response.ProjectCompilationMappedResponse;
import dtsa.mapped.client.response.ProjectTestingMappedResponse;
import dtsa.util.aws.AWS;
import dtsa.util.aws.AWSConfiguration;
import dtsa.util.aws.S3ObjectURI;
import java.util.logging.Logger;

/**
 * 
 * @description 
 * @author Victorien ELvinger
 * @date 2014
 *
 */
public class LocalClientRequestVisitor
		extends AWS
		implements ClientRequestVisitor {
	
// Creation
	public LocalClientRequestVisitor (AWSConfiguration aAWSConfiguration, LocalConfiguration alocalConfiguration) {
		super (aAWSConfiguration);
		localConfiguration = alocalConfiguration;
		logger = Logger.getLogger (getClass ().getName ());
		
		assert localConfiguration == alocalConfiguration: "esnure:  `configuration' set with `aConfiguration'";
	}
	
// Constant
	/**
	 * First variable: project directory.
	 * Second variable: target.
	 */
	protected final static String testingResultRelativePathScheme = "%s/EIFGENs/%s/Testing/auto_test";
	
// Status
	@Override
	public boolean isReactive () {
		return true;
	}
	
// Visit
	@Override
	public void visitProjectCompilation (ProjectCompilationClientRequest aVisited) {
		String [] partitionedPath;
		ReadableByteChannel rbc;
		ProcessBuilder builder;
		BufferedReader reader;
		FileOutputStream fos;
		String name, line, workspace;
		S3ObjectURI object;
		ZipFile zip;
		Process p;
		URI uri;
		URL url;
		
		try {
			if (aVisited.getUri ().startsWith (S3ObjectURI.protocol)) {
				object = new S3ObjectURI (aVisited.getUri ());
				
				name = object.getId ();
				workspace = localConfiguration.getWorkspace ();
				
				defaultBucket ().storeToPath (name, workspace);
				
				zip = new ZipFile (localConfiguration.getWorkspace () + name);
				zip.extractAll (localConfiguration.getWorkspace ());
			}
			else {
				url = new URL (aVisited.getUri ());
				partitionedPath = url.getPath ().split ("/");
				name = partitionedPath [partitionedPath.length - 1];
				
				if (url.getProtocol ().equals ("file")) {
					workspace = url.getPath ();
				}
				else {
					workspace = localConfiguration.getWorkspace ();
					
					if (name.endsWith (".git")) {
						name = name.substring (0, name.length () - 4);
						// Shallow clone
						builder = new ProcessBuilder ("git", "clone", 
								"--depth", "1",
								aVisited.getUri (),
								workspace + name);
						p = builder.start ();

						if (p.waitFor () == 1) {
							reader = new BufferedReader (new InputStreamReader (p.getErrorStream ()));
							line = reader.readLine ();
							if (line == null) {
								line = "Git error.";
							}
							throw new Exception (line);
						}
					}
					else {
						rbc = Channels.newChannel (url.openStream ());
						fos = new FileOutputStream (localConfiguration.getWorkspace () + name);
						fos.getChannel ().transferFrom (rbc, 0, Long.MAX_VALUE);
						
						zip = new ZipFile (localConfiguration.getWorkspace () + name);
						zip.extractAll (localConfiguration.getWorkspace ());
					}
				}
			}
			
			builder = new ProcessBuilder (localConfiguration.getEc (),
					"-project_path", workspace + aVisited.getProject (),
					"-config", workspace + aVisited.getConfiguration (),
					"-target", aVisited.getTarget (),
					"-c_compile", "-clean", "-freeze");
			p = builder.start ();
			
			logger.info ("Command: " + Arrays.toString (new String [] {localConfiguration.getEc (),
					"-project_path", workspace + aVisited.getProject (),
					"-config", workspace + aVisited.getConfiguration (),
					"-target", aVisited.getTarget (),
					"-c_compile", "-clean", "-freeze"}).replace (",", ""));
			
			line = "";
			reader = new BufferedReader (new InputStreamReader (p.getErrorStream ()));			
			do {
				logger.info (line);
				line = reader.readLine ();
			} while (line != null && ! line.equals ("System Recompiled."));
			
			p.destroy ();
			
			aVisited.setResponse (new ProjectCompilationMappedResponse (localConfiguration.getWorkspace ()));
		}
		catch (Exception e) {
			aVisited.setException (new MappedExceptionResponse (e));
		}
	}

	@Override
	public void visitEcho (EchoClientRequest aVisited) {
		aVisited.setResponse (new EchoMappedResponse (aVisited.getId ()));
		System.out.println ("answering");
	}

	@Override
	public void visitProjectTesting (ProjectTestingClientRequest aVisited) {
		ProjectCompilationClientRequest compilation;
		ProjectCompilationMappedResponse compilationResponse;
		ProcessBuilder builder;
		BufferedReader reader;
		String line, classes, key;
		Process p;
		
		compilation = new ProjectCompilationClientRequest (aVisited.getUri (), aVisited.getProject (), aVisited.getConfiguration (), aVisited.getTarget ());
		visitProjectCompilation (compilation);
		compilationResponse = compilation.response ();
		
		if (compilation.hasResponse ()) {
			assert aVisited.getTimeout () > 0: "check: timeout is strictly positive.";

			classes = "";
			for (String item : aVisited.getClasses ()) {
				classes = classes + item + " ";
			}
			classes = classes.substring (0, classes.length () - 1);
			
			builder = new ProcessBuilder (localConfiguration.getEc (), 
					"-project_path", localConfiguration.getWorkspace () + aVisited.getProject (), 
					"-config", localConfiguration.getWorkspace () + aVisited.getConfiguration (), 
					"-target", aVisited.getTarget (), 
					"-auto_test", 
					"-i", 
					"-f", 
					"--agents", "none", 
					"-t", "" + aVisited.getTimeout (), 
					"--state", "argumentless", 
					"--serialization", "FAILING",
					classes);
			
			logger.info (Arrays.toString (new String [] {localConfiguration.getEc (), 
				"-project_path", localConfiguration.getWorkspace () + aVisited.getProject (), 
				"-config", localConfiguration.getWorkspace () + aVisited.getConfiguration (), 
				"-target", aVisited.getTarget (), 
				"-auto_test", 
				"-i", 
				"-f", 
				"--agents", "none", 
				"-t", "" + aVisited.getTimeout (), 
				"--state", "argumentless", 
				"--serialization", "FAILING", 
				classes}).replace (",", ""));
			
			try {
				p = builder.start ();
				
				// Eiffel instrumentation
				line = "";
				reader = new BufferedReader (new InputStreamReader (p.getErrorStream ()));			
				do {
					logger.info (line);
					line = reader.readLine ();
				} while (line != null && ! line.equals ("Degree -1: Generating Code"));
				
				// C compilation
				line = "";
				reader = new BufferedReader (new InputStreamReader (p.getInputStream ()));
				do {
					logger.info (line);
					line = reader.readLine ();
				} while (line != null && ! line.equals ("C compilation completed"));
				
				// AutoTest execution
				line = "";
				reader = new BufferedReader (new InputStreamReader (p.getErrorStream ()));			
				do {
					logger.info (line);
					line = reader.readLine ();
				} while (line != null);
				
				p.destroy ();
				
				key = "results_" + defaultBucket ().salt ();
				defaultBucket ().storeFromPathAs (String.format (testingResultRelativePathScheme, localConfiguration.getWorkspace () + aVisited.getProject (), aVisited.getTarget ()), key);
				
				aVisited.setResponse (new ProjectTestingMappedResponse (defaultBucket ().createURI (key)));
			}
			catch (Exception e) {
				aVisited.setException (new MappedExceptionResponse (e));
			}
		}
		else {
			aVisited.setException (compilation.exception ());
		}
	}
	
// Implementation
	/**
	 * Local configuration.
	 */
	protected LocalConfiguration localConfiguration;
	
	/**
	 * Logger.
	 */
	protected Logger logger;
	
}
