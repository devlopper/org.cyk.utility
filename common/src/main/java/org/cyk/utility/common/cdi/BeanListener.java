package org.cyk.utility.common.cdi;

public interface BeanListener {

	void info(String message);

	void initialisationStarted(AbstractBean bean);
	
	void initialisationEnded(AbstractBean bean);
	
	void afterInitialisationStarted(AbstractBean bean);
	
	void afterInitialisationEnded(AbstractBean bean);
	
}
