package org.cyk.utility.system;

import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.system.action.SystemActionCreate;
import org.cyk.utility.system.action.SystemActionRelatedClassesNamesGetter;
import org.cyk.utility.system.action.SystemActionSelect;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class SystemActionRelatedClassesNamesGetterUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void get_create_classIsData() {
		org.assertj.core.api.Assertions.assertThat(__inject__(SystemActionRelatedClassesNamesGetter.class).setEntityClass(MyEntity.class).setSystemActionClass(SystemActionCreate.class)
				.setExtendedInterface(Data.class).setDefaultSuffix("Default").execute().getOutput().get().stream().map(x->StringUtils.substringAfter(x, "$"))
				.collect(Collectors.toList())).contains("MyEntityCreate","MyEntityEdit","MyEntity","DataCreateDefault","DataEditDefault","DataDefault");
	}
	
	@Test
	public void get_select_classIsData() {
		org.assertj.core.api.Assertions.assertThat(__inject__(SystemActionRelatedClassesNamesGetter.class).setEntityClass(MyEntity.class).setSystemActionClass(SystemActionSelect.class)
				.setExtendedInterface(Data.class).setDefaultSuffix("Default").execute().getOutput().get().stream().map(x->StringUtils.substringAfter(x, "$"))
				.collect(Collectors.toList())).contains("MyEntitySelect","MyEntity","DataSelectDefault","DataDefault");
	}
	
	@Test
	public void get_select_classIsData_windowBuilder() {
		org.assertj.core.api.Assertions.assertThat(__inject__(SystemActionRelatedClassesNamesGetter.class).setEntityClass(MyEntity.class).setSystemActionClass(SystemActionSelect.class)
				.setExtendedInterface(WindowBuilder.class).setNameSuffix("WindowBuilder").setDefaultSuffix("DataDefault").execute().getOutput().get().stream().map(x->StringUtils.substringAfter(x, "$"))
				.collect(Collectors.toList())).contains("MyEntitySelectWindowBuilder","MyEntityWindowBuilder","WindowBuilderSelectDataDefault","WindowBuilderDataDefault");
	}
	
	/**/
	
	public static interface Data {
		
	}
	
	public static interface MyEntity extends Data {
		
	}
	
	public static interface WindowBuilder {
		
	}
}
