package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.cyk.utility.client.controller.component.command.CommandButton;
import org.cyk.utility.client.controller.component.input.InputStringLineManyBuilder;
import org.cyk.utility.client.controller.component.input.InputStringLineOneBuilder;
import org.cyk.utility.client.controller.view.View;
import org.cyk.utility.client.controller.view.ViewBuilder;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageImpl;
import org.cyk.utility.system.action.SystemActionCreate;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class ViewPage extends AbstractPageImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject private View view01;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		ViewBuilder viewBuilder = __inject__(ViewBuilder.class);
		
		viewBuilder.getNameOutputStringTextBuilder(Boolean.TRUE).getOutputProperties(Boolean.TRUE).setValue("Mon titre de formulaire");
		
		InputStringLineOneBuilder inputStringLineOneBuilder;
		
		inputStringLineOneBuilder = __inject__(InputStringLineOneBuilder.class);
		inputStringLineOneBuilder.getOutputProperties(Boolean.TRUE).setRequired(Boolean.TRUE);
		inputStringLineOneBuilder.getLabelBuilder(Boolean.TRUE).getOutputProperties(Boolean.TRUE).setValue("Firstname");
		inputStringLineOneBuilder.getLabelBuilder(Boolean.TRUE).getArea(Boolean.TRUE).getWidth(Boolean.TRUE).setProportions(1, 1, 1, 1, 12);
		inputStringLineOneBuilder.getArea(Boolean.TRUE).getWidth(Boolean.TRUE).setProportions(4, 4, 4, 4, 12);
		inputStringLineOneBuilder.getMessageBuilder(Boolean.TRUE).getArea(Boolean.TRUE).getWidth(Boolean.TRUE).setProportions(1, 1, 1, 1, 12);
		viewBuilder.addInputBuilder(inputStringLineOneBuilder);
		
		inputStringLineOneBuilder = __inject__(InputStringLineOneBuilder.class);
		inputStringLineOneBuilder.getLabelBuilder(Boolean.TRUE).getOutputProperties(Boolean.TRUE).setValue("Lastnames");
		inputStringLineOneBuilder.getLabelBuilder(Boolean.TRUE).getArea(Boolean.TRUE).getWidth(Boolean.TRUE).setProportions(1, 1, 1, 1, 12);
		inputStringLineOneBuilder.getArea(Boolean.TRUE).getWidth(Boolean.TRUE).setProportions(4, 4, 4, 4, 12);
		inputStringLineOneBuilder.getMessageBuilder(Boolean.TRUE).getArea(Boolean.TRUE).getWidth(Boolean.TRUE).setProportions(1, 1, 1, 1, 12);
		viewBuilder.addInputBuilder(inputStringLineOneBuilder);
		
		InputStringLineManyBuilder inputStringLineManyBuilder;
		inputStringLineManyBuilder = __inject__(InputStringLineManyBuilder.class);
		inputStringLineManyBuilder.getOutputProperties(Boolean.TRUE).setRequired(Boolean.TRUE);
		inputStringLineManyBuilder.getLabelBuilder(Boolean.TRUE).getOutputProperties(Boolean.TRUE).setValue("Other details");
		inputStringLineManyBuilder.getLabelBuilder(Boolean.TRUE).getArea(Boolean.TRUE).getWidth(Boolean.TRUE).setProportions(1, 1, 1, 1, 12);
		inputStringLineManyBuilder.getArea(Boolean.TRUE).getWidth(Boolean.TRUE).setProportions(7, 7, 7, 7, 12);
		inputStringLineManyBuilder.getMessageBuilder(Boolean.TRUE).getArea(Boolean.TRUE).getWidth(Boolean.TRUE).setProportions(4, 4, 4, 4, 12);
		viewBuilder.addInputBuilder(inputStringLineManyBuilder);
		
		CommandButton submitCommandButton = __inject__(CommandButton.class);
		submitCommandButton.getProperties().setValue("Execute");
		submitCommandButton.getCommand(Boolean.TRUE).getFunction(Boolean.TRUE).setAction(__inject__(SystemActionCreate.class));
		submitCommandButton.getCommand(Boolean.TRUE).getFunction(Boolean.TRUE).try_().getRun(Boolean.TRUE).addRunnables(new Runnable() {	
			@Override
			public void run() {
				System.out.println("Executed");
			}
		});
		viewBuilder.getProcessingCommandables(Boolean.TRUE).add(submitCommandButton);
		
		submitCommandButton = __inject__(CommandButton.class);
		submitCommandButton.getProperties().setValue("Cancel");
		submitCommandButton.getCommand(Boolean.TRUE).getFunction(Boolean.TRUE).setAction(__inject__(SystemActionCreate.class));
		submitCommandButton.getCommand(Boolean.TRUE).getFunction(Boolean.TRUE).try_().getRun(Boolean.TRUE).addRunnables(new Runnable() {	
			@Override
			public void run() {
				System.out.println("Canceled");
			}
		});
		viewBuilder.getProcessingCommandables(Boolean.TRUE).add(submitCommandButton);
		
		view01 = viewBuilder.execute().getOutput();
	}
	
}
