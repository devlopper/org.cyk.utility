package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.cyk.utility.client.controller.component.Command;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageImpl;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class CommandPage extends AbstractPageImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject private Command commandSimple;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		commandSimple.getProperties().setValue("Simple");
		commandSimple.getProperties().setRendered(Boolean.TRUE);
		
		commandSimple.setFunctionRunnable(new Runnable() {	
			@Override
			public void run() {
				System.out.println("Call done!!!");
			}
		});
	}
	
	public void executeCommandSimple() {
		System.out.println("CommandPage.executeCommandSimple() 0");
	}
}
