package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common.collection;

import javax.validation.constraints.NotNull;

import org.cyk.utility.client.controller.component.annotation.Commandable;
import org.cyk.utility.client.controller.component.annotation.CommandableButton;
import org.cyk.utility.client.controller.component.annotation.Input;
import org.cyk.utility.client.controller.component.annotation.InputString;
import org.cyk.utility.client.controller.component.annotation.InputStringLineOne;
import org.cyk.utility.client.controller.data.AbstractDataImpl;
import org.cyk.utility.system.action.SystemActionCreate;
import org.primefaces.PrimeFaces;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @ToString
public class Data extends AbstractDataImpl {
	private static final long serialVersionUID = 1L;
	
	@Input @InputString @InputStringLineOne @NotNull
	private String string;
	@Input @InputString @InputStringLineOne @NotNull
	private String number;
	private String date;
	
	@Input @InputString @InputStringLineOne @NotNull
	private String inputText;
	
	@Override
	public Data setOrderNumber(Object orderNumber) {
		return (Data) super.setOrderNumber(orderNumber);
	}
	
	@Commandable(systemActionClass=SystemActionCreate.class) @CommandableButton
	public void submit() {
		PrimeFaces.current().dialog().closeDynamic(this);
	}
}