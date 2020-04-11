package org.cyk.utility.__kernel__.user.interface_;

import javax.persistence.Persistence;

import org.cyk.utility.__kernel__.persistence.EntityManagerFactoryGetterImpl;
import org.cyk.utility.__kernel__.persistence.query.QueryHelper;
import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class UserInterfaceUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenBefore__() {
		super.__listenBefore__();
		EntityManagerFactoryGetterImpl.ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("user_interface");
		QueryHelper.getQueries().setIsRegisterableToEntityManager(Boolean.TRUE);
	}
	
	@Override
	protected void __listenAfter__() {
		super.__listenAfter__();
		QueryHelper.clear();
	}
	
	@Test
	public void bean_validation(){
		//EntityCreator.getInstance().createOneInTransaction(new Employee());
		//assertThat(TimeHelper.formatLocalDateTimeFromString("2019-12-07T09:18:41.585586", "dd/MM/yyyy")).isEqualTo("07/12/2019");
	}
	
}
