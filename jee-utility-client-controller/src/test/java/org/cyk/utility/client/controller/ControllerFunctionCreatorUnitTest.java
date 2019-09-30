package org.cyk.utility.client.controller;

import java.io.Serializable;

import org.cyk.utility.__kernel__.function.AbstractFunctionRunnableImpl;
import org.cyk.utility.__kernel__.function.FunctionRunnableMap;
import org.cyk.utility.client.controller.component.annotation.Commandable;
import org.cyk.utility.client.controller.component.annotation.InputStringLineMany;
import org.cyk.utility.client.controller.component.annotation.InputStringLineOne;
import org.cyk.utility.client.controller.component.annotation.OutputStringText;
import org.cyk.utility.client.controller.data.DataRepresentationClassGetter;
import org.cyk.utility.client.controller.data.DataRepresentationClassGetterImpl;
import org.cyk.utility.client.controller.data.DataTransferObjectClassGetter;
import org.cyk.utility.client.controller.data.DataTransferObjectClassGetterImpl;
import org.cyk.utility.client.controller.proxy.ProxyGetter;
import org.cyk.utility.client.controller.proxy.ProxyGetterImpl;
import org.cyk.utility.__kernel__.system.action.SystemAction;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.Test;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class ControllerFunctionCreatorUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = -2849775962912387317L;

	static {
		__inject__(FunctionRunnableMap.class).set(DataTransferObjectClassGetterImpl.class, DataTransferObjectClassGetterFunctionRunnableImpl.class,1);
		__inject__(FunctionRunnableMap.class).set(DataRepresentationClassGetterImpl.class, DataRepresentationClassGetterFunctionRunnableImpl.class,1);
		__inject__(FunctionRunnableMap.class).set(ProxyGetterImpl.class, ProxyGetterFunctionRunnableImpl.class,1);
	}
	
	@Test
	public void createOne() {
		ControllerFunctionCreator function = __inject__(ControllerFunctionCreator.class);
		function.getAction().setEntityClass(Model.class).getEntities(Boolean.TRUE).add(new Model());
		function.execute().getOutput();
		//assertionHelper.assertEquals(null, fun);
	}
	
	
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Model {
		
		@OutputStringText private String __title__;
		@InputStringLineOne private String inputText;
		@InputStringLineMany private String inputTextArea;
		
		@Commandable(systemActionClass=SystemAction.class)
		public void submit() {
			
		}
		
	}
	
	public static class ModelDto {
		
	}
	
	public static class ModelRepresentation {
		
	}
	
	public static class DataTransferObjectClassGetterFunctionRunnableImpl extends AbstractFunctionRunnableImpl<DataTransferObjectClassGetter> implements Serializable {
		private static final long serialVersionUID = 1L;
		
		public DataTransferObjectClassGetterFunctionRunnableImpl() {
			setRunnable(new Runnable() {
				@Override
				public void run() {
					if(Model.class.equals(getFunction().getDataClass()))
						setOutput(ModelDto.class);
				}
			});
		}
		
	}
	
	public static class DataRepresentationClassGetterFunctionRunnableImpl extends AbstractFunctionRunnableImpl<DataRepresentationClassGetter> implements Serializable {
		private static final long serialVersionUID = 1L;
		
		public DataRepresentationClassGetterFunctionRunnableImpl() {
			setRunnable(new Runnable() {
				@Override
				public void run() {
					if(Model.class.equals(getFunction().getDataClass()))
						setOutput(ModelRepresentation.class);
				}
			});
		}
		
	}
	
	public static class ProxyGetterFunctionRunnableImpl extends AbstractFunctionRunnableImpl<ProxyGetter> implements Serializable {
		private static final long serialVersionUID = 1L;
		
		public ProxyGetterFunctionRunnableImpl() {
			setRunnable(new Runnable() {
				@Override
				public void run() {
					if(ModelRepresentation.class.equals(getFunction().getClazz()))
						setOutput(new ModelRepresentation());
				}
			});
		}
		
	}
	
}
