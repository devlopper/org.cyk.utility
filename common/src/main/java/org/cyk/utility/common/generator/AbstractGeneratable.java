package org.cyk.utility.common.generator;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

import org.cyk.utility.common.Constant;
import org.cyk.utility.common.ListenerUtils;
import org.cyk.utility.common.Constant.Date.Length;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.cdi.BeanAdapter;
import org.cyk.utility.common.formatter.DateFormatter;
import org.cyk.utility.common.formatter.Formatter.CharacterSet;
import org.cyk.utility.common.formatter.NumberFormatter;

import lombok.Getter;
import lombok.Setter;

public abstract class AbstractGeneratable<T> extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 6717138845030531852L;
	
	static {
		AbstractGeneratable.Listener.COLLECTION.add(new AbstractGeneratable.Listener.Adapter.Default());
	}
	
	protected RandomDataProvider provider = RandomDataProvider.getInstance();
	
	@Getter @Setter protected T previous;
	@Getter @Setter protected T next;
	
	@Getter @Setter protected Object source;
	@Getter @Setter protected String text;
	
	public void setSourceOnly(Object source){
		this.source = source;
	}
	
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
	
	protected String formatNumberToWords(final Number number){
		Object result = ListenerUtils.getInstance().getValue(Object.class, Listener.COLLECTION, new ListenerUtils.ResultMethod<Listener, Object>() {

			@Override
			public Object execute(Listener listener) {
				return listener.formatNumberToWords(AbstractGeneratable.this, number);
			}

			@Override
			public Object getNullValue() {
				return null;
			}
		});
		if(result==null){
			if(number==null)
				return null;
			else
				return number.toString();
		}
		return result.toString();
	}
	
	protected String formatDate(final Date date,final Constant.Date.Part part,final Constant.Date.Length length){
		Object result = ListenerUtils.getInstance().getValue(Object.class, Listener.COLLECTION, new ListenerUtils.ResultMethod<Listener, Object>() {

			@Override
			public Object execute(Listener listener) {
				return listener.formatDate(AbstractGeneratable.this, date,part,length);
			}

			@Override
			public Object getNullValue() {
				return null;
			}
		});
		if(result==null){
			if(date==null)
				return null;
			else
				return date.toString();
		}
		return result.toString();
	}
	
	/**/
	
	public static interface Listener {
		Collection<Listener> COLLECTION = new ArrayList<>();
		
		Locale getLocale();
		Object format(Object object,Object fieldValue);
		Object formatDate(Object object,Date fieldValue,Constant.Date.Part part,Constant.Date.Length length);
		Object formatNumberToWords(Object object,Number fieldValue);
		
		public static class Adapter extends BeanAdapter implements Listener,Serializable {
			private static final long serialVersionUID = 1L;
			
			@Override
			public Locale getLocale() {
				return null;
			}
			
			@Override
			public Object format(Object object, Object fieldValue) {
				return null;
			}
			
			@Override
			public Object formatNumberToWords(Object object, Number fieldValue) {
				return null;
			}
			
			@Override
			public Object formatDate(Object object, Date fieldValue,Constant.Date.Part part, Length length) {
				return null;
			}
			
			public static class Default extends Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
				
				public static Locale LOCALE = Locale.FRENCH;
				
				@Override
				public Locale getLocale() {
					return LOCALE;
				}
				
				@Override
				public Object format(Object object, Object fieldValue) {
					if(fieldValue==null)
						return null;
					if(fieldValue instanceof Number){
						NumberFormatter.String numberFormatter = new NumberFormatter.String.Adapter.Default((Number) fieldValue,null);
						return numberFormatter.execute();
					}
					if(fieldValue instanceof Date){
						return formatDate(object, (Date)fieldValue, Constant.Date.Part.DATE_ONLY, Constant.Date.Length.SHORT);
					}
					return fieldValue.toString();
				}
				
				@Override
				public Object formatNumberToWords(Object object, Number fieldValue) {
					if(fieldValue==null)
						return null;
					NumberFormatter.String numberFormatter = new NumberFormatter.String.Adapter.Default((Number) fieldValue,null);
					numberFormatter.setCharacterSet(CharacterSet.LETTER);
					numberFormatter.setLocale(getLocale());
					return numberFormatter.execute();
				}
				
				@Override
				public Object formatDate(Object object, Date fieldValue,Constant.Date.Part part,Constant.Date.Length length) {
					if(fieldValue==null)
						return null;
					DateFormatter.String formatter = new DateFormatter.String.Adapter.Default(fieldValue,null);
					formatter.setPart(part).setLength(length).setLocale(getLocale());
					return formatter.execute();
				}
				
			}
			
		}
	}
}
