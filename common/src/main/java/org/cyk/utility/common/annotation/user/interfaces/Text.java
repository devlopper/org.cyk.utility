package org.cyk.utility.common.annotation.user.interfaces;

import java.io.Serializable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.FIELD})
public @interface Text {

	public static enum Type{AUTO,LABEL,DESCRIPTION}
	public static enum ValueType{AUTO,ID,VALUE}
	
	Type type() default Type.AUTO;
	
	ValueType valueType() default ValueType.AUTO;
	
	String value() default "";
	
	Class<? extends Builder> builderClass() default Builder.Null.class;
	
	/**/
	
	public static interface Builder extends org.cyk.utility.common.Builder.NullableInput<String> {
		
		public static class Adapter extends org.cyk.utility.common.Builder.NullableInput.Adapter.Default<String> implements Builder,Serializable {

			private static final long serialVersionUID = 1L;

			public Adapter() {
				super(String.class);
			}
		
			public static class Default extends Builder.Adapter implements Serializable {

				private static final long serialVersionUID = 1L;

				public Default() {}
				
			}
		}
		
		/**/
		
		public static interface Null extends Builder {
			
		}
	}
}
