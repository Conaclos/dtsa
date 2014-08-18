package dtsa.mapper.client.base;

import dtsa.mapper.client.response.EchoMapperResponse;
import dtsa.mapper.client.response.MapperExceptionResponse;
import dtsa.mapper.client.response.MapperResponseVisitor;
import dtsa.mapper.client.response.DistributedProjectTestingMapperResponse;
import dtsa.mapper.client.response.RetrieveMapperResponse;
import dtsa.mapper.client.response.StoreMapperResponse;
import dtsa.util.annotation.NonNull;

public class MockMapperResponseVisitor 
	implements MapperResponseVisitor {

// Visit
	@Override
	public void visitStore (@NonNull StoreMapperResponse aResponse) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitRetrieve (@NonNull RetrieveMapperResponse aVisited) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitProjectTesting (@NonNull DistributedProjectTestingMapperResponse aVisited) {
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
