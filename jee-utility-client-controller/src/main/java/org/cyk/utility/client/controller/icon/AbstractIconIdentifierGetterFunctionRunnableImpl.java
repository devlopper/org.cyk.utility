package org.cyk.utility.client.controller.icon;

import java.io.Serializable;

import org.cyk.utility.__kernel__.function.AbstractFunctionRunnableImpl;

public abstract class AbstractIconIdentifierGetterFunctionRunnableImpl extends AbstractFunctionRunnableImpl<IconIdentifierGetter> implements Serializable {
	private static final long serialVersionUID = 1L;

	public AbstractIconIdentifierGetterFunctionRunnableImpl() {
		setRunnable(new Runnable() { @Override public void run() {
			Icon icon = getFunction().getIcon();
			if(icon!=null) {
				Object identifier = __process__(icon);
				setOutput(identifier);
			}
		}});
	}
	
	protected abstract Object __process__(Icon icon);
	
}
