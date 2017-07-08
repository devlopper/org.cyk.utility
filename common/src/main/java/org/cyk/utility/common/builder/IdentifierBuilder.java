package org.cyk.utility.common.builder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public interface IdentifierBuilder extends AbstractStringBuilder.Listener {
	
	Map<Class<?>,Collection<IdentifierBuilder>> MAP = new HashMap<>();
	
	public static class Adapter extends AbstractStringBuilder.Listener.Adapter.Default implements IdentifierBuilder,Serializable {
		private static final long serialVersionUID = 1L;
			
		/**/
		
		public static class Default extends IdentifierBuilder.Adapter implements Serializable {
			private static final long serialVersionUID = 1L;
			
		}

	}
	
	/**/
	
	public static interface StringMapping extends AbstractStringBuilder.Listener {
		
		Map<Class<?>,Collection<StringMapping>> MAP = new HashMap<>();
		
		public static class Adapter extends AbstractStringBuilder.Listener.Adapter.Default implements StringMapping,Serializable {
			private static final long serialVersionUID = 1L;
			
			public static void add(Class<?> aClass,StringMapping mapping){
				Collection<StringMapping> collection = MAP.get(aClass);
				if(collection==null)
					MAP.put(aClass, collection = new ArrayList<>());
				collection.add(mapping);
			}
			
			/**/
			
			public static class Default extends StringMapping.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
				
			}

		}
		
	}
	
}