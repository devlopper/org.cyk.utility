package org.cyk.utility.common.model.identifiable;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface IdentifiablePersistable<IDENTIFIER> extends Identifiable<IDENTIFIER> {

	public static interface Long extends IdentifiablePersistable<java.lang.Long> {
    	
    	@Getter @Setter 
    	public static class Class extends IdentifiablePersistable.Class<java.lang.Long> implements IdentifiablePersistable.Long,Serializable {
			private static final long serialVersionUID = 1L;
			
			@Getter @Setter @javax.persistence.MappedSuperclass
	    	public static class JavaPersistenceApiEntity extends IdentifiablePersistable.Long.Class implements Serializable {
				private static final long serialVersionUID = 1L;
				
				@javax.persistence.Id
				public java.lang.Long getIdentifier(){
					return super.getIdentifier();
				}
				
				@Override
				public JavaPersistenceApiEntity setIdentifier(java.lang.Long identifier) {
					super.setIdentifier(identifier);
					return this;
				}
				
	    	}
			
    	}
    }
    
    @Getter @Setter @Accessors(chain=true)
    public static class Class<IDENTIFIER> extends Identifiable.Class<IDENTIFIER> implements IdentifiablePersistable<IDENTIFIER>,Serializable {
		private static final long serialVersionUID = 1L;
    	
		//protected java.util.Map<String, Boolean> fieldValueComputedByUserMap;
		
		//@Transient protected LoggingHelper.Message.Builder loggingMessageBuilder;
		
    }
	
}
