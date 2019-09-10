package org.cyk.utility.field;

import java.io.Serializable;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.cyk.utility.test.weld.AbstractWeldUnitTestBenchmark;
import org.cyk.utility.value.ValueUsageType;
import org.junit.jupiter.api.Test;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class FieldBenchmark extends AbstractWeldUnitTestBenchmark implements Serializable {
	private static final long serialVersionUID = 1L;

	@Test
	public void buildFieldName(){
		FieldHelper fieldHelper = FieldHelperImpl.getInstance(Boolean.TRUE);
		java.lang.Class<?> klass = Class.class;
		FieldName fieldName = FieldName.IDENTIFIER;
		ValueUsageType valueUsageType = ValueUsageType.SYSTEM;
		
		execute(new Jobs().setName("Build field name").setNumberOfRound(1000000)
			.add("FieldNameGetter",new Runnable() {
			@Override
			public void run() {
				__inject__(FieldNameGetter.class).execute(klass, fieldName,valueUsageType);
			}
		}).add("FieldHelper.buildFieldName", new Runnable() {
			@Override
			public void run() {
				fieldHelper.buildFieldName(klass, fieldName,valueUsageType);
			}
		}).add("FieldHelperImpl.__buildFieldName__", new Runnable() {
			@Override
			public void run() {
				FieldHelperImpl.__getName__(klass, fieldName,valueUsageType);
			}
		})
			);
	}
	
	@Test
	public void getFieldByName(){
		FieldHelper fieldHelper = FieldHelperImpl.getInstance(Boolean.TRUE);
		java.lang.Class<?> klass = Class.class;
		String fieldName = "f01";
		
		execute(new Jobs().setName("Get field by name").setNumberOfRound(1000000)
			.add("FieldsGetter",new Runnable() {
			@Override
			public void run() {
				__inject__(FieldsGetter.class).execute(klass, fieldName);
			}
		}).add("FieldHelper.getFieldByName", new Runnable() {
			@Override
			public void run() {
				fieldHelper.getFieldByName(klass, fieldName);
			}
		}).add("FieldHelperImpl.__getFieldByName__", new Runnable() {
			@Override
			public void run() {
				FieldHelperImpl.__getByName__(klass, fieldName);
			}
		}).add("Apache FieldUtils.getField", new Runnable() {
			@Override
			public void run() {
				FieldUtils.getField(klass, fieldName, Boolean.TRUE);
			}
		}));
	}
	
	@Test
	public void readFieldValue(){
		FieldHelper fieldHelper = FieldHelperImpl.getInstance(Boolean.TRUE);
		Class object = new Class().setF01("f01");
		String fieldName = "f01";
		
		execute(new Jobs().setName("Read field value").setNumberOfRound(1000000)
			.add("FieldValueGetter",new Runnable() {
			@Override
			public void run() {
				__inject__(FieldValueGetter.class).setObject(object).setFieldName(fieldName).execute();
			}
		}).add("FieldHelper.readFieldValue", new Runnable() {
			@Override
			public void run() {
				fieldHelper.readFieldValue(object, fieldName);
			}
		}).add("FieldHelperImpl.__readFieldValue__", new Runnable() {
			@Override
			public void run() {
				FieldHelperImpl.__read__(object, fieldName);
			}
		}).add("Apache FieldUtils.readField", new Runnable() {
			@Override
			public void run() {
				try {
					FieldUtils.readField(object, fieldName, Boolean.TRUE);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}));
	}
	
	@Test
	public void writeFieldValue(){
		FieldHelper fieldHelper = FieldHelperImpl.getInstance(Boolean.TRUE);
		Class object = new Class();
		String fieldName = "f01";
		
		execute(new Jobs().setName("Write field value").setNumberOfRound(1000000)
			.add("FieldValueSetter",new Runnable() {
			@Override
			public void run() {
				__inject__(FieldValueSetter.class).execute(object, fieldName, "Hello");
			}
		}).add("FieldHelper.writeFieldValue", new Runnable() {
			@Override
			public void run() {
				fieldHelper.writeFieldValue(object, fieldName,"Hello");
			}
		}).add("FieldHelperImpl.__writeFieldValue__", new Runnable() {
			@Override
			public void run() {
				FieldHelperImpl.__write__(object, fieldName,"Hello");
			}
		}).add("Apache FieldUtils.writeField", new Runnable() {
			@Override
			public void run() {
				try {
					FieldUtils.writeField(object, fieldName,"Hello", Boolean.TRUE);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}));
	}

	/**/
	
	@Getter @Setter @Accessors(chain=true)
	private static class Class implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private String f01;
		private String identifier,code;
		
	}
}
