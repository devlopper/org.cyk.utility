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
	
	/*@SafeVarargs
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
	}*/
	
	@SafeVarargs
	public static <OBJECT> OBJECT injectByQualifiersClasses(Class<OBJECT> aClass,Class<?>...classes){
		Collection<AnnotationLiteral<?>> annotationLiterals = null;
		if(classes!=null && classes.length>0)
			for(Class<?> index : classes){
				Object annotationLiteral = null;
				if(index!=null) {
					Class<?> annotationLiteralClass = null;
					if(Boolean.TRUE.equals(KernelHelperImpl.__isInstanceOf__(index, AnnotationLiteral.class)))
						annotationLiteralClass = index;
					else if(Boolean.TRUE.equals(index.isAnnotation())) {
						String annotationLiteralClassName = index.getName()+"$Class";
						annotationLiteralClass = KernelHelperImpl.__getClassByName__(annotationLiteralClassName, Boolean.FALSE);
						if(annotationLiteralClass == null) {
							
						}
					}
					if(annotationLiteralClass!=null)
						annotationLiteral = KernelHelperImpl.__instanciate__(annotationLiteralClass);
					
					if(annotationLiteral == null) {
						System.err.println(DependencyInjection.class.getSimpleName()+" : Cannot find annotation literal from "+index);
					}else {
						if(annotationLiterals == null)
							annotationLiterals = new ArrayList<AnnotationLiteral<?>>();
						annotationLiterals.add((AnnotationLiteral<?>)annotationLiteral);
					}
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
