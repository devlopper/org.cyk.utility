package org.cyk.utility.__kernel__;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import javax.enterprise.inject.spi.CDI;
import javax.enterprise.util.AnnotationLiteral;

public class DependencyInjection implements Serializable {
	private static final long serialVersionUID = 1L;

	public static <OBJECT> OBJECT inject(Class<OBJECT> aClass,AnnotationLiteral<?>...annotationLiterals){
		if(aClass == null){
			//TODO log a warning
			return null;
		}
		return CDI.current().select(aClass,annotationLiterals).get();
	}
	
	@SafeVarargs
	public static <OBJECT> OBJECT injectByQualifiersClasses(Class<OBJECT> aClass,Class<? extends AnnotationLiteral<?>>...annotationLiteralClasses){
		Collection<AnnotationLiteral<?>> annotationLiterals = null;
		if(annotationLiteralClasses!=null)
			for(Class<? extends AnnotationLiteral<?>> index : annotationLiteralClasses){
				if(annotationLiterals == null)
					annotationLiterals = new ArrayList<AnnotationLiteral<?>>();
				try {
					annotationLiterals.add(index.newInstance());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}				
		return inject(aClass, annotationLiterals == null ? null : annotationLiterals.toArray(new AnnotationLiteral<?>[]{}));
	}
	
	@SuppressWarnings("unchecked")
	public static <OBJECT> Set<OBJECT> injectAll(Class<OBJECT> aClass){
		if(aClass == null){
			//TODO log a warning
			return null;
		}
		return (Set<OBJECT>) CDI.current().getBeanManager().getBeans(aClass);
	}
	
}
