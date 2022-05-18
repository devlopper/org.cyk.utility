package org.cyk.utility.business.server;

import java.io.Serializable;

import org.cyk.utility.business.MaterializedViewSpecificBusiness;
import org.cyk.utility.business.Result;
import org.cyk.utility.persistence.MaterializedViewSpecificPersistence;

public abstract class AbstractMaterializedViewSpecificBusinessImpl<ENTITY> extends AbstractSpecificBusinessImpl<ENTITY> implements MaterializedViewSpecificBusiness<ENTITY>,Serializable {

	protected abstract MaterializedViewSpecificPersistence<ENTITY> getPersistence();
	
	@Override
	public Result create() {
		Result result = new Result().setName(String.format("Materialized view creation of <<%s>>",entityClass)).open();
		getPersistence().create();
		result.close().addMessages(String.format("Materialized view of <<%s>> has been created.",entityClass));
		return result;
	}
	
	@Override
	public Result update() {
		Result result = new Result().setName(String.format("Materialized view update of <<%s>>",entityClass)).open();
		getPersistence().update();
		result.close().addMessages(String.format("Materialized view of <<%s>> has been updated.",entityClass));
		return result;
	}
	
	@Override
	public Result actualize() {
		Result result = new Result().setName(String.format("Materialized view actualization of <<%s>>",entityClass)).open();
		getPersistence().actualize();
		result.close().addMessages(String.format("Materialized view of <<%s>> has been actualized.",entityClass));
		return result;
	}
	
	@Override
	public Result delete() {
		Result result = new Result().setName(String.format("Materialized view deletion of <<%s>>",entityClass)).open();
		getPersistence().delete();
		result.close().addMessages(String.format("Materialized view of <<%s>> has been deleted.",entityClass));
		return result;
	}
	
}