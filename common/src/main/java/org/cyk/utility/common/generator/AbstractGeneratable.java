package org.cyk.utility.common.generator;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.cyk.utility.common.Constant;
import org.cyk.utility.common.Constant.Date.Length;
import org.cyk.utility.common.ListenerUtils;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.cdi.BeanAdapter;
import org.cyk.utility.common.helper.NumberHelper;
import org.cyk.utility.common.helper.TimeHelper;

import lombok.Getter;
import lombok.Setter;

public abstract class AbstractGeneratable<T> extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 6717138845030531852L;
	public static Object NULL_VALUE = Constant.EMPTY_STRING;
	static {
		AbstractGeneratable.Listener.COLLECTION.add(new AbstractGeneratable.Listener.Adapter.Default());
	}
	
	protected RandomDataProvider provider = RandomDataProvider.getInstance();
	
	@Getter @Setter protected String identifier;
	
	@Getter @Setter protected T previous;
	@Getter @Setter protected T next;
	
	@Getter @Setter protected Object source;
	@Getter @Setter protected String text;
	
	@Setter protected Map<String,List<?>> fieldsRandomValues;
	
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
	
	public Map<String,List<?>> createFieldsRandomValues(){
		return new HashMap<>();
	}
	
	public Map<String,List<?>> getFieldsRandomValues(){
		if(fieldsRandomValues==null){
			fieldsRandomValues = createFieldsRandomValues();
		}
		return fieldsRandomValues;
	}
	
	@SuppressWarnings("unchecked")
	public <VALUE_TYPE> List<VALUE_TYPE> getFieldRandomValues(Class<VALUE_TYPE> valueTypeClass,String fieldName){
		Map<String,List<?>> map = getFieldsRandomValues();
		if(map!=null)
			return (List<VALUE_TYPE>) map.get(fieldName);
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public <VALUE_TYPE> VALUE_TYPE getFieldRandomValue(Class<VALUE_TYPE> valueTypeClass,String fieldName){
		List<VALUE_TYPE> list = getFieldRandomValues(valueTypeClass, fieldName);
		if(list!=null)
			return (VALUE_TYPE) provider.randomFromList(list);
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public <VALUE_TYPE> void setFieldRandomValues(Class<VALUE_TYPE> valueTypeClass,String fieldName,List<VALUE_TYPE> values){
		Map<String,List<?>> map = getFieldsRandomValues();
		if(map!=null)
			map.put(fieldName, (List<Object>) values);
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
						return NULL_VALUE;
					if(fieldValue instanceof Number)
						return new NumberHelper.Stringifier.Adapter.Default().setInput((Number) fieldValue).execute();
					if(fieldValue instanceof Date){
						return formatDate(object, (Date)fieldValue, Constant.Date.Part.DATE_ONLY, Constant.Date.Length.SHORT);
					}
					return fieldValue.toString();
				}
				
				@Override
				public Object formatNumberToWords(Object object, Number fieldValue) {
					if(fieldValue==null)
						return NULL_VALUE;
					/*NumberFormatter.String numberFormatter = new NumberFormatter.String.Adapter.Default((Number) fieldValue,null);
					numberFormatter.setCharacterSet(CharacterSet.LETTER);
					numberFormatter.setLocale(getLocale());
					*/
					return new NumberHelper.Stringifier.Adapter.Default().setCharacterSet(NumberHelper.Stringifier.CharacterSet.LETTER).setInput((Number) fieldValue)
							.setLocale(getLocale()).execute();
				}
				
				@Override
				public Object formatDate(Object object, Date fieldValue,Constant.Date.Part part,Constant.Date.Length length) {
					if(fieldValue==null)
						return NULL_VALUE;
					/*DateFormatter.String formatter = new DateFormatter.String.Adapter.Default(fieldValue,null);
					formatter.setPart(part).setLength(length).setLocale(getLocale());
					*/
					return new TimeHelper.Stringifier.Date.Adapter.Default(fieldValue).setLocale(getLocale())
							.setProperty(TimeHelper.Stringifier.PROPERTY_NAME_TIME_PART, part)
							.setProperty(TimeHelper.Stringifier.PROPERTY_NAME_TIME_LENGTH, length).execute();
				}
				
			}
			
		}
	}
}
