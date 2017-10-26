package org.cyk.utility.common.test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.cdi.BeanListener;

import lombok.Getter;
import lombok.Setter;

public interface Runnable<LISTENER extends Runnable.Listener> {

	Collection<LISTENER> getListeners();
					
    public abstract void run();
    
    /**/
    
    @Getter @Setter
    public class Adapter<LISTENER extends Runnable.Listener> extends AbstractBean implements Runnable<LISTENER>,Serializable {
		private static final long serialVersionUID = 4540958768790791089L;
    	
		protected Collection<LISTENER> listeners = new ArrayList<>();
		
		@Override
		public void run() {
			
		}
    }
    
    /**/
    
    public static interface Listener{
    	    	
    	/**/
    	
    	public static class Adapter extends BeanListener.Adapter implements Listener,Serializable{
			private static final long serialVersionUID = -8100384307117008109L;

    	}
    }
}
