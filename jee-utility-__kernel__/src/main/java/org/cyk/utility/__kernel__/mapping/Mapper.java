package org.cyk.utility.__kernel__.mapping;

import java.io.Serializable;

import javax.annotation.PostConstruct;

public interface Mapper {

	/**/
	
	public abstract class AbstractImpl implements Mapper,Serializable {
		private static final long serialVersionUID = 1L;
		
		@PostConstruct
		protected void listenPostConstruct() {
			__listenPostConstruct__();
	    }
		
	    protected void __listenPostConstruct__() {}
		
	}

	
}
