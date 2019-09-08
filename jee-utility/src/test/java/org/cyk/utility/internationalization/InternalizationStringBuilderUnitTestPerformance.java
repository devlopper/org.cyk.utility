package org.cyk.utility.internationalization;

import org.cyk.utility.test.weld.AbstractWeldUnitTestPerformance;
import org.junit.jupiter.api.Test;

public class InternalizationStringBuilderUnitTestPerformance extends AbstractWeldUnitTestPerformance {
	private static final long serialVersionUID = 1L;

	@Test
	public void isType_de_xxx_whenKeyXxxDotType_1000(){
		execute("is type of when xxx.type",100000,1000,new Runnable() {
			@Override
			public void run() {
				__inject__(InternalizationStringBuilder.class).setKey("xxx.type").execute();
			}
		});
	}	
	
	@Test
	public void isSalut_whenKeyIsHi_1000(){
		execute("is salut when key is hi",100000,1000,new Runnable() {
			@Override
			public void run() {
				__inject__(InternalizationStringBuilder.class).setKey("hi").execute();
			}
		});		
	}
	
	@Test
	public void isH1H2_whenKeyIsH1H2_1000(){
		execute("is ##??h1H2??## when key is h1H2",100000,1000,new Runnable() {
			@Override
			public void run() {
				__inject__(InternalizationStringBuilder.class).setKey("h1H2").execute();
			}
		});			
	}
	
	/**/
	
	public static class Person {
		
	}
}
