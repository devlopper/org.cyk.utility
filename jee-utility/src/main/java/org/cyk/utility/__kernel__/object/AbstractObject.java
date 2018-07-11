package org.cyk.utility.__kernel__.object;

import java.io.Serializable;

import javax.enterprise.util.AnnotationLiteral;

import org.cyk.utility.__kernel__.DependencyInjection;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public abstract class AbstractObject implements Objectable,Serializable {
	private static final long serialVersionUID = 1L;

	protected static <OBJECT> OBJECT __inject__(Class<OBJECT> aClass,AnnotationLiteral<?>...annotationLiterals){
		if(aClass == null){
			//TODO log warning
			return null;
		}
		return DependencyInjection.inject(aClass,annotationLiterals);
	}
	
	public <OBJECT> OBJECT __injectByQualifiersClasses__(Class<OBJECT> aClass,@SuppressWarnings("unchecked") Class<? extends AnnotationLiteral<?>>...annotationLiteralClasses){
		if(aClass == null){
			//TODO log warning
			return null;
		}
		return DependencyInjection.injectByQualifiersClasses(aClass,annotationLiteralClasses);
	}
}
