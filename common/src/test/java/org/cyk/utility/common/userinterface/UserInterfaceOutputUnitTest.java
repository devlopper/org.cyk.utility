package org.cyk.utility.common.userinterface;

import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.helper.FieldHelper;
import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.common.test.TestCase;
import org.cyk.utility.common.userinterface.output.Output;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class UserInterfaceOutputUnitTest extends AbstractUnitTest {
	private static final long serialVersionUID = -6691092648665798471L;
	
	static {
		StringHelper.ToStringMapping.Datasource.Adapter.Default.initialize();
	}
	
	@Test
	public void getOutput(){
		TestCase testCase = new TestCase();
		Output output = Output.get(null, new MyClass(), FieldHelper.getInstance().get(MyClass.class, "name")
				, null, null);
		testCase.assertNotNull(testCase);
		new MyClass().setXxx("",2);
		testCase.assertEquals("Nom", output.getLabel().getPropertiesMap().getValue());
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class MyClass extends AbstractBean {
		private static final long serialVersionUID = 1L;
		
		private String name,myOtherField;
		
		public MyClass setXxx(String string,Integer integer){
			
			return this;
		}
		
	}
	
}
