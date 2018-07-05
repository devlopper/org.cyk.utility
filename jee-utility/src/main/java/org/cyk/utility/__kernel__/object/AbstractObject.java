package org.cyk.utility.__kernel__.object;

import java.io.Serializable;

import org.cyk.utility.__kernel__.DependencyInjection;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public abstract class AbstractObject implements Objectable,Serializable {
	private static final long serialVersionUID = 1L;

	protected static <OBJECT> OBJECT __inject__(Class<OBJECT> aClass){
		if(aClass == null){
			//TODO log warning
			return null;
		}
		return DependencyInjection.inject(aClass);
	}
	
}
