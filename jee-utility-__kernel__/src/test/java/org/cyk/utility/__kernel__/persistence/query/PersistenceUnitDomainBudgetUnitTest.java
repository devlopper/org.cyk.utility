package org.cyk.utility.__kernel__.persistence.query;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Persistence;
import javax.persistence.Tuple;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.__entities__.domain.budget.AdministrativeUnit;
import org.cyk.utility.__kernel__.__entities__.domain.budget.FunctionalClassification;
import org.cyk.utility.__kernel__.__entities__.domain.budget.Localisation;
import org.cyk.utility.__kernel__.__entities__.domain.budget.Section;
import org.cyk.utility.__kernel__.__entities__.domain.budget.ServiceGroup;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.persistence.EntityManagerFactoryGetterImpl;
import org.cyk.utility.__kernel__.random.RandomHelper;
import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;
import org.openjdk.jol.info.GraphLayout;

public class PersistenceUnitDomainBudgetUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenBefore__() {
		super.__listenBefore__();
		EntityManagerFactoryGetterImpl.ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("persistence_unit_domain_budget");
		QueryHelper.getQueries().setIsRegisterableToEntityManager(Boolean.TRUE);
		QueryHelper.QUERIES.add(Query.build(AdministrativeUnit.class, "read.constructor", "SELECT new org.cyk.utility.__kernel__.__entities__.domain.budget.AdministrativeUnit(t.identifier,t.code) FROM AdministrativeUnit t"));
		QueryHelper.QUERIES.add(Query.build(AdministrativeUnit.class, "read.tuple", "SELECT t.identifier,t.code FROM AdministrativeUnit t",Tuple.class));
		QueryHelper.QUERIES.add(Query.build(AdministrativeUnit.class, "read.array", "SELECT t.identifier,t.code FROM AdministrativeUnit t"));
	}
	
	@Override
	protected void __listenAfter__() {
		super.__listenAfter__();
		QueryHelper.clear();
	}
	
	@Test
	public void read_administrativeUnit(){
		Integer numberOfAdministrativeUnits = 1000;
		Integer numberOfSections = 50;
		persistAdministrativeUnits(numberOfAdministrativeUnits,numberOfSections);
		
		printReadQueryUsageInformations(AdministrativeUnit.class, "AdministrativeUnit.read.constructor","AdministrativeUnit.read.tuple","AdministrativeUnit.read.array");
		
		/*
		Collection<Object[]> objects = EntityReader.getInstance().readMany(Object[].class,new QueryExecutorArguments().setQuery(QueryGetter.getInstance().get("AdministrativeUnit.read.array")));
		graphLayout = GraphLayout.parseInstance(objects);
		System.out.println(graphLayout.totalCount()+" - "+(graphLayout.totalSize()/1024));
		
		administrativeUnits = QueryResultMapper.getInstance().map(AdministrativeUnit.class, new QueryResultMapper.Arguments()
				.setQuery(QueryGetter.getInstance().get("AdministrativeUnit.read.array")).setObjects(objects));
		graphLayout = GraphLayout.parseInstance(administrativeUnits);
		System.out.println(graphLayout.totalCount()+" - "+(graphLayout.totalSize()/1024));
		*/
	}
	
	public <T> void printReadQueryUsageInformations(Class<T> tupleClass,Collection<String> queriesIdentifiers) {
		System.out.println(String.format("%1$s %2$s %1$s", StringUtils.repeat('=',50),tupleClass.getSimpleName()));
		
		Map<String,GraphLayout> map = new LinkedHashMap<>();
		Collection<T> collection = EntityReader.getInstance().readMany(tupleClass);
		map.put("read.orm", GraphLayout.parseInstance(collection));
		
		if(CollectionHelper.isNotEmpty(queriesIdentifiers))
			queriesIdentifiers.forEach(queryIdentifier -> {
				Query query = QueryGetter.getInstance().get(queryIdentifier);
				map.put(queryIdentifier, GraphLayout.parseInstance(EntityReader.getInstance().readMany(query.getResultClass(),new QueryExecutorArguments()
						.setQuery(QueryGetter.getInstance().get(queryIdentifier)))));
			});
		
		printReadQueryUsageInformationsLine("Query.ID", "#O", "#B","#KB","MB","B/O");
		map.forEach( (queryIdentifier,graphLayout) -> {
			printReadQueryUsageInformationsLine(queryIdentifier, graphLayout.totalCount()+"", graphLayout.totalSize()+""
					,new BigDecimal(graphLayout.totalSize()).divide(new BigDecimal("1024"),2,RoundingMode.HALF_DOWN)+""
					,new BigDecimal(graphLayout.totalSize()).divide(new BigDecimal("1024"),2,RoundingMode.HALF_DOWN).divide(new BigDecimal("1024"),2,RoundingMode.HALF_DOWN)+""
					,new BigDecimal(graphLayout.totalSize()).divide(new BigDecimal(graphLayout.totalCount()),2,RoundingMode.HALF_DOWN)+"");
			//System.out.println(StringUtils.leftPad(queryIdentifier,50)+" | "+StringUtils.leftPad(,10)+" | "+StringUtils.leftPad((graphLayout.totalSize()/1024)+"",15));
		});
	}
	
	public <T> void printReadQueryUsageInformationsLine(String queryIdentifier,String numberOfObjects,String numberOfBytes,String numberOfKiloBytes,String numberOfMegaBytes
			,String numberOfBytesPerObject) {
		System.out.println(String.format("%s | %s | %s | %s | %s | %s", StringUtils.leftPad(queryIdentifier,50),StringUtils.leftPad(numberOfObjects,10),
				StringUtils.leftPad(numberOfBytes,15),StringUtils.leftPad(numberOfKiloBytes,10),StringUtils.leftPad(numberOfMegaBytes,5),StringUtils.leftPad(numberOfBytesPerObject,5)));
	}
	
	public <T> void printReadQueryUsageInformations(Class<T> tupleClass,String...queriesIdentifiers) {
		printReadQueryUsageInformations(tupleClass, CollectionHelper.listOf(queriesIdentifiers));
	}
	
	/**/
	
	private void persistAdministrativeUnits(Integer numberOfAdministrativeUnits,Integer numberOfSections) {
		System.out.println("Persisting "+(numberOfAdministrativeUnits*numberOfSections)+" administrative units...");
		Long t = System.currentTimeMillis();
		Collection<Object> objects = new ArrayList<>();
		for(Integer index = 0 ; index < numberOfSections ; index = index + 1) {
			objects.add(new Section().setIdentifier(index+"").setCode(index+"").setName(index+""));	
			objects.add(new ServiceGroup().setIdentifier(index+"").setCode(index+"").setName(index+""));	
			objects.add(new FunctionalClassification().setIdentifier(index+"").setCode(index+"").setName(index+""));	
			objects.add(new Localisation().setIdentifier(index+"").setCode(index+"").setName(index+""));	
		}
		EntityCreator.getInstance().createManyInTransaction(objects);
		
		objects = new ArrayList<>();
		for(Integer sectionIndex = 0 ; sectionIndex < numberOfSections ; sectionIndex = sectionIndex + 1) {
			for(Integer index = 0 ; index < numberOfAdministrativeUnits ; index = index + 1)
				objects.add(new AdministrativeUnit().setIdentifier(sectionIndex+"."+index).setCode(sectionIndex+"."+index).setName(index+"")
						.setOrderNumber(RandomHelper.getNumeric(4).intValue())
						.setSectionFromCode("1").setServiceGroupFromCode("1").setFunctionalClassificationFromCode("1").setLocalisationFromCode("1")
						);
		}		
		EntityCreator.getInstance().createManyInTransaction(objects);	
		System.out.println("Persisted in "+((System.currentTimeMillis() - t))+" ms");
	}
}