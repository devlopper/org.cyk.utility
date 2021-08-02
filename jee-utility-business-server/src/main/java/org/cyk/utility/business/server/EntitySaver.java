package org.cyk.utility.business.server;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.throwable.RuntimeException;
import org.cyk.utility.__kernel__.value.Value;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface EntitySaver {

	@Transactional
	<T> void save(Class<T> tupleClass,Arguments<T> arguments);
	
	/**/
	
	public abstract class AbstractImpl extends AbstractObject implements EntitySaver,Serializable {

		@Transactional
		@Override
		public <T> void save(Class<T> tupleClass,Arguments<T> arguments) {
			validatePreConditions(tupleClass,arguments);
			arguments.prepare();
			__save__(tupleClass, arguments);
			arguments.finalise();
		}
		
		protected <T> void __save__(Class<T> tupleClass,Arguments<T> arguments) {
			org.cyk.utility.persistence.query.EntitySaver.getInstance().save(tupleClass, arguments.__persistenceArguments__);
		}
		
		/**/
		
		protected <T> void validatePreConditions(Class<T> tupleClass,Arguments<?> arguments) {
			if(tupleClass == null)
				throw new RuntimeException("tuple class is required");
			if(arguments == null)
				throw new RuntimeException("arguments are required");
		}
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Arguments<T> implements Serializable {
		private org.cyk.utility.persistence.query.EntitySaver.Arguments<T> persistenceArguments;
		
		private org.cyk.utility.persistence.query.EntitySaver.Arguments<T> __persistenceArguments__;
		
		public org.cyk.utility.persistence.query.EntitySaver.Arguments<T> getPersistenceArguments(Boolean injectIfNull) {
			if(persistenceArguments == null && Boolean.TRUE.equals(injectIfNull))
				persistenceArguments = new org.cyk.utility.persistence.query.EntitySaver.Arguments<T>();
			return persistenceArguments;
		}
		
		public Arguments<T> prepare() {
			__persistenceArguments__ = persistenceArguments;
			if(__persistenceArguments__ == null)
				__persistenceArguments__ = new org.cyk.utility.persistence.query.EntitySaver.Arguments<T>();
			return this;
		}
		
		public Arguments<T> finalise() {
			return this;
		}
		
		public Boolean isPersistenceCreatableAndUpdatablesAndDeletablesEmpty() {
			return persistenceArguments == null || Boolean.TRUE.equals(persistenceArguments.isCreatableAndUpdatablesAndDeletablesEmpty());
		}
	}
	
	/**/
	
	static EntitySaver getInstance() {
		return Helper.getInstance(EntitySaver.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
	
	/**/
	
	public static class AbstractPersistenceArgumentsListener<T> extends org.cyk.utility.persistence.query.EntitySaver.Listener.AbstractImpl<T> {
		
		@Override
		public void create(Collection<T> collection, EntityManager entityManager) {
			EntityCreator.getInstance().createMany(collection);
		}
		
	}
}