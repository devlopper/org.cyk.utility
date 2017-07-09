package org.cyk.utility.common;

import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.common.helper.ListenerHelper;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

public class ListenerHelperUT extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
 	
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
		
		public static class Adapter implements MyListener {

			@Override
			public String getString(String myParam1) {
				return null;
			}
			
		}
		
	}
}
