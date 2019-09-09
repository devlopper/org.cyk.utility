package org.cyk.utility.internationalization;

import org.cyk.utility.test.weld.AbstractWeldUnitTestPerformance;
import org.junit.jupiter.api.Test;
import static org.cyk.utility.internationalization.InternationalizationHelperImpl.__buildInternationalizationKey__;
import static org.cyk.utility.internationalization.InternationalizationHelperImpl.__buildInternationalizationString__;
import static org.cyk.utility.internationalization.InternationalizationHelperImpl.__buildInternationalizationPhraseFromKeysValues__;

public class InternalizationHelperUnitTestPerformance extends AbstractWeldUnitTestPerformance {
	private static final long serialVersionUID = 1L;

	@Test
	public void buildInternalizationKey_hi_1000000(){
		execute("build internalization key hi",1000000,500,new Runnable() {
			@Override
			public void run() {
				__buildInternationalizationKey__("hi");
			}
		});
	}	
	
	@Test
	public void buildInternalizationString_hi_1000000(){
		execute("build internalization string hi",1000000,500,new Runnable() {
			@Override
			public void run() {
				__buildInternationalizationString__("hi");
			}
		});
	}	
	
	@Test
	public void buildInternalizationString_xxx_type_1000000(){
		execute("build internalization string xxx.type",1000000,500,new Runnable() {
			@Override
			public void run() {
				__buildInternationalizationString__("xxx.type");
			}
		});
	}	
	
	@Test
	public void buildInternalizationPhrase_type_of_person_1000000(){
		execute("build internalization string type of person",1000000,4000,new Runnable() {
			@Override
			public void run() {
				__buildInternationalizationPhraseFromKeysValues__("type","of","person");
			}
		});
	}	
}
