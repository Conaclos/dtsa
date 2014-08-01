package dtsa.mapped.base;

import dtsa.mapped.client.response.EchoMappedResponse;
import dtsa.mapped.client.response.MappedExceptionResponse;
import dtsa.mapped.client.response.MappedResponseVisitor;
import dtsa.mapped.client.response.ProjectCompilationMappedResponse;
import dtsa.mapped.client.response.ProjectTestingMappedResponse;
import dtsa.util.annotation.NonNull;


public class MockMappedResponseVisitor
		implements MappedResponseVisitor {

	@Override
	public void visitProjectCompilation (@NonNull ProjectCompilationMappedResponse aVisited) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitException (@NonNull MappedExceptionResponse aVisited) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitEcho (@NonNull EchoMappedResponse aVisited) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitProjectTesting (@NonNull ProjectTestingMappedResponse aVisited) {
		// TODO Auto-generated method stub
		
	}
	
}
