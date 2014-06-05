package org.cyk.utility.common;

import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.test.AbstractUnitTest;
import org.junit.Assert;

public class CommonUtilsUT extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;

	@ModelBean(crudStrategy=CrudStrategy.BUSINESS)
	public static class ClassA{};
	public static class ClassB extends ClassA{};
	
	@Override
	protected void _execute_() {
		super._execute_();
		Assert.assertNotNull(CommonUtils.getInstance().getAnnotation(ClassA.class, ModelBean.class));
		Assert.assertNotNull(CommonUtils.getInstance().getAnnotation(ClassB.class, ModelBean.class));
	}
	
	
}
