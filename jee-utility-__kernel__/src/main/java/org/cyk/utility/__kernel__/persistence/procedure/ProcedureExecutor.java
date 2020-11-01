package org.cyk.utility.__kernel__.persistence.procedure;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.StoredProcedureQuery;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.persistence.EntityManagerGetter;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;
import org.cyk.utility.__kernel__.time.TimeHelper;
import org.cyk.utility.__kernel__.value.Value;

public interface ProcedureExecutor {

	Boolean execute(ProcedureExecutorArguments arguments);
	
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
			StoredProcedureQuery query = entityManager.createNamedStoredProcedureQuery(name);
			Long t = System.currentTimeMillis();
			LogHelper.logInfo(String.format("Execution de la procedure stockée %s en cours...", name), getClass());
			Boolean result = query.execute();
			LogHelper.logInfo(String.format("Procedure stockée %s exécutée en %s", name,TimeHelper.formatDuration(System.currentTimeMillis() - t)), getClass());
			return result;
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