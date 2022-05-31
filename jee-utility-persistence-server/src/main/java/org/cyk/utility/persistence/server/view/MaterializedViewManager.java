package org.cyk.utility.persistence.server.view;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.time.TimeHelper;
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
	
	void actualize(ActualizeArguments arguments);
	
	void actualize(Class<?> klass,EntityManager entityManager);
	void actualize(Class<?> klass);
	void actualizeAsynchronously(Class<?> klass);
	
	void actualizeAll();
	void actualizeAllAsynchronously();
	
	@Getter @Setter @Accessors(chain=true)
	public static class ActualizeArguments extends MaterializedViewProcedureExecutor.Arguments implements Serializable{
		
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static abstract class AbstractImpl extends AbstractObject implements MaterializedViewManager,Serializable{
		
		public static final Set<Class<?>> ACTUALIZATION_RUNNING = new HashSet<>();
		
		protected String actualizeProcedureName = STORED_PROCEDURE_QUERY_PROCEDURE_NAME;
		protected String actualizeParameterNameTable = STORED_PROCEDURE_QUERY_PARAMETER_NAME_TABLE;
		
		protected ProcedureExecutor procedureExecutor;
		
		protected Collection<Class<?>> viewsClasses;
		
		@Override
		public void actualize(ActualizeArguments arguments) {
			if(arguments == null || Boolean.TRUE.equals(CollectionHelper.isEmpty(arguments.classes)))
				return;
			__actualize__(arguments.classes, arguments.entityManager, arguments.isAsynchronous,arguments.delay,arguments.logLevel);
		}
		
		protected void __actualize__(Collection<Class<?>> classes,EntityManager entityManager,Boolean isAsynchronous,Long delay,java.util.logging.Level logLevel) {
			if(Boolean.TRUE.equals(CollectionHelper.isEmpty(classes)))
				return;
			if(delay != null && delay > 0)
				TimeHelper.pause(delay);
			for(Class<?> klass : classes)
				__actualize__(klass, entityManager, isAsynchronous,null,logLevel);
		}
		
		protected void __actualize__(Class<?> klass,EntityManager entityManager,Boolean isAsynchronous,Long delay,java.util.logging.Level logLevel) {
			if(klass == null)
				return;
			synchronized(MaterializedViewManager.class) {
				if(ACTUALIZATION_RUNNING.contains(klass)) {
					LogHelper.logWarning(String.format("Actualization is already running for <<%s>>", klass), getClass());
					return;
				}
				ACTUALIZATION_RUNNING.add(klass);
			}
			Runnable runnable = new Runnable() {
				@Override
				public void run() {
					ProcedureExecutorArguments arguments = new ProcedureExecutorArguments();
					arguments.setName(getActualizeProcedureName());
					arguments.setParameters(Map.of(getActualizeParameterNameTable(),getTableName(klass)));
					arguments.setEntityManager(entityManager);
					arguments.setLogLevel(logLevel);
					try {
						getProcedureExecutor().execute(arguments);
					} catch (Exception exception) {
						LogHelper.logSevere(String.format("Exception while actualizing <<%s>> : %s", klass.getName(),exception), getClass());
						LogHelper.log(exception, getClass());
					} finally {
						ACTUALIZATION_RUNNING.remove(klass);
					}
				}
			};
			if(delay != null && delay > 0)
				TimeHelper.pause(delay);
			if(Boolean.TRUE.equals(isAsynchronous))
				new Thread(runnable).start();
			else
				runnable.run();
		}
		
		@Override
		public void actualize(Class<?> klass,EntityManager entityManager) {
			__actualize__(klass,entityManager,null,null,null);
			/*
			ProcedureExecutorArguments arguments = new ProcedureExecutorArguments();
			arguments.setName(getActualizeProcedureName());
			arguments.setParameters(Map.of(getActualizeParameterNameTable(),getTableName(klass)));
			arguments.setEntityManager(entityManager);
			//arguments.setLogLevel(Level.INFO);
			getProcedureExecutor().execute(arguments);
			*/
		}
		
		@Override
		public void actualize(Class<?> klass) {
			actualize(klass, __inject__(EntityManager.class));
		}
		
		@Override
		public void actualizeAsynchronously(Class<?> klass) {
			__actualize__(klass, null, Boolean.TRUE,null,null);
		}
		
		@Override
		public void actualizeAll() {
			if(CollectionHelper.isEmpty(viewsClasses))
				return;
			__actualize__(viewsClasses, null, null,null,null);
		}
		
		@Override
		public void actualizeAllAsynchronously() {
			if(CollectionHelper.isEmpty(viewsClasses))
				return;
			__actualize__(viewsClasses, null, Boolean.TRUE,null,null);
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