package org.cyk.utility.client.controller.navigation;

import java.io.Serializable;

import org.cyk.utility.__kernel__.function.AbstractFunctionRunnableImpl;

@Deprecated
public abstract class AbstractNavigationIdentifierStringBuilderExtensionFunctionRunnableImpl extends AbstractFunctionRunnableImpl<NavigationIdentifierStringBuilderExtension> implements Serializable {
	private static final long serialVersionUID = 1L;

	public AbstractNavigationIdentifierStringBuilderExtensionFunctionRunnableImpl() {
		setRunnable(new Runnable() { @Override public void run() {
			setOutput(getFunction().getResult());
		}});
	}
	
}
