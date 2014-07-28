package dtsa.mapper.cloud.mapped.base;


public abstract interface MappedProxyFactory {
	
// Access
	/**
	 * @return New mapped Instance
	 */
	public abstract MappedProxy apply (int aPort, ServiceAvailability aAvailabilty);
	
}
