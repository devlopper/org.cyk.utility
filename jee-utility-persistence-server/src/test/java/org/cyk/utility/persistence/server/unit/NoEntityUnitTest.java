package org.cyk.utility.persistence.server.unit;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.persistence.EntityManagerFactoryGetter;
import org.cyk.utility.persistence.EntityManagerGetter;

public class NoEntityUnitTest extends AbstractUnitTest {

	@Override
	protected String getUnitName() {
		return "noentity";
	}
	
	@org.junit.jupiter.api.Test
	public void entityManagerInjected() {
		assertThat(EntityManagerGetter.getInstance().get()).isNotNull();
	}
	
	@org.junit.jupiter.api.Test
	public void noEntity() {
		assertThat(CollectionHelper.getSize(EntityManagerFactoryGetter.getInstance().get().getMetamodel().getEntities())).isEqualTo(0);
	}
}