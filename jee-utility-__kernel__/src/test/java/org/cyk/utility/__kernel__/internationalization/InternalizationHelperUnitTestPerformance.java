package org.cyk.utility.__kernel__.internationalization;

import org.junit.jupiter.api.Test;
import static org.cyk.utility.__kernel__.internationalization.InternationalizationHelper.buildKey;
import static org.cyk.utility.__kernel__.internationalization.InternationalizationHelper.buildString;

import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTestPerformance;

import static org.cyk.utility.__kernel__.internationalization.InternationalizationHelper.buildPhraseFromKeysValues;

public class InternalizationHelperUnitTestPerformance extends AbstractWeldUnitTestPerformance {
	private static final long serialVersionUID = 1L;

	@Test
	public void buildInternalizationKey_hi_1000000(){
		execute("build internalization key hi",1000000,500,new Runnable() {
			@Override
			public void run() {
				buildKey("hi");
			}
		});
	}	
	
	@Test
	public void buildInternalizationString_hi_1000000(){
		execute("build internalization string hi",1000000,500,new Runnable() {
			@Override
			public void run() {
				buildString("hi");
			}
		});
	}	
	
	@Test
	public void buildInternalizationString_xxx_type_1000000(){
		execute("build internalization string xxx.type",1000000,500,new Runnable() {
			@Override
			public void run() {
				buildString("xxx.type");
			}
		});
	}	
	
	@Test
	public void buildInternalizationPhrase_type_of_person_1000000(){
		execute("build internalization string type of person",1000000,4000,new Runnable() {
			@Override
			public void run() {
				buildPhraseFromKeysValues("type","of","person");
			}
		});
	}	
}
