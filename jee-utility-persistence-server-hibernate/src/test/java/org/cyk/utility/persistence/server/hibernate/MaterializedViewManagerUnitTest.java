package org.cyk.utility.persistence.server.hibernate;

import static org.assertj.core.api.Assertions.assertThat;

import javax.inject.Inject;

import org.cyk.utility.persistence.EntityManagerFactoryGetterImpl;
import org.cyk.utility.persistence.server.hibernate.annotation.Hibernate;
import org.cyk.utility.persistence.server.view.MaterializedViewManager;
import org.junit.jupiter.api.Test;

public class MaterializedViewManagerUnitTest extends AbstractUnitTest {

	@Inject @Hibernate private MaterializedViewManager materializedViewManager;
	
	@Test
	public void actualize() {
		Integer count = DataType.AP_ACTUALIZE_MV_CALL_COUNT;
		materializedViewManager.actualize(DataType.class,EntityManagerFactoryGetterImpl.ENTITY_MANAGER_FACTORY.createEntityManager());
		assertThat(DataType.AP_ACTUALIZE_MV_CALL_COUNT).isEqualTo(count+1);
	}
	
	@Test
	public void actualize_same_sequential() {
		Integer count = DataType.AP_ACTUALIZE_MV_CALL_COUNT;
		materializedViewManager.actualize(DataType.class,EntityManagerFactoryGetterImpl.ENTITY_MANAGER_FACTORY.createEntityManager());
		assertThat(DataType.AP_ACTUALIZE_MV_CALL_COUNT).isEqualTo(count+1);
		materializedViewManager.actualize(DataType.class,EntityManagerFactoryGetterImpl.ENTITY_MANAGER_FACTORY.createEntityManager());
		assertThat(DataType.AP_ACTUALIZE_MV_CALL_COUNT).isEqualTo(count+2);
	}
	
	@Test
	public void actualize_same_asynchron() {
		Integer count1 = DataType.AP_ACTUALIZE_MV_CALL_COUNT;
		Integer count2 = LongRunActualizationDataType.AP_ACTUALIZE_MV_CALL_COUNT;
		Thread thread1 = new Thread(new Runnable() {
			@Override
			public void run() {
				materializedViewManager.actualize(LongRunActualizationDataType.class,EntityManagerFactoryGetterImpl.ENTITY_MANAGER_FACTORY.createEntityManager());
			}
		});
		
		Thread thread2 = new Thread(new Runnable() {
			@Override
			public void run() {
				materializedViewManager.actualize(DataType.class,EntityManagerFactoryGetterImpl.ENTITY_MANAGER_FACTORY.createEntityManager());
			}
		});
		
		Thread thread3 = new Thread(new Runnable() {
			@Override
			public void run() {
				materializedViewManager.actualize(LongRunActualizationDataType.class,EntityManagerFactoryGetterImpl.ENTITY_MANAGER_FACTORY.createEntityManager());
			}
		});
		
		thread1.start();
		thread2.start();
		thread3.start();
		
		try {
			thread2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		assertThat(DataType.AP_ACTUALIZE_MV_CALL_COUNT).isEqualTo(count1+1);
		assertThat(LongRunActualizationDataType.AP_ACTUALIZE_MV_CALL_COUNT).isEqualTo(count2+1);
	}
}