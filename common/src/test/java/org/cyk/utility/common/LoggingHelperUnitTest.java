package org.cyk.utility.common;

import java.util.LinkedHashSet;
import java.util.Set;

import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.helper.LoggingHelper;
import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.common.helper.LoggingHelper.Message;
import org.cyk.utility.common.helper.StackTraceHelper;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

public class LoggingHelperUnitTest extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
	
	static{
		StringHelper.ToStringMapping.Datasource.Adapter.Default.initialize();
	}
	
	
	@Test
	public void marker(){		
		LoggingHelper.Logger<?,?,?> logger = LoggingHelper.getInstance().getLogger();
		logger.getMessageBuilder(Boolean.TRUE).addManyParameters("my message",new Object[]{"p1","v1"}).getLogger()
			.execute(getClass(),LoggingHelper.Logger.Level.TRACE,"m1");
		
		LoggingHelper.Logger.Log4j.Adapter.Default.log("my debug message1 with marker ml0",getClass(),LoggingHelper.Logger.Level.TRACE,"ML0");
		LoggingHelper.Logger.Log4j.Adapter.Default.log("my debug message1 with marker ml1",getClass(),LoggingHelper.Logger.Level.TRACE,"ML1");
		LoggingHelper.Logger.Log4j.Adapter.Default.log("my debug message1 with marker ml2",getClass(),LoggingHelper.Logger.Level.TRACE,"ML2");
		LoggingHelper.Logger.Log4j.Adapter.Default.log("my debug message1 with marker ml3",getClass(),LoggingHelper.Logger.Level.TRACE,"ML3");
		
		LoggingHelper.Logger.Log4j.Adapter.Default.log("MY ACTION",getClass(),LoggingHelper.Logger.Level.TRACE,"MY MARKER");
		
		LoggingHelper.Logger.Log4j.Adapter.Default.log("MY ACTION",getClass(),LoggingHelper.Logger.Level.TRACE,"MY MARKER 2");
		
		new LogClass().m1();
	}
	
	@Test
	public void myoperation001(){		
		new LoggingHelper.Run.Adapter.Default(StackTraceHelper.getInstance().getAt(2)){
			private static final long serialVersionUID = 1L;
			
			public Object __execute__() {
				return null;
			}
			
		}.execute();
	}
	
	@Test
	public void myoperation002(){		
		new LoggingHelper.Run.Adapter.Default(StackTraceHelper.getInstance().getAt(2)){
			private static final long serialVersionUID = 1L;
			
			public Object __execute__() {
				return null;
			}
			
		}.execute();
	}
	
	@Test
	public void executeMessageBuild(){
		assertMessage((Message.Builder) new LoggingHelper.Message.Builder.Adapter.Default().addManyParameters("simple1","simple2"), "simple1 , simple2", null);
		assertMessage((Message.Builder) new LoggingHelper.Message.Builder.Adapter.Default().addManyParameters("simple1","simple2",new Object[]{"p1","v1"})
				, "simple1 , simple2 , p1={}", new Object[]{"v1"});
		assertMessage((Message.Builder) new LoggingHelper.Message.Builder.Adapter.Default().addManyParameters("simple1","simple2",new Object[]{"p1","v1"}
			,new Object[]{"p2","v2"}), "simple1 , simple2 , p1={} , p2={}", new Object[]{"v1","v2"});
		
		assertMessage((Message.Builder) new LoggingHelper.Message.Builder.Adapter.Default().addManyParameters(new Object[]{"p1","v1"},"simple1","simple2"
			,new Object[]{"p2","v2"}), "p1={} , simple1 , simple2 , p2={}", new Object[]{"v1","v2"});
	}
	
	@Test
	public void stackTrace(){
		new A().fa();
	}
	
	public static class LogClass {
		
		public void m1(){
			LoggingHelper.Logger.Log4j.Adapter.Default.log("another message from m1",getClass(),LoggingHelper.Logger.Level.TRACE,"ML3"
					,StackTraceHelper.getInstance().getAt(3));
		}
		
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
	
	private void assertMessage(LoggingHelper.Message.Builder messageBuilder,String expectedTemplate,Object[] extpectedArguments){
		Message message = messageBuilder.execute();
		assertEquals(expectedTemplate, message.getTemplate());
		if(extpectedArguments!=null){
			int i = 0;
			for(Object extpectedArgument : extpectedArguments)
				assertEquals(extpectedArgument, message.getArguments().get(i++));
		}
	}
	
}
