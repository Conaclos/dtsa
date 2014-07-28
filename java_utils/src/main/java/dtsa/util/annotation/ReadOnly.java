package dtsa.util.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 
 * @description Entity where commands should not be applicable.
 * @author Victorien ELvinger
 * @date 2014
 *
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
public @interface ReadOnly {
	
}
