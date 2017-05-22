package org.cyk.utility.common.builder.javascript;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @NoArgsConstructor @Accessors(chain=true)
public class OpenWindowStringBuilder extends AbstractJavascriptStringBuilder implements Serializable {
	private static final long serialVersionUID = -872728112292086623L;
	
	
	
	@Override
	public String build() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**/
	
	/**/
	
	public static interface Listener extends AbstractJavascriptStringBuilder.Listener {
		
		Collection<Listener> COLLECTION = new ArrayList<>();
		
		public static class Adapter extends AbstractJavascriptStringBuilder.Listener.Adapter.Default implements Listener,Serializable {
			private static final long serialVersionUID = 1L;
				
			/**/
			
			public static class Default extends Listener.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
				
			}

		}
		
	}
	
}