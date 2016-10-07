package org.cyk.utility.common;

import java.math.BigDecimal;
import java.util.Collection;

public class ListenerUtils {

	private static final ListenerUtils INSTANCE = new ListenerUtils();
	public static ListenerUtils getInstance() {
		return INSTANCE;
	}
	
	public <RESULT,LISTENER> RESULT getValue(Class<RESULT> valueClass,Collection<LISTENER> listeners,ResultMethod<LISTENER,RESULT> method){
		RESULT result = null;
		if(listeners!=null)
			for(LISTENER listener : listeners){
				RESULT value = method.execute(listener);
				if(value==null)
					;
				else
					result = value;
			}
		if(result==null)
			result = method.getNullValue();
		return result;
	}
	
	public <LISTENER> String getString(Collection<LISTENER> listeners,ResultMethod<LISTENER,String> method){
		return getValue(String.class, listeners, method);
	}
	
	public <LISTENER> BigDecimal getBigDecimal(Collection<LISTENER> listeners,ResultMethod<LISTENER,BigDecimal> method){
		return getValue(BigDecimal.class, listeners, method);
	}
	
	public <LISTENER> Boolean getBoolean(Collection<LISTENER> listeners,ResultMethod<LISTENER,Boolean> method){
		return getValue(Boolean.class, listeners, method);
	}
	
	public <LISTENER> Long getLong(Collection<LISTENER> listeners,ResultMethod<LISTENER,Long> method){
		return getValue(Long.class, listeners, method);
	}
	
	public <LISTENER> Integer getInteger(Collection<LISTENER> listeners,ResultMethod<LISTENER,Integer> method){
		return getValue(Integer.class, listeners, method);
	}
	
	public <LISTENER> Double getDouble(Collection<LISTENER> listeners,ResultMethod<LISTENER,Double> method){
		return getValue(Double.class, listeners, method);
	}
	
	public <LISTENER,ELEMENT_TYPE> Collection<ELEMENT_TYPE> getCollection(Collection<LISTENER> listeners,ResultMethod<LISTENER,Collection<ELEMENT_TYPE>> method){
		Collection<ELEMENT_TYPE> collection = null;
		if(listeners != null)
			for(LISTENER listener : listeners){
				Collection<ELEMENT_TYPE> value = method.execute(listener);
				if(value!=null)
					collection = value;
			}
		return collection;
	}
	
	public <RESULT,LISTENER> void execute(Collection<LISTENER> listeners,VoidMethod<LISTENER> method){
		if(listeners==null)
			return;
		for(LISTENER listener : listeners)
			method.execute(listener);
	}
	
	/**/
	
	public static interface ResultMethod<LISTENER,RESULT>{
		RESULT execute(LISTENER listener);
		RESULT getNullValue();
		public static abstract class Adapter<LISTENER,RESULT> implements ResultMethod<LISTENER, RESULT> {
			@Override
			public RESULT getNullValue() {
				return null;
			}
		}
	}
	
	public static abstract class StringMethod<LISTENER> extends ResultMethod.Adapter<LISTENER,String>{}
	public static abstract class BigDecimalMethod<LISTENER> extends ResultMethod.Adapter<LISTENER,BigDecimal>{}
	public static abstract class BooleanMethod<LISTENER> extends ResultMethod.Adapter<LISTENER,Boolean>{}
	public static abstract class LongMethod<LISTENER> extends ResultMethod.Adapter<LISTENER,Long>{}
	public static abstract class IntegerMethod<LISTENER> extends ResultMethod.Adapter<LISTENER,Integer>{}
	public static abstract class DoubleMethod<LISTENER> extends ResultMethod.Adapter<LISTENER,Double>{}
	public static abstract class CollectionMethod<LISTENER,ELEMENT_TYPE> extends ResultMethod.Adapter<LISTENER,Collection<ELEMENT_TYPE>>{
		//Class<COLLECTION_RUNTIME_TYPE> getCollectionRuntimeType();
	}
	
	public static interface VoidMethod<LISTENER>{
		void execute(LISTENER listener);
	}
}
