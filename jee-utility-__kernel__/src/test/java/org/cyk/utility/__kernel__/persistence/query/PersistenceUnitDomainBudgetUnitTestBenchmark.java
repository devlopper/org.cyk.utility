package org.cyk.utility.__kernel__.persistence.query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.persistence.Persistence;

import org.cyk.utility.__kernel__.__entities__.domain.budget.AdministrativeUnit;
import org.cyk.utility.__kernel__.__entities__.domain.budget.FunctionalClassification;
import org.cyk.utility.__kernel__.__entities__.domain.budget.Localisation;
import org.cyk.utility.__kernel__.__entities__.domain.budget.Section;
import org.cyk.utility.__kernel__.__entities__.domain.budget.ServiceGroup;
import org.cyk.utility.__kernel__.persistence.EntityManagerFactoryGetterImpl;
import org.cyk.utility.__kernel__.random.RandomHelper;
import org.cyk.utility.test.weld.AbstractWeldUnitTestBenchmark;
import org.junit.jupiter.api.Test;

public class PersistenceUnitDomainBudgetUnitTestBenchmark extends AbstractWeldUnitTestBenchmark {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenBefore__() {
		super.__listenBefore__();
		EntityManagerFactoryGetterImpl.ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("persistence_unit_domain_budget");
		QueryHelper.getQueries().setIsRegisterableToEntityManager(Boolean.TRUE);
		QueryHelper.QUERIES.add(Query.build(AdministrativeUnit.class, "read.view.01", "SELECT t.identifier,CONCAT(t.code,' ',t.name)"
				+ ",CONCAT(section.code,' ',section.name) "
				+ ",CONCAT(serviceGroup.code,' ',serviceGroup.name) "
				+ ",CONCAT(functionalClassification.code,' ',functionalClassification.name) "
				+ ",CONCAT(localisation.code,' ',localisation.name) "
				
				+ "FROM AdministrativeUnit t "
				
				+ "LEFT JOIN t.section section "
				+ "LEFT JOIN t.serviceGroup serviceGroup "
				+ "LEFT JOIN t.functionalClassification functionalClassification "
				+ "LEFT JOIN t.localisation localisation")
				.setTupleFieldsNamesIndexes(Map.of("identifier",0,"asString",1,"sectionAsString",2,"serviceGroupAsString",3,"functionalClassificationAsString",4,"localisationAsString",5)));
	}
	
	@Override
	protected void __listenAfter__() {
		super.__listenAfter__();
		QueryHelper.clear();
	}
	
	private void persistAdministrativeUnits(Integer numberOfAdministrativeUnits,Integer numberOfSections) {
		System.out.println("Persisting "+(numberOfAdministrativeUnits*numberOfSections)+" administrative units...");
		Long t = System.currentTimeMillis();
		Collection<Object> objects = new ArrayList<>();
		for(Integer index = 1 ; index <= numberOfSections ; index = index + 1) {
			objects.add(new Section().setIdentifier(index+"").setCode("SEC"+index).setName("Section "+index));	
			objects.add(new ServiceGroup().setIdentifier(""+index).setCode("SG"+index).setName("Service Group "+index));	
			objects.add(new FunctionalClassification().setIdentifier(index+"").setCode("FC"+index).setName("Funct. Class. "+index));	
			objects.add(new Localisation().setIdentifier(index+"").setCode("LOC"+index).setName("Localisation "+index));	
		}
		EntityCreator.getInstance().createManyInTransaction(objects);
		
		objects = new ArrayList<>();
		for(Integer sectionIndex = 0 ; sectionIndex < numberOfSections ; sectionIndex = sectionIndex + 1) {
			for(Integer index = 0 ; index < numberOfAdministrativeUnits ; index = index + 1) {
				AdministrativeUnit administrativeUnit = new AdministrativeUnit().setIdentifier(sectionIndex+"."+index).setCode(sectionIndex+"."+index).setName(index+"")
						.setOrderNumber(RandomHelper.getNumeric(4).intValue())
						.setSectionFromIdentifier("1").setServiceGroupFromIdentifier("1").setFunctionalClassificationFromIdentifier("1").setLocalisationFromIdentifier("1")
						;
				objects.add(administrativeUnit);
			}
		}		
		EntityCreator.getInstance().createManyInTransaction(objects);	
		System.out.println("Persisted in "+((System.currentTimeMillis() - t))+" ms");
	}
	
	@Test
	public void readMany_100_1(){
		readMany(100, 1);
	}
	
	@Test
	public void readMany_50_2(){
		readMany(50, 2);
	}
	
	@Test
	public void readMany_25_4(){
		readMany(25,4);
	}
	
	@Test
	public void readMany_5_20(){
		readMany(5, 20);
	}
	
	private void readMany(Integer numberOfAdministrativeUnits,Integer numberOfSections){
		persistAdministrativeUnits(numberOfAdministrativeUnits, numberOfSections);
		execute(new Jobs().setName("read "+(numberOfAdministrativeUnits*numberOfSections)+" administrative units").setNumberOfRound(10000)
				.add("relation information is fetched as object",new Runnable() {
				@Override
				public void run() {
					EntityReader.getInstance().readMany(AdministrativeUnit.class,new QueryExecutorArguments()
							.setQuery(QueryGetter.getInstance().get("AdministrativeUnit.read")));
				}
			}).add("relation information is fetched as string",new Runnable() {
				@Override
				public void run() {
					EntityReader.getInstance().readMany(AdministrativeUnit.class,new QueryExecutorArguments()
							.setQuery(QueryGetter.getInstance().get("AdministrativeUnit.read.view.01")));
				}
			})
		);
	}
}