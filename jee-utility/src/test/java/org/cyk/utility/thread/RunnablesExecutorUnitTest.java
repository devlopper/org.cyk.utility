package org.cyk.utility.thread;

import java.util.concurrent.TimeUnit;

import org.cyk.utility.runnable.RunnablesExecutor;
import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.cyk.utility.time.TimeHelper;
import org.junit.Test;

public class RunnablesExecutorUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	@Test
	public void isCompletedBeforeTimeOut() {
		RunnablesExecutor runner = __inject__(RunnablesExecutor.class);
		runner.setTimeOut(1l).setTimeOutUnit(TimeUnit.MINUTES);
		runner.addRunnables(new Runnable() {
			@Override
			public void run() {
				System.out.println("Run 01");
			}
		});
		runner.execute();
	}
	
	@Test
	public void isTimedOut() {
		RunnablesExecutor runner = __inject__(RunnablesExecutor.class);
		runner.setTimeOut(3l).setTimeOutUnit(TimeUnit.SECONDS);
		runner.addRunnables(new Runnable() {
			@Override
			public void run() {
				__inject__(TimeHelper.class).pause(1000l * 5);
				System.out.println("Run 02");
			}
		});
		runner.execute();
	}
	
	/**/
	
	
}
