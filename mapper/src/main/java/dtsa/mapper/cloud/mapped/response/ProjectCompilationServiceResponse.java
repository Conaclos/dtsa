package dtsa.mapper.cloud.mapped.response;


public class ProjectCompilationServiceResponse 
	extends ServiceResponse {

// Other
	@Override
	public void accept (ServiceResponseVisitor aVisitor) {
		aVisitor.visitProjectCompilation (this);
	}
	
}
