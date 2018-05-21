package org.cyk.utility.common.model.identifiable;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface IdentifiablePersistable<IDENTIFIER> extends Identifiable<IDENTIFIER> {

	public static interface ByLong extends IdentifiablePersistable<java.lang.Long> {
    	
    	@Getter @Setter 
    	public static class BaseClass extends IdentifiablePersistable.BaseClass<java.lang.Long> implements IdentifiablePersistable.ByLong,Serializable {
			private static final long serialVersionUID = 1L;
			
			@Getter @Setter @javax.persistence.MappedSuperclass @Accessors(chain=true)
	    	public static class JavaPersistenceEntity extends IdentifiablePersistable.ByLong.BaseClass implements Serializable {
				private static final long serialVersionUID = 1L;
				
				@javax.persistence.Id protected Long identifier;
				
	    	}
    	}
    }
	
	public static interface ByString extends IdentifiablePersistable<java.lang.String> {
    	
    	@Getter @Setter 
    	public static class BaseClass extends IdentifiablePersistable.BaseClass<java.lang.String> implements IdentifiablePersistable.ByString,Serializable {
			private static final long serialVersionUID = 1L;
			
			@Getter @Setter @javax.persistence.MappedSuperclass @Accessors(chain=true)
	    	public static class JavaPersistenceEntity extends IdentifiablePersistable.ByString.BaseClass implements Serializable {
				private static final long serialVersionUID = 1L;
				
				@javax.persistence.Id protected String identifier;
				
	    	}
    	}
    }
    
    @Getter @Setter @Accessors(chain=true)
    public static class BaseClass<IDENTIFIER> extends Identifiable.BaseClass<IDENTIFIER> implements IdentifiablePersistable<IDENTIFIER>,Serializable {
		private static final long serialVersionUID = 1L;
    	
		//protected java.util.Map<String, Boolean> fieldValueComputedByUserMap;
		
		//@Transient protected LoggingHelper.Message.Builder loggingMessageBuilder;
		
		@Getter @Setter @javax.persistence.MappedSuperclass
    	public static class JavaPersistenceEntity<IDENTIFIER> extends IdentifiablePersistable.BaseClass<IDENTIFIER> implements Serializable {
			private static final long serialVersionUID = 1L;
			
			@javax.persistence.Id
			public IDENTIFIER getIdentifier(){
				return super.getIdentifier();
			}
			
			@Override
			public JavaPersistenceEntity<IDENTIFIER> setIdentifier(IDENTIFIER identifier) {
				super.setIdentifier(identifier);
				return this;
			}
			
    	}
		
    }
	
}
