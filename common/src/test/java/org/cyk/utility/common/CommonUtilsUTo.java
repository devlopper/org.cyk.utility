package org.cyk.utility.common;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Assert;

public class CommonUtilsUTo extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
 
	@Override
	protected void _execute_() {
		super._execute_();
		Assert.assertNotNull(CommonUtils.getInstance().getAnnotation(ClassA.class, ModelBean.class));
		Assert.assertNotNull(CommonUtils.getInstance().getAnnotation(ClassB.class, ModelBean.class));
		
		Field fieldA1 = CommonUtils.getInstance().getFieldFromClass(ClassA.class, "attributeA1");
		Field fieldB1 = CommonUtils.getInstance().getFieldFromClass(ClassB.class, "attributeB1");
		//Field fieldC2 = CommonUtils.getInstance().getFieldFromClass(ClassC.class, "attributeC2");
		//Field fieldD3 = CommonUtils.getInstance().getFieldFromClass(ClassD.class, "attributeD3");
		
		Collection<Field> fieldsA = CommonUtils.getInstance().getAllFields(ClassA.class);
		Assert.assertTrue(fieldsA.contains(fieldA1));
		
		Collection<Field> fieldsB = CommonUtils.getInstance().getAllFields(ClassB.class);
		Assert.assertTrue(fieldsB.contains(fieldB1));
		
		Assert.assertTrue(fieldsB.contains(fieldA1));
		
		Collection<Field> fieldsC = CommonUtils.getInstance().getAllFields(ClassC.class);
		Assert.assertFalse(fieldsC.contains(fieldB1));
		
		ClassA aObject = new ClassA();
		ClassC cObject = new ClassC();
		
		Assert.assertTrue(CommonUtils.getInstance().canWriteSourceToDestination(
				aObject, CommonUtils.getInstance().getFieldFromClass(ClassA.class, "attributeA2"), 
				cObject, CommonUtils.getInstance().getFieldFromClass(ClassC.class, "attributeC3")
			));
		
		Assert.assertTrue(CommonUtils.getInstance().canWriteSourceToDestination(
				cObject, CommonUtils.getInstance().getFieldFromClass(ClassC.class, "attributeC3"),
				aObject, CommonUtils.getInstance().getFieldFromClass(ClassA.class, "attributeA2")
			));
		
		//Assert.assertEquals(CommonUtils.getInstance().readField(new ClassD(), fieldC2,Boolean.TRUE, Boolean.FALSE).toString(), "It is B");
		//Assert.assertEquals(CommonUtils.getInstance().readField(new ClassD(), fieldD3,Boolean.TRUE, Boolean.FALSE).toString(), "It is A");
		//System.out.println(CommonUtils.getInstance().readField(new ClassD(), fieldC2, Boolean.TRUE));
	}
	
	/**/
	
	@ModelBean(crudStrategy=CrudStrategy.BUSINESS) @Getter @Setter
	public static class ClassA{
		//@Getter @Setter 
		//@Sequence(direction=Direction.AFTER,field="hello")
		private String attributeA1;
		private Collection<String> attributeA2 = new ArrayList<>();
		private List<String> attributeA3 = new ArrayList<>();
		private Set<String> attributeA4 = new HashSet<>();
		
		public void method(){}
		
		public String getAttribute(){
			return null;
		}
		
		@Override
		public String toString() {
			return "It is A";
		}
	};

	@Getter @Setter
	public static class ClassB extends ClassA{
		
		private String attributeB1;
		
		@Override
		public String toString() {
			return "It is B";
		}
	};
	
	@Getter @Setter
	public static class ClassC{
		
		private String attributeC1;
		private ClassB attributeC2 = new ClassB();
		
		private Collection<String> attributeC3 = new ArrayList<>();
		private List<String> attributeC4 = new ArrayList<>();
		private Set<String> attributeC5 = new HashSet<>();
		
		@Override
		public String toString() {
			return "It is C";
		}
	};
	
	@Getter @Setter
	public static class ClassD{
		
		private String attributeD1;
		private Date attributeD2;
		private ClassA attributeD3 = new ClassA();
		private ClassC attributeD4 = new ClassC();
		
		@Override
		public String toString() {
			return "It is D";
		}
	};
	
	
}
