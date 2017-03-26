package org.cyk.utility.common;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.cyk.utility.common.accessor.InstanceFieldSetter.OneDimensionObjectArray;
import org.cyk.utility.common.accessor.InstanceFieldSetter.TwoDimensionObjectArray;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

import lombok.Getter;
import lombok.Setter;

public class InstanceFieldSetterUT extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
	
	@Override
	protected void _execute_() {}
	
	@Test
	public void oneDimensionObjectArray(){
		A a = new A();
		OneDimensionObjectArray<A> setter = 
				new OneDimensionObjectArray.Adapter.Default<A>(new Object[]{"FOne","FTwo",3.3,"true","SubF11",3333.123,"false","Sub And Sub AA FF2","Hello"},A.class){
			
			private static final long serialVersionUID = 1L;

			@Override
			public Object getValue(Class<?> fieldType, Object value) {
				if(SubAEntity.class.equals(fieldType)){
					SubAEntity subAEntity = new SubAEntity();
					subAEntity.setFf1("Found From Datasource V2.0");
					return subAEntity;
				}
				return super.getValue(fieldType, value);
			}
			
		};
		setter.setInstance(a);
		setter.addFieldName("f1", 0).addFieldName("subA.ff3",5).addFieldName("subA.subAA.ff1", 7).addFieldName("f4", 3).addFieldName("subAEntity", 8);
		
		setter.execute();
		
		assertEquals("FOne", a.getF1());
	}
	
	@Test
	public void twoDimensionObjectArray(){
		
		OneDimensionObjectArray<A> setter = new OneDimensionObjectArray.Adapter.Default<A>(new Object[]{},A.class){
			private static final long serialVersionUID = 1L;

			@Override
			public Object getValue(Class<?> fieldType, Object value) {
				if(SubAEntity.class.equals(fieldType)){
					SubAEntity subAEntity = new SubAEntity();
					subAEntity.setFf1("Found From Datasource V5.1");
					return subAEntity;
				}
				return super.getValue(fieldType, value);
			}
		};
		
		setter.addFieldName("f1", 0).addFieldName("subA.ff3",5).addFieldName("subA.subAA.ff1", 7).addFieldName("f4", 3).addFieldName("subAEntity", 8);
		
		TwoDimensionObjectArray<A> twoDimensionObjectArray = new TwoDimensionObjectArray.Adapter.Default<A>(new Object[][]{
			{"FOne","FTwo",3.3,"true","SubF11",3333.123,"false","Sub And Sub AA FF2","Hello"}
			,{"FOne","FTwo",3.3,"true","SubF11",3333.123,"false","Sub And Sub AA FF2","Hello"}
			,{"FOne","FTwo",3.3,"true","SubF11",3333.123,"false","Sub And Sub AA FF2","Hello"}
		},setter){
			
			private static final long serialVersionUID = 1L;

			@Override
			public A getInstance(Object[] values) {
				if(values[0].toString().startsWith("CODE")){
					A a = new A();
					a.setF1("GEAT oh LOORDD!!!!!!!");
					return a;
				}					
				return super.getInstance(values);
			}
		};
		
		twoDimensionObjectArray.execute();
	
	}
	
	/**/
	
	@Getter @Setter
	public static class A {
		
		private String f1;
		private Integer f2;
		private double f3;
		private Boolean f4;
		private SubA subA = new SubA();
		private SubAEntity subAEntity;
		
		@Override
		public String toString() {
			return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
		}
	}
	
	@Getter @Setter
	public static class SubA {
		
		private String ff1;
		private Integer ff2;
		private double ff3;
		private Boolean ff4;
		
		private SubAA subAA = new SubAA();
		
		@Override
		public String toString() {
			return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
		}
		
	}
	
	@Getter @Setter
	public static class SubAA {
		
		private String ff1;
		private Integer ff2;
		private double ff3;
		private Boolean ff4;
		
		@Override
		public String toString() {
			return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
		}
		
	}
	
	@Getter @Setter
	public static class SubAEntity {
		
		private String ff1;
		private Integer ff2;
		private double ff3;
		private Boolean ff4;
		
		private SubAA subAA = new SubAA();
		
		@Override
		public String toString() {
			return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
		}
		
	}
	
	
}
