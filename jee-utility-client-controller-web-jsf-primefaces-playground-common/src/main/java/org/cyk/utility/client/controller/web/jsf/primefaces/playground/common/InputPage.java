package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.cyk.utility.client.controller.component.Component;
import org.cyk.utility.client.controller.component.VisibleComponent;
import org.cyk.utility.client.controller.component.VisibleComponents;
import org.cyk.utility.client.controller.component.VisibleComponentsBuilder;
import org.cyk.utility.client.controller.component.command.CommandButton;
import org.cyk.utility.client.controller.component.input.Input;
import org.cyk.utility.client.controller.component.input.InputStringLineMany;
import org.cyk.utility.client.controller.component.input.InputStringLineOne;
import org.cyk.utility.client.controller.component.layout.LayoutBuilder;
import org.cyk.utility.client.controller.component.layout.LayoutBuilerItem;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageImpl;
import org.cyk.utility.system.action.SystemActionCreate;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class InputPage extends AbstractPageImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	private VisibleComponents visibleComponents;
	@Inject private InputStringLineOne inputText;
	@Inject private InputStringLineMany inputTextArea;
	@Inject private InputStringLineOne inputTextRequired;
	private InputStringLineOne inputTextNull;
	
	@Inject private CommandButton commandButton;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		
		LayoutBuilder layoutBuilder01 = __inject__(LayoutBuilder.class)
				.addItems(__inject__(LayoutBuilerItem.class).setWidth(2).setWidthForDesktop(2).setWidthForTablet(2).setWidthForPhone(12))
				.addItems(__inject__(LayoutBuilerItem.class).setWidth(6).setWidthForDesktop(6).setWidthForTablet(6).setWidthForPhone(12))
				.addItems(__inject__(LayoutBuilerItem.class).setWidth(4).setWidthForDesktop(4).setWidthForTablet(4).setWidthForPhone(12))
				
				.addItems(__inject__(LayoutBuilerItem.class).setWidth(2).setWidthForDesktop(2).setWidthForTablet(2).setWidthForPhone(12))
				.addItems(__inject__(LayoutBuilerItem.class).setWidth(6).setWidthForDesktop(6).setWidthForTablet(6).setWidthForPhone(12))
				.addItems(__inject__(LayoutBuilerItem.class).setWidth(4).setWidthForDesktop(4).setWidthForTablet(4).setWidthForPhone(12))
				
				.addItems(__inject__(LayoutBuilerItem.class).setWidth(2).setWidthForDesktop(2).setWidthForTablet(2).setWidthForPhone(12))
				.addItems(__inject__(LayoutBuilerItem.class).setWidth(6).setWidthForDesktop(6).setWidthForTablet(6).setWidthForPhone(12))
				.addItems(__inject__(LayoutBuilerItem.class).setWidth(4).setWidthForDesktop(4).setWidthForTablet(4).setWidthForPhone(12))
				
				.addItems(__inject__(LayoutBuilerItem.class).setWidth(12).setWidthForDesktop(12).setWidthForTablet(12).setWidthForPhone(12))
				
				//.execute().getOutput()
				;
		
		LayoutBuilder layoutBuilder02 = __inject__(LayoutBuilder.class)
				.addItems(__inject__(LayoutBuilerItem.class).setWidth(2).setWidthForDesktop(2).setWidthForTablet(2).setWidthForPhone(12))
				.addItems(__inject__(LayoutBuilerItem.class).setWidth(2).setWidthForDesktop(2).setWidthForTablet(2).setWidthForPhone(12))
				.addItems(__inject__(LayoutBuilerItem.class).setWidth(2).setWidthForDesktop(2).setWidthForTablet(2).setWidthForPhone(12))
				.addItems(__inject__(LayoutBuilerItem.class).setWidth(2).setWidthForDesktop(2).setWidthForTablet(2).setWidthForPhone(12))
				.addItems(__inject__(LayoutBuilerItem.class).setWidth(2).setWidthForDesktop(2).setWidthForTablet(2).setWidthForPhone(12))
				.addItems(__inject__(LayoutBuilerItem.class).setWidth(2).setWidthForDesktop(2).setWidthForTablet(2).setWidthForPhone(12))
				
				.addItems(__inject__(LayoutBuilerItem.class).setWidth(2).setWidthForDesktop(2).setWidthForTablet(2).setWidthForPhone(12))
				.addItems(__inject__(LayoutBuilerItem.class).setWidth(6).setWidthForDesktop(6).setWidthForTablet(6).setWidthForPhone(12))
				.addItems(__inject__(LayoutBuilerItem.class).setWidth(4).setWidthForDesktop(4).setWidthForTablet(4).setWidthForPhone(12))
				
				.addItems(__inject__(LayoutBuilerItem.class).setWidth(12).setWidthForDesktop(12).setWidthForTablet(12).setWidthForPhone(12))
				
				//.execute().getOutput()
				;
		
		List<VisibleComponent> components = new ArrayList<>();
		
		//inputText.setLabelComponentValue("Simple");
		components.add(inputText.getLabel());
		components.add(inputText);
		components.add(inputText.getMessage());
		
		inputTextRequired.getProperties().setRequired(Boolean.TRUE);
		//inputTextRequired.setLabelComponentValue("Required");
		components.add(inputTextRequired.getLabel());
		components.add(inputTextRequired);
		components.add(inputTextRequired.getMessage());
		
		//inputTextArea.setLabelComponentValue("TextArea");
		inputTextArea.getProperties().setRequired(Boolean.TRUE);
		components.add(inputTextArea.getLabel());
		components.add(inputTextArea);
		components.add(inputTextArea.getMessage());
		
		components.add(commandButton);
		
		commandButton.getProperties().setValue("Send data to server");
		commandButton.getCommand(Boolean.TRUE).getFunction(Boolean.TRUE).setAction(__inject__(SystemActionCreate.class));
		commandButton.getCommand(Boolean.TRUE).getFunction(Boolean.TRUE).try_().getRun(Boolean.TRUE).addRunnables(new Runnable() {	
			@Override
			public void run() {
				System.out.println("Receiving data from client.");
				for(Component index : visibleComponents.get())
					if(index instanceof Input) {
						Input<?> input = (Input<?>) index;
						System.out.println("Input : "+input.getValue());
					}
			}
		});
		
		visibleComponents = __inject__(VisibleComponentsBuilder.class).setComponents(components).setLayoutBuilder(layoutBuilder01).execute().getOutput();
	}
}
