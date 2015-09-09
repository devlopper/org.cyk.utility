package org.cyk.utility.common;

import java.util.ArrayList;
import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

public class ClassRepositoryUT extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
	
	private int getFieldsMaxRound = 1000000;
	
	public void getFields(Boolean classRepositoryEnabled){
		ClassRepository.ENABLED = classRepositoryEnabled;
		Collection<Class<?>> classes = new ArrayList<>();
		classes.add(ClassA.class);
		classes.add(ClassA1.class);
		classes.add(ClassA2.class);
		classes.add(ClassB.class);
		classes.add(ClassC.class);
		for(int i=0;i<getFieldsMaxRound;i++)
			for(Class<?> clazz : classes)
				CommonUtils.getInstance().getAllFields(clazz);
	}
	
	@Test
	public void getFieldsClassRepositoryEnabled(){
		getFields(Boolean.TRUE);
	}
	
	@Test
	public void getFieldsClassRepositoryNotEnabled(){
		getFields(Boolean.FALSE);
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
}
