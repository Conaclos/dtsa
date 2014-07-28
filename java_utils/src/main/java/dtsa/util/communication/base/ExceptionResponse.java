package dtsa.util.communication.base;

/**
 * 
 * @description Root ancestor of all exceptional server response. Response are simple objects for reception.
 * @author Victorien ELvinger
 * @date 2014
 *
 * @param <P>
 */
public abstract class ExceptionResponse <P extends ResponseVisitor>
	extends Response <P> {
	
}
