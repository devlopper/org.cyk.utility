package org.cyk.utility.common;

import org.cyk.utility.common.ClassRepository.ClassField;
import org.cyk.utility.common.ClassRepository.Clazz;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Assert;
import org.junit.Test;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class ClassRepositoryUnitTestO extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
	
	//private int getFieldsMaxRound = 100/*0000*/;
	
	public void getFields(Boolean classRepositoryEnabled){
		/*ClassRepository.ENABLED = classRepositoryEnabled;
		Collection<Class<?>> classes = new ArrayList<>();
		classes.add(ClassA.class);
		classes.add(ClassA1.class);
		classes.add(ClassA2.class);
		classes.add(ClassB.class);
		classes.add(ClassC.class);
		for(int i=0;i<getFieldsMaxRound;i++)
			for(Class<?> clazz : classes)
				CommonUtils.getInstance().getAllFields(clazz);
		*/
	}
	
	@Test
	public void getFieldsClassRepositoryEnabled(){
		getFields(Boolean.TRUE);
	}
	
	@Test
	public void getFieldsClassRepositoryNotEnabled(){
		getFields(Boolean.FALSE);
	}
	
	@Test
	public void getClazzFields(){
		assertOwnedAndDependentFields(L0.class, "f0","l1","l1.f1","l1.l2","l1.l2.f2","l1.l2.l3","l1.l2.l3.f3","l1.l2.l3.l4","l1.l2.l3.l4.f4");
		assertOwnedAndDependentFields(L1.class, "f1","l2","l2.f2","l2.l3","l2.l3.f3","l2.l3.l4","l2.l3.l4.f4");
		assertOwnedAndDependentFields(L2.class, "f2","l3","l3.f3","l3.l4","l3.l4.f4");
		assertOwnedAndDependentFields(L3.class, "f3","l4","l4.f4");
		assertOwnedAndDependentFields(L4.class, "f4");
		
		assertOwnedAndDependentFields(AB.class, "ba","ba.ab","ba.ab.ba");
		
		assertOwnedAndDependentFields(AB0.class, "ab","ab.ba","ab.ba.ab","ab.ba.ab.ba");
	}
	
	private void assertOwnedAndDependentFields(Class<?> aClass,String...names){
		Clazz clazz = ClassRepository.getInstance().get(aClass);
		assertEquals("Number of owned and dependent fields", names.length, clazz.getOwnedAndDependentFields().size());
		for(String name : names){
			Boolean found = Boolean.FALSE;
			for(ClassField classField : clazz.getOwnedAndDependentFields())
				if(classField.getName().equals(name)){
					found = Boolean.TRUE;
					break;
				}
			
			Assert.assertTrue(name,found);
			
		}
	}
	
	@Getter @Setter @NoArgsConstructor @AllArgsConstructor
	private static class ClassA{
		private ClassA1 c1;
		private ClassA1 c2;
		private ClassA1 c3;
		private ClassA1 c4;
		private ClassA1 c5;
		private ClassA1 c6;
		private ClassA1 c7;
		private ClassA1 c8;
		private ClassA1 c9;
		private ClassA1 c10;
		private ClassA1 c11;
		private ClassA1 c12;
		private ClassA1 c13;
		private ClassA1 c14;
		private ClassA1 c15;
		private ClassA1 c16;
		private ClassA1 c17;
		private ClassA1 c18;
		private ClassA1 c19;
		private ClassA1 c20;
		private ClassA1 c21;
		private ClassA1 c22;
		private ClassA1 c23;
		private ClassA1 c24;
		private ClassA1 c25;
		private ClassA1 c26;
		private ClassA1 c27;
		private ClassA1 c28;
		private ClassA1 c29;
	}
	
	@Getter @Setter @NoArgsConstructor @AllArgsConstructor
	private static class ClassA1 extends ClassA {
		private String vA1;
	}
	
	@Getter @Setter @NoArgsConstructor @AllArgsConstructor
	private static class ClassA2 extends ClassA{
		private String vA2;
	}
	
	@Getter @Setter @NoArgsConstructor @AllArgsConstructor
	private static class ClassB{
		private ClassA1 c1;
		private ClassA1 c2;
		private ClassA1 c3;
		private ClassA1 c4;
		private ClassA1 c5;
		private ClassA1 c6;
		private ClassA1 c7;
		private ClassA1 c8;
		private ClassA1 c9;
		private ClassA1 c10;
		private ClassA1 c11;
		private ClassA1 c12;
		private ClassA1 c13;
		private ClassA1 c14;
		private ClassA1 c15;
		private ClassA1 c16;
		private ClassA1 c17;
		private ClassA1 c18;
		private ClassA1 c19;
		private ClassA1 c20;
		private ClassA1 c21;
		private ClassA1 c22;
		private ClassA1 c23;
		private ClassA1 c24;
		private ClassA1 c25;
		private ClassA1 c26;
		private ClassA1 c27;
		private ClassA1 c28;
		private ClassA1 c29;
	}
	
	@Getter @Setter @NoArgsConstructor @AllArgsConstructor
	private static class ClassC{
		private ClassA1 c1;
		private ClassA1 c2;
		private ClassA1 c3;
		private ClassA1 c4;
		private ClassA1 c5;
		private ClassA1 c6;
		private ClassA1 c7;
		private ClassA1 c8;
		private ClassA1 c9;
		private ClassA1 c10;
		private ClassA1 c11;
		private ClassA1 c12;
		private ClassA1 c13;
		private ClassA1 c14;
		private ClassA1 c15;
		private ClassA1 c16;
		private ClassA1 c17;
		private ClassA1 c18;
		private ClassA1 c19;
		private ClassA1 c20;
		private ClassA1 c21;
		private ClassA1 c22;
		private ClassA1 c23;
		private ClassA1 c24;
		private ClassA1 c25;
		private ClassA1 c26;
		private ClassA1 c27;
		private ClassA1 c28;
		private ClassA1 c29;
	}

	@Getter @Setter
	private static class L0{
		private String f0;
		private L1 l1;
	}
	@Getter @Setter
	private static class L1{
		private String f1;
		private L2 l2;
	}
	
	@Getter @Setter
	private static class L2{
		private String f2;
		private L3 l3;
	}
	
	@Getter @Setter
	private static class L3{
		private String f3;
		private L4 l4;
	}
	
	@Getter @Setter
	private static class L4{
		private String f4;
	}
	
	@Getter @Setter
	private static class AB0{
		private AB ab;
	}
	
	@Getter @Setter
	private static class AB{
		private BA ba;
	}
	
	@Getter @Setter
	private static class BA{
		private AB ab;
	}
}
