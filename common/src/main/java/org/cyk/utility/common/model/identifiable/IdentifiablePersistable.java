package org.cyk.utility.common.model.identifiable;

import java.io.Serializable;

import javax.persistence.AccessType;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface IdentifiablePersistable<IDENTIFIER> extends Identifiable<IDENTIFIER> {

	public static interface ByLong extends IdentifiablePersistable<Long> {
    	
    	@Getter @Setter 
    	public static class BaseClass extends IdentifiablePersistable.BaseClass<Long> implements IdentifiablePersistable.ByLong,Serializable {
			private static final long serialVersionUID = 1L;
			
			@Getter @Setter @Accessors(chain=true) @javax.persistence.MappedSuperclass @javax.persistence.Access(AccessType.FIELD)
	    	public static class JavaPersistenceEntity extends IdentifiablePersistable.ByLong.BaseClass implements Serializable {
				private static final long serialVersionUID = 1L;
				
				@javax.persistence.Access(AccessType.PROPERTY) @javax.persistence.Id
				@Override
				public Long getIdentifier() {
					return super.getIdentifier();
				}
				
				@Override
				public Identifiable.BaseClass<Long> setIdentifier(Long identifier) {
					return super.setIdentifier(identifier);
				}
				
	    	}
    	}
    }
	
	public static interface ByString extends IdentifiablePersistable<String> {
    	
    	@Getter @Setter 
    	public static class BaseClass extends IdentifiablePersistable.BaseClass<String> implements IdentifiablePersistable.ByString,Serializable {
			private static final long serialVersionUID = 1L;
			
			@Getter @Setter @Accessors(chain=true) @javax.persistence.MappedSuperclass @javax.persistence.Access(AccessType.FIELD)
	    	public static class JavaPersistenceEntity extends IdentifiablePersistable.ByString.BaseClass implements Serializable {
				private static final long serialVersionUID = 1L;
				
				@javax.persistence.Access(AccessType.PROPERTY) @javax.persistence.Id
				@Override
				public String getIdentifier() {
					return super.getIdentifier();
				}
				
				@Override
				public Identifiable.BaseClass<String> setIdentifier(String identifier) {
					return super.setIdentifier(identifier);
				}
				
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
