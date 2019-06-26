package org.cyk.utility.client.controller.web.jsf.primefaces.component;

import java.io.Serializable;

import javax.faces.component.UIComponent;

import org.cyk.utility.client.controller.component.Component;
import org.cyk.utility.client.controller.component.Components;
import org.cyk.utility.client.controller.component.view.View;
import org.cyk.utility.client.controller.web.ValueExpressionMap;
import org.cyk.utility.collection.CollectionHelper;
import org.primefaces.component.outputpanel.OutputPanel;

public class OutputPanelFromViewBuilderImpl extends AbstractUIComponentBuilderImpl<OutputPanel,View> implements OutputPanelFromViewBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected OutputPanel __execute__(View view, ValueExpressionMap valueExpressionMap) throws Exception {
		OutputPanel outputPanel = null;
		Components components = view.getComponents();
		if(__inject__(CollectionHelper.class).isNotEmpty(components)) {
			outputPanel = new OutputPanel();
			outputPanel.setStyleClass(components.getLayout().getStyle().getClassesAsString());
			for(Component index : components.get()) {
				if(index != null) {
					UIComponent uiComponent = (UIComponent) __inject__(ComponentBuilderHelper.class).build(index);
					if(uiComponent != null)
						outputPanel.getChildren().add(uiComponent);	
				}
			}
		}
		return outputPanel;
	}
	
}
