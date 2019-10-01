package org.cyk.utility.client.controller.component.layout;

import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.field.FieldName;
import org.cyk.utility.__kernel__.function.FunctionRunnableMap;
import org.cyk.utility.__kernel__.value.ValueUsageType;
import org.cyk.utility.client.controller.ApplicationScopeLifeCycleListener;
import org.cyk.utility.css.StyleClassBuilderWidthImpl;
import org.cyk.utility.test.weld.AbstractWeldUnitTestBenchmark;
import org.junit.jupiter.api.Test;

public class LayoutItemBuilderUnitTestBenchmark extends AbstractWeldUnitTestBenchmark {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenBefore__() {
		super.__listenBefore__();
		__inject__(ApplicationScopeLifeCycleListener.class).__initialize__(null);
		__inject__(FunctionRunnableMap.class).set(StyleClassBuilderWidthImpl.class, StyleClassBuilderWidthCssPrimefacesGridFunctionRunnableImpl.class);
	}
	
	@Test
	public void buildFieldName(){
		java.lang.Class<?> klass = Class.class;
		FieldName fieldName = FieldName.IDENTIFIER;
		ValueUsageType valueUsageType = ValueUsageType.SYSTEM;
		
		execute(new Jobs().setName("Build layout item").setNumberOfRound(100)
			.add("LayoutItemBuilder",new Runnable() {
			@Override
			public void run() {
				__inject__(LayoutItemBuilder.class).execute().getOutput();
			}
		}).add("FieldHelper.buildFieldName", new Runnable() {
			@Override
			public void run() {
				FieldHelper.getName(klass, fieldName,valueUsageType);
			}
		})
			);
	}
}
