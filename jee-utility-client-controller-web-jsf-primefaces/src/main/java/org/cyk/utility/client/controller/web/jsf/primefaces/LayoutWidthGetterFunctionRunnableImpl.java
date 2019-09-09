package org.cyk.utility.client.controller.web.jsf.primefaces;

import java.io.Serializable;

import org.cyk.utility.__kernel__.function.AbstractFunctionRunnableImpl;
import org.cyk.utility.client.controller.component.layout.LayoutWidthGetter;

@Deprecated
public class LayoutWidthGetterFunctionRunnableImpl extends AbstractFunctionRunnableImpl<LayoutWidthGetter> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public LayoutWidthGetterFunctionRunnableImpl() {
		setRunnable(new Runnable() {
			@Override
			public void run() {
				setOutput(12);
			}
		});
	}
	
}