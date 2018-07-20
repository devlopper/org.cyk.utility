package org.cyk.utility.__kernel__.object.__static__.identifiable;

import org.cyk.utility.__kernel__.object.Objectable;

/**
 * Any object that is uniquely identified by an identifier.
 * @author Christian Yao Komenan
 * @param <IDENTIFIER>
 */
public interface Identified<IDENTIFIER> extends Objectable {

    /**
     * Get the identifier of the object.
     * @return Identifier of the object.
     */
    IDENTIFIER getIdentifier();
    
    /**
     * Set the identifier of the object
     * @param identifier
     * @return
     */
    Identified<IDENTIFIER> setIdentifier(IDENTIFIER identifier);

    /**/
        
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
