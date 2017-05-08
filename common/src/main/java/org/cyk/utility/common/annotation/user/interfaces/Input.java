package org.cyk.utility.common.annotation.user.interfaces;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.FIELD})
public @interface Input {

	/* grouping */
	
	String setId() default "";
	
	boolean readOnly() default false;
	boolean disabled() default false;
	
	/* Texts */
	
	Text label() default @Text;
	
	Text description() default @Text;
	
	/* Rendering */
	
	public enum RendererStrategy{AUTO,ALWAYS,NEVER,ADMINISTRATION,MANAGEMENT,MANUAL}
	
	DataCollectorType[] ignoreDataCollectorType() default {};
	
	RendererStrategy rendererStrategy() default RendererStrategy.AUTO;
	
}
