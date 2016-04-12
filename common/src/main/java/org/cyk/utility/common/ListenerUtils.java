package org.cyk.utility.common;

import java.util.Collection;

public class ListenerUtils {

	private static final ListenerUtils INSTANCE = new ListenerUtils();
	public static ListenerUtils getInstance() {
		return INSTANCE;
	}
	
	public <RESULT,LISTENER> RESULT getValue(Class<RESULT> valueClass,Collection<LISTENER> listeners,GetValueMethodListener<LISTENER,RESULT> method){
		RESULT result = null;
		for(LISTENER listener : listeners){
			RESULT value = method.execute(listener);
			if(value!=null)
				result = value;
		}
		return result;
	}
	
	public static interface GetValueMethodListener<LISTENER,RESULT>{
		RESULT execute(LISTENER listener);
	}
	
}
