package org.cyk.utility.persistence.server.view;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.cyk.utility.__kernel__.field.FieldHelper;
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
	
	void actualize(Class<?> klass,EntityManager entityManager);
	void actualize(Class<?> klass);
	
	@Getter @Setter @Accessors(chain=true)
	public static abstract class AbstractImpl extends AbstractObject implements MaterializedViewManager,Serializable{
		
		protected String actualizeProcedureName = STORED_PROCEDURE_QUERY_PROCEDURE_NAME;
		protected String actualizeParameterNameTable = STORED_PROCEDURE_QUERY_PARAMETER_NAME_TABLE;
		
		private ProcedureExecutor procedureExecutor;
		
		@Override @Transactional
		public void actualize(Class<?> klass,EntityManager entityManager) {
			ProcedureExecutorArguments arguments = new ProcedureExecutorArguments();
			arguments.setName(getActualizeProcedureName());
			arguments.setParameters(Map.of(getActualizeParameterNameTable(),getTableName(klass)));
			arguments.setEntityManager(entityManager);
			//arguments.setLogLevel(Level.INFO);
			getProcedureExecutor().execute(arguments);
		}
		
		@Override @Transactional
		public void actualize(Class<?> klass) {
			actualize(klass, __inject__(EntityManager.class));
		}
		
		protected String getTableName(Class<?> klass) {
			return (String) FieldHelper.readStatic(klass, TABLE_NAME);
		}
		
		/**/
		
		public static final String STORED_PROCEDURE_QUERY_PROCEDURE_NAME = "PA_ACTUALISER_VM";
		public static final String STORED_PROCEDURE_QUERY_PARAMETER_NAME_TABLE = "NOM_TABLE";
		public static final String TABLE_NAME = "TABLE_NAME";
	}
}