package org.cyk.utility.__kernel__.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Persistence;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.__entities__.TestedEntityParent;
import org.cyk.utility.__kernel__.__entities__.TestedEntityParentData;
import org.cyk.utility.__kernel__.__entities__.TestedEntityParentDto;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.persistence.EntityManagerFactoryGetterImpl;
import org.cyk.utility.__kernel__.persistence.query.EntityCreator;
import org.cyk.utility.__kernel__.persistence.query.Query;
import org.cyk.utility.__kernel__.persistence.query.QueryExecutorArgumentsDto;
import org.cyk.utility.__kernel__.persistence.query.QueryHelper;
import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
import org.cyk.utility.__kernel__.throwable.RuntimeException;
import org.cyk.utility.__kernel__.user.interface_.message.MessageRenderer;
import org.junit.jupiter.api.Test;

public class ControllerUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenBefore__() {
		super.__listenBefore__();
		DependencyInjection.setQualifierClass(MessageRenderer.class, org.cyk.utility.__kernel__.annotation.Test.Class.class);
		EntityManagerFactoryGetterImpl.ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("pu");
		RepresentationEntityClassGetterImpl.MAP.put(TestedEntityParentData.class, TestedEntityParentDto.class);
		QueryHelper.getQueries().setIsRegisterableToEntityManager(Boolean.TRUE);
		QueryHelper.scan(List.of(getClass().getPackage()));	
		QueryHelper.addQueries(Query.buildSelectBySystemIdentifiers(TestedEntityParent.class, "SELECT t FROM TestedEntityParent t WHERE t.identifier IN :identifiers"));
		QueryHelper.addQueries(Query.build(TestedEntityParent.class,"read.1", "SELECT new org.cyk.utility.__kernel__.__entities__.TestedEntityParent(t.identifier,t.code,t.name) FROM TestedEntityParent t"));
		Arguments.IS_REPRESENTATION_PROXYABLE = Boolean.FALSE;
	}
	
	@Override
	protected void __listenAfter__() {
		super.__listenAfter__();
		QueryHelper.clear();
		EntityReader.INSTANCE.set(null);
		org.cyk.utility.__kernel__.representation.EntityReader.INSTANCE.set(null);
		org.cyk.utility.__kernel__.persistence.query.EntityReader.INSTANCE.set(null);
		EntityCreator.INSTANCE.set(null);
	}
	
	/* read all*/
	
	@Test
	public void testedEntityParent_withoutTuple_read_all(){		
		__assertRead__(EntityReader.getInstance().readMany(TestedEntityParentData.class));
	}
	
	@Test
	public void testedEntityParent_withTuple_one_read_all(){		
		EntityCreator.getInstance().createOneInTransaction(new TestedEntityParent("1","1","1").setLazy("lazy").setEager("eager"));
		__assertRead__(EntityReader.getInstance().readMany(TestedEntityParentData.class), "1");
	}
	
	@Test
	public void testedEntityParent_withTuple_many_read_all(){		
		EntityCreator.getInstance().createManyInTransaction(new TestedEntityParent("1","1","1"),new TestedEntityParent("2","2","1"));		
		__assertRead__(EntityReader.getInstance().readMany(TestedEntityParentData.class), "1","2");
	}
	
	@Test
	public void testedEntityParent_withTuple_projection(){		
		EntityCreator.getInstance().createOneInTransaction(new TestedEntityParent("1","2","3").setLazy("lazy").setEager("eager"));
		Collection<TestedEntityParentData> testedEntityParents = EntityReader.getInstance().readMany(TestedEntityParentData.class,new Arguments()
				.setRepresentationArguments(new org.cyk.utility.__kernel__.representation.Arguments()
						.setQueryExecutorArguments(new QueryExecutorArgumentsDto().setQueryIdentifier("TestedEntityParent.read.1"))));
		assertThat(testedEntityParents.stream().map(TestedEntityParentData::getIdentifier).collect(Collectors.toList())).containsExactly(new String[] {"1"});
		assertThat(testedEntityParents.stream().map(TestedEntityParentData::getCode).collect(Collectors.toList())).containsExactly(new String[] {"2"});
		assertThat(testedEntityParents.stream().map(TestedEntityParentData::getName).collect(Collectors.toList())).containsExactly(new String[] {"3"});
		assertThat(testedEntityParents.stream().map(TestedEntityParentData::getLazy).collect(Collectors.toList())).containsExactly(new String[] {null});
		assertThat(testedEntityParents.stream().map(TestedEntityParentData::getEarger).collect(Collectors.toList())).containsExactly(new String[] {null});
	}
	
	/* read by system identifier */
	
	@Test
	public void testedEntityParent_withoutTuple_read_bySystemIdentifier(){
		EntityCreator.getInstance().createManyInTransaction(new TestedEntityParent("1","1","1"),new TestedEntityParent("2","2","1"));		
		__assertRead__(EntityReader.getInstance().readMany(TestedEntityParentData.class,new Arguments().setRepresentationArguments(new org.cyk.utility.__kernel__.representation
				.Arguments().setQueryExecutorArguments(new QueryExecutorArgumentsDto().setQueryIdentifier("TestedEntityParent.readBySystemIdentifiers")
						.addFilterField("identifiers",List.of("1"))))), "1");
	}
	
	/* read by business identifier */
	
	/* read by filter */
	
	/* exception */
	
	@Test
	public void notYetRegistered(){
		assertThrows(RuntimeException.class, () -> {
			EntityReader.getInstance().readMany(TestedEntityParentData.class,new Arguments().setRepresentationArguments(new org.cyk.utility.__kernel__.representation
					.Arguments().setQueryExecutorArguments(new QueryExecutorArgumentsDto().setQueryIdentifier("TestedEntityParent.xxx")
							.addFilterField("identifiers",List.of("1")))));
		});
	}
	
	/**/
	
	private <T> void __assertRead__(Collection<T> entities,Object...expectedSystemIdentifiers) {
		if(ArrayHelper.isEmpty(expectedSystemIdentifiers)) {
			assertThat(entities).isNull();
			return;
		}
		assertThat(entities).isNotNull();
		assertThat(entities.stream().map(entity -> FieldHelper.readSystemIdentifier(entity)).collect(Collectors.toList())).containsExactly(expectedSystemIdentifiers);
	}
		
}