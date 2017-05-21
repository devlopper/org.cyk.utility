package org.cyk.utility.common.builder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.ListenerUtils;
import org.cyk.utility.common.builder.NameValueStringBuilder.Listener.Strategy;
import org.cyk.utility.common.helper.NumberHelper;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class NameValueStringBuilder extends AbstractStringBuilder implements Serializable {
	private static final long serialVersionUID = -872728112292086623L;
	
	public static final String NAME_VALUE_STRING_FORMAT = "%s%s%s";
	public static final String NAME_VALUE_SEPARATOR = "=";
	
	static {
		NameValueStringBuilder.Listener.COLLECTION.add(new NameValueStringBuilder.Listener.Adapter.Default());
	}
	
	private Object name;
	private Collection<Object> values = new ArrayList<>();
	private String separator;
	private Boolean encoded;
	
	public NameValueStringBuilder set(Object name,Object value){
		setName(name);
		getValues().add(value);
		return this;
	}
	
	public NameValueStringBuilder set(Object value){
		getValues().add(value);
		return this;
	}
	
	@Override
	public String build() {
		StringBuilder stringBuilder = new StringBuilder();
		if(StringUtils.isBlank(instance)){
			if(Boolean.TRUE.equals(listenerUtils.getBoolean(Listener.COLLECTION, new ListenerUtils.BooleanMethod<Listener>() {
				@Override
				public Boolean execute(Listener listener) {
					return listener.isName(name);
				}
			}))){
				String nameAndValueSeparator = listenerUtils.getString(Listener.COLLECTION, new ListenerUtils.StringMethod<Listener>() {
					@Override
					public String execute(Listener listener) {
						return listener.getNameAndValueSeparator();
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
				final String name = listenerUtils.getString(Listener.COLLECTION, new ListenerUtils.StringMethod<Listener>() {
					@Override
					public String execute(Listener listener) {
						return listener.getNameAsString(NameValueStringBuilder.this.name);
					}
				});
				
				final List<String> values = new ArrayList<>();
				if(Boolean.TRUE.equals(getEncoded())){
					values.add(listenerUtils.getString(Listener.COLLECTION, new ListenerUtils.StringMethod<Listener>() {
						@Override
						public String execute(Listener listener) {
							return listener.encode(NameValueStringBuilder.this.values);
						}
					}));
				}else{
					for(Object value : NameValueStringBuilder.this.values){
						final Object finalValue = value;
						if(Boolean.TRUE.equals(listenerUtils.getBoolean(Listener.COLLECTION, new ListenerUtils.BooleanMethod<Listener>() {
							@Override
							public Boolean execute(Listener listener) {
								return listener.isValue(finalValue);
							}
						}))){
							values.add(listenerUtils.getString(Listener.COLLECTION, new ListenerUtils.StringMethod<Listener>() {
								@Override
								public String execute(Listener listener) {
									return listener.getValueAsString(finalValue);
								}
							}));	
						}	
					}	
				}
				
				if(Strategy.NAME_ONE_VALUE.equals(strategy)){
					Set<String> set = new LinkedHashSet<>();
					for(String value : values)
						set.add(String.format(NAME_VALUE_STRING_FORMAT, name,nameAndValueSeparator,value));
					stringBuilder.append(StringUtils.join(set,listenerUtils.getString(Listener.COLLECTION, new ListenerUtils.StringMethod<Listener>() {
						@Override
						public String execute(Listener listener) {
							return listener.getSeparator();
						}
					})));
				}else if(Strategy.NAME_MANY_VALUES.equals(strategy)) {
					throwNotYetImplemented();
				}	
				
				
				
			}
		}else{
			stringBuilder.append(instance);
		}
		return stringBuilder.toString();
	}
		
	/**/
	
	/**/
	
	public static interface Listener extends AbstractStringBuilder.Listener {
		
		Collection<Listener> COLLECTION = new ArrayList<>();
		
		public static enum Strategy{NAME_ONE_VALUE,NAME_MANY_VALUES}
		
		Strategy getStrategy();
		Boolean isName(Object key);
		Boolean isValue(Object value);
		String getNameAsString(Object key);
		String getValueAsString(Object value);
		String getNameAndValueSeparator();
		String getAsString(String key,List<String> values,String nameValueSeparator,String parameterSeparator);
		String encode(Collection<Object> values);
		
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
			public String getAsString(String key, List<String> values, String nameValueSeparator,String parameterSeparator) {
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
			
			/**/
			
			public static class Default extends Listener.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
				
				@Override
				public Boolean isName(Object key) {
					return key == null 
						? Boolean.FALSE 
						: (key instanceof String ? StringUtils.isNotBlank((String)key) : Boolean.FALSE);
				}
				
				@Override
				public Boolean isValue(Object value) {
					return value!=null || (value instanceof String && StringUtils.isNotBlank((String)value));
				}
				
				@Override
				public String getNameAndValueSeparator() {
					return Constant.CHARACTER_EQUAL.toString();
				}
				
				@Override
				public String getSeparator() {
					return Constant.CHARACTER_AMPERSTAMP.toString();
				}
				
				@Override
				public String getNameAsString(Object key) {
					return key.toString();
				}
				
				@Override
				public String getValueAsString(Object value) {
					return value.toString();
				}
				
				@Override
				public String getAsString(String key, List<String> values,String nameValueSeparator,String parameterSeparator) {
					Collection<String> parameterTokens = new ArrayList<>();
					for(String value : values)
						parameterTokens.add(String.format(NAME_VALUE_STRING_FORMAT, key,nameValueSeparator,value));
					return StringUtils.join(parameterTokens,parameterSeparator);
				}
				
				@Override
				public String encode(Collection<Object> values) {
					Collection<Long> longs = new ArrayList<>();
					for(Object v : values)
						longs.add(Long.parseLong(v.toString()));
					return NumberHelper.getInstance().encodeToBase62(longs);
				}
				
			}

		}
		
	}
	
}