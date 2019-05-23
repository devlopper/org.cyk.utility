package org.cyk.utility.client.controller.web.jsf;

import java.io.Serializable;

import javax.faces.model.SelectItem;

import org.cyk.utility.__kernel__.function.AbstractFunctionRunnableImpl;
import org.cyk.utility.client.controller.component.input.choice.ChoiceBuilder;
import org.cyk.utility.string.StringHelper;

public class ChoiceBuilderFunctionRunnableImpl extends AbstractFunctionRunnableImpl<ChoiceBuilder> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public ChoiceBuilderFunctionRunnableImpl() {
		setRunnable(new Runnable() {
			@Override
			public void run() {
				SelectItem selectItem = new SelectItem();
				Object value = getFunction().getValue(); //TODO be careful take the code
				selectItem.setValue(value);
				
				String label = getFunction().getLabel();
				if(__inject__(StringHelper.class).isBlank(label))
					label = value == null ? "??NULL LABEL??" : value.toString();
				if(__inject__(StringHelper.class).isBlank(label))
					label = "??EMPTY LABEL??";
				selectItem.setLabel(label);
				
				setOutput(selectItem);
			}
		});
	}
	
	
}