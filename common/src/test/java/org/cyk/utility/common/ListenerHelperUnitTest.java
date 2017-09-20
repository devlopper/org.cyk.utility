package org.cyk.utility.common;

import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.common.helper.ListenerHelper;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

public class ListenerHelperUnitTest extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;

	@Test
	public void assertProcedure(){
		MyListener myListener = new MyListener.Adapter();
		ListenerHelper.getInstance().listen(new ListenerHelper.Executor.Procedure.Adapter.Default<MyListener>(), myListener, "exe");
		assertThat("has not been executed", myListener.isHasBeenExecuted());
		
	}
	
	@Test
	public void assertNumberOfListeners(){
		ListenerHelper.Executor<MyListener, String> executor = new ListenerHelper.Executor.Function.Adapter.Default.String<MyListener>();
		executor.setResultMethod(new ListenerHelper.Executor.ResultMethod.Adapter.Default.String<MyListener>() {
			private static final long serialVersionUID = 1L;
			@Override
			protected java.lang.String __execute__() {
				return getListener().getString("p");
			}
		});
		executor.addListener(new MyListener.Adapter() ).execute();
		
	}
	
	@Test
	public void getString(){
		Collection<MyListener> listeners = null;
		ListenerHelper.Executor<MyListener, String> executor = new ListenerHelper.Executor.Function.Adapter.Default.String<MyListener>();
		executor.setResultMethod(new ListenerHelper.Executor.ResultMethod.Adapter.Default.String<MyListener>() {
			private static final long serialVersionUID = 1L;

			@Override
			protected java.lang.String __execute__() {
				return getListener().getString("p");
			}
		});
		assertThat("result is not null", executor.clear().execute() == null);
		
		listeners = new ArrayList<>();
		executor.setInput(listeners);
		assertThat("result is not null", executor.clear().execute() == null);
		
		listeners.add(new MyListener.Adapter() {
			@Override
			public String getString(String myParam1) {
				return "myParam1Value1";
			}
		});
		assertEquals("myParam1Value1", executor.clear().execute());
		
		listeners.add(new MyListener.Adapter() {
			@Override
			public String getString(String myParam1) {
				return "myParam1Value2";
			}
		});
		assertEquals("myParam1Value2", executor.clear().execute());
		
		listeners.add(new MyListener.Adapter() {});
		assertEquals("myParam1Value2", executor.clear().execute());
		
		listeners.add(new MyListener.Adapter() {
			@Override
			public String getString(String myParam1) {
				return "myParam1Value4";
			}
		});
		assertEquals("myParam1Value4", executor.clear().execute());
	}
	
	@Test
	public void getStringFirstNotNull(){
		Collection<MyListener> listeners = null;
		ListenerHelper.Executor<MyListener, String> executor = new ListenerHelper.Executor.Function.Adapter.Default.String<MyListener>();
		executor.setReturnFirstNotNull(Boolean.TRUE);
		executor.setResultMethod(new ListenerHelper.Executor.ResultMethod.Adapter.Default.String<MyListener>() {
			private static final long serialVersionUID = 1L;

			@Override
			protected java.lang.String __execute__() {
				return getListener().getString("p");
			}
		});
		assertThat("result is not null", executor.clear().execute() == null);
		
		listeners = new ArrayList<>();
		executor.setInput(listeners);
		assertThat("result is not null", executor.clear().execute() == null);
		
		listeners.add(new MyListener.Adapter() {
			@Override
			public String getString(String myParam1) {
				return "myParam1Value1";
			}
		});
		assertEquals("myParam1Value1", executor.clear().execute());
		
		listeners.add(new MyListener.Adapter() {
			@Override
			public String getString(String myParam1) {
				return "myParam1Value2";
			}
		});
		assertEquals("myParam1Value1", executor.clear().execute());
		
		listeners.add(new MyListener.Adapter() {});
		assertEquals("myParam1Value1", executor.clear().execute());
		
		listeners.add(new MyListener.Adapter() {
			@Override
			public String getString(String myParam1) {
				return "myParam1Value4";
			}
		});
		assertEquals("myParam1Value1", executor.clear().execute());
	}
	
	public static interface MyListener {
		
		public String getString(String myParam1);
		public Boolean isHasBeenExecuted();
		public void setHasBeenExecuted(Boolean value);
		public void exe();
		public Boolean myBoolean();
		public String myString();
		public Integer myInteger();
		
		public static class Adapter implements MyListener {

			protected Boolean e;
			
			@Override
			public void setHasBeenExecuted(Boolean value) {
				e = value;
			}
			
			@Override
			public Boolean isHasBeenExecuted() {
				return e;
			}
			
			@Override
			public void exe() {
				e = true;
			}
			
			@Override
			public String getString(String myParam1) {
				return null;
			}

			@Override
			public Boolean myBoolean() {
				return null;
			}

			@Override
			public String myString() {
				return null;
			}

			@Override
			public Integer myInteger() {
				return null;
			}
			
		}
		
	}

	@Test
	public void assertManyFunctions(){
		Collection<MyListener> listeners = new ArrayList<ListenerHelperUnitTest.MyListener>();
		listeners.add(new MyListener.Adapter(){
			@Override
			public String myString() {
				return "s1";
			}
			@Override
			public Boolean myBoolean() {
				return null;
			}
			@Override
			public Integer myInteger() {
				return 0;
			}
		});
		assertEquals("s1", ListenerHelper.getInstance().listenString(listeners, "myString"));
		assertEquals(null, ListenerHelper.getInstance().listenBoolean(listeners, "myBoolean"));
		assertEquals(0, ListenerHelper.getInstance().listenInteger(listeners, "myInteger"));
		listeners.add(new MyListener.Adapter(){
			@Override
			public String myString() {
				return "s3";
			}
			@Override
			public Boolean myBoolean() {
				return false;
			}
			@Override
			public Integer myInteger() {
				return null;
			}
		});
		assertEquals("s3", ListenerHelper.getInstance().listenString(listeners, "myString"));
		assertEquals(false, ListenerHelper.getInstance().listenBoolean(listeners, "myBoolean"));
		assertEquals(0, ListenerHelper.getInstance().listenInteger(listeners, "myInteger"));
		listeners.add(new MyListener.Adapter(){
			@Override
			public String myString() {
				return null;
			}
			@Override
			public Boolean myBoolean() {
				return false;
			}
			@Override
			public Integer myInteger() {
				return 2;
			}
		});
		assertEquals("s3", ListenerHelper.getInstance().listenString(listeners, "myString"));
		assertEquals(false, ListenerHelper.getInstance().listenBoolean(listeners, "myBoolean"));
		assertEquals(2, ListenerHelper.getInstance().listenInteger(listeners, "myInteger"));
		listeners.add(new MyListener.Adapter(){
			@Override
			public String myString() {
				return "s2";
			}
			@Override
			public Boolean myBoolean() {
				return true;
			}
			@Override
			public Integer myInteger() {
				return null;
			}
		});
		assertEquals("s2", ListenerHelper.getInstance().listenString(listeners, "myString"));
		assertEquals(true, ListenerHelper.getInstance().listenBoolean(listeners, "myBoolean"));
		assertEquals(2, ListenerHelper.getInstance().listenInteger(listeners, "myInteger"));
	}
}
