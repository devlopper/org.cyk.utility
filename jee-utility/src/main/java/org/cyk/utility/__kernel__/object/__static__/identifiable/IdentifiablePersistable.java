package org.cyk.utility.__kernel__.object.__static__.identifiable;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface IdentifiablePersistable<IDENTIFIER> extends Identifiable<IDENTIFIER> {

	public static interface ByLong extends IdentifiablePersistable<Long> {
    	
    	@Getter @Setter 
    	public static class BaseClass extends IdentifiablePersistable.BaseClass<Long> implements IdentifiablePersistable.ByLong,Serializable {
			private static final long serialVersionUID = 1L;
						
    	}
    }
	
	public static interface ByString extends IdentifiablePersistable<String> {
    	
    	@Getter @Setter 
    	public static class BaseClass extends IdentifiablePersistable.BaseClass<String> implements IdentifiablePersistable.ByString,Serializable {
			private static final long serialVersionUID = 1L;
			
			
    	}
    }
    
    @Getter @Setter @Accessors(chain=true)
    public static class BaseClass<IDENTIFIER> extends Identifiable.BaseClass<IDENTIFIER> implements IdentifiablePersistable<IDENTIFIER>,Serializable {
		private static final long serialVersionUID = 1L;
    	
    }
	
}
