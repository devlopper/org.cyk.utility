package org.cyk.utility.client.controller.data;

import org.cyk.utility.client.controller.Objectable;

public interface Form extends Objectable {

	String getTitle();
	Form setTitle(String title);
	
	void submit();
	
	/**/
	
	String PROPERTY_TITLE = "title";
	String METHOD_SUBMIT = "submit";
}
