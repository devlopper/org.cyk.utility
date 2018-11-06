package org.cyk.utility.client.controller.navigation;

import java.net.URL;

import org.cyk.utility.client.controller.Objectable;

public interface Navigation extends Objectable {

	URL getUniformResourceLocator();
	Navigation setUniformResourceLocator(URL uniformResourceLocator);
	
}