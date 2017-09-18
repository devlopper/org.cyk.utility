package org.cyk.utility.common;

import java.math.BigDecimal;

import org.cyk.utility.common.helper.CommandHelper;
import org.cyk.utility.common.helper.NumberHelper;
import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.common.helper.ThrowableHelper;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

public class CommandUnitTest extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
	
	static {
		StringHelper.ToStringMapping.Datasource.Adapter.Default.initialize();
	}
	
	@Test
	public void sum(){
		CommandHelper.Command action = new CommandHelper.Command.Adapter.Default(){
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
		action.execute();
		assertEquals(Action.Status.SUCCESS, action.getStatus());
		assertEquals(new BigDecimal(3), action.getOutput());
	}
	
	@Test
	public void sumWithException(){
		CommandHelper.Command action = new CommandHelper.Command.Adapter.Default(){
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
		action.execute();
		assertEquals(Action.Status.FAILURE, action.getStatus());
	}

}
