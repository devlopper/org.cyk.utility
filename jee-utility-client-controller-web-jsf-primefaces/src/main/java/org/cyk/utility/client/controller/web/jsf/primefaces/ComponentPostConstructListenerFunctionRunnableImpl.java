package org.cyk.utility.client.controller.web.jsf.primefaces;

import java.io.Serializable;

import org.cyk.utility.__kernel__.function.AbstractFunctionRunnableImpl;
import org.cyk.utility.client.controller.component.Component;
import org.cyk.utility.client.controller.component.ComponentPostConstructListener;
import org.cyk.utility.client.controller.component.form.Form;
import org.cyk.utility.client.controller.component.input.Input;
import org.cyk.utility.client.controller.component.layout.Layout;
import org.cyk.utility.client.controller.component.layout.LayoutBasedCss;
import org.cyk.utility.client.controller.component.layout.LayoutItemBasedCss;
import org.cyk.utility.random.RandomHelper;

public class ComponentPostConstructListenerFunctionRunnableImpl extends AbstractFunctionRunnableImpl<ComponentPostConstructListener> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private static final String IDENTIFIER_FORMAT = "%s_%s";
	
	public ComponentPostConstructListenerFunctionRunnableImpl() {
		setRunnable(new Runnable() {
			@Override
			public void run() {
				Component component = (Component) getFunction().getObject();
				component.getProperties().setIdentifier(String.format(IDENTIFIER_FORMAT,component.getClass().getSimpleName(),__inject__(RandomHelper.class).getAlphabetic(3)));
				component.getProperties().setRendered(Boolean.TRUE);
				
				if(component instanceof Input) {
					Input<?> input = (Input<?>) component;
					
					if(input.getLabelComponent()!=null) {
						input.getLabelComponent().getProperties().setFor(input.getProperties().getIdentifier());
						input.getLabelComponent().getLayoutCellComponent(Boolean.TRUE).getProperties().setClass("ui-g-2 ui-xl-2 ui-lg-2 ui-md-2 ui-sm-12");
					}
					
					input.getLayoutCellComponent(Boolean.TRUE).getProperties().setClass("ui-g-6 ui-xl-6 ui-lg-6 ui-md-6 ui-sm-12");
					
					if(input.getMessageComponent()!=null) {
						input.getMessageComponent().getProperties().setFor(input.getProperties().getIdentifier());
						input.getMessageComponent().getLayoutCellComponent(Boolean.TRUE).getProperties().setClass("ui-g-4 ui-xl-4 ui-lg-4 ui-md-4 ui-sm-12");
					}
				}
				
				if(component instanceof LayoutBasedCss) {
					Layout layout = (Layout) component;
					layout.getProperties().setClass("ui-g");
				}
				
				if(component instanceof LayoutItemBasedCss) {
					//LayoutCell layoutCell = (LayoutCell) component;
					//layoutCell.getProperties().setClass("ui-g-12 ui-md-6 ui-lg-3");
				}
				
				if(component instanceof Form) {
					/*Form form = (Form) component;
					form.setHeaderLayoutCellComponent(__inject__(LayoutCellResponsive.class));
					form.getHeaderLayoutCellComponent().getProperties().setClass("ui-g-12 ui-xl-12 ui-lg-12 ui-md-12 ui-sm-12");
					
					form.setBodyLayoutCellComponent(__inject__(LayoutCellResponsive.class));
					form.getBodyLayoutCellComponent().getProperties().setClass("ui-g-12 ui-xl-12 ui-lg-12 ui-md-12 ui-sm-12");
					
					form.setFooterLayoutCellComponent(__inject__(LayoutCellResponsive.class));
					form.getFooterLayoutCellComponent().getProperties().setClass("ui-g-12 ui-xl-12 ui-lg-12 ui-md-12 ui-sm-12");
					*/
				}
			}
		});
	}
	
}