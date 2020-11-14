package org.cyk.utility.__kernel__.script;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTestBenchmark;
import org.junit.jupiter.api.Test;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import static org.assertj.core.api.Assertions.assertThat;

public class ScriptExecutorUnitTestBenchmark extends AbstractWeldUnitTestBenchmark {
	private static final long serialVersionUID = 1L;

	@Test
	public void writeField(){
		List<MyClass> instances01 = new ArrayList<>();
		String script01 = "f+s";
		
		List<MyClass> instances02 = new ArrayList<>();
		String script02 = ScriptHelper.formatLoopForCollectionOverFunction("l", "i", "l.get(i).code = generateCode('GC',i);", "generateCode", "return f+s", "f","s");
		
		for(Integer index = 0; index < 200; index++) {
			instances01.add(new MyClass());
			instances02.add(new MyClass());
		}
		
		execute(new Jobs().setName("Write value").setNumberOfRound(1)
				.add("calling script engine by instance",new Runnable() {
				@Override
				public void run() {
					int i = 0;
					for(MyClass instance : instances01)
						instance.setCode(ScriptExecutor.getInstance().execute(String.class,script01, "f","GC","s",i++));
				}
			}).add("calling script engine for all instances",new Runnable() {
				@Override
				public void run() {
					ScriptExecutor.getInstance().execute(String.class,script02, "l",instances02);
				}
			})
		);
		
		for(Integer index = 0; index < 200; index++) {
			assertThat(instances01.get(index).getCode()).isEqualTo("GC"+index);
			assertThat(instances02.get(index).getCode()).isEqualTo("GC"+index);
		}
	}	
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class MyClass {		
		private String code;
	}
}
