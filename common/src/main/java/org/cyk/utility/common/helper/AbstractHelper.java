package org.cyk.utility.common.helper;

import java.io.Serializable;

import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.cdi.BeanListener;

public abstract class AbstractHelper extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 9139445069356830304L;
	
	/**/
	
	public static interface Listener {
		
		/**/
		
		public static class Adapter extends BeanListener.Adapter implements Listener,Serializable {
			private static final long serialVersionUID = 1L;
			
			/**/
			
			public static class Default extends Listener.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
				
			}
			
		}
	}
}
