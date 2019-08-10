package org.cyk.utility.__kernel__.object;

import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.util.AnnotationLiteral;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.KernelHelper;
import org.cyk.utility.__kernel__.constant.ConstantString;

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
	
	protected Object __getInjectIfNull__(Boolean injectIfNull) {
		String fieldName = StringUtils.substringAfter(Thread.currentThread() .getStackTrace()[2] .getMethodName(),ConstantString.GET);
		Integer length = fieldName.length();
		if(length > 0) {
			if(length == 1)
				fieldName = fieldName.toLowerCase();	
			else
				fieldName = fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1);
		}
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
	
	/**/
	
	protected byte[] __getResourceAsBytes__(String name) {
		try {
			return IOUtils.toByteArray(getClass().getResourceAsStream(name));
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}
	
	/* Logging */
	
	protected void __log__(String message,Level level) {
		Logger.getLogger(getClass().getName()).log(level, message);
	}
	
	protected void __logFinest__(String message) {
		__log__(message, Level.FINEST);
	}
	
	protected void __logFiner__(String message) {
		__log__(message, Level.FINER);
	}
	
	protected void __logFine__(String message) {
		__log__(message, Level.FINE);
	}

	protected void __logInfo__(String message) {
		__log__(message, Level.INFO);
	}
	
	protected void __logWarning__(String message) {
		__log__(message, Level.WARNING);
	}
	
	protected void __logSevere__(String message) {
		__log__(message, Level.SEVERE);
	}
	
	protected void __logAll__(String message) {
		__log__(message, Level.ALL);
	}
	
	protected void __logConfig__(String message) {
		__log__(message, Level.CONFIG);
	}
	
	protected void __logOff__(String message) {
		__log__(message, Level.OFF);
	}
	
	protected void __log__(Throwable throwable) {
		__logSevere__(throwable.toString());
	}
}
