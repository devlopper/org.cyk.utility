package org.cyk.utility.common.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Any object that can be identified by a unique identifier.
 * @author Christian Yao Komenan
 * @param <IDENTIFIER>
 */
public interface Identifiable<IDENTIFIER> {

    /**
     * Get the identifier of the object.
     * @return Identifier of the object.
     */
    IDENTIFIER getIdentifier();
    
    Identifiable<IDENTIFIER> setIdentifier(IDENTIFIER anIdentifier);

    /**/
    
    public static interface Long extends Identifiable<Long> {
    	
    	@Getter @Setter 
    	public static abstract class Class extends Identifiable.Class<Long> implements Identifiable.Long,Serializable {
			private static final long serialVersionUID = 1L;
			
    	}
    }
    
    @Getter @Setter @Accessors(chain=true)
    public static abstract class Class<IDENTIFIER> implements Identifiable<IDENTIFIER>,Serializable {
		private static final long serialVersionUID = 1L;
    	
		protected IDENTIFIER identifier;
    }
}
