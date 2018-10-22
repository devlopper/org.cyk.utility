package org.cyk.utility.client.controller.view;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.AbstractVisibleComponentImpl;
import org.cyk.utility.client.controller.component.VisibleComponents;
import org.cyk.utility.client.controller.component.command.Commandables;
import org.cyk.utility.client.controller.component.output.OutputStringText;

public class ViewImpl extends AbstractVisibleComponentImpl implements View,Serializable {
	private static final long serialVersionUID = 1L;

	private VisibleComponents visibleComponents;
	
	private OutputStringText name;
	private Commandables processingCommandables;

	@Override
	public VisibleComponents getVisibleComponents() {
		return visibleComponents;
	}
	
	@Override
	public View setVisibleComponents(VisibleComponents visibleComponents) {
		this.visibleComponents = visibleComponents;
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
		VisibleComponents visibleComponents = getVisibleComponents();
		if(visibleComponents!=null)
			visibleComponents.setInputOutputValueFromFieldValue();
		return this;
	}

	@Override
	public View setInputOutputFieldValueFromValue() {
		VisibleComponents visibleComponents = getVisibleComponents();
		if(visibleComponents!=null)
			visibleComponents.setInputOutputFieldValueFromValue();
		return this;
	}
}
