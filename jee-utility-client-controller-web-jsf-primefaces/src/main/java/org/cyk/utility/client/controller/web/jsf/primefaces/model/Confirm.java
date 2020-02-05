package org.cyk.utility.client.controller.web.jsf.primefaces.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Confirm extends AbstractObject implements Serializable {

	private String header = "Confirmation";
	private String message = "Êtes-vous sûr de vouloir exécuter cette action ?";
	private String icon = "fa fa-warning";
	private Boolean disabled = Boolean.FALSE;
	private Boolean escape = Boolean.FALSE;
	
}
