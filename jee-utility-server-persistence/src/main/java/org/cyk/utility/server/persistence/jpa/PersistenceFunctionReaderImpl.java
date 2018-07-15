package org.cyk.utility.server.persistence.jpa;

import java.util.Collection;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.field.FieldName;
import org.cyk.utility.field.FieldNameGetter;
import org.cyk.utility.server.persistence.AbstractPersistenceFunctionReaderImpl;
import org.cyk.utility.sql.builder.QueryWherePredicateStringBuilder;
import org.cyk.utility.sql.builder.Tuple;
import org.cyk.utility.sql.jpql.JpqlQualifier;
import org.cyk.utility.sql.jpql.builder.QueryStringBuilderSelectJpql;
import org.cyk.utility.sql.jpql.builder.QueryWherePredicateStringBuilderEqualJpql;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.action.SystemActionRead;
import org.cyk.utility.value.ValueUsageType;

public class PersistenceFunctionReaderImpl extends AbstractPersistenceFunctionReaderImpl implements PersistenceFunctionReader {
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager entityManager;
	
	@Override
	protected void __executeQuery__(SystemAction action) {
		Class<?> aClass = getEntityClass();
		Object entityIdentifier = getEntityIdentifier();
		ValueUsageType valueUsageType = getEntityIdentifierValueUsageType();
		if(valueUsageType == null)
			valueUsageType = ValueUsageType.SYSTEM;
		String identifierFieldName = __inject__(FieldNameGetter.class).execute(aClass, FieldName.IDENTIFIER, valueUsageType).getOutput();
		Object entity;
		
		if(ValueUsageType.SYSTEM.equals(valueUsageType))
			entity = getEntityManager().find(aClass,entityIdentifier);
		else{
			Tuple tuple = new Tuple().setName(aClass.getSimpleName());
			QueryWherePredicateStringBuilder predicateBuilder = (QueryWherePredicateStringBuilder) JpqlQualifier.inject(QueryWherePredicateStringBuilderEqualJpql.class)
					.addOperandBuilderByAttribute(identifierFieldName,tuple);
			
			QueryStringBuilderSelectJpql queryBuilder = JpqlQualifier.inject(QueryStringBuilderSelectJpql.class).from(tuple).where(predicateBuilder);
			
			Collection<?> objects = getEntityManager().createQuery(queryBuilder.execute().getOutput(), aClass).setParameter(identifierFieldName, entityIdentifier).getResultList();
			entity = __inject__(CollectionHelper.class).getFirst(objects);
		}
		getProperties().setEntity(entity);
		if(entity == null)
			__addLogMessageBuilderParameter__(MESSAGE_NOT_FOUND);
	}
	
	@Override
	protected void __listenPostConstruct__() {
		getProperties().setEntityManager(entityManager).setAction(__inject__(SystemActionRead.class));
		super.__listenPostConstruct__();
	}
	
	@Override
	public EntityManager getEntityManager() {
		return (EntityManager) getProperties().getEntityManager();
	}
	
	@Override
	public PersistenceFunctionReader setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
		getProperties().setEntityManager(entityManager);
		return this;
	}
}
