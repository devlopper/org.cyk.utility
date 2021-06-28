package org.cyk.utility.persistence.server.hibernate;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.object.marker.AuditableWhoDoneWhatWhen;
import org.cyk.utility.__kernel__.time.TimeHelper;
import org.cyk.utility.__kernel__.value.ValueHelper;
import org.cyk.utility.persistence.query.EntityCounter;
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
		assertAudit(null,null,0);
		List<LocalDateTime> dateTimes = new ArrayList<>();
		String identifier = "d01";
		pause(dateTimes, 100);
		
		DataAudited data = new DataAudited().setCode(identifier);
		pause(dateTimes, 100);
		EntityCreator.getInstance().createOneInTransaction(data);
		data = EntityFinder.getInstance().find(DataAudited.class,identifier);
		
		assertThat(data).isNotNull();
		assertAudit(null,null,1);
		
		data.setCode("code01");
		pause(dateTimes, 100);
		EntityUpdater.getInstance().updateOneInTransaction(data.set__auditWhen__(null));
		assertAudit(null,null,2);
		
		data.setCode("code 01");
		pause(dateTimes, 100);
		EntityUpdater.getInstance().updateOneInTransaction(data.set__auditWhen__(null));
		pause(dateTimes, 100);
		assertAudit(null,null,3);
		
		assertAudit(dateTimes.get(0),dateTimes.get(4),3);
		assertAudit(dateTimes.get(0),dateTimes.get(3),2);
		assertAudit(dateTimes.get(0),dateTimes.get(2),1);
		assertAudit(dateTimes.get(0),dateTimes.get(1),0);
	}
	
	/**/
	
	private void assertAudit(LocalDateTime fromDate,LocalDateTime toDate,Integer expectedCount) {
		QueryExecutorArguments queryExecutorArguments = new QueryExecutorArguments().setIsProcessableAsAuditByDates(Boolean.TRUE)
				.addProjectionsFromStrings(DataAuditedAudit.FIELD_IDENTIFIER,DataAuditedAudit.FIELD_CODE,DataAuditedAudit.FIELD_NAME
						,DataAuditedAudit.FIELD___AUDIT_REVISION__)
				.addProcessableTransientFieldsNames(DataAuditedAudit.FIELD___AUDIT_WHEN_AS_STRING__);
		if(fromDate != null)
			queryExecutorArguments.addFilterField(Querier.PARAMETER_NAME_FROM_DATE, fromDate);
		if(toDate != null)
			queryExecutorArguments.addFilterField(Querier.PARAMETER_NAME_TO_DATE, toDate);
		Collection<DataAudited> audits = EntityReader.getInstance().readMany(DataAudited.class, queryExecutorArguments);
		
		Long count = ValueHelper.defaultToIfNull(EntityCounter.getInstance().count(DataAudited.class, queryExecutorArguments),0l);
		
		assertThat(CollectionHelper.getSize(audits)).as("collection size is not equal").isEqualTo(expectedCount);
		assertThat(count.intValue()).as("count is not equal").isEqualTo(expectedCount);
		
		if(CollectionHelper.isNotEmpty(audits)) {
			audits.forEach(audit -> {
				assertThat(audit.getIdentifier()).as("identifier is null").isNotNull();
				assertThat(audit.getCode()).as("code is null").isNotNull();
				assertThat(audit.getName()).as("name is null").isNotNull();
				assertThat(audit.get__auditWhenAsString__()).as("when as string is null").isNotNull();
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