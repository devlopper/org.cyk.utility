package org.cyk.utility.__kernel__.object;

import java.io.Serializable;

import javax.enterprise.util.AnnotationLiteral;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.KernelHelper;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public abstract class AbstractObject implements Objectable,Serializable {
	private static final long serialVersionUID = 1L;

	protected <OBJECT> OBJECT ____inject____(Class<OBJECT> aClass,AnnotationLiteral<?>...annotationLiterals){
		return __inject__(aClass, annotationLiterals);
	}
	
	protected <OBJECT> OBJECT ____injectByQualifiersClasses____(Class<OBJECT> aClass,@SuppressWarnings("unchecked") Class<? extends AnnotationLiteral<?>>...annotationLiteralClasses){
		return __injectByQualifiersClasses__(aClass, annotationLiteralClasses);
	}
	
	protected Object __getInjectOrInstanciateIfNull__(String fieldName,Boolean injectableIfNull,Boolean instanciatableIfNull) {
		Object object = DependencyInjection.inject(KernelHelper.class).executeMethodGetter(this, fieldName);
		if(object == null && (Boolean.TRUE.equals(injectableIfNull) || Boolean.TRUE.equals(instanciatableIfNull))) {
			Class<?> fieldType = DependencyInjection.inject(KernelHelper.class).getFieldType(getClass(),fieldName);
			object = Boolean.TRUE.equals(injectableIfNull) ? DependencyInjection.inject(fieldType) 
					: (DependencyInjection.inject(KernelHelper.class).instanciate(fieldType));
			DependencyInjection.inject(KernelHelper.class).executeMethodSetter(this, fieldName, object);
		}
		return object;
	}
	
	protected Object __getInjectIfNull__(String fieldName,Boolean injectIfNull) {
		return __getInjectOrInstanciateIfNull__(fieldName, injectIfNull, null);
	}
	
	protected Object __getInstanciateIfNull__(String fieldName,Boolean instanciateIfNull) {
		return __getInjectOrInstanciateIfNull__(fieldName, null,instanciateIfNull);
	}
	
	/**/
	
	protected static <OBJECT> OBJECT __inject__(Class<OBJECT> aClass,AnnotationLiteral<?>...annotationLiterals){
		if(aClass == null){
			//TODO log warning
			return null;
		}
		return DependencyInjection.inject(aClass,annotationLiterals);
	}
	
	protected static <OBJECT> OBJECT __injectByQualifiersClasses__(Class<OBJECT> aClass,Class<?>...classes){
		if(aClass == null){
			//TODO log warning
			return null;
		}
		return DependencyInjection.injectByQualifiersClasses(aClass,classes);
	}
	
	protected static <OBJECT> OBJECT __inject__(Class<OBJECT> aClass){
		return DependencyInjection.inject(aClass);
	}
	
	/**/
	
	protected static KernelHelper __injectKernelHelper__() {
		return __inject__(KernelHelper.class);
	}
}
