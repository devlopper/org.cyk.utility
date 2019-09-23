package org.cyk.utility.__kernel__.object.dynamic;

import java.io.Serializable;

import org.cyk.utility.__kernel__.klass.ClassHelper;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public abstract class AbstractSingleton extends AbstractObject implements Singleton,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setIdentifier(__getIdentifier__());
	}
	
	protected String __getIdentifier__(){
		return ClassHelper.getInterfaceSimpleNameFromImplementationClass(getClass());
	}
	
}
