package org.cyk.utility.common.cdi.annotation;

import java.io.Serializable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Collection;

import javax.interceptor.InterceptorBinding;

@Inherited
@InterceptorBinding
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Log { 
	
	@javax.interceptor.Interceptor
	@Log
	public class Interceptor extends org.cyk.utility.common.cdi.annotation.Interceptor.Abstract implements Serializable {

		private static final long serialVersionUID = 1L;

		public static final Collection<org.cyk.utility.common.cdi.annotation.Interceptor> COLLECTION = new ArrayList<>();
		
		@Override
		protected Collection<org.cyk.utility.common.cdi.annotation.Interceptor> getListeners() {
			return COLLECTION;
		}
		
	}
	
}
