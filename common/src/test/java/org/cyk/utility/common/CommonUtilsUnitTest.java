package org.cyk.utility.common;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.enterprise.inject.spi.CDI;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class CommonUtilsUnitTest extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
 
	@Override
	protected void _execute_() {
		super._execute_();
		Assert.assertNotNull(CommonUtils.getInstance().getAnnotation(ClassA.class, ModelBean.class));
		Assert.assertNotNull(CommonUtils.getInstance().getAnnotation(ClassB.class, ModelBean.class));
		
		ClassD objectD = new ClassD();
		objectD.setAttributeD1("aD1");
		objectD.setAttributeD4(new ClassC());
		objectD.getAttributeD3().setAttributeA1("aD1C1");
		//objectD.getAttributeD4().setAttributeC2(new ClassB());
		//objectD.getAttributeD4().getAttributeC2().setAttributeA1("aD1C1A1");
		
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
		Assert.assertEquals(new DateTime(2000, 2, 1, 0, 0).toDate(), CommonUtils.getInstance().convertString("1/2/2000", Date.class));
		Assert.assertEquals(null, CommonUtils.getInstance().convertString("951", Object.class));
	}

	//@Test
	public void cdiLookup(){
		//System.out.println("CommonUtilsUT.cdiLookup() : "+CDI.current().select(Master.class).get());
		assertThat("Master injected", CDI.current().select(Master.class).get()!=null);
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
		Assert.assertNotNull(CommonUtils.getInstance().getConstructor(UseMaster1.class, new Class<?>[]{MasterChild1.class}));
		Assert.assertNotNull(CommonUtils.getInstance().getConstructor(UseMaster1.class, new Class<?>[]{MasterChild2.class}));
		
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
		CommonUtils.getInstance().increment(Integer.class, incrementable, "nullIntegerValue", new Integer("3"));
		assertEquals("3", incrementable.getNullIntegerValue().toString());
		
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
	public void concatenate(){
		Assert.assertEquals("", CommonUtils.getInstance().concatenate(null, new String[]{}, " ", Boolean.TRUE));
		Assert.assertEquals("a", CommonUtils.getInstance().concatenate(null, new String[]{"a"}, " ", Boolean.TRUE));
		Assert.assertEquals("a 1", CommonUtils.getInstance().concatenate(null, new String[]{"a","1"}, " ", Boolean.TRUE));
		Assert.assertEquals("a 1 b z", CommonUtils.getInstance().concatenate("a 1", new String[]{"b","1","a","z"}, " ", Boolean.TRUE));
	}
	
	@Test
	public void convertToStringOneDimension(){
		assertEquals("a,1,b,AbC", CommonUtils.getInstance().convertToString(new Object[]{"a",1,"b","AbC"}, Constant.CHARACTER_COMA));
		assertEquals("a,1,b,AbC", CommonUtils.getInstance().convertToString(new String[]{"a","1","b","AbC"}, Constant.CHARACTER_COMA));
	}
	
	@Test
	public void convertToStringTwoDimensions(){
		assertEquals("a,1,b,AbC|1,2", CommonUtils.getInstance().convertToString(new Object[][]{{"a",1,"b","AbC"},{1,2}}, Constant.CHARACTER_VERTICAL_BAR,Constant.CHARACTER_COMA));
	}
	
	@Test
	public void compress() throws Exception{
		String m = "HélloW0rld";
		String t1 = StringUtils.repeat(m, 10);
		assertEquals(10 * m.length(), t1.length());
		byte[] bytes = CommonUtils.getInstance().compress(t1);
		//System.out.println(t1.length()+" : "+bytes.length);
		String nt1 = Base64.encodeBase64String(bytes);
		assertThat("less", nt1.length() < t1.length());
		nt1 = CommonUtils.getInstance().decompress(Base64.decodeBase64(nt1));
		assertEquals(t1, nt1);
	}
	
	@Test
	public void compressString() throws Exception{
		String m = "HélloW0rld";
		String t1 = StringUtils.repeat(m, 10);
		assertEquals(10 * m.length(), t1.length());
		String nt1 = CommonUtils.getInstance().compressString(t1);
		//System.out.println(t1.length()+" : "+nt1.length());
		assertThat("less", nt1.length() < t1.length());
		nt1 = CommonUtils.getInstance().decompressString(nt1);
		assertEquals(t1, nt1);
	}
	
	@Test
	public void decompress(){
		
	}
	
	@Test
	public void getPropertyValue(){
		Collection<ClassC> cs = new ArrayList<>();
		for(int i = 0 ; i < 3 ; i++){
			ClassC c1 = new ClassC();
			c1.getAttributeC2().setAttributeA1("v"+i);
			cs.add(c1);
		}
		//System.out.println(CommonUtils.getInstance().getPropertyValue(cs, String.class, "attributeC2.attributeA1"));
	}
	
	@Test
	public void setProperty(){
		ClassA a = new ClassA();
		CommonUtils.getInstance().setProperty(a, "attributeA1", "MyValue");
		
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
		private Integer nullIntegerValue;
	}
}
