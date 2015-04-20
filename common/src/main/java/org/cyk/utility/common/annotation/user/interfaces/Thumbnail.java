package org.cyk.utility.common.annotation.user.interfaces;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Thumbnail {

	public enum RenderStrategy{AUTO,ALWAYS,NEVER}
	
	RenderStrategy renderStrategy() default RenderStrategy.AUTO;
	
}
