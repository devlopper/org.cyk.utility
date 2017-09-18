package org.cyk.utility.common;

import java.math.BigDecimal;

import org.cyk.utility.common.helper.NumberHelper;
import org.cyk.utility.common.helper.ThrowableHelper;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

public class ActionUnitTest extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;

	@Test(expected=RuntimeException.class)
	public void failureThrowException(){
		Action<Object, Object> action = new Action.Adapter.Default<Object, Object>("myaction", Object.class, new Object(), Object.class){
			private static final long serialVersionUID = 1L;
			@Override
			protected Object __execute__() {
				ThrowableHelper.getInstance().throw_("something goes wrong!!!");
				return null;
			}
		};
		action.execute();
	}
	
	@Test
	public void failureHandleException(){
		Action<Object, Object> action = new Action.Adapter.Default<Object, Object>("myaction", Object.class, new Object(), Object.class){
			private static final long serialVersionUID = 1L;
			@Override
			protected Object __execute__() {
				ThrowableHelper.getInstance().throw_("something goes wrong!!!");
				return null;
			}
		};
		action.setIsProcessableOnStatus(Boolean.TRUE).execute();
		assertEquals(Action.Status.FAILURE, action.getStatus());
	}
	
	@Test
	public void sum(){
		Action<Object, Object> action = new Action.Adapter.Default<Object, Object>("do sum", Object.class, new Object(), Object.class){
			private static final long serialVersionUID = 1L;
			@Override
			public Action<Object, Object> validateInput() {
				super.validateInput();
				if(!(getInput() instanceof Object[]))
					ThrowableHelper.getInstance().throw_("an array of number is expected");
				Object[] numbers = (Object[]) getInput();
				for(Object number : numbers)
					if(NumberHelper.getInstance().isNotNumber(number))
						ThrowableHelper.getInstance().throw_("this is not a number : "+number);
				return this;
			}
			
			@Override
			protected Object __execute__() {
				Object[] numbers = (Object[]) getInput();
				BigDecimal sum = BigDecimal.ZERO;
				for(Object number : numbers)
					sum = sum.add(new BigDecimal(NumberHelper.getInstance().get(number).toString()));
				return sum;
			}
			
		};
		action.addActionListener(new Action.ActionListener.Adapter(){
			private static final long serialVersionUID = 1L;
			@Override
			public void getInput(Action<Object, Object> action) {
				action.setInput(new Object[]{1,2});
			}
			
		});
		action.setIsInputValidatable(Boolean.TRUE).setIsProcessableOnStatus(Boolean.TRUE).execute();
		assertEquals(Action.Status.SUCCESS, action.getStatus());
		assertEquals(new BigDecimal(3), action.getOutput());
	}
	
	@Test
	public void sumHandleException(){
		Action<Object, Object> action = new Action.Adapter.Default<Object, Object>("do sum", Object.class, new Object(), Object.class){
			private static final long serialVersionUID = 1L;
			@Override
			public Action<Object, Object> validateInput() {
				super.validateInput();
				if(!(getInput() instanceof Object[]))
					ThrowableHelper.getInstance().throw_("an array of number is expected");
				Object[] numbers = (Object[]) getInput();
				for(Object number : numbers)
					if(NumberHelper.getInstance().isNotNumber(number))
						ThrowableHelper.getInstance().throw_("this is not a number : "+number);
				return this;
			}
			
			@Override
			protected Object __execute__() {
				Object[] numbers = (Object[]) getInput();
				BigDecimal sum = BigDecimal.ZERO;
				for(Object number : numbers)
					sum = sum.add(new BigDecimal(NumberHelper.getInstance().get(number).toString()));
				return sum;
			}
			
		};
		action.addActionListener(new Action.ActionListener.Adapter(){
			private static final long serialVersionUID = 1L;
			@Override
			public void getInput(Action<Object, Object> action) {
				action.setInput(new Object[]{1,"a"});
			}
			
		});
		action.setIsInputValidatable(Boolean.TRUE).setIsProcessableOnStatus(Boolean.TRUE).execute();
		assertEquals(Action.Status.FAILURE, action.getStatus());
	}
	
	@Test
	public void sumWithConfirmation(){
		Action<Object, Object> action = new Action.Adapter.Default<Object, Object>("do sum", Object.class, new Object(), Object.class){
			private static final long serialVersionUID = 1L;
			@Override
			public Action<Object, Object> validateInput() {
				super.validateInput();
				if(!(getInput() instanceof Object[]))
					ThrowableHelper.getInstance().throw_("an array of number is expected");
				Object[] numbers = (Object[]) getInput();
				for(Object number : numbers)
					if(NumberHelper.getInstance().isNotNumber(number))
						ThrowableHelper.getInstance().throw_("this is not a number : "+number);
				return this;
			}
			
			@Override
			protected Object __execute__() {
				Object[] numbers = (Object[]) getInput();
				BigDecimal sum = BigDecimal.ZERO;
				for(Object number : numbers)
					sum = sum.add(new BigDecimal(NumberHelper.getInstance().get(number).toString()));
				return sum;
			}
			
		};
		action.addActionListener(new Action.ActionListener.Adapter(){
			private static final long serialVersionUID = 1L;
			@Override
			public void getInput(Action<Object, Object> action) {
				action.setInput(new Object[]{1,2});
			}
			
			@Override
			public void confirm(Action<?, ?> action) {
				super.confirm(action);
				action.setIsConfirmed(Boolean.FALSE);
			}
			
		});
		action.setIsInputValidatable(Boolean.TRUE).setIsProcessableOnStatus(Boolean.TRUE).setIsConfirmable(Boolean.TRUE).execute();
		assertEquals(null, action.getStatus());
	}
	
}
