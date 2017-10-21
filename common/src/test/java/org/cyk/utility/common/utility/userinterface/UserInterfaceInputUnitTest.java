package org.cyk.utility.common.utility.userinterface;

import java.util.Date;

import org.cyk.utility.common.helper.FieldHelper;
import org.cyk.utility.common.userinterface.input.Input;
import org.cyk.utility.common.userinterface.input.InputText;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class UserInterfaceInputUnitTest extends AbstractUnitTest {
	private static final long serialVersionUID = -6691092648665798471L;
	
	@Test
	public void inputText(){
		Model model = new Model();
		InputText input = new InputText();
		assertInputText(input, model, "string1","myVal", new Values(null, null, null), new Values(null, null, null), new Values(null, "myVal", null), new Values("myVal", "myVal", null));
		
		model = new Model().setString1("is01");
		input = new InputText();
		assertInputText(input, model, "string1","myVal", new Values("is01", null, null), new Values("is01", "is01", "is01"), new Values("is01", "myVal", "is01"), new Values("myVal", "myVal", "is01"));
		
	}
	
	@Test
	public void inputTextFromField(){
		assertNull(Input.get(new Model(), "string1"));
		assertNotNull(Input.get(new Model(), "string2"));
		assertNotNull(Input.get(new Model(), "string3"));
		
		Model model = new Model();
		InputText input = (InputText) Input.get(model, "string3");
		assertInputText(input, model, "string3","myVal", new Values(null, null, null), new Values(null, null, null), new Values(null, "myVal", null), new Values("myVal", "myVal", null));
		
		model = new Model().setString3("is01");
		input = (InputText) Input.get(model, "string3");
		assertBuiltInput(input, "##__field__.string.3##", new Values("is01", "is01", "is01"));
		assertInputText(input, model, "string3","myVal", new Values("is01", "is01", "is01"), new Values("is01", "is01", "is01"), new Values("is01", "myVal", "is01"), new Values("myVal", "myVal", "is01"));
		
	}
	
	/**/
	
	private void assertBuiltInput(Input<?> input,String label,Values expectedValues){
		assertEquals("label is not correct",label, input.getLabel() == null ? null : input.getLabel().getPropertiesMap().getValue());
		assertValues(input, input.getObject(), input.getField().getName(), expectedValues);
	}
	
	private <T> void assertInput(Class<T> valueClass,Input<T> input,Model model,String fieldName,T valueToSet,Values expectedValuesRead,Values expectedValuesSetField,Values expectedValuesSetValue,Values expectedValuesWrite){
		assertValues(input, model, fieldName, expectedValuesRead);
		
		input.setField(model, fieldName);
		assertValues(input, model, fieldName, expectedValuesSetField);
		
		input.setValue(valueToSet);
		assertValues(input, model, fieldName, expectedValuesSetValue);
		
		input.write();
		assertValues(input, model, fieldName, expectedValuesWrite);
	}
	
	private void assertInputText(InputText input,Model model,String fieldName,String valueToSet,Values expectedValuesRead,Values expectedValuesSetField,Values expectedValuesSetValue,Values expectedValuesWrite){
		assertInput(String.class, input, model, fieldName, valueToSet, expectedValuesRead, expectedValuesSetField, expectedValuesSetValue, expectedValuesWrite);
	}
	
	private void assertValues(Input<?> input,Object model,String fieldName,Values expectedValues){
		assertEquals("model value not correct",expectedValues.getModelValue(), FieldHelper.getInstance().read(model, fieldName));
		assertEquals("input value not correct",expectedValues.getInputValue(), input.getValue());
		assertEquals("input initial value not correct",expectedValues.getInputInitialValue(), input.getInitialValue());
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Model {
	
		private String string1;
		@org.cyk.utility.common.annotation.user.interfaces.Input private String string2;
		@org.cyk.utility.common.annotation.user.interfaces.Input @org.cyk.utility.common.annotation.user.interfaces.InputText private String string3;
		private Date date1,date2,date3;
		
	}
	
	@Getter @Setter @Accessors(chain=true) @AllArgsConstructor
	public static class Values {
		
		private Object modelValue,inputValue,inputInitialValue;
		
	}
}
