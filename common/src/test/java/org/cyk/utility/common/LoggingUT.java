package org.cyk.utility.common;

import java.util.LinkedHashSet;
import java.util.Set;

import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;


public class LoggingUT extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
	
	@Test
	public void stackTrace(){
		new A().fa();
	}
	
	private class A{
		public void fa(){
			new B().fb();
		}
	}
	
	private class B{
		public void fb(){
			new C().fc();
		}
	}

	private class C{
		public void fc(){
			new D().fd();
		}
	}
	
	private class D{
		public void fd(){
			new E().fe();
		}
	}
	
	private class E extends AbstractBean{
		private static final long serialVersionUID = -8323704410184920928L;

		public void fe(){
			Set<String> packages = new LinkedHashSet<>();
			packages.add(E.class.getPackage().getName());
			logStackTraceAsString(packages);
		}
	}
	
}
