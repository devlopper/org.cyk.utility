package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common.layout;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.cyk.utility.client.controller.component.layout.Insert;
import org.cyk.utility.client.controller.component.layout.InsertBuilder;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageImpl;

import lombok.Getter;
import lombok.Setter;

@Named @RequestScoped @Getter @Setter
public class InsertPage extends AbstractPageImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	private Insert insert01;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		
		insert01 = __inject__(InsertBuilder.class).execute().getOutput();
		
		
	}
	
	
}
