package dtsa.mapped.client.response;

import dtsa.util.communication.base.Response;

/**
 * 
 * @description Root ancestor of all Mapper response. Response are simple objects for reception.
 * @author Victorien ELvinger
 * @date 2014
 *
 */
public abstract class MappedResponse 
	extends Response <MappedResponseVisitor> {
	
}
