package org.cyk.utility.persistence.server.procedure;

import java.io.Serializable;
import java.util.logging.Level;

import javax.persistence.EntityManager;
import javax.persistence.StoredProcedureQuery;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;
import org.cyk.utility.__kernel__.time.TimeHelper;
import org.cyk.utility.__kernel__.value.Value;
import org.cyk.utility.__kernel__.value.ValueHelper;
import org.cyk.utility.persistence.EntityManagerGetter;

public interface ProcedureExecutor {

	Boolean execute(ProcedureExecutorArguments arguments);
	Boolean execute(String name,Object...parameters);
	
	Boolean executeRefreshMaterializedView(Class<?> klass);
	
	/**/
	
	public static abstract class AbstractImpl extends AbstractObject implements ProcedureExecutor,Serializable {
		
		@Override
		public Boolean execute(ProcedureExecutorArguments arguments) {
			ThrowableHelper.throwIllegalArgumentExceptionIfNull("arguments", arguments);
			String name = arguments.getName();
			if(StringHelper.isBlank(name))
				name = arguments.getKlass().getSimpleName()+"."+arguments.getProcedureName().getValue();
			ThrowableHelper.throwIllegalArgumentExceptionIfBlank("procedure name", name);
			EntityManager entityManager = arguments.getEntityManager() == null ? EntityManagerGetter.getInstance().get() : arguments.getEntityManager();
			StoredProcedureQuery storedProcedureQuery = entityManager.createNamedStoredProcedureQuery(name);
			if(MapHelper.isNotEmpty(arguments.getParameters())) {
				arguments.getParameters().forEach( (key,value) -> {
					storedProcedureQuery.setParameter(key, value);
				});
			}
			LogHelper.log(String.format("Exécution de la procédure stockée %s en cours...", name),ValueHelper.defaultToIfNull(arguments.getLogLevel(), Level.FINE), getClass());
			if(arguments.getEntityManager() == null)
				entityManager.getTransaction().begin();
			Long t = System.currentTimeMillis();
			Boolean result = storedProcedureQuery.execute();
			if(arguments.getEntityManager() == null)
				entityManager.getTransaction().commit();
			LogHelper.log(String.format("Procédure stockée %s exécutée en %s", name,TimeHelper.formatDuration(System.currentTimeMillis() - t)),ValueHelper.defaultToIfNull(arguments.getLogLevel(), Level.FINE), getClass());
			releaseConnection(storedProcedureQuery, name);
			return result;
		}
		
		protected void releaseConnection(StoredProcedureQuery storedProcedureQuery,String name) {
			LogHelper.logWarning(String.format("#### WE DO NOT KNOW HOW TO RELEASE CONNECTION FROM STORED PROCEDURE QUERY named <<%s>> #### --- #### THIS CAN PRODUCE CONNECTION LEAK ####", name), getClass());
		}
		
		@Override
		public Boolean execute(String name, Object... parameters) {
			ThrowableHelper.throwIllegalArgumentExceptionIfBlank("name", name);
			ProcedureExecutorArguments arguments = new ProcedureExecutorArguments();
			arguments.setName(name);
			if(ArrayHelper.isNotEmpty(parameters))
				arguments.setParameters(MapHelper.instantiateStringObject(parameters));
			return execute(arguments);
		}
		
		@Override
		public Boolean executeRefreshMaterializedView(Class<?> klass) {
			ThrowableHelper.throwIllegalArgumentExceptionIfNull("klass", klass);
			return execute(new ProcedureExecutorArguments().setKlass(klass).setProcedureName(ProcedureName.REFRESH_MATERIALIZED_VIEW));
		}
	}
	
	/**/
	
	static ProcedureExecutor getInstance() {
		return Helper.getInstance(ProcedureExecutor.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
}