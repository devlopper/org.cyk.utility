package org.cyk.utility.__kernel__;

import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
import org.jboss.weld.bootstrap.spi.BeanDiscoveryMode;
import org.jboss.weld.environment.se.Weld;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public abstract class AbstractBeanDiscoveryModeUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Override
	protected Weld __getWeld__() {
		return super.__getWeld__().setBeanDiscoveryMode(__getBeanDiscoveryMode__());
	}
	
	protected abstract BeanDiscoveryMode __getBeanDiscoveryMode__();
	
	@Test
	public void inject_INotAmbiguous_isNotNull(){
		Assert.assertTrue(__inject__(INotAmbiguous.class) instanceof C_NotAmbiguous_Bean02);
	}
	
	/**/
	
	public static interface IAmbiguous {
		
	}
	
	public static class C_Ambiguous_Bean01 implements IAmbiguous {
		
	}
	
	public static class C_Ambiguous_Bean02 implements IAmbiguous {
		
	}
	
	public static interface INotAmbiguous {
		
	}
	
	public static class C_NotAmbiguous_Bean01 implements INotAmbiguous {
		public C_NotAmbiguous_Bean01(Integer integer) {}
	}
	
	public static class C_NotAmbiguous_Bean02 implements INotAmbiguous {
		
	}
}
