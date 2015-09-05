package org.cyk.utility.common.annotation.user.interfaces;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.FIELD})
public @interface OutputSeperator {

	public enum SeperatorLocation{AUTO,FOLLOW,AFTER,BEFORE}
	
	SeperatorLocation location() default SeperatorLocation.AUTO;
	
	Text label() default @Text;
}
