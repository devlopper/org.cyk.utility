package org.cyk.utility.persistence.server.hibernate;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.object.marker.AuditableWhoDoneWhatWhen;
import org.cyk.utility.persistence.query.EntityCreator;
import org.cyk.utility.persistence.query.EntityFinder;
import org.cyk.utility.persistence.query.EntityReader;
import org.cyk.utility.persistence.query.EntityUpdater;
import org.cyk.utility.persistence.query.Querier;
import org.cyk.utility.persistence.query.QueryExecutorArguments;
import org.cyk.utility.persistence.server.TransientFieldsProcessor;
import org.cyk.utility.persistence.server.audit.AuditReader;
import org.cyk.utility.persistence.server.query.executor.DynamicManyExecutor;
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
	public void readHistoriesByFlag(){
		List<LocalDateTime> dateTimes = new ArrayList<>();
		String identifier = "d01";
		Collection<DataAuditedAudit> histories = null;
		dateTimes.add(LocalDateTime.now());
		histories = DynamicManyExecutor.getInstance().read(DataAuditedAudit.class, null);
		assertThat(histories).isNull();
		
		DataAudited data = new DataAudited().setCode(identifier);
		data.set__auditFunctionality__("creation_functionality");
		dateTimes.add(LocalDateTime.now());
		EntityCreator.getInstance().createOneInTransaction(data);
		data = EntityFinder.getInstance().find(DataAudited.class,identifier);
		
		assertThat(data).isNotNull();
		assertHistory(null,null,1);
		
		data.setCode("code01");
		dateTimes.add(LocalDateTime.now());
		EntityUpdater.getInstance().updateOneInTransaction(data);
		assertHistory(null,null,2);
		
		data.setCode("code 01");
		dateTimes.add(LocalDateTime.now());
		EntityUpdater.getInstance().updateOneInTransaction(data);
		dateTimes.add(LocalDateTime.now());
		assertHistory(null,null,3);
		
		assertHistory(dateTimes.get(0),dateTimes.get(4),3);
		assertHistory(dateTimes.get(0),dateTimes.get(3),2);
		assertHistory(dateTimes.get(0),dateTimes.get(2),1);
		assertHistory(dateTimes.get(0),dateTimes.get(1),0);
		
		//assertThat(AuditReader.getInstance().readByDates(DataAuditedAudit.class, dateTimes.get(0),dateTimes.get(4))).hasSize(3);
		//assertThat(AuditReader.getInstance().readByDates(DataAuditedAudit.class, dateTimes.get(0),dateTimes.get(3))).hasSize(2);
	}
	
	@Test
	public void readHistoriesByDates(){
		String identifier = "d01";
		Collection<DataAudited> histories = null;
		histories = AuditReader.getInstance().readByIdentifier(DataAudited.class, identifier);
		assertThat(histories).isNull();
		/*
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
		*/
	}
	
	/**/
	
	private void assertHistory(LocalDateTime fromDate,LocalDateTime toDate,Integer expectedCount) {
		QueryExecutorArguments queryExecutorArguments = new QueryExecutorArguments()
				.addProjectionsFromStrings(DataAuditedAudit.FIELD_IDENTIFIER,DataAuditedAudit.FIELD_CODE,DataAuditedAudit.FIELD_NAME)
				.addProcessableTransientFieldsNames(DataAuditedAudit.FIELD___WHEN_AS_STRING__);
		if(fromDate != null)
			queryExecutorArguments.addFilterFieldsValues(Querier.PARAMETER_NAME_FROM_DATE,fromDate);
		if(toDate != null)
			queryExecutorArguments.addFilterFieldsValues(Querier.PARAMETER_NAME_TO_DATE,toDate);
		Collection<DataAuditedAudit> histories = DynamicManyExecutor.getInstance().read(DataAuditedAudit.class, queryExecutorArguments);
		assertThat(CollectionHelper.getSize(histories)).isEqualTo(expectedCount);
		if(CollectionHelper.isNotEmpty(histories)) {
			histories.forEach(history -> {
				assertThat(history.getIdentifier()).as("Identifier is null").isNotNull();
				assertThat(history.getCode()).as("Code is null").isNotNull();
				assertThat(history.getName()).as("Name is null").isNotNull();
				/*
				assertThat(history.get__functionality__()).as("Functionality is null").isNotNull();
				assertThat(history.get__what__()).as("What is null").isNotNull();
				assertThat(history.get__who__()).as("Who is null").isNotNull();
				*/
				assertThat(history.get__whenAsString__()).as("When is null").isNotNull();
			});
		}
	}
}