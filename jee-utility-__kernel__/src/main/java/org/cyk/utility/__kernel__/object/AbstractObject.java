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
	
	/**/
	
	protected static <OBJECT> OBJECT __inject__(Class<OBJECT> aClass,AnnotationLiteral<?>...annotationLiterals){
		if(aClass == null){
			//TODO log warning
			return null;
		}
		return DependencyInjection.inject(aClass,annotationLiterals);
	}
	
	protected static <OBJECT> OBJECT __injectByQualifiersClasses__(Class<OBJECT> aClass,@SuppressWarnings("unchecked") Class<? extends AnnotationLiteral<?>>...annotationLiteralClasses){
		if(aClass == null){
			//TODO log warning
			return null;
		}
		return DependencyInjection.injectByQualifiersClasses(aClass,annotationLiteralClasses);
	}
	
	/**/
	
	protected static KernelHelper __injectKernelHelper__() {
		return __inject__(KernelHelper.class);
	}
}
