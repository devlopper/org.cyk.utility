package org.cyk.utility.common.cdi;

import java.io.Serializable;

public class BeanAdapter extends AbstractBean implements BeanListener,Serializable {

	private static final long serialVersionUID = 7673343587983459174L;

	@Override
	public void info(String message) {}

	@Override
	public void initialisationStarted(AbstractBean bean) {}

	@Override
	public void initialisationEnded(AbstractBean bean) {}

	@Override
	public void afterInitialisationStarted(AbstractBean bean) {}

	@Override
	public void afterInitialisationEnded(AbstractBean bean) {}

}
