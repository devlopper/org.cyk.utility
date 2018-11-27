package org.cyk.utility.client.controller.data;

import java.io.Serializable;

import org.cyk.utility.__kernel__.function.AbstractFunctionRunnableImpl;
import org.cyk.utility.__kernel__.function.FunctionRunnableMap;
import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

public class DataTransferObjectClassGetterUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = -2849775962912387317L;
	
	static {
		__inject__(FunctionRunnableMap.class).set(DataTransferObjectClassGetterImpl.class, DataTransferObjectClassGetterFunctionRunnableImpl.class,Boolean.TRUE);
	}
	
	@Test
	public void isNull() {
		assertionHelper.assertEquals(null, __inject__(DataTransferObjectClassGetter.class).execute().getOutput());
	}
	
	@Test
	public void isMyDataDto() {
		assertionHelper.assertEquals(MyDataDto.class, __inject__(DataTransferObjectClassGetter.class).setDataClass(MyData.class).execute().getOutput());
	}
	
	/**/
	
	public static class DataTransferObjectClassGetterFunctionRunnableImpl extends AbstractFunctionRunnableImpl<DataTransferObjectClassGetter> implements Serializable {
		private static final long serialVersionUID = 1L;
		
		public DataTransferObjectClassGetterFunctionRunnableImpl() {
			setRunnable(new Runnable() {
				@Override
				public void run() {
					Class<?> dataClass = getFunction().getDataClass();
					if(MyData.class.equals(dataClass))
						setOutput(MyDataDto.class);
				}
			});
		}
	}
	
	public static interface MyData {
		
	}
	
	public static interface MyDataDto {
		
	}
}
