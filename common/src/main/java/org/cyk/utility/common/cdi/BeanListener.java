package org.cyk.utility.common.cdi;

import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.common.Properties;

public interface BeanListener {

	Collection<BeanListener> COLLECTION = new ArrayList<BeanListener>();
	
	void info(String message);

	void initialisationStarted(AbstractBean bean);
	
	void initialisationEnded(AbstractBean bean);
	
	void afterInitialisationStarted(AbstractBean bean);
	
	void afterInitialisationEnded(AbstractBean bean);
	
	void instanciated(AbstractBean instance);
	
	void propertiesMapInstanciated(AbstractBean instance,Properties properties);
	
	/**/
	
	/*public static class Adapter extends AbstractBean implements BeanListener,Serializable {

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

		@Override
		public void instanciated(AbstractBean instance) {}

		@Override
		public void propertiesMapInstanciated(AbstractBean instance) {}

	}*/

}
