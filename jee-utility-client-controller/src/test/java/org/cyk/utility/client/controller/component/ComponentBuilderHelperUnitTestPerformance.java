package org.cyk.utility.client.controller.component;

import org.cyk.utility.client.controller.component.annotation.InputStringLineOne;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.action.SystemActionAdd;
import org.cyk.utility.test.weld.AbstractWeldUnitTestPerformance;
import org.junit.jupiter.api.Test;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class ComponentBuilderHelperUnitTestPerformance extends AbstractWeldUnitTestPerformance {
	private static final long serialVersionUID = -2849775962912387317L;
	
	//@Test
	public void getComponentBuilderClass_10() {
		ComponentBuilderHelper componentBuilderHelper = ComponentBuilderHelperImpl.getInstance(Boolean.TRUE);
		String[] strings = new String[] {"inputText"};
		execute("get component builder class",10,20,new Runnable() {
			@Override
			public void run() {
				componentBuilderHelper.getComponentBuilderClass(Model.class, null, null, strings, null, null, null);
			}
		});
	}
	
	//@Test
	public void getComponentBuilderClass_100() {
		ComponentBuilderHelper componentBuilderHelper = ComponentBuilderHelperImpl.getInstance(Boolean.TRUE);
		String[] strings = new String[] {"inputText"};
		execute("get component builder class",100000,1000,new Runnable() {
			@Override
			public void run() {
				componentBuilderHelper.getComponentBuilderClass(Model.class, null, null, strings, null, null, null);
			}
		});
	}
	
	@Test
	public void getComponentBuilder_10000() {
		Model model = new Model();
		SystemAction systemAction = __inject__(SystemActionAdd.class);
		ComponentBuilderHelper componentBuilderHelper = ComponentBuilderHelperImpl.getInstance(Boolean.TRUE);
		String[] strings = new String[] {"inputText"};
		Class<? extends ComponentBuilder<?>> klass = componentBuilderHelper.getComponentBuilderClass(Model.class, null, null, strings, null, null, null);
		execute("get component builder ",10000,100000,new Runnable() {
			@Override
			public void run() {
				//componentBuilderHelper.getComponentBuilder(klass, model, null, strings, null, null, null);
				__inject__(ComponentBuilderGetter.class).setClazz(klass).setObject(model).addFieldNameStrings("inputText").execute();
				
			}
		});
		
		execute("get component builder ",10000,100000,new Runnable() {
			@Override
			public void run() {
				componentBuilderHelper.getComponentBuilder(klass, model, null, strings, null, null, null);
				//__inject__(ComponentBuilderGetter.class).setClazz(klass).setObject(model).addFieldNameStrings("inputText").execute();
				
			}
		});
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Model {
		
		@InputStringLineOne private String inputText;
		
	}
	
}
