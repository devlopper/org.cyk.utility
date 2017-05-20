package org.cyk.utility.common.builder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.cyk.utility.common.AbstractBuilder;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public abstract class AbstractStringBuilder extends AbstractBuilder<String> implements Serializable {

	private static final long serialVersionUID = 1L;

	protected String identifier;
	protected Map<String,String> tokenReplacementMap;
	
	public Map<String,String> getTokenReplacementMap(){
		if(tokenReplacementMap==null)
			tokenReplacementMap = new HashMap<>();
		return tokenReplacementMap;
	}
	
	public AbstractStringBuilder addTokenReplacement(String token,String replacement){
		getTokenReplacementMap().put(token, replacement);
		return this;
	}
	
	/**/
	
	public static interface Listener extends AbstractBuilder.Listener<String> {
		
		Collection<Listener> COLLECTION = new ArrayList<>();
		
		String getIdentifierMapping(String identifier);
		String getDefaultIdentifierMapping(String identifier);
		String getIdentifierWhenMappingIsBlank();
		
		@Getter @Setter @Accessors(chain=true)
		public static class Adapter extends AbstractBuilder.Listener.Adapter.Default<String> implements Listener,Serializable {
			private static final long serialVersionUID = 1L;
			
			private String identifierWhenMappingIsBlank;
			
			@Override
			public String getIdentifierMapping(String identifier) {
				return null;
			}
			
			@Override
			public String getDefaultIdentifierMapping(String identifier) {
				return null;
			}
			
			public static class Default extends Listener.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
			
			}
		}
	}
}
