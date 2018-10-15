package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.cyk.utility.client.controller.component.Component;
import org.cyk.utility.client.controller.component.VisibleComponent;
import org.cyk.utility.client.controller.component.input.Input;
import org.cyk.utility.client.controller.component.input.InputText;
import org.cyk.utility.client.controller.component.input.InputTextArea;
import org.cyk.utility.client.controller.component.layout.Layout;
import org.cyk.utility.client.controller.component.layout.LayoutBuiler;
import org.cyk.utility.client.controller.component.layout.LayoutBuilerItem;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageImpl;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class InputPage extends AbstractPageImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject private Layout layout01;
	@Inject private Layout layout02;
	private List<VisibleComponent> components;
	@Inject private InputText inputText;
	@Inject private InputTextArea inputTextArea;
	@Inject private InputText inputTextRequired;
	private InputText inputTextNull;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		
		layout01 = __inject__(LayoutBuiler.class)
				.addItems(__inject__(LayoutBuilerItem.class).setWidth(2).setWidthForDesktop(2).setWidthForTablet(2).setWidthForPhone(12))
				.addItems(__inject__(LayoutBuilerItem.class).setWidth(6).setWidthForDesktop(6).setWidthForTablet(6).setWidthForPhone(12))
				.addItems(__inject__(LayoutBuilerItem.class).setWidth(4).setWidthForDesktop(4).setWidthForTablet(4).setWidthForPhone(12))
				
				.addItems(__inject__(LayoutBuilerItem.class).setWidth(2).setWidthForDesktop(2).setWidthForTablet(2).setWidthForPhone(12))
				.addItems(__inject__(LayoutBuilerItem.class).setWidth(6).setWidthForDesktop(6).setWidthForTablet(6).setWidthForPhone(12))
				.addItems(__inject__(LayoutBuilerItem.class).setWidth(4).setWidthForDesktop(4).setWidthForTablet(4).setWidthForPhone(12))
				
				.addItems(__inject__(LayoutBuilerItem.class).setWidth(2).setWidthForDesktop(2).setWidthForTablet(2).setWidthForPhone(12))
				.addItems(__inject__(LayoutBuilerItem.class).setWidth(6).setWidthForDesktop(6).setWidthForTablet(6).setWidthForPhone(12))
				.addItems(__inject__(LayoutBuilerItem.class).setWidth(4).setWidthForDesktop(4).setWidthForTablet(4).setWidthForPhone(12))
				
				.execute().getOutput()
				;
		
		layout02 = __inject__(LayoutBuiler.class)
				.addItems(__inject__(LayoutBuilerItem.class).setWidth(2).setWidthForDesktop(2).setWidthForTablet(2).setWidthForPhone(12))
				.addItems(__inject__(LayoutBuilerItem.class).setWidth(2).setWidthForDesktop(2).setWidthForTablet(2).setWidthForPhone(12))
				.addItems(__inject__(LayoutBuilerItem.class).setWidth(2).setWidthForDesktop(2).setWidthForTablet(2).setWidthForPhone(12))
				.addItems(__inject__(LayoutBuilerItem.class).setWidth(2).setWidthForDesktop(2).setWidthForTablet(2).setWidthForPhone(12))
				.addItems(__inject__(LayoutBuilerItem.class).setWidth(2).setWidthForDesktop(2).setWidthForTablet(2).setWidthForPhone(12))
				.addItems(__inject__(LayoutBuilerItem.class).setWidth(2).setWidthForDesktop(2).setWidthForTablet(2).setWidthForPhone(12))
				
				.addItems(__inject__(LayoutBuilerItem.class).setWidth(2).setWidthForDesktop(2).setWidthForTablet(2).setWidthForPhone(12))
				.addItems(__inject__(LayoutBuilerItem.class).setWidth(6).setWidthForDesktop(6).setWidthForTablet(6).setWidthForPhone(12))
				.addItems(__inject__(LayoutBuilerItem.class).setWidth(4).setWidthForDesktop(4).setWidthForTablet(4).setWidthForPhone(12))
				
				.execute().getOutput()
				;
		
		components = new ArrayList<>();
		
		inputText.setLabelComponentValue("Simple");
		components.add(inputText.getLabelComponent());
		components.add(inputText);
		components.add(inputText.getMessageComponent());
		
		inputTextRequired.getProperties().setRequired(Boolean.TRUE);
		inputTextRequired.setLabelComponentValue("Required");
		components.add(inputTextRequired.getLabelComponent());
		components.add(inputTextRequired);
		components.add(inputTextRequired.getMessageComponent());
		
		inputTextArea.setLabelComponentValue("TextArea");
		inputTextArea.getProperties().setRequired(Boolean.TRUE);
		components.add(inputTextArea.getLabelComponent());
		components.add(inputTextArea);
		components.add(inputTextArea.getMessageComponent());
		
		Layout layout = layout02;
		
		inputText.getLabelComponent().setLayoutItem(layout.getChildAt(0));
		inputText.setLayoutItem(layout.getChildAt(1));
		inputText.getMessageComponent().setLayoutItem(layout.getChildAt(2));
		
		inputTextRequired.getLabelComponent().setLayoutItem(layout.getChildAt(3));
		inputTextRequired.setLayoutItem(layout.getChildAt(4));
		inputTextRequired.getMessageComponent().setLayoutItem(layout.getChildAt(5));
		
		inputTextArea.getLabelComponent().setLayoutItem(layout.getChildAt(6));
		inputTextArea.setLayoutItem(layout.getChildAt(7));
		inputTextArea.getMessageComponent().setLayoutItem(layout.getChildAt(8));
	}
	
	public void show() {
		System.out.println("InputPage.show()");
		for(Component index : components)
			if(index instanceof Input) {
				Input<?> input = (Input<?>) index;
				System.out.println("Input : "+input.getValue());
			}
	}
}
