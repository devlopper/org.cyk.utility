package org.cyk.utility.common;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.common.CommonUtils.ReadExcelSheetArguments;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Assert;
import org.junit.Test;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class CommonUtilsUT extends AbstractUnitTest {

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
		
		ClassD objectD = new ClassD();
		objectD.setAttributeD1("aD1");
		objectD.setAttributeD4(new ClassC());
		objectD.getAttributeD3().setAttributeA1("aD1C1");
		//objectD.getAttributeD4().setAttributeC2(new ClassB());
		//objectD.getAttributeD4().getAttributeC2().setAttributeA1("aD1C1A1");
		
		Assert.assertEquals(objectD,CommonUtils.getInstance().getFieldValueContainer(objectD, 
				CommonUtils.getInstance().getFieldFromClass(ClassD.class, "attributeD1")));
		//Assert.assertEquals(objectD.getAttributeD4(),CommonUtils.getInstance().getFieldValueContainer(objectD, 
		//		CommonUtils.getInstance().getFieldFromClass(ClassC.class, "attributeD4")));
		Assert.assertEquals(objectD.getAttributeD3(),CommonUtils.getInstance().getFieldValueContainer(objectD, 
				CommonUtils.getInstance().getFieldFromClass(ClassA.class, "attributeA1")));
		
		
		Collection<Sumable> sumables = new ArrayList<>();
		sumables.add(new Sumable(null, new BigDecimal("5"), 6));
		sumables.add(new Sumable(null, null, 10));
		sumables.add(new Sumable(null, new BigDecimal("0"), null));
		sumables.add(new Sumable(null, new BigDecimal("7.2"), 0));
		
		Sumable result = CommonUtils.getInstance().sum(Sumable.class, sumables, new HashSet<>(Arrays.asList("attribute2","attribute3")));
		Assert.assertEquals(new BigDecimal("12.2"), result.getAttribute2());
		Assert.assertEquals(new Integer("16"), result.getAttribute3());
		
		result = CommonUtils.getInstance().sum(Sumable.class, sumables, null);
		Assert.assertEquals(new BigDecimal("12.2"), result.getAttribute2());
		Assert.assertEquals(new Integer("16"), result.getAttribute3());
		
		Assert.assertEquals(new String("yao"), CommonUtils.getInstance().convertString("yao", String.class));
		Assert.assertEquals(new String("12"), CommonUtils.getInstance().convertString("12", String.class));
		Assert.assertEquals(new BigDecimal("12.36"), CommonUtils.getInstance().convertString("12.36", BigDecimal.class));
		Assert.assertEquals(new Long("159"), CommonUtils.getInstance().convertString("159", Long.class));
		Assert.assertEquals(new Integer("951"), CommonUtils.getInstance().convertString("951", Integer.class));
		Assert.assertEquals(new Byte("5"), CommonUtils.getInstance().convertString("5", Byte.class));
		Assert.assertEquals(null, CommonUtils.getInstance().convertString("951", Object.class));
	}

	@Test
	public void constructor(){
		/*for(Constructor<?> constructor : ClassC.class.getDeclaredConstructors()){
			System.out.println(constructor);
			System.out.println(StringUtils.join(constructor.getParameterTypes()));
		}*/
		
		Assert.assertNotNull(CommonUtils.getInstance().getConstructor(ClassC.class, new Class<?>[]{ClassA.class}));
		Assert.assertNull(CommonUtils.getInstance().getConstructor(ClassC.class, new Class<?>[]{ClassC.class}));
		
		Assert.assertNull(CommonUtils.getInstance().getConstructor(ClassC.class, new Class<?>[]{String.class}));
		Assert.assertNotNull(CommonUtils.getInstance().getConstructor(ClassC.class, new Class<?>[]{ClassB.class}));
		
		Assert.assertNotNull(CommonUtils.getInstance().getConstructor(UseMaster1.class, new Class<?>[]{Master.class}));
		Assert.assertNotNull(CommonUtils.getInstance().getConstructor(UseMaster2.class, new Class<?>[]{MasterChild1.class}));
		Assert.assertNotNull(CommonUtils.getInstance().getConstructor(UseMaster3.class, new Class<?>[]{MasterChild2.class}));
		Assert.assertNotNull(CommonUtils.getInstance().getConstructor(UseMaster4.class, new Class<?>[]{MasterChild1.class}));
		Assert.assertNotNull(CommonUtils.getInstance().getConstructor(UseMaster4.class, new Class<?>[]{MasterChild2.class}));
	}
	
	@Test
	public void increment(){
		Incrementable incrementable = new Incrementable();
		CommonUtils.getInstance().increment(BigDecimal.class, incrementable, "bigDecimalValue", new BigDecimal("1"));
		assertEquals("1", incrementable.getBigDecimalValue().toString());
		CommonUtils.getInstance().increment(BigDecimal.class, incrementable, "bigDecimalValue", new BigDecimal("4"));
		assertEquals("5", incrementable.getBigDecimalValue().toString());
		
		CommonUtils.getInstance().increment(Long.class, incrementable, "longValue", new Long("1"));
		assertEquals("1", incrementable.getLongValue().toString());
		CommonUtils.getInstance().increment(Long.class, incrementable, "longValue", new Long("-6"));
		assertEquals("-5", incrementable.getLongValue().toString());
		
		CommonUtils.getInstance().increment(Integer.class, incrementable, "integerValue", new Integer("-3"));
		assertEquals("-3", incrementable.getIntegerValue().toString());
		CommonUtils.getInstance().increment(Integer.class, incrementable, "integerValue", new Integer("5"));
		assertEquals("2", incrementable.getIntegerValue().toString());
		CommonUtils.getInstance().increment(Integer.class, incrementable, "integerValue", new Integer("0"));
		assertEquals("2", incrementable.getIntegerValue().toString());
		
	}
	
	@Test
	public void instanciateProperty(){
		ClassD d = new ClassD();
		d.setAttributeD3(null);
		d.setAttributeD4(null);
		CommonUtils.getInstance().instanciateProperty(d, "attributeD3");
		Assert.assertNotNull(d.getAttributeD3());
		d.setAttributeD3(null);
		CommonUtils.getInstance().instanciateProperty(d, "attributeD3.attributeA1");
		Assert.assertNotNull(d.getAttributeD3());
		Assert.assertNotNull(d.getAttributeD3().getAttributeA1());
		
		CommonUtils.getInstance().instanciateProperty(d, "attributeD4.attributeZ1.attributeA1");
		Assert.assertNotNull(d.getAttributeD4());
		Assert.assertNotNull(d.getAttributeD4().getAttributeZ1());
		Assert.assertNotNull(d.getAttributeD4().getAttributeZ1().getAttributeA1());
	}
	
	@Test
	public void instanciateOne(){
		ClassA classA = CommonUtils.getInstance().instanciateOne(ClassA.class, new ObjectFieldValues(ClassA.class).set("attributeA1","valueA1"));
		Assert.assertEquals("valueA1", classA.getAttributeA1());
		
		ClassB classB = CommonUtils.getInstance().instanciateOne(ClassB.class, new ObjectFieldValues(ClassB.class).set("attributeB1","valueB1"
				,"attributeA1","valueA1To"));
		Assert.assertEquals("valueA1To", classB.getAttributeA1());
		Assert.assertEquals("valueB1", classB.getAttributeB1());
		
		ClassC classC = CommonUtils.getInstance().instanciateOne(ClassC.class, new ObjectFieldValues(ClassC.class)
			.set("attributeC1","valueC1","attributeZ1.attributeA1","valueA1new")
		);
		Assert.assertEquals("valueC1", classC.getAttributeC1());
		Assert.assertEquals("valueA1new", classC.getAttributeZ1().getAttributeA1());
	}
	
	@Test
	public void readExcelSheetXls(){
		File directory = new File(System.getProperty("user.dir")+"\\src\\test\\resources\\files\\excel");
		ReadExcelSheetArguments arguments = new ReadExcelSheetArguments();
		
		try {
			arguments.setWorkbookBytes(IOUtils.toByteArray(new FileInputStream(new File(directory, "1xls.xls"))));
			arguments.setSheetIndex(0);
			arguments.setRowCount(5);
			arguments.setColumnCount(3);
			List<String[]> list = CommonUtils.getInstance().readExcelSheet(arguments);
			for(String[] line : list)
				System.out.println(StringUtils.join(line," ; "));
			
			arguments = new ReadExcelSheetArguments();
			arguments.setWorkbookBytes(IOUtils.toByteArray(new FileInputStream(new File(directory, "1xls.xls"))));
			arguments.setSheetIndex(0);
			list = CommonUtils.getInstance().readExcelSheet(arguments);
			for(String[] line : list)
				System.out.println(StringUtils.join(line," ; "));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void readExcelSheetXlsx(){
		File directory = new File(System.getProperty("user.dir")+"\\src\\test\\resources\\files\\excel");
		ReadExcelSheetArguments arguments = new ReadExcelSheetArguments();
		try {
			arguments.setWorkbookBytes(IOUtils.toByteArray(new FileInputStream(new File(directory, "1xlsx.xlsx"))));
			arguments.setSheetIndex(0);
			arguments.setRowCount(5);
			arguments.setColumnCount(3);
			List<String[]> list = CommonUtils.getInstance().readExcelSheet(arguments);
			for(String[] line : list)
				System.out.println(StringUtils.join(line," ; "));
			
			arguments = new ReadExcelSheetArguments();
			arguments.setWorkbookBytes(IOUtils.toByteArray(new FileInputStream(new File(directory, "1xlsx.xlsx"))));
			arguments.setSheetIndex(0);
			list = CommonUtils.getInstance().readExcelSheet(arguments);
			for(String[] line : list)
				System.out.println(StringUtils.join(line," ; "));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
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
	
	@Getter @Setter @NoArgsConstructor
	public static class ClassC{
		
		private String attributeC1;
		private ClassB attributeC2 = new ClassB();
		private ClassA attributeZ1;
		
		private Collection<String> attributeC3 = new ArrayList<>();
		private List<String> attributeC4 = new ArrayList<>();
		private Set<String> attributeC5 = new HashSet<>();
		
		public ClassC(ClassA attributeZ1) {
			super();
			this.attributeZ1 = attributeZ1;
		}
		
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
	
	@Getter @Setter
	public static class Master{
		private String attribute;
	}
	@Getter @Setter
	public static class MasterChild1 extends Master{
		private String attribute1;
	}
	@Getter @Setter
	public static class MasterChild2 extends Master{
		private String attribute2;
	}
	@Getter @Setter
	public static class UseMaster1{
		private String attribute;
		public UseMaster1(Master master) {
			
		}
		
	}
	
	@Getter @Setter
	public static class UseMaster2{
		private String attribute;
		public UseMaster2(MasterChild1 master) {
			
		}
		
	}
	
	@Getter @Setter
	public static class UseMaster3{
		private String attribute;
		public UseMaster3(MasterChild2 master) {
	
		}
	}
	
	@Getter @Setter
	public static class UseMaster4{
		private String attribute;
		public UseMaster4(MasterChild2 master) {
			
		}
		public UseMaster4(MasterChild1 master) {
			
		}
		
	}
	
	@Getter @Setter @NoArgsConstructor @AllArgsConstructor
	public static class Sumable{
		private String attribute1;
		private BigDecimal attribute2;
		private Integer attribute3;
		
	}
	
	@Getter @Setter
	public static class Incrementable{
		private BigDecimal bigDecimalValue = BigDecimal.ZERO;
		private Long longValue=0l;
		private Integer integerValue=0;
	}
}
