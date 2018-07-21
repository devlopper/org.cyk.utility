package org.cyk.utility.test;

import java.io.File;
import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.utility.assertion.AssertionHelper;
import org.cyk.utility.log.LogEventEntityRepository;
import org.cyk.utility.random.RandomHelper;
import org.junit.Before;

public abstract class AbstractTest extends org.cyk.utility.__kernel__.test.AbstractTest implements Serializable {
	private static final long serialVersionUID = -4375668358714913342L;
	
	static {
		//setLog4j2ConfigurationFile("org/cyk/utility/log4j2.xml");
	}
	
	@Inject protected LogEventEntityRepository logEventEntityRepository;
	
	@Inject protected AssertionHelper assertionHelper;
	
	@Before
	public void listenBefore() {
		__listenBefore__();
	}
	
	protected void __listenBefore__(){
		if(logEventEntityRepository!=null)
			logEventEntityRepository.clear();
	}
	
	protected String getRandomCode(){
		return __inject__(RandomHelper.class).getAlphabetic(3);
	}
	
	/**/
	
	protected static void setLog4j2ConfigurationFile(String path){
		File file = new File(System.getProperty("user.dir")+"/src/test/resources/"+path/*"org/cyk/utility/log4j2.xml"*/);
		if(Boolean.TRUE.equals(file.exists())){
			System.setProperty("log4j.configurationFile", file.getAbsolutePath());
			((org.apache.logging.log4j.core.LoggerContext) org.apache.logging.log4j.LogManager.getContext(false)).reconfigure();	
		}else{
			System.out.println("File <<"+file+">>does not exist.");
		}
	}
}
