package org.cyk.utility.__kernel__.object.__static__.identifiable;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.cyk.utility.character.CharacterConstant;

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
		
		//protected LoggingHelper.Message.Builder loggingMessageBuilder;
		
		/**/
		
		private String getRuntimeIdentifier(){
			return //getMemoryAddress()
					getClass().getSimpleName()
					+CharacterConstant.SLASH+StringUtils.defaultString(identifier==null?null:identifier.toString(),CharacterConstant.QUESTION_MARK.toString());
		}
		
		@Override
		public int hashCode() {
			String id = getRuntimeIdentifier();
			return id==null?HashCodeBuilder.reflectionHashCode(this, false):id.hashCode();
		}
		
		@Override
		public boolean equals(Object object) {
			if(!(object instanceof BaseClass))
				return Boolean.FALSE;
			String id1 = getRuntimeIdentifier() , id2 = ((BaseClass<?>) object).getRuntimeIdentifier();
			if(id1==null || id2==null)
				return Boolean.FALSE;
			return id1.equals(id2);
		}
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
