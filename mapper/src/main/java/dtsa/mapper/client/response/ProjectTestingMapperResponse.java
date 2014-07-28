package dtsa.mapper.client.response;


public class ProjectTestingMapperResponse 
	extends MapperResponse {

	@Override
	public void accept (MapperResponseVisitor aVisitor) {
		aVisitor.visitProjectTesting (this);
	}
	
}
