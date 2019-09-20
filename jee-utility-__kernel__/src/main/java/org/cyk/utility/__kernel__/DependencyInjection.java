package org.cyk.utility.__kernel__;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.enterprise.inject.spi.CDI;
import javax.enterprise.util.AnnotationLiteral;

import org.jboss.weld.exceptions.IllegalArgumentException;

public class DependencyInjection implements Serializable {
	private static final long serialVersionUID = 1L;

	public static <OBJECT> OBJECT inject(Class<OBJECT> klass,AnnotationLiteral<?>...annotationLiterals){
		if(klass == null)
			throw new IllegalArgumentException("class to inject is required");
		CDI<Object> cdi = CDI.current();
		if(cdi == null)
			throw new IllegalStateException("dependency injection context not found");
		return annotationLiterals == null || annotationLiterals.length == 0 ? cdi.select(klass).get() : cdi.select(klass,annotationLiterals).get();
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
					if(Boolean.TRUE.equals(ClassHelper.isInstanceOf(index, AnnotationLiteral.class)))
						annotationLiteralClass = index;
					else if(Boolean.TRUE.equals(index.isAnnotation())) {
						String annotationLiteralClassName = index.getName()+"$Class";
						annotationLiteralClass = ClassHelper.getByName(annotationLiteralClassName, Boolean.FALSE);
						if(annotationLiteralClass == null) {
							
						}
					}
					if(annotationLiteralClass!=null)
						annotationLiteral = ClassHelper.instanciate(annotationLiteralClass);
					
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
	
	public static <OBJECT> OBJECT inject(Class<OBJECT> aClass){
		Set<Class<?>> qualifierClasses = getQualifierClasses(aClass);
		return injectByQualifiersClasses(aClass, qualifierClasses == null || qualifierClasses.size() == 0 ? null : qualifierClasses.toArray(new Class<?>[] {}));
	}
	
	@SuppressWarnings("unchecked")
	public static <OBJECT> Set<OBJECT> injectAll(Class<OBJECT> klass){
		if(klass == null)
			throw new IllegalArgumentException("class to inject is required");
		return (Set<OBJECT>) CDI.current().getBeanManager().getBeans(klass);
	}
	
	/**/
	
	public static void setQualifiersClasses(Class<?> clazz,Collection<Class<?>> qualifiersClasses) {
		if(qualifiersClasses!=null && !qualifiersClasses.isEmpty())
			QUALIFIER_CLASSES.put(clazz, new HashSet<>(qualifiersClasses));	
	}
	
	public static void setQualifiersClasses(Class<?> clazz,Class<?>...qualifiersClasses) {
		if(qualifiersClasses!=null && qualifiersClasses.length>0) {
			Set<Class<?>> classes = new HashSet<>();
			for(Class<?> index : qualifiersClasses)
				classes.add(index);
			setQualifiersClasses(clazz, classes);	
		}
	}
	
	public static void setQualifierClass(Class<?> clazz,Class<?> qualifierClass) {
		setQualifiersClasses(clazz, qualifierClass);
	}
	
	public static void setQualifierClassTo(Class<?> qualifierClass,Class<?>...classes) {
		if(classes!=null)
			for(Class<?> index : classes)
				setQualifierClass(index, qualifierClass);
	}
	
	public static Set<Class<?>> getQualifierClasses(Class<?> clazz) {
		return QUALIFIER_CLASSES.get(clazz);
	}
	
	/**/
	
	private static final Map<Class<?>,Set<Class<?>>> QUALIFIER_CLASSES = new HashMap<>();
	
}
