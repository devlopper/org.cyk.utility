package org.cyk.utility.common.builder;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.common.AbstractBuilder;
import org.cyk.utility.common.ListenerUtils;
import org.cyk.utility.common.builder.UrlStringBuilder.PathStringBuilder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public abstract class AbstractStringBuilder extends AbstractBuilder<String> implements Serializable {

	private static final long serialVersionUID = 1L;

	public static Locale LOCALE = Locale.FRENCH;
	
	protected Locale locale = LOCALE;
	protected String identifier,separator;
	protected AbstractIdentifierBuilder identifierBuilder;
	
	@Override
	public String build() {
		if(StringUtils.isBlank(instance))
			return buildWhenBlank();
		return instance;
	}
	
	@SuppressWarnings("unchecked")
	protected String getIdentifierMapping(){
		return listenerUtils.getString(getListeners(), new ListenerUtils.StringMethod<Listener>() {
			@Override
			public String execute(Listener listener) {
				return listener.getIdentifierMapping(identifier);
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	protected String buildSeparator(){
		return listenerUtils.getString(getListeners(), new ListenerUtils.StringMethod<Listener>() {
			@Override
			public String execute(Listener listener) {
				return listener.getSeparator();
			}
			
			@Override
			public String getNullValue() {
				return separator;
			}
		});
	}
	
	protected abstract String buildWhenBlank();
	
	protected Map<String,String> getTokenReplacementMap(){
		Map<String,String> tokenReplacementMap=null;
		for(Listener listener : PathStringBuilder.Listener.COLLECTION){
			Map<String, String> v = listener.getTokenReplacementMap();
			if(v!=null)
				tokenReplacementMap = v;
		}
		if(tokenReplacementMap==null)
			tokenReplacementMap = new HashMap<>();
		return tokenReplacementMap;
	}
		
	/**/
	
	public static interface Listener extends AbstractBuilder.Listener<String> {
		
		//Collection<Listener> COLLECTION = new ArrayList<>();
		
		String getIdentifierMapping(String identifier);
		String getDefaultIdentifierMapping(String identifier);
		String getIdentifierWhenMappingIsBlank();
		Map<String,String> getTokenReplacementMap();
		String getSeparator();
		
		@Getter @Setter @Accessors(chain=true)
		public static class Adapter extends AbstractBuilder.Listener.Adapter.Default<String> implements Listener,Serializable {
			private static final long serialVersionUID = 1L;
			
			protected String identifierWhenMappingIsBlank;
			protected Map<String,String> tokenReplacementMap;
			
			@Override
			public String getIdentifierMapping(String identifier) {
				return null;
			}
			
			@Override
			public String getDefaultIdentifierMapping(String identifier) {
				return null;
			}
			
			@Override
			public String getSeparator() {
				return null;
			}
			
			public static class Default extends Listener.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
			
			}
		}
	}

	/**/
	
	@Getter @Setter @NoArgsConstructor @Accessors(chain=true)
	public abstract static class AbstractIdentifierBuilder extends AbstractBuilder<String> implements Serializable {
		private static final long serialVersionUID = -872728112292086623L;
		
		private static final String CANNOT_BUILD_IDENTIFIER_FORMAT = "cannot build identifier with action <<%s>> and subject <<%s>>";
		
		protected Object action,subject;
		
		@SuppressWarnings("unchecked")
		@Override
		public String build() {
			return listenerUtils.getString(getListeners(), new ListenerUtils.StringMethod<Listener>() {
				@Override
				public String execute(Listener listener) {
					return listener.get(action,subject);
				}
				public String getNullValue() {
					return null;//String.format(CANNOT_BUILD_IDENTIFIER_FORMAT, action,subject);
				}
			});
		}
		
		/**/
		
		public static interface Listener extends AbstractBuilder.Listener<String> {
			
			String get(Object action,Object subject);
			
			public static class Adapter extends AbstractBuilder.Listener.Adapter.Default<String> implements Listener,Serializable {
				private static final long serialVersionUID = 1L;
				
				@Override
				public String get(Object action, Object subject) {
					return null;
				}
				
				/**/
				
				public static class Default extends Listener.Adapter implements Serializable {
					private static final long serialVersionUID = 1L;
					
				}
			}
		}
	}
}
