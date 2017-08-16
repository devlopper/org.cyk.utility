package org.cyk.utility.common;

import java.util.LinkedHashSet;
import java.util.Set;

import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.helper.LoggingHelper;
import org.cyk.utility.common.helper.LoggingHelper.Message;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

public class LoggingHelperUnitTest extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
	
	@Test
	public void marker(){
		new LoggingHelper.Run.Adapter.Default(getClass(),"create"){
			private static final long serialVersionUID = 1L;
			
			public void addParameters(org.cyk.utility.common.helper.LoggingHelper.Message.Builder builder, Boolean before) {
				builder.addManyParameters(before ? "create" : "created");
			}
			
			public Object __execute__() {
				return null;
			}
			
		}.execute();
		LoggingHelper.Logger<?,?,?> logger = LoggingHelper.getInstance().getLogger();
		logger.getMessageBuilder(Boolean.TRUE).addManyParameters("my message",new Object[]{"p1","v1"}).getLogger()
			.execute(getClass(),LoggingHelper.Logger.Level.TRACE,"m1");
		
		//LoggingHelper.Logger.Log4j.Adapter.Default.log("my debug message1",getClass(),LoggingHelper.Logger.Level.TRACE,"m1");
		LoggingHelper.Logger.Log4j.Adapter.Default.log("my debug message1 with marker 1",getClass(),LoggingHelper.Logger.Level.TRACE,"FLOW");
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
