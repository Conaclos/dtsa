package dtsa.mapper.util.configuration;

/**
 * 
 * @description Share a single configuration instance.
 * @author Victorien ELvinger
 * @date 2014/07/7
 *
 */
public class SharedLabeledConfigurations {

// Shared
	/**
	 * Configurations
	 */
	public final static LabeledConfigurations configurations;
	
	static {
		configurations = new LabeledConfigurations ("configurations/");
	}
}
