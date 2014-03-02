package org.cyk.utility.common.cdi.provider;

import java.io.Serializable;

import javax.inject.Singleton;

import org.cyk.utility.common.cdi.AbstractBean;

import lombok.Setter;

@Singleton
@Setter
public class CommonMethodProvider extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 1762618572766766794L;
	
	
	private TextServiceFindMethod textServiceFindMethod;
	
	public TextServiceFindMethod getTextServiceFindMethod() {
		if(textServiceFindMethod==null)
			noServiceProvided("Text Service Find");
		return textServiceFindMethod;
	}
	
	private void noServiceProvided(String id){
		throw new RuntimeException("No method has been provided for <<"+id+">>");
	}

}
