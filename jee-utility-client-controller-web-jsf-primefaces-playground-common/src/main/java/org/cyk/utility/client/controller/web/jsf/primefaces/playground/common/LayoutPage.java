package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.cyk.utility.client.controller.component.layout.LayoutCellResponsive;
import org.cyk.utility.client.controller.component.layout.LayoutGridResponsive;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageImpl;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class LayoutPage extends AbstractPageImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject private LayoutGridResponsive formResponsive01;
	@Inject private LayoutCellResponsive formResponsive01Label01;
	@Inject private LayoutCellResponsive formResponsive01Input01;
	@Inject private LayoutCellResponsive formResponsive01Label02;
	@Inject private LayoutCellResponsive formResponsive01Input02;
	@Inject private LayoutCellResponsive formResponsive01Label03;
	@Inject private LayoutCellResponsive formResponsive01Input03;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		String uigLabel = "ui-g-1";
		String bigScreenLabel = "ui-xl-1";
		String desktopLabel = "ui-lg-1";
		String tabletLabel = "ui-md-1";
		String phoneLabel = "ui-sm-12";
		
		String uigInput = "ui-g-11";
		String bigScreenInput = "ui-xl-11";
		String desktopInput = "ui-lg-11";
		String tabletInput = "ui-md-11";
		String phoneInput = "ui-sm-12";
		
		formResponsive01Label01.getProperties().setClass(uigLabel+" "+bigScreenLabel+" "+desktopLabel+" "+tabletLabel+" "+phoneLabel);
		formResponsive01Input01.getProperties().setClass(uigInput+" "+bigScreenInput+" "+desktopInput+" "+tabletInput+" "+phoneInput);
		formResponsive01Label02.getProperties().setClass(uigLabel+" "+bigScreenLabel+" "+desktopLabel+" "+tabletLabel+" "+phoneLabel);
		formResponsive01Input02.getProperties().setClass(uigInput+" "+bigScreenInput+" "+desktopInput+" "+tabletInput+" "+phoneInput);
		formResponsive01Label03.getProperties().setClass(uigLabel+" "+bigScreenLabel+" "+desktopLabel+" "+tabletLabel+" "+phoneLabel);
		formResponsive01Input03.getProperties().setClass(uigInput+" "+bigScreenInput+" "+desktopInput+" "+tabletInput+" "+phoneInput);
	}
	
}
