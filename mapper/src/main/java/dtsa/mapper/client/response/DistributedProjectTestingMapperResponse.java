package dtsa.mapper.client.response;

/**
 * 
 * @description 
 * @author Victorien ELvinger
 * @date 2014
 *
 */
public class DistributedProjectTestingMapperResponse 
	extends MapperResponse {

	@Override
	public void accept (MapperResponseVisitor aVisitor) {
		aVisitor.visitProjectTesting (this);
	}
	
}
