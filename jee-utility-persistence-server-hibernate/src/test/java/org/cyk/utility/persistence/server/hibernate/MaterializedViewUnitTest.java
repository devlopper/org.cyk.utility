package org.cyk.utility.persistence.server.hibernate;

import static org.assertj.core.api.Assertions.assertThat;

import javax.inject.Inject;

import org.cyk.utility.__kernel__.time.TimeHelper;
import org.cyk.utility.persistence.EntityManagerFactoryGetterImpl;
import org.cyk.utility.persistence.server.view.MaterializedViewActualizer;
import org.cyk.utility.persistence.server.view.MaterializedViewManager;
import org.cyk.utility.persistence.server.view.MaterializedViewManager.ActualizeArguments;
import org.junit.jupiter.api.Test;

public class MaterializedViewUnitTest extends AbstractUnitTest {

	@Inject private MaterializedViewActualizer materializedViewActualizer;
	
	@Test
	public void actualize() {
		Integer count = DataType.AP_ACTUALIZE_MV_CALL_COUNT;
		materializedViewActualizer.execute(EntityManagerFactoryGetterImpl.ENTITY_MANAGER_FACTORY.createEntityManager(),DataType.class);
		assertThat(DataType.AP_ACTUALIZE_MV_CALL_COUNT).isEqualTo(count+1);
	}
	
	@Test
	public void actualize_same_sequential() {
		Integer count = DataType.AP_ACTUALIZE_MV_CALL_COUNT;
		materializedViewActualizer.execute(EntityManagerFactoryGetterImpl.ENTITY_MANAGER_FACTORY.createEntityManager(),DataType.class);
		assertThat(DataType.AP_ACTUALIZE_MV_CALL_COUNT).isEqualTo(count+1);
		materializedViewActualizer.execute(EntityManagerFactoryGetterImpl.ENTITY_MANAGER_FACTORY.createEntityManager(),DataType.class);
		assertThat(DataType.AP_ACTUALIZE_MV_CALL_COUNT).isEqualTo(count+2);
	}
	
	@Test
	public void actualize_same_asynchron() {
		Integer count1 = DataType.AP_ACTUALIZE_MV_CALL_COUNT;
		Integer count2 = LongRunActualizationDataType.AP_ACTUALIZE_MV_CALL_COUNT;
		Thread thread1 = new Thread(new Runnable() {
			@Override
			public void run() {
				materializedViewActualizer.execute(EntityManagerFactoryGetterImpl.ENTITY_MANAGER_FACTORY.createEntityManager(),LongRunActualizationDataType.class);
			}
		});
		
		Thread thread2 = new Thread(new Runnable() {
			@Override
			public void run() {
				materializedViewActualizer.execute(EntityManagerFactoryGetterImpl.ENTITY_MANAGER_FACTORY.createEntityManager(),DataType.class);
			}
		});
		
		Thread thread3 = new Thread(new Runnable() {
			@Override
			public void run() {
				materializedViewActualizer.execute(EntityManagerFactoryGetterImpl.ENTITY_MANAGER_FACTORY.createEntityManager(),LongRunActualizationDataType.class);
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
	
	@Test
	public void actualize_same_asynchron_blocking() {
		Integer count = VeryLongRunActualizationDataType.AP_ACTUALIZE_MV_CALL_COUNT;
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				materializedViewActualizer.execute(EntityManagerFactoryGetterImpl.ENTITY_MANAGER_FACTORY.createEntityManager(),VeryLongRunActualizationDataType.class);
			}
		});
		thread.start();
		TimeHelper.pause(1000l * 1);
		materializedViewActualizer.execute((ActualizeArguments) new MaterializedViewManager.ActualizeArguments().addClasses(VeryLongRunActualizationDataType.class).setEntityManager(EntityManagerFactoryGetterImpl.ENTITY_MANAGER_FACTORY.createEntityManager())
				.setBlockableIfRunning(Boolean.TRUE));
		assertThat(VeryLongRunActualizationDataType.AP_ACTUALIZE_MV_CALL_COUNT).isEqualTo(count+1);
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertThat(VeryLongRunActualizationDataType.AP_ACTUALIZE_MV_CALL_COUNT).isEqualTo(count+1);
	}
}