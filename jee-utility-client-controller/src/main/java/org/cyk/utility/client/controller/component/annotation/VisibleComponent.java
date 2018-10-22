package org.cyk.utility.client.controller.component.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.FIELD})
public @interface VisibleComponent {

	/* grouping */
	
	String setId() default "";
	
	boolean disabled() default false;
	
	public enum RendererStrategy{AUTO,ALWAYS,NEVER,ADMINISTRATION,MANAGEMENT,MANUAL}
	
	RendererStrategy rendererStrategy() default RendererStrategy.AUTO;
	
	/**/
	
}
