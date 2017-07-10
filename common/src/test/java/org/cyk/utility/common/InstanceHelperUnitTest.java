package org.cyk.utility.common;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.cyk.utility.common.builder.InstanceCopyBuilder;
import org.cyk.utility.common.helper.InstanceHelper;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;
import org.mockito.InjectMocks;

public class InstanceHelperUnitTest extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
	
	@InjectMocks private InstanceHelper instanceHelper; 
	
	@Override
	protected void _execute_() {}
	
	@Test
	public void copy(){
		A a1 = new A();
		a1.setF1("inF1");
		a1.setF2(15);
		
		A a2 = new InstanceCopyBuilder<A>(a1).build();
		assertA(a2, "inF1", 15);
		
		A a3 = new InstanceCopyBuilder<A>(a1).addIgnoredFieldNames("f1").build();
		assertA(a3, null, 15);
		
		A a4 = new InstanceCopyBuilder<A>(a1).addIgnoredFieldNames("f2").build();
		assertA(a4, "inF1", null);
		
		A a5 = new InstanceCopyBuilder<A>(a1).addIgnoredFieldNames("f1","f2").build();
		assertA(a5, null, null);
		
		A a6 = new InstanceCopyBuilder<A>(a1).addIgnoredFieldAnnotationClasses(NotNull.class).build();
		assertA(a6, null, 15);
		
		A a7 = new InstanceCopyBuilder<A>(a1).addIgnoredFieldAnnotationClasses(Digits.class).build();
		assertA(a7, "inF1", null);
		
		A a8 = new InstanceCopyBuilder<A>(a1).addIgnoredFieldAnnotationClasses(NotNull.class,Digits.class).build();
		assertA(a8, null, null);
	}
	
	private void assertA(A a,String f1,Integer f2){
		assertEquals(f1, a.getF1());
		assertEquals(f2, a.getF2());
	}
	
	/**/
	
	@Getter @Setter
	public static class A {
		
		@NotNull private String f1;
		@Digits(integer=2,fraction=2) private Integer f2;
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
