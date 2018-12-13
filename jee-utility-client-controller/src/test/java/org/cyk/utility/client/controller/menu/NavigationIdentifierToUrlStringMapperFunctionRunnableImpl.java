package org.cyk.utility.client.controller.menu;

import java.io.Serializable;

import org.cyk.utility.__kernel__.function.AbstractFunctionRunnableImpl;
import org.cyk.utility.client.controller.navigation.NavigationIdentifierToUrlStringMapper;

public class NavigationIdentifierToUrlStringMapperFunctionRunnableImpl extends AbstractFunctionRunnableImpl<NavigationIdentifierToUrlStringMapper> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public NavigationIdentifierToUrlStringMapperFunctionRunnableImpl() {
		setRunnable(new Runnable() {
			@Override
			public void run() {
				//Object identifier = getFunction().getIdentifier();
				//if("__entity__ListView".equals(identifier))
				//	setOutput("http://localhost:8080/list.jsf");
				setOutput("http://localhost:8080/");
			}
		});
	}
	
}