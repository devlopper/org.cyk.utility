package org.cyk.utility.common.builder.javascript;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import org.cyk.utility.common.builder.AbstractStringBuilder;

@Getter @Setter @NoArgsConstructor @Accessors(chain=true)
public abstract class AbstractJavascriptStringBuilder extends AbstractStringBuilder implements Serializable {
	private static final long serialVersionUID = -872728112292086623L;
		
	
		
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
	
}