package org.cyk.utility.business.server;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.enumeration.Action;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.object.marker.AuditableWhoDoneWhatWhen;
import org.cyk.utility.__kernel__.throwable.ThrowablesMessages;
import org.cyk.utility.business.SpecificBusiness;
import org.cyk.utility.business.TransactionResult;
import org.cyk.utility.business.ValidatorImpl;
import org.cyk.utility.persistence.query.QueryExecutorArguments;

public abstract class AbstractSpecificBusinessImpl<ENTITY> extends AbstractObject implements SpecificBusiness<ENTITY>,Serializable {

	protected Class<ENTITY> entityClass;
	
	@Override @Transactional
	public TransactionResult create(QueryExecutorArguments arguments) {
		if(arguments == null)
			return null;
		return __create__(arguments);
	}
	
	protected TransactionResult __create__(QueryExecutorArguments arguments) {
		ThrowablesMessages throwablesMessages = new ThrowablesMessages();
		__prepare__((Collection<ENTITY>) arguments.getObjects(),Action.CREATE,throwablesMessages);
		throwablesMessages.throwIfNotEmpty();
		TransactionResult result = new TransactionResult();
		EntityCreator.getInstance().create(arguments);
		result.incrementNumberOfCreation(Long.valueOf(arguments.getObjects().size()));
		return result;
	}
	
	protected void __prepare__(Collection<ENTITY> entities,Action action,ThrowablesMessages throwablesMessages) {
		for(ENTITY entity : entities) {
			if(entity == null)
				continue;
			__prepare__(entity,action,throwablesMessages);
		}
	}
	
	protected void __prepare__(ENTITY entity,Action action,ThrowablesMessages throwablesMessages) {
		
	}
	
	@Override @Transactional
	public TransactionResult create(Collection<ENTITY> entities) {
		if(CollectionHelper.isEmpty(entities))
			return null;
		return create(new QueryExecutorArguments().setObjects(CollectionHelper.cast(Object.class, entities.stream().filter(x -> x != null).collect(Collectors.toList()))));
	}
	
	@Override @Transactional
	public TransactionResult create(ENTITY... entities) {
		if(ArrayHelper.isEmpty(entities))
			return null;
		return create(CollectionHelper.listOf(entities));
	}
	
	@Override
	public TransactionResult delete(Collection<String> identifiers, String actorCode) {
		throw new RuntimeException("Delete not yet implemented");
	}

	protected void createBatch(Collection<ENTITY> entities,EntityManager entityManager,Boolean isUserTransaction,Level logLevel) {
		processBatch(entities, entityManager, isUserTransaction, Action.CREATE, logLevel);
	}
	
	protected void updateBatch(Collection<ENTITY> entities,EntityManager entityManager,Boolean isUserTransaction,Level logLevel) {
		processBatch(entities, entityManager, isUserTransaction, Action.UPDATE, logLevel);
	}
	
	protected void deleteBatch(Collection<ENTITY> entities,EntityManager entityManager,Boolean isUserTransaction,Level logLevel) {
		processBatch(entities, entityManager, isUserTransaction, Action.DELETE, logLevel);
	}
	
	protected void processBatch(Collection<ENTITY> entities,EntityManager entityManager,Boolean isUserTransaction,Action action,Level logLevel) {
		//LogHelper.log(String.format("Traitement du lot %s/%s | %s",batchIndex,batchsCount,CollectionHelper.getSize(entities)),ValueHelper.defaultToIfNull(logLevel, Level.FINE), getClass());
		if(Boolean.TRUE.equals(isUserTransaction))
			entityManager.getTransaction().begin();
		
		for(ENTITY entity : entities)
			if(Action.CREATE.equals(action))
				entityManager.persist(entity);
			else if(Action.UPDATE.equals(action))
				entityManager.merge(entity);
			else if(Action.DELETE.equals(action))
				entityManager.remove(entity);
		
		if(Boolean.TRUE.equals(isUserTransaction))
			entityManager.getTransaction().commit();
		else
			entityManager.flush();
		entityManager.clear();
		entities.clear();
		entities = null;
		System.gc();
	}
	
	protected void shutdownExecutorService(ExecutorService executorService,Long timeout,TimeUnit timeoutUnit) {
		//Recommended by Oracle to shutdown
		executorService.shutdown();
		try {
		    if (!executorService.awaitTermination(timeout, timeoutUnit)) {
		        executorService.shutdownNow();
		    } 
		} catch (InterruptedException e) {
		    executorService.shutdownNow();
		}
	}
	
	protected String generateAuditIdentifier() {
		return generateAuditIdentifier(getEntityClass());
	}
	
	/**/
	
	protected Class<ENTITY> getEntityClass() {
		if(entityClass == null)
			entityClass = (Class<ENTITY>) ClassHelper.getParameterAt(getClass(), 0);
		return entityClass;
	}
	
	/**/
	
	public static void throwIfNotEmpty(ThrowablesMessages throwablesMessages) {
		ValidatorImpl.throwIfNotEmpty(throwablesMessages);	
	}
	
	public static void audit(AuditableWhoDoneWhatWhen instance,String identifier,String functionality,String who,LocalDateTime when) {
		instance.set__auditIdentifier__(identifier);
		instance.set__auditFunctionality__(functionality);
		instance.set__auditWhat__(null);// It will be deduced
		instance.set__auditWhen__(when);
		instance.set__auditWho__(who);
	}
	
	public static String generateAuditIdentifier(Class<?> klass) {
		return String.format(AUDIT_IDENTIFIER_FORMAT, System.currentTimeMillis(),UUID.randomUUID().toString());
	}
	
	private static final String AUDIT_IDENTIFIER_FORMAT = "%s-%s";
}