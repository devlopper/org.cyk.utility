package org.cyk.utility.client.controller.web.jsf.primefaces.__internal__;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class __InternalCheckerBuildTimeTemplateNotExtendsPage__ implements Serializable {
	private static final long serialVersionUID = 1L;

	private String title;
	
	@PostConstruct
	private void listenPostConstruct() {
		title = "Template without extends";
	}
	
}
