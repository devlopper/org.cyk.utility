package org.cyk.utility.client.controller.navigation;

import org.cyk.utility.string.StringFunction;

/**
 * map a navigation identifier to a uniform resource locator
 * @author CYK
 *
 */
public interface NavigationIdentifierToUrlStringMapper extends StringFunction {

	@Override NavigationIdentifierToUrlStringMapper setIdentifier(Object identifier);
	
}
