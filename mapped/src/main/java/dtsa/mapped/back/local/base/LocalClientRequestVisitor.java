package dtsa.mapped.back.local.base;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Arrays;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import dtsa.mapped.client.request.ClientRequestVisitor;
import dtsa.mapped.client.request.EchoClientRequest;
import dtsa.mapped.client.request.ProjectCompilationClientRequest;
import dtsa.mapped.client.request.ProjectTestingClientRequest;
import dtsa.mapped.client.response.EchoMappedResponse;
import dtsa.mapped.client.response.MappedExceptionResponse;
import dtsa.mapped.client.response.ProjectCompilationMappedResponse;
import dtsa.mapped.client.response.ProjectTestingMappedResponse;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LocalClientRequestVisitor
		implements ClientRequestVisitor {
	
// Creation
	public LocalClientRequestVisitor (LocalConfiguration aConfiguration) {
		configuration = aConfiguration;
		logger = Logger.getLogger (getClass ().getName ());
		
		assert configuration == aConfiguration: "esnure:  `configuration' set with `aConfiguration'";
	}
	
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
		String name, line;
		ZipFile zip;
		Process p;
		URL uri;
		
		try {
			uri = new URL (aVisited.getUri ());
			partitionedPath = uri.getPath ().split ("/");
			name = partitionedPath [partitionedPath.length - 1];
			
			/*
			rbc = Channels.newChannel (uri.openStream ());
			fos = new FileOutputStream (configuration.getWorkspace () + name);
			fos.getChannel ().transferFrom (rbc, 0, Long.MAX_VALUE);
			*/
			
			zip = new ZipFile (configuration.getWorkspace () + name);
			zip.extractAll (configuration.getWorkspace ());
			
			builder = new ProcessBuilder (configuration.getEc (),
					"-project_path", configuration.getWorkspace () + aVisited.getProject (),
					"-config", configuration.getWorkspace () + aVisited.getConfiguration (),
					"-target", aVisited.getTarget (),
					"-c_compile", "-clean", "-freeze");
			p = builder.start ();
			
			logger.info ("Command: " + Arrays.toString (new String [] {configuration.getEc (),
					"-project_path", configuration.getWorkspace () + aVisited.getProject (),
					"-config", configuration.getWorkspace () + aVisited.getConfiguration (),
					"-target", aVisited.getTarget (),
					"-c_compile", "-clean", "-freeze"}).replace (",", ""));
			
			line = "";
			reader = new BufferedReader (new InputStreamReader (p.getErrorStream ()));			
			do {
				logger.info (line);
				line = reader.readLine ();
			} while (line != null && ! line.equals ("System Recompiled."));
			
			p.destroy ();
			
			aVisited.setResponse (new ProjectCompilationMappedResponse (configuration.getWorkspace ()));
		}
		catch (MalformedURLException | FileNotFoundException e) {
			aVisited.setException (new MappedExceptionResponse (e));
			// TODO Auto-generated catch block
			e.printStackTrace ();
		}
		catch (IOException e) {
			aVisited.setException (new MappedExceptionResponse (e));
			// TODO Auto-generated catch block
			e.printStackTrace ();
		}
		catch (ZipException e) {
			aVisited.setException (new MappedExceptionResponse (e));
			// TODO Auto-generated catch block
			e.printStackTrace ();
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
		String line, classes;
		Process p;
		
		compilation = new ProjectCompilationClientRequest (aVisited.getUri (), aVisited.getProject (), aVisited.getConfiguration (), aVisited.getTarget ());
		visitProjectCompilation (compilation);
		compilationResponse = compilation.response ();
		
		assert aVisited.getTimeout () > 0: "check: timeout is strictly positive.";

		classes = "";
		for (String item : aVisited.getClasses ()) {
			classes = classes + item + " ";
		}
		classes = classes.substring (0, classes.length () - 1);
		
		builder = new ProcessBuilder (configuration.getEc (), 
				"-project_path", configuration.getWorkspace () + aVisited.getProject (), 
				"-config", configuration.getWorkspace () + aVisited.getConfiguration (), 
				"-target", aVisited.getTarget (), 
				"-auto_test", 
				"-i", 
				"-f", 
				"--agents", "none", 
				"-t", "" + aVisited.getTimeout (), 
				"--state", "argumentless", 
				"--serialization", "FAILING",
				classes);
		
		logger.info (Arrays.toString (new String [] {configuration.getEc (), 
			"-project_path", configuration.getWorkspace () + aVisited.getProject (), 
			"-config", configuration.getWorkspace () + aVisited.getConfiguration (), 
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
			
			aVisited.setResponse (new ProjectTestingMappedResponse (configuration.getWorkspace ()));
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
// Implementation
	/**
	 * Local configuration.
	 */
	protected LocalConfiguration configuration;
	
	/**
	 * Logger.
	 */
	protected Logger logger;
	
}
