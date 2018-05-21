package org.cyk.utility.common.model.identifiable;

import java.io.Serializable;

import org.cyk.utility.common.helper.LoggingHelper;

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
    
    public static interface ByLong extends Identifiable<java.lang.Long> {
    	
    	@Getter @Setter 
    	public static class BaseClass extends Identifiable.BaseClass<java.lang.Long> implements Identifiable.ByLong,Serializable {
			private static final long serialVersionUID = 1L;
			
    	}
    }
    
    @Getter @Setter @Accessors(chain=true)
    public static class BaseClass<IDENTIFIER> extends Common implements Identifiable<IDENTIFIER>,Serializable {
		private static final long serialVersionUID = 1L;
    	
		protected IDENTIFIER identifier;
		
		protected java.util.Map<String, Boolean> fieldValueComputedByUserMap;
		
		protected LoggingHelper.Message.Builder loggingMessageBuilder;
		
    }
    
    /**/
    /*
    String COLUMN_NAME_UNKEYWORD = "the_";
	String COLUMN_NAME_WORD_SEPARATOR = "_";
	Integer COLUMN_VALUE_PRECISION = 30;
	Integer COEFFICIENT_PRECISION = 5;
	Integer PERCENT_PRECISION = 5;
	Integer FLOAT_SCALE = 2;
	Integer PERCENT_SCALE = 4;
	*/
}
