package org.cyk.utility.__kernel__.string;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class StringTemplateIdentifierGetterUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void getServerPersistenceEntitySystemIdentifiable(){	
		assertThat(StringTemplateIdentifierGetter.getInstance().getServerPersistenceEntitySystemIdentifiable()).isEqualTo("project/system/server/persistence/entities/systemidentifiable.ftl");
	}
	
	@Test
	public void getServerPersistenceEntityBusinessIdentifiable(){	
		assertThat(StringTemplateIdentifierGetter.getInstance().getServerPersistenceEntityBusinessIdentifiable()).isEqualTo("project/system/server/persistence/entities/businessidentifiable.ftl");
	}
	
	@Test
	public void getServerPersistenceEntityNamable(){	
		assertThat(StringTemplateIdentifierGetter.getInstance().getServerPersistenceEntityNamable()).isEqualTo("project/system/server/persistence/entities/namable.ftl");
	}
	
	@Test
	public void getServerPersistenceApi(){	
		assertThat(StringTemplateIdentifierGetter.getInstance().getServerPersistenceApi()).isEqualTo("project/system/server/persistence/api/api.ftl");
	}
	
	@Test
	public void getServerPersistenceImpl(){	
		assertThat(StringTemplateIdentifierGetter.getInstance().getServerPersistenceImpl()).isEqualTo("project/system/server/persistence/impl/impl.ftl");
	}
	
	@Test
	public void getServerBusinessApi(){	
		assertThat(StringTemplateIdentifierGetter.getInstance().getServerBusinessApi()).isEqualTo("project/system/server/business/api/api.ftl");
	}
	
	@Test
	public void getServerBusinessImpl(){	
		assertThat(StringTemplateIdentifierGetter.getInstance().getServerBusinessImpl()).isEqualTo("project/system/server/business/impl/impl.ftl");
	}
	
	@Test
	public void getServerRepresentationEntitySystemIdentifiable(){	
		assertThat(StringTemplateIdentifierGetter.getInstance().getServerRepresentationEntitySystemIdentifiable()).isEqualTo("project/system/server/representation/entities/systemidentifiable.ftl");
	}
	
	@Test
	public void getServerRepresentationEntityBusinessIdentifiable(){	
		assertThat(StringTemplateIdentifierGetter.getInstance().getServerRepresentationEntityBusinessIdentifiable()).isEqualTo("project/system/server/representation/entities/businessidentifiable.ftl");
	}
	
	@Test
	public void getServerRepresentationEntityNamable(){	
		assertThat(StringTemplateIdentifierGetter.getInstance().getServerRepresentationEntityNamable()).isEqualTo("project/system/server/representation/entities/namable.ftl");
	}
	
	@Test
	public void getServerRepresentationEntityMapper(){	
		assertThat(StringTemplateIdentifierGetter.getInstance().getServerRepresentationEntityMapper()).isEqualTo("project/system/server/representation/entities/mapper.ftl");
	}
	
	@Test
	public void getServerRepresentationApi(){	
		assertThat(StringTemplateIdentifierGetter.getInstance().getServerRepresentationApi()).isEqualTo("project/system/server/representation/api/api.ftl");
	}
	
	@Test
	public void getServerRepresentationImpl(){	
		assertThat(StringTemplateIdentifierGetter.getInstance().getServerRepresentationImpl()).isEqualTo("project/system/server/representation/impl/impl.ftl");
	}
	
	/* Controller */
	
	@Test
	public void getClientControllerEntitySystemIdentifiable(){	
		assertThat(StringTemplateIdentifierGetter.getInstance().getClientControllerEntitySystemIdentifiable()).isEqualTo("project/system/client/controller/entities/systemidentifiable.ftl");
	}
	
	@Test
	public void getClientControllerEntityBusinessIdentifiable(){	
		assertThat(StringTemplateIdentifierGetter.getInstance().getClientControllerEntityBusinessIdentifiable()).isEqualTo("project/system/client/controller/entities/businessidentifiable.ftl");
	}
	
	@Test
	public void getClientControllerEntityNamable(){	
		assertThat(StringTemplateIdentifierGetter.getInstance().getClientControllerEntityNamable()).isEqualTo("project/system/client/controller/entities/namable.ftl");
	}
	
	@Test
	public void getClientControllerEntityMapper(){	
		assertThat(StringTemplateIdentifierGetter.getInstance().getClientControllerEntityMapper()).isEqualTo("project/system/client/controller/entities/mapper.ftl");
	}
	
	@Test
	public void getClientControllerApi(){	
		assertThat(StringTemplateIdentifierGetter.getInstance().getClientControllerApi()).isEqualTo("project/system/client/controller/api/api.ftl");
	}
	
	@Test
	public void getClientControllerImpl(){	
		assertThat(StringTemplateIdentifierGetter.getInstance().getClientControllerImpl()).isEqualTo("project/system/client/controller/impl/impl.ftl");
	}
	
}
