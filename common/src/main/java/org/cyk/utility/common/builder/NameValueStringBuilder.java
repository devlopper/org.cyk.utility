package org.cyk.utility.common.builder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.ListenerUtils;
import org.cyk.utility.common.builder.NameValueStringBuilder.Listener.Strategy;
import org.cyk.utility.common.helper.NumberHelper;

@Getter @Setter @NoArgsConstructor @Accessors(chain=true)
public class NameValueStringBuilder extends AbstractStringBuilder implements Serializable {
	private static final long serialVersionUID = -872728112292086623L;
	
	public static final String NAME_VALUE_STRING_FORMAT = "%s%s%s";
	public static final String NAME_VALUE_SEPARATOR = "=";
	
	static {
		//NameValueStringBuilder.Listener.COLLECTION.add(new NameValueStringBuilder.Listener.Adapter.Default());
	}
	
	private Object name;
	private Collection<Object> values = new ArrayList<>();
	private String separator;
	private Boolean encoded;
	
	private String resultName;
	
	public NameValueStringBuilder(Object name,Object value) {
		this.name = name;
		if(value instanceof Collection){
			addCollection((Collection<?>) value);
			setEncoded(Boolean.TRUE);
		}else{
			if(value instanceof AbstractStringBuilder)
				value = ((AbstractStringBuilder)value).build();
			add(value);
		}
	}
	
	public NameValueStringBuilder(Object name) {
		this(name,null);
	}
	
	public NameValueStringBuilder set(Object name,Object value){
		setName(name);
		getValues().clear();
		getValues().add(value);
		return this;
	}
	
	public NameValueStringBuilder add(Object value){
		getValues().add(value);
		return this;
	}
	
	public NameValueStringBuilder addArray(Object...values){
		if(values!=null)
			for(Object object : values)
				add(object);
		return this;
	}
	
	public NameValueStringBuilder addCollection(Collection<?> values){
		if(values!=null)
			for(Object object : values)
				add(object);
		return this;
	}
	
	@Override
	public String buildWhenBlank() {
		java.lang.StringBuilder stringBuilder = new java.lang.StringBuilder();
		
		if(Boolean.TRUE.equals(listenerUtils.getBoolean(Listener.COLLECTION, new ListenerUtils.BooleanMethod<Listener>() {
			@Override
			public Boolean execute(Listener listener) {
				return listener.isName(name);
			}
			@Override
			public Boolean getNullValue() {
				return name == null 
						? Boolean.FALSE 
						: (name instanceof String ? StringUtils.isNotBlank((String)name) : Boolean.FALSE);
			}
		}))){
			String nameAndValueSeparator = listenerUtils.getString(Listener.COLLECTION, new ListenerUtils.StringMethod<Listener>() {
				@Override
				public String execute(Listener listener) {
					return listener.getNameAndValueSeparator();
				}
				@Override
				public String getNullValue() {
					return Constant.CHARACTER_EQUAL.toString();
				}
			});
			Strategy strategy = listenerUtils.getValue(Strategy.class, Listener.COLLECTION, new ListenerUtils.ResultMethod<Listener, Strategy>() {

				@Override
				public Strategy execute(Listener listener) {
					return listener.getStrategy();
				}

				@Override
				public Strategy getNullValue() {
					return Strategy.NAME_ONE_VALUE;
				}
			});
			
			resultName = listenerUtils.getString(Listener.COLLECTION, new ListenerUtils.StringMethod<Listener>() {
				@Override
				public String execute(Listener listener) {
					return listener.getNameAsString(name);
				}
				
				@Override
				public String getNullValue() {
					return name.toString();
				}
			});
			
			Boolean encoded = listenerUtils.getBoolean(Listener.COLLECTION, new ListenerUtils.BooleanMethod<Listener>() {
				@Override
				public Boolean execute(Listener listener) {
					return listener.getEncoded(name, values);
				}
				@Override
				public Boolean getNullValue() {
					return NameValueStringBuilder.this.encoded;
				}
			});
			
			if(encoded==null){
				
			}
			
			final List<String> values = new ArrayList<>();
			
			if(Boolean.TRUE.equals(encoded)){
				Collection<Object> valuesToProcessed = listenerUtils.getCollection(Listener.COLLECTION, new ListenerUtils.CollectionMethod<Listener, Object>(){
					@Override
					public Collection<Object> execute(Listener listener) {
						return listener.getValuesToProcessed(NameValueStringBuilder.this.values);
					}
				});
				
				if(valuesToProcessed==null){
					valuesToProcessed = new ArrayList<>();
					for(Object value : NameValueStringBuilder.this.values){
						final Object finalValue = value;
						Object valueToProcessed = listenerUtils.getObject(Listener.COLLECTION, new ListenerUtils.ObjectMethod<Listener>() {
							@Override
							public Object execute(Listener listener) {
								return listener.getValueToProcessed(finalValue);
							}
						});
						if(valueToProcessed==null)
							valueToProcessed = value;
						if(valueToProcessed!=null)
							valuesToProcessed.add(valueToProcessed);
					}
				}
				
				
				final Collection<Object> finalValuesToProcessed = valuesToProcessed;
				String encodedValues = listenerUtils.getString(Listener.COLLECTION, new ListenerUtils.StringMethod<Listener>() {
					@Override
					public String execute(Listener listener) {
						return listener.encode(finalValuesToProcessed);
					}

				});
				if(encodedValues==null){
					Collection<Long> longs = new ArrayList<>();
					
					for(Object value : finalValuesToProcessed)
						if(value!=null)
							longs.add(value instanceof Number ? ((Number)value).longValue() : Long.parseLong(value.toString()));
					encodedValues = NumberHelper.getInstance().encode(longs,listenerUtils.getString(Listener.COLLECTION, new ListenerUtils.StringMethod<Listener>() {
						@Override
						public String execute(Listener listener) {
							return listener.getEncodingCharacterSet();
						}
						
						@Override
						public String getNullValue() {
							return NumberHelper.BASE_62_CHARACTERS;
						}
					}));
					
					
				}
				values.add(encodedValues);
			}else{
				for(Object value : NameValueStringBuilder.this.values){
					final Object v = value;
					final Object valueToProcessed = listenerUtils.getObject(Listener.COLLECTION, new ListenerUtils.ObjectMethod<Listener>() {
						@Override
						public Object execute(Listener listener) {
							return listener.getValueToProcessed(v);
						}
					});
					final Object finalValue = valueToProcessed == null ? value : valueToProcessed ;
					if(Boolean.TRUE.equals(listenerUtils.getBoolean(Listener.COLLECTION, new ListenerUtils.BooleanMethod<Listener>() {
						@Override
						public Boolean execute(Listener listener) {
							return listener.isValue(finalValue);
						}
						public Boolean getNullValue() {
							return finalValue!=null || (finalValue instanceof String && StringUtils.isNotBlank((String)finalValue));
						}
					}))){
						values.add(listenerUtils.getString(Listener.COLLECTION, new ListenerUtils.StringMethod<Listener>() {
							@Override
							public String execute(Listener listener) {
								return listener.getValueAsString(finalValue);
							}
							public String getNullValue() {
								return finalValue.toString();
							}
						}));	
					}	
				}	
			}
			
			if(Strategy.NAME_ONE_VALUE.equals(strategy)){
				Set<String> set = new LinkedHashSet<>();
				for(String value : values)
					set.add(String.format(NAME_VALUE_STRING_FORMAT, resultName,nameAndValueSeparator,value));
				stringBuilder.append(StringUtils.join(set,listenerUtils.getString(Listener.COLLECTION, new ListenerUtils.StringMethod<Listener>() {
					@Override
					public String execute(Listener listener) {
						return listener.getSeparator();
					}
					@Override
					public String getNullValue() {
						return Constant.CHARACTER_AMPERSTAMP.toString();
					}
				})));
			}else if(Strategy.NAME_MANY_VALUES.equals(strategy)) {
				throwNotYetImplemented();
			}	
			
			
			
		}
		
		return stringBuilder.toString();
	}
	
	/**/
	
	
	
	/**/
	
	/**/
	
	public static interface Listener extends AbstractStringBuilder.Listener {
		
		Collection<Listener> COLLECTION = new ArrayList<>();
		
		public static enum Strategy{NAME_ONE_VALUE,NAME_MANY_VALUES}
		
		Strategy getStrategy();
		Boolean isName(Object key);
		Boolean isValue(Object value);
		String getNameAsString(Object key);
		Object getValueToProcessed(Object value);
		Collection<Object> getValuesToProcessed(Collection<Object> values);
		String getValueAsString(Object value);
		String getNameAndValueSeparator();
		String encode(Collection<Object> values);
		String getEncodingCharacterSet();
		Boolean getEncoded(Object name,Collection<Object> values);
		
		@Getter 
		public static class Adapter extends AbstractStringBuilder.Listener.Adapter.Default implements Listener,Serializable {
			private static final long serialVersionUID = 1L;
			
			@Override
			public Boolean isName(Object key) {
				return null;
			}

			@Override
			public Boolean isValue(Object value) {
				return null;
			}

			@Override
			public String getNameAsString(Object key) {
				return null;
			}

			@Override
			public String getValueAsString(Object value) {
				return null;
			}

			@Override
			public String getNameAndValueSeparator() {
				return null;
			}
			
			@Override
			public Strategy getStrategy() {
				return null;
			}
			
			@Override
			public String encode(Collection<Object> values) {
				return null;
			}
			
			@Override
			public Object getValueToProcessed(Object value) {
				return null;
			}
			
			@Override
			public Collection<Object> getValuesToProcessed(Collection<Object> values) {
				return null;
			}
			
			@Override
			public String getEncodingCharacterSet() {
				return null;
			}
			
			@Override
			public Boolean getEncoded(Object name,Collection<Object> values) {
				return null;
			}
			
			/**/
			
			public static class Default extends Listener.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
									
			}

		}
		
	}
	
}