package dtsa.mapper.client.base;

import dtsa.mapper.client.response.EchoMapperResponse;
import dtsa.mapper.client.response.MapperExceptionResponse;
import dtsa.mapper.client.response.MapperResponseVisitor;
import dtsa.mapper.client.response.ProjectTestingMapperResponse;
import dtsa.mapper.client.response.StoreMapperResponse;
import dtsa.util.annotation.NonNull;

public class MockMapperResponseVisitor 
	implements MapperResponseVisitor {

	@Override
	public void visitStore (@NonNull StoreMapperResponse aResponse) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitProjectTesting (@NonNull ProjectTestingMapperResponse aVisited) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitException (@NonNull MapperExceptionResponse aVisited) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitEcho (@NonNull EchoMapperResponse aVisited) {
		// TODO Auto-generated method stub
		
	}
	
}
