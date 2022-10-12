package org.cyk.utility.persistence.server.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.time.TimeHelper;
import org.cyk.utility.persistence.server.procedure.ProcedureExecutor;
import org.cyk.utility.persistence.server.procedure.ProcedureExecutorArguments;
import org.cyk.utility.persistence.server.procedure.ProcedureExecutorGetter;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface MaterializedViewProcedureExecutor {

	String getProcedureName();
	MaterializedViewProcedureExecutor setProcedureName(String name);
	
	String getParameterNameTable();
	MaterializedViewProcedureExecutor setParameterNameTable(String name);
	
	void execute(Arguments arguments);
	
	void execute(Collection<Class<?>> classes,EntityManager entityManager);
	void execute(Collection<Class<?>> classes);
	void executeAsynchronously(Collection<Class<?>> classes);
	
	void execute(EntityManager entityManager,Class<?>...classes);
	void executeAsynchronously(Class<?>...classes);
	
	Boolean areExecutionsRunning(Collection<Class<?>> classes,Boolean loggable);
	Boolean areExecutionsRunning(Boolean loggable,Class<?>...classes);
	
	@Getter @Setter @Accessors(chain=true)
	public static class Arguments implements Serializable{
		protected EntityManager entityManager;
		protected Collection<Class<?>> classes;
		protected Boolean isAsynchronous;
		protected Long delay;
		protected Boolean blockableIfRunning;
		protected java.util.logging.Level logLevel;
		
		public Collection<Class<?>> getClasses(Boolean instantiateIfNull) {
			if(classes == null && Boolean.TRUE.equals(instantiateIfNull))
				classes = new ArrayList<>();
			return classes;
		}
		
		public Arguments addClasses(Collection<Class<?>> classes) {
			if(CollectionHelper.isNotEmpty(classes))
				getClasses(Boolean.TRUE).addAll(classes);
			return this;
		}
		
		public Arguments addClasses(Class<?>...classes) {
			return addClasses(CollectionHelper.listOf(Boolean.TRUE, classes));
		}
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static abstract class AbstractImpl extends AbstractObject implements MaterializedViewProcedureExecutor,Serializable{
		public static final Set<Class<?>> RUNNING = new HashSet<>();
		protected String procedureName;
		public static final String PARAMETER_NAME_TABLE = "P_TABLE_NAME";
		protected String parameterNameTable = PARAMETER_NAME_TABLE;
		@Inject protected ProcedureExecutorGetter procedureExecutorGetter;
		
		@Override
		public void execute(Arguments arguments) {
			if(arguments == null || Boolean.TRUE.equals(CollectionHelper.isEmpty(arguments.classes)))
				return;
			__execute__(arguments.classes, arguments.entityManager, arguments.isAsynchronous,arguments.delay,arguments.blockableIfRunning,arguments.logLevel);
		}
		
		protected void __execute__(Collection<Class<?>> classes,EntityManager entityManager,Boolean isAsynchronous,Long delay,Boolean blockableIfRunning,java.util.logging.Level logLevel) {
			if(Boolean.TRUE.equals(CollectionHelper.isEmpty(classes)))
				return;
			if(delay != null && delay > 0)
				TimeHelper.pause(delay);
			for(Class<?> klass : classes)
				try {
					__execute__(klass, entityManager, isAsynchronous,null,blockableIfRunning,logLevel);
				} catch (Exception exception) {
					LogHelper.logSevere(String.format("ERROR while %s(%s) : ",procedureName,klass.getSimpleName(), exception.toString()), getClass());
				}
		}
		
		protected void __execute__(Class<?> klass,EntityManager entityManager,Boolean isAsynchronous,Long delay,Boolean blockableIfRunning,java.util.logging.Level logLevel) {
			if(klass == null)
				return;
			if(Boolean.TRUE.equals(blockableIfRunning)) {
				while(Boolean.TRUE.equals(areExecutionsRunning(Boolean.FALSE, klass))) {
					LogHelper.logInfo(String.format("Waiting completion of procedure <<%s>> for <<%s>> before returning",getProcedureName(), klass), getClass());
					TimeHelper.pause(1000l * 30);
				}
				return;
			}else if(Boolean.TRUE.equals(areExecutionsRunning(Boolean.TRUE, klass)))
				return;
			synchronized(MaterializedViewProcedureExecutor.class) {	
				RUNNING.add(klass);
			}
			Exception[] exceptions = {null};
			Runnable runnable = new Runnable() {
				@Override
				public void run() {
					ProcedureExecutorArguments arguments = new ProcedureExecutorArguments();
					arguments.setName(getProcedureName());
					arguments.setParameters(Map.of(getParameterNameTable(),getTableName(klass)));
					arguments.setEntityManager(entityManager);
					arguments.setLogLevel(logLevel);
					arguments.setTransactionable(Boolean.TRUE);//to avoid connection leak
					ProcedureExecutor procedureExecutor = procedureExecutorGetter.getProcedureExecutor();
					if(procedureExecutor == null)
						throw new RuntimeException(String.format("Procedure executor is required to execute procedure <<%s>> on <<%s>>", getProcedureName(),klass.getName()));
					try {
						procedureExecutor.execute(arguments);
					} catch (Exception exception) {
						exceptions[0] = exception;
						LogHelper.logSevere(String.format("Exception while running procedure <<%s>> on <<%s>> : %s", getProcedureName(),klass.getName(),exception), getClass());
						LogHelper.log(exception, getClass());
					} finally {
						synchronized(MaterializedViewProcedureExecutor.class) {
							RUNNING.remove(klass);
						}
					}
				}
			};
			if(delay != null && delay > 0)
				TimeHelper.pause(delay);
			if(Boolean.TRUE.equals(isAsynchronous))
				new Thread(runnable).start();
			else
				runnable.run();
			if(exceptions[0] != null)
				throw new RuntimeException(exceptions[0]);
		}
		
		@Override
		public void execute(Collection<Class<?>> classes, EntityManager entityManager) {
			__execute__(classes, entityManager, null, null,null, null);
		}
		
		@Override
		public void execute(Collection<Class<?>> classes) {
			__execute__(classes, null, null, null,null, null);
		}
		
		@Override
		public void executeAsynchronously(Collection<Class<?>> classes) {
			__execute__(classes, null, Boolean.TRUE, null,null, null);
		}
		
		@Override
		public void execute(EntityManager entityManager, Class<?>... classes) {
			__execute__(CollectionHelper.listOf(Boolean.TRUE, classes), entityManager, null, null,null, null);
		}
		
		@Override
		public void executeAsynchronously(Class<?>... classes) {
			__execute__(CollectionHelper.listOf(Boolean.TRUE, classes), null, Boolean.TRUE, null,null, null);
		}
		
		protected String getTableName(Class<?> klass) {
			return (String) FieldHelper.readStatic(klass, "TABLE_NAME");
		}
		
		@Override
		public Boolean areExecutionsRunning(Collection<Class<?>> classes,Boolean loggable) {
			if(CollectionHelper.isEmpty(classes))
				return null;
			synchronized(MaterializedViewProcedureExecutor.class) {
				if(RUNNING.containsAll(classes)) {
					LogHelper.logWarning(String.format("Procedure <<%s>> is already running for <<%s>>",getProcedureName(), classes), getClass());
					return Boolean.TRUE;
				}
				return Boolean.FALSE;
			}
		}
		
		@Override
		public Boolean areExecutionsRunning(Boolean loggable,Class<?>... classes) {
			return areExecutionsRunning(CollectionHelper.listOf(Boolean.TRUE, classes),loggable);
		}
	}
}