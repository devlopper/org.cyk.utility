package org.cyk.utility.persistence.server.hibernate;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.object.marker.AuditableWhoDoneWhatWhen;
import org.cyk.utility.__kernel__.time.TimeHelper;
import org.cyk.utility.persistence.query.EntityCreator;
import org.cyk.utility.persistence.query.EntityFinder;
import org.cyk.utility.persistence.query.EntityReader;
import org.cyk.utility.persistence.query.EntityUpdater;
import org.cyk.utility.persistence.query.Querier;
import org.cyk.utility.persistence.query.QueryExecutorArguments;
import org.cyk.utility.persistence.server.TransientFieldsProcessor;
import org.cyk.utility.persistence.server.audit.AuditReader;
import org.cyk.utility.persistence.server.query.executor.DynamicOneExecutor;
import org.cyk.utility.persistence.server.query.string.RuntimeQueryStringBuilder;
import org.junit.jupiter.api.Test;

public class AuditUnitTest extends AbstractUnitTest {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void __listenBefore__() {
		super.__listenBefore__();
		DependencyInjection.setQualifierClassTo(org.cyk.utility.__kernel__.annotation.Test.class, AuditReader.class,EntityReader.class,RuntimeQueryStringBuilder.class
				,TransientFieldsProcessor.class);
	}
	
	@Override
	protected String getPersistenceUnitName() {
		return "audit";
	}
	
	@Test
	public void readHistoriesByIdentifiers(){
		String identifier = "d01";
		Collection<DataAudited> histories = null;
		histories = AuditReader.getInstance().readByIdentifier(DataAudited.class, identifier);
		assertThat(histories).isNull();		
		
		DataAudited data = new DataAudited().setCode(identifier);
		EntityCreator.getInstance().createOneInTransaction(data);
		data = DynamicOneExecutor.getInstance().read(DataAudited.class, new QueryExecutorArguments()
				.addFilterFieldsValues(Querier.PARAMETER_NAME_IDENTIFIER,identifier).addProcessableTransientFieldsNames(AuditableWhoDoneWhatWhen.FIELD___AUDIT_RECORDS__));
		
		assertThat(data).isNotNull();
		histories = data.get__auditRecords__();
		assertThat(histories).hasSize(1);
		
		data.setCode("code01");
		EntityUpdater.getInstance().updateOneInTransaction(data);
		data = DynamicOneExecutor.getInstance().read(DataAudited.class, new QueryExecutorArguments()
				.addFilterFieldsValues(Querier.PARAMETER_NAME_IDENTIFIER,identifier).addProcessableTransientFieldsNames(AuditableWhoDoneWhatWhen.FIELD___AUDIT_RECORDS__));
		histories = data.get__auditRecords__();
		assertThat(histories).hasSize(2);
		
		assertThat(CollectionHelper.getElementAt(histories, 0).getCode()).isEqualTo("code01");
		assertThat(CollectionHelper.getElementAt(histories, 1).getCode()).isEqualTo("d01");
	}
	
	@Test
	public void readAudits(){
		List<LocalDateTime> dateTimes = new ArrayList<>();
		String identifier = "d01";
		Collection<DataAudited> histories = null;
		pause(dateTimes, 100);
		histories = AuditReader.getInstance().readByDates(DataAudited.class, null, null);
		assertThat(histories).isNull();
		
		DataAudited data = new DataAudited().setCode(identifier);
		pause(dateTimes, 100);
		EntityCreator.getInstance().createOneInTransaction(data);
		data = EntityFinder.getInstance().find(DataAudited.class,identifier);
		
		assertThat(data).isNotNull();
		assertHistory(null,null,1);
		
		data.setCode("code01");
		pause(dateTimes, 100);
		EntityUpdater.getInstance().updateOneInTransaction(data);
		assertHistory(null,null,2);
		
		data.setCode("code 01");
		pause(dateTimes, 100);
		EntityUpdater.getInstance().updateOneInTransaction(data);
		pause(dateTimes, 100);
		assertHistory(null,null,3);
		
		assertHistory(dateTimes.get(0),dateTimes.get(4),3);
		assertHistory(dateTimes.get(0),dateTimes.get(3),2);
		assertHistory(dateTimes.get(0),dateTimes.get(2),1);
		assertHistory(dateTimes.get(0),dateTimes.get(1),0);
	}
	
	/**/
	
	private void assertHistory(LocalDateTime fromDate,LocalDateTime toDate,Integer expectedCount) {
		QueryExecutorArguments queryExecutorArguments = new QueryExecutorArguments()
				.addProjectionsFromStrings(DataAuditedAudit.FIELD_IDENTIFIER,DataAuditedAudit.FIELD_CODE,DataAuditedAudit.FIELD_NAME)
				.addProcessableTransientFieldsNames(DataAuditedAudit.FIELD___AUDIT_WHEN_AS_STRING__);
		Collection<DataAudited> histories = AuditReader.getInstance().read(DataAudited.class,new AuditReader.Arguments<DataAudited>()
				.setFromDate(fromDate).setToDate(toDate).setIsReadableByDates(Boolean.TRUE).setQueryExecutorArguments(queryExecutorArguments));
		//histories = DynamicManyExecutor.getInstance().read(DataAuditedAudit.class, queryExecutorArguments);
		assertThat(CollectionHelper.getSize(histories)).isEqualTo(expectedCount);
		if(CollectionHelper.isNotEmpty(histories)) {
			histories.forEach(history -> {
				if(CollectionHelper.isNotEmpty(queryExecutorArguments.getProjections()))
					queryExecutorArguments.getProjections().forEach(p -> {
						assertThat(FieldHelper.read(history, p.getFieldName())).as(p.getFieldName()+" is null").isNotNull();
					});
				
				if(CollectionHelper.isNotEmpty(queryExecutorArguments.getProcessableTransientFieldsNames()))
					queryExecutorArguments.getProcessableTransientFieldsNames().forEach(p -> {
						assertThat(FieldHelper.read(history, p)).as(p+" is null").isNotNull();
					});
				//assertThat(history.get__auditFunctionality__()).as("Functionality is null").isNotNull();
				/*
				assertThat(history.get__what__()).as("What is null").isNotNull();
				assertThat(history.get__who__()).as("Who is null").isNotNull();
				*/
			});
		}
	}
	
	/**/
	
	private static void pause(List<LocalDateTime> dateTimes,Integer duration) {
		if(dateTimes == null)
			return;
		dateTimes.add(LocalDateTime.now()); 
		TimeHelper.pause(duration * 1l);
	}
}