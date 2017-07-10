package org.cyk.utility.common;

import javax.inject.Named;
import javax.inject.Singleton;

import org.cyk.utility.common.helper.AbstractHelper;
import org.cyk.utility.common.helper.ThrowableHelper;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

public class ThrowableHelperUnitTest extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
	
	@Test
	public void executeThrowableBuilder(){
		ThrowableHelper.Throwable throwable = new ThrowableHelper.Throwable.Builder.Adapter.Default<RuntimeException>(RuntimeException.class)
				.addManyParameters("message1","message2","another message again","one more...").execute();
		System.out.println(throwable);
		/*assertEquals("java.lang.Throwable: message1"+Constant.LINE_DELIMITER
				+ "message2"+Constant.LINE_DELIMITER
				+ "another message again"
				+ "one more...", new ThrowableHelper.Throwable.Builder.Adapter.Default()
				.addManyParameters("message1","message2","another message again","one more...").execute());
		*/
	}
	
	/**/
	
	public static class MyHelper extends AbstractHelper {
		private static final long serialVersionUID = 1L;
		
	}
	
	@Singleton
	public static class MyAnnotatedHelper extends AbstractHelper {
		private static final long serialVersionUID = 1L;
	}
	
	@Named
	public static class MyAnnotatedHelper2 extends AbstractHelper {
		private static final long serialVersionUID = 1L;
	}
	
}
