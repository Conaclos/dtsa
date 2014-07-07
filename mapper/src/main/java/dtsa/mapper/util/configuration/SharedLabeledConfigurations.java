package dtsa.mapper.util.configuration;


public abstract class SharedLabeledConfigurations {

// Implementation
	/**
	 * Configurations
	 */
	public final static LabeledConfigurations configurations;
	
	static {
		configurations = new LabeledConfigurations ("configurations/");
	}
}
