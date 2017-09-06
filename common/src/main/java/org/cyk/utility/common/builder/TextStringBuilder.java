package org.cyk.utility.common.builder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import org.apache.commons.lang3.StringUtils;

@Getter @Setter @NoArgsConstructor @Accessors(chain=true) @Deprecated
public class TextStringBuilder extends AbstractStringBuilder implements Serializable {
	private static final long serialVersionUID = -872728112292086623L;
			
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
	
	@Override
	protected Collection<Listener> getListeners() {
		return Listener.COLLECTION;
	}
		
	/**/
	
	/**/
	@Deprecated
	public static interface Listener extends AbstractStringBuilder.Listener {
		
		Collection<Listener> COLLECTION = new ArrayList<>();
		@Deprecated
		public static class Adapter extends AbstractStringBuilder.Listener.Adapter.Default implements Listener,Serializable {
			private static final long serialVersionUID = 1L;
				
			/**/
			@Deprecated
			public static class Default extends Listener.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
				
			}

		}
		
	}
	
	/**/
	
	public static final String YES = "yes";
	public static final String NO = "no";
}