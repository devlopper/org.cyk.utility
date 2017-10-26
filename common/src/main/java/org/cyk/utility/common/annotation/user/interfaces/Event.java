package org.cyk.utility.common.annotation.user.interfaces;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.common.ListenerUtils;
import org.cyk.utility.common.When;
import org.cyk.utility.common.cdi.BeanListener;

public enum Event{
	CLICK,
	KEYS
	
	;
	
	public static interface Listener {
		
		void on(Event event,When when);
		
		public static class Adapter extends BeanListener.Adapter implements Listener,Serializable {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void on(Event event, When when) {}
			
			/**/
			
			public static void listen(Collection<Listener> listeners,final Event event,final When when){
				ListenerUtils.getInstance().execute(listeners, new ListenerUtils.VoidMethod<Listener>() {
					@Override
					public void execute(Event.Listener listener) {
						listener.on(event, when);
					}
				});
			}
			public static void listen(Collection<Listener> listeners,Event event,Runnable runnable){
				listen(listeners,event, When.BEFORE);
				runnable.run();
		    	listen(listeners,event, When.AFTER);
			}
			
			
		}
		
	}
}