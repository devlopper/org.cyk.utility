package org.cyk.utility.persistence.server.view;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import javax.persistence.EntityManager;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.persistence.server.procedure.ProcedureExecutor;
import org.cyk.utility.persistence.server.procedure.ProcedureExecutorArguments;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface MaterializedViewManager {

	String getActualizeProcedureName();
	MaterializedViewManager setActualizeProcedureName(String name);
	
	String getActualizeParameterNameTable();
	MaterializedViewManager setActualizeParameterNameTable(String name);
	
	ProcedureExecutor getProcedureExecutor();
	MaterializedViewManager setProcedureExecutor(ProcedureExecutor procedureExecutor);
	
	Collection<Class<?>> getViewsClasses(); 
	MaterializedViewManager setViewsClasses(Collection<Class<?>> classes);
	
	void actualize(Class<?> klass,EntityManager entityManager);
	void actualize(Class<?> klass);
	
	void actualizeAll();
	
	@Getter @Setter @Accessors(chain=true)
	public static abstract class AbstractImpl extends AbstractObject implements MaterializedViewManager,Serializable{
		
		protected String actualizeProcedureName = STORED_PROCEDURE_QUERY_PROCEDURE_NAME;
		protected String actualizeParameterNameTable = STORED_PROCEDURE_QUERY_PARAMETER_NAME_TABLE;
		
		protected ProcedureExecutor procedureExecutor;
		
		protected Collection<Class<?>> viewsClasses;
		
		@Override
		public void actualize(Class<?> klass,EntityManager entityManager) {
			ProcedureExecutorArguments arguments = new ProcedureExecutorArguments();
			arguments.setName(getActualizeProcedureName());
			arguments.setParameters(Map.of(getActualizeParameterNameTable(),getTableName(klass)));
			arguments.setEntityManager(entityManager);
			//arguments.setLogLevel(Level.INFO);
			getProcedureExecutor().execute(arguments);
		}
		
		@Override
		public void actualize(Class<?> klass) {
			actualize(klass, __inject__(EntityManager.class));
		}
		
		@Override
		public void actualizeAll() {
			if(CollectionHelper.isEmpty(viewsClasses))
				return;
			for(Class<?> klass : viewsClasses)
				try {
					actualize(klass);
				} catch (Exception exception) {
					LogHelper.logSevere(String.format("Exception while actualizing <<%s>> : %s", klass.getName(),exception), getClass());
					LogHelper.log(exception, getClass());
				}
		}
		
		protected String getTableName(Class<?> klass) {
			return (String) FieldHelper.readStatic(klass, TABLE_NAME);
		}
		
		/**/
		
		public static final String STORED_PROCEDURE_QUERY_PROCEDURE_NAME = "AP_ACTUALIZE_MV";// "PA_ACTUALISER_VM";
		public static final String STORED_PROCEDURE_QUERY_PARAMETER_NAME_TABLE = "P_TABLE_NAME"; //"NOM_TABLE";
		public static final String TABLE_NAME = "TABLE_NAME";
	}
}