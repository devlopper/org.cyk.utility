package org.cyk.utility.business.server;

import java.io.Serializable;

import javax.transaction.Transactional;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.value.Value;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface EntityImporter {

	@Transactional
	void import_(Arguments arguments);
	
	@Transactional
	void import_(Class<?> entityClass);
	
	/**/
	
	public static abstract class AbstractImpl extends AbstractObject implements EntityImporter,Serializable{
		public static Boolean IS_CONTAINER_MANAGED_TRANSACTION = Boolean.TRUE;
		
		@Override @Transactional
		public void import_(Arguments arguments) {
			if(arguments == null)
				return;
			
			__import__(arguments);
		}
		
		protected void __import__(Arguments arguments) {
			//org.cyk.utility.persistence.query.EntityImporter.getInstance().createMany(queryExecutorArguments);
		}
		
		@Override
		public void import_(Class<?> entityClass) {
			Arguments arguments = new Arguments();
			arguments.setEntityClass(entityClass);
			import_(arguments);
		}
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Arguments extends AbstractObject implements Serializable {
		private Class<?> entityClass;
	}
	
	/**/
	
	static EntityImporter getInstance() {
		return Helper.getInstance(EntityImporter.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
}