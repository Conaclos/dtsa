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

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import dtsa.mapped.client.request.ClientRequestVisitor;
import dtsa.mapped.client.request.EchoClientRequest;
import dtsa.mapped.client.request.ProjectCompilationClientRequest;
import dtsa.mapped.client.response.EchoMappedResponse;
import dtsa.mapped.client.response.MappedExceptionResponse;
import dtsa.util.annotation.NonNull;

public class LocalClientRequestVisitor
		implements ClientRequestVisitor {
	
// Creation
	public LocalClientRequestVisitor (LocalConfiguration aConfiguration) {
		configuration = aConfiguration;
		
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
		FileOutputStream fos;
		ZipFile zip;
		String name, s;
		URL uri;
		
		Runtime runtime = Runtime.getRuntime ();
		
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
			
			
			ProcessBuilder builder = new ProcessBuilder (configuration.getEc (),
					"-project_path", configuration.getWorkspace () + aVisited.getProject (),
					"-config", configuration.getWorkspace () + aVisited.getConfiguration (),
					"-target", aVisited.getTarget (),
					"-c_compile", "-clean", "-freeze");
			Process p = builder.start ();
			
			while (true) {
				System.out.println (new BufferedReader (new InputStreamReader (p.getErrorStream ())).readLine ());
			}
			
			// System.out.println ("finished");
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
	public void visitProjectTesting (@NonNull ProjectTestingClientRequest aVisited) {
		// TODO Auto-generated method stub
		
	}
	
// Implementation
	/**
	 * Local configuration.
	 */
	protected LocalConfiguration configuration;
	
}
