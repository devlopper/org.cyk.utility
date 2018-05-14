package org.cyk.utility.common;

import org.cyk.utility.common.utility.stringhelper.StringHelperUnitTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses(value= {StringHelperUnitTest.class,ClassHelperUnitTest.class,StructuredQueryLanguageHelperUnitTest.class,TimeHelperUnitTest.class,RandomHelperUnitTest.class
		,FileHelperUnitTest.class,StreamHelperUnitTest.class
})
public class CommonTestSuite {

}
