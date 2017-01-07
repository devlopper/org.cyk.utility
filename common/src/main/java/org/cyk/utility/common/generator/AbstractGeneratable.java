package org.cyk.utility.common.generator;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.common.ListenerUtils;
import org.cyk.utility.common.cdi.BeanAdapter;

import lombok.Getter;
import lombok.Setter;

public abstract class AbstractGeneratable<T> implements Serializable {

	private static final long serialVersionUID = 6717138845030531852L;

	protected RandomDataProvider provider = RandomDataProvider.getInstance();
	
	@Getter @Setter protected Object source;
	
	protected ByteArrayInputStream inputStream(byte[] bytes){
		return new ByteArrayInputStream(bytes);
	}
	
	protected String positiveFloatNumber(Integer left,Integer minRight,Integer maxRight){
		return provider.randomPositiveInt(left)+"."+provider.randomInt(minRight,maxRight);
	}
	
	public abstract void generate();
	
	protected String format(final Object object){
		Object result = ListenerUtils.getInstance().getValue(Object.class, Listener.COLLECTION, new ListenerUtils.ResultMethod<Listener, Object>() {

			@Override
			public Object execute(Listener listener) {
				return listener.format(AbstractGeneratable.this, object);
			}

			@Override
			public Object getNullValue() {
				return null;
			}
		});
		if(result==null){
			if(object==null)
				return null;
			else
				return object.toString();
		}
		return result.toString();
	}
	
	/**/
	
	public static interface Listener {
		Collection<Listener> COLLECTION = new ArrayList<>();
		
		Object format(Object object,Object fieldValue);
		
		public static class Adapter extends BeanAdapter implements Listener,Serializable {
			private static final long serialVersionUID = 1L;
			
			@Override
			public Object format(Object object, Object fieldValue) {
				return null;
			}
			
			public static class Default extends Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
				
				@Override
				public Object format(Object object, Object fieldValue) {
					if(fieldValue==null)
						return null;
					return fieldValue.toString();
				}
				
			}
			
		}
	}
}
