package org.cyk.utility.server.persistence;

import java.io.Serializable;
import java.util.Map;

import org.cyk.utility.__kernel__.configuration.ConfigurationHelper;
import org.cyk.utility.__kernel__.log.LogLevel;
import org.cyk.utility.__kernel__.persistence.query.Query;
import org.cyk.utility.__kernel__.persistence.query.QueryHelper;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.system.action.SystemAction;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;
import org.cyk.utility.__kernel__.variable.VariableName;
import org.cyk.utility.system.AbstractSystemFunctionServerImpl;
import org.cyk.utility.system.layer.SystemLayer;
import org.cyk.utility.system.layer.SystemLayerPersistence;

public abstract class AbstractPersistenceFunctionImpl extends AbstractSystemFunctionServerImpl implements PersistenceFunction, Serializable {
	private static final long serialVersionUID = 1L;

	public static LogLevel LOG_LEVEL = LogLevel.TRACE;
	
	private Long queryFirstTupleIndex,queryNumberOfTuple;
	private Boolean isQueryResultPaginated;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setLogLevel(LOG_LEVEL);
	}
	
	@Override
	protected final void __execute__(SystemAction action) {
		String queryIdentifier = (String) getQueryIdentifier();
		String queryValue = getQueryValue();
		if(StringHelper.isBlank(queryIdentifier) &&StringHelper.isBlank(queryValue)){
			__executeQuery__(action);		
		}else {
			Query query = QueryHelper.getQueries().getBySystemIdentifier(queryIdentifier);
			if(query == null){
				if(StringHelper.isBlank(queryValue))
					throw new RuntimeException("persistence query with identifier "+queryIdentifier+" not found.");	
				query = new Query().setValue(queryValue).setResultClass(getQueryResultClass());	
			}
			__executeQuery__(action, query);
		}
		
		Class<?> entityClass = action.getEntityClass();
		String entityName = entityClass == null ? "??????" : entityClass.getSimpleName();
		
		__produceFunction__(entityName+"."+action.getIdentifier()
		, __injectMapHelper__().instanciateKeyAsStringValueAsString(
				"system.identifier",ConfigurationHelper.getValueAsString(VariableName.SYSTEM_IDENTIFIER)
				,"action.identifier",action.getIdentifier()
				,"entity.name",entityName
				)
		, __getProduceFunctionOutputs__()
		);
	}
	
	protected Map<String,String> __getProduceFunctionOutputs__() {
		return null;
	}
	
	protected void __executeQuery__(SystemAction action){
		ThrowableHelper.throwNotYetImplemented();
	}
	
	protected void __executeQuery__(SystemAction action,Query query){
		ThrowableHelper.throwNotYetImplemented();
	}
	
	@Override
	public PersistenceFunction setAction(SystemAction action) {
		return (PersistenceFunction) super.setAction(action);
	}
	
	@Override
	public PersistenceFunction setEntityClass(Class<?> aClass) {
		return (PersistenceFunction) super.setEntityClass(aClass);
	}
	
	@Override
	protected SystemLayer getSystemLayer() {
		return __inject__(SystemLayerPersistence.class);
	}
	
	@Override
	public PersistenceFunction setQueryIdentifier(Object identifier) {
		getProperties().setFromPath(new Object[]{Properties.QUERY,Properties.IDENTIFIER}, identifier);
		if(getEntityClass() == null){
			Query query = QueryHelper.getQueries().getBySystemIdentifier(identifier,Boolean.TRUE);
			if(query!=null){
				setEntityClass(query.getResultClass());
				setQueryResultClass(query.getResultClass());
			}
		}
		return this;
	}
	
	@Override
	public Object getQueryIdentifier() {
		return getProperties().getFromPath(Properties.QUERY,Properties.IDENTIFIER);
	}
	
	@Override
	public PersistenceFunction setQueryValue(String value) {
		getProperties().setFromPath(new Object[]{Properties.QUERY,Properties.VALUE}, value);
		/*if(getEntityClass() == null){
			PersistenceQuery query = __inject__(PersistenceQueryRepository.class).getBySystemIdentifier(identifier,Boolean.TRUE);
			if(query!=null)
				setEntityClass(query.getResultClass());
		}*/
		return this;
	}
	
	@Override
	public String getQueryValue() {
		return (String) getProperties().getFromPath(Properties.QUERY,Properties.VALUE);
	}

	@Override
	public PersistenceFunction setQueryParameters(Properties parameters) {
		getProperties().setParameters(parameters);
		return this;
	}
	
	@Override
	public Properties getQueryParameters() {
		return (Properties) getProperties().getParameters();
	}
	
	@Override
	public PersistenceFunction setQueryParameter(String name, Object value) {
		Properties parameters = getQueryParameters();
		if(parameters == null)
			setQueryParameters(parameters = new Properties());
		parameters.set(name, value);
		return this;
	}
	
	@Override
	public Class<?> getQueryResultClass() {
		return (Class<?>) getProperties().getFromPath(Properties.QUERY,Properties.RESULT,Properties.CLASS);
	}
	
	@Override
	public PersistenceFunction setQueryResultClass(Class<?> aClass) {
		getProperties().setFromPath(new Object[]{Properties.QUERY,Properties.RESULT,Properties.CLASS}, aClass);
		return this;
	}

	@Override
	public Boolean getIsQueryResultPaginated() {
		return isQueryResultPaginated;
	}
	
	@Override
	public PersistenceFunction setIsQueryResultPaginated(Boolean isQueryResultPaginated) {
		this.isQueryResultPaginated = isQueryResultPaginated;
		return this;
	}
	
	@Override
	public Long getQueryFirstTupleIndex() {
		return queryFirstTupleIndex;
	}
	
	@Override
	public PersistenceFunction setQueryFirstTupleIndex(Long queryFirstTupleIndex) {
		this.queryFirstTupleIndex = queryFirstTupleIndex;
		return this;
	}
	
	@Override
	public Long getQueryNumberOfTuple() {
		return queryNumberOfTuple;
	}
	
	@Override
	public PersistenceFunction setQueryNumberOfTuple(Long queryNumberOfTuple) {
		this.queryNumberOfTuple = queryNumberOfTuple;
		return this;
	}
}
