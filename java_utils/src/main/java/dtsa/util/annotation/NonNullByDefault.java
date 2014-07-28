package dtsa.util.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @description Package setting to make all entity as NonNull by default.
 * @see org.eclipse.jdt.annotation.NonNullByDefault
 * @see dtsa.mapper.util.annotation.NonNull
 * @author Victorien Elvinger
 * @date 2014/06/25
 *
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
public @interface NonNullByDefault {
	
}
