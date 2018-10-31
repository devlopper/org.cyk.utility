package org.cyk.utility.client.controller.web.jsf.primefaces;

import java.io.Serializable;

import org.cyk.utility.__kernel__.function.AbstractFunctionRunnableImpl;
import org.cyk.utility.client.controller.component.theme.ThemeClassGetter;
import org.cyk.utility.client.controller.web.jsf.primefaces.theme.ThemeDesktopDefault;

public class ThemeClassGetterFunctionRunnableImpl extends AbstractFunctionRunnableImpl<ThemeClassGetter> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public ThemeClassGetterFunctionRunnableImpl() {
		setRunnable(new Runnable() {
			@Override
			public void run() {
				setOutput(ThemeDesktopDefault.class);
			}
		});
	}
	
}