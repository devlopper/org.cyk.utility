package org.cyk.utility.test;

import java.io.File;
import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.utility.assertion.AssertionHelper;
import org.cyk.utility.clazz.ClassHelper;
import org.cyk.utility.field.FieldHelper;
import org.cyk.utility.log.LogEventEntityRepository;
import org.cyk.utility.random.RandomHelper;

public abstract class AbstractTest extends org.cyk.utility.__kernel__.test.AbstractTest implements Serializable {
	private static final long serialVersionUID = -4375668358714913342L;
	
	static {
		//setLog4j2ConfigurationFile("org/cyk/utility/log4j2.xml");
	}
	
	@Inject protected LogEventEntityRepository logEventEntityRepository;
	@Inject protected AssertionHelper assertionHelper;
	
	protected void __listenBefore__(){
		if(logEventEntityRepository!=null)
			logEventEntityRepository.clear();
	}
	
	protected <T> T instanciateOne(Class<T> aClass) {
		return __inject__(ClassHelper.class).instanciateOne(aClass);
	}
	
	protected void __setFieldValues__(Object object) {}
	
	protected String __getRandomCode__(){
		return __inject__(RandomHelper.class).getAlphabetic(3);
	}
	
	protected Object __getSystemIdentifier__(Object object){
		return __inject__(FieldHelper.class).getFieldValueSystemIdentifier(object);
	}
	
	protected Object __getBusinessIdentifier__(Object object){
		return __inject__(FieldHelper.class).getFieldValueBusinessIdentifier(object);
	}
	
	protected void __setBusinessIdentifier__(Object object,Object value){
		__inject__(FieldHelper.class).setFieldValueBusinessIdentifier(object,value);
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
