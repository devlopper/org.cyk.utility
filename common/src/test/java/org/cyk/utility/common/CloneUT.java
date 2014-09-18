package org.cyk.utility.common;

import lombok.Getter;
import lombok.Setter;

import org.cyk.utility.common.test.AbstractUnitTest;

public class CloneUT extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;

	@Getter @Setter
	public static class ClassA implements Cloneable{
		private Integer fa1;
		public Object clone(){
			System.out.println("CloneUT.ClassA.clone()");
			ClassA c = new ClassA();
			c.setFa1(fa1);
			return c;
		}
	};
	@Getter @Setter
	public static class ClassB extends ClassA{
		private Integer fb1;
		public Object clone(){
			System.out.println("CloneUT.ClassB.clone()");
			ClassB c = new ClassB();
			c.setFb1(fb1);
			return c;
		}
	};
	
	@Override
	protected void _execute_() {
		super._execute_();
		ClassA a = new ClassA();
		a.setFa1(45);
		debug(a);
		ClassA aClone = (ClassA) a.clone();
		debug(aClone);
		
		ClassB b = new ClassB();
		debug(b);
		b.setFb1(12);
		b.setFa1(6);
		ClassB bClone = (ClassB) b.clone();
		debug(bClone);
	}
	
	
}
