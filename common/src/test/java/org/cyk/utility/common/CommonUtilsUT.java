package org.cyk.utility.common;

import org.cyk.utility.common.annotation.Model;
import org.cyk.utility.common.annotation.Model.CrudStrategy;
import org.cyk.utility.common.test.AbstractUnitTest;
import org.junit.Assert;

public class CommonUtilsUT extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;

	@Model(crudStrategy=CrudStrategy.BUSINESS)
	public static class ClassA{};
	public static class ClassB extends ClassA{};
	
	@Override
	protected void _execute_() {
		super._execute_();
		Assert.assertNotNull(CommonUtils.getInstance().getAnnotation(ClassA.class, Model.class));
		Assert.assertNotNull(CommonUtils.getInstance().getAnnotation(ClassB.class, Model.class));
	}
	
	
}
