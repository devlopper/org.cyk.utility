package org.cyk.utility.client.controller.component.view;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.AbstractVisibleComponentImpl;
import org.cyk.utility.client.controller.component.Components;
import org.cyk.utility.client.controller.component.command.Commandables;
import org.cyk.utility.client.controller.component.output.OutputStringText;

public class ViewImpl extends AbstractVisibleComponentImpl implements View,Serializable {
	private static final long serialVersionUID = 1L;

	private Components components;
	
	private OutputStringText name;
	private Commandables processingCommandables;

	@Override
	public Components getComponents() {
		return components;
	}
	
	@Override
	public View setComponents(Components components) {
		this.components = components;
		return this;
	}
	
	@Override
	public OutputStringText getName() {
		return name;
	}

	@Override
	public OutputStringText getName(Boolean injectIfNull) {
		OutputStringText name = getName();
		if(name == null && Boolean.TRUE.equals(injectIfNull))
			setName(name = __inject__(OutputStringText.class));
		return name;
	}

	@Override
	public View setName(OutputStringText name) {
		this.name = name;
		return this;
	}
	
	@Override
	public Commandables getProcessingCommandables() {
		return processingCommandables;
	}
	
	@Override
	public View setProcessingCommandables(Commandables processingCommandables) {
		this.processingCommandables = processingCommandables;
		return this;
	}

	@Override
	public View setInputOutputValueFromFieldValue() {
		Components components = getComponents();
		if(components!=null)
			components.setInputOutputValueFromFieldValue();
		return this;
	}

	@Override
	public View setInputOutputFieldValueFromValue() {
		Components components = getComponents();
		if(components!=null)
			components.setInputOutputFieldValueFromValue();
		return this;
	}
}
