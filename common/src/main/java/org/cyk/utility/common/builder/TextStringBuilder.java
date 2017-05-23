package org.cyk.utility.common.builder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import org.apache.commons.lang3.StringUtils;

@Getter @Setter @NoArgsConstructor @Accessors(chain=true)
public class TextStringBuilder extends AbstractStringBuilder implements Serializable {
	private static final long serialVersionUID = -872728112292086623L;
		
	private Locale locale;
		
	@Override
	protected String buildWhenBlank() {
		String result = getIdentifierMapping();
		if(StringUtils.isBlank(result))
			if(YES.equals(identifier))
				result = "yes";
			else if(NO.equals(identifier))
				result = "no";
		return result;
	}
	
	public TextStringBuilder setResponse(Boolean response){
		identifier = Boolean.TRUE.equals(response) ? YES : NO;
		return this;
	}
		
	/**/
	
	/**/
	
	public static interface Listener extends AbstractStringBuilder.Listener {
		
		Collection<Listener> COLLECTION = new ArrayList<>();
		
		public static class Adapter extends AbstractStringBuilder.Listener.Adapter.Default implements Listener,Serializable {
			private static final long serialVersionUID = 1L;
				
			/**/
			
			public static class Default extends Listener.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
				
			}

		}
		
	}
	
	/**/
	
	public static final String YES = "yes";
	public static final String NO = "no";
}