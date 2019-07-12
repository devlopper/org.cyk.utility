package org.cyk.utility.__kernel__.object.__static__.identifiable;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.cyk.utility.__kernel__.annotation.Generatable;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public abstract class AbstractIdentified<IDENTIFIER> extends Common implements Identified<IDENTIFIER>,Serializable {
	private static final long serialVersionUID = 1L;
	
	@Generatable
	protected IDENTIFIER identifier;
	
	//protected java.util.Map<String, Boolean> fieldValueComputedByUserMap;
	
	//protected LoggingHelper.Message.Builder loggingMessageBuilder;
	
	/**/
	
	protected String getRuntimeIdentifier(){
		return //getMemoryAddress()
				getClass().getSimpleName()
				+CHARACTER_SLASH+StringUtils.defaultString(identifier==null?null:identifier.toString(),CHARACTER_QUESTION_MARK);
	}
	
	@Override
	public int hashCode() {
		String id = getRuntimeIdentifier();
		return id==null?HashCodeBuilder.reflectionHashCode(this, false):id.hashCode();
	}
	
	@Override
	public boolean equals(Object object) {
		if(!(object instanceof AbstractIdentified))
			return Boolean.FALSE;
		String id1 = getRuntimeIdentifier() , id2 = ((AbstractIdentified<?>) object).getRuntimeIdentifier();
		if(id1==null || id2==null)
			return Boolean.FALSE;
		return id1.equals(id2);
	}
	
	/**/
	
	public static final String FIELD_IDENTIFIER = "identifier";
	private static final String CHARACTER_SLASH = "/";
	private static final String CHARACTER_QUESTION_MARK = "?";
}