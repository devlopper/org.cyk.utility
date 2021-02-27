package org.cyk.utility.persistence.server.procedure;

import java.io.Serializable;

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
			EntityManager entityManager = EntityManagerGetter.getInstance().get();
			StoredProcedureQuery storedProcedureQuery = entityManager.createNamedStoredProcedureQuery(name);
			if(MapHelper.isNotEmpty(arguments.getParameters())) {
				arguments.getParameters().forEach( (key,value) -> {
					storedProcedureQuery.setParameter(key, value);
				});
			}
			LogHelper.logInfo(String.format("Exécution de la procédure stockée %s en cours...", name), getClass());
			Long t = System.currentTimeMillis();
			Boolean result = storedProcedureQuery.execute();
			LogHelper.logInfo(String.format("Procédure stockée %s exécutée en %s", name,TimeHelper.formatDuration(System.currentTimeMillis() - t)), getClass());
			releaseConnection(storedProcedureQuery, name);
			return result;
		}
		
		protected void releaseConnection(StoredProcedureQuery storedProcedureQuery,String name) {
			LogHelper.logWarning(String.format("#### WE DO NOT KNOW HOW TO RELEASE CONNECTION FROM STORED PROCEDURE QUERY named <<%s>> ####", name), getClass());
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