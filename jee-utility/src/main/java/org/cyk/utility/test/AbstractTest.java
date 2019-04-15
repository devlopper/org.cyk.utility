package org.cyk.utility.test;

import java.io.File;
import java.io.Serializable;
import java.util.UUID;

import javax.inject.Inject;

import org.cyk.utility.ApplicationScopeLifeCycleListener;
import org.cyk.utility.assertion.AssertionHelper;
import org.cyk.utility.clazz.ClassHelper;
import org.cyk.utility.field.FieldHelper;
import org.cyk.utility.log.LogEventEntityRepository;
import org.cyk.utility.random.RandomHelper;
import org.cyk.utility.system.OperatingSystemCommandExecutor;
import org.cyk.utility.system.SystemHelper;
import org.cyk.utility.time.TimeHelper;
import org.cyk.utility.value.ValueHelper;

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
		__initializeApplicationScopeLifeCycleListener__();
	}
	
	protected void __initializeApplicationScopeLifeCycleListener__() {
		__inject__(ApplicationScopeLifeCycleListener.class).initialize(null);
	}
	
	protected <T> T instanciateOne(Class<T> aClass) {
		return __inject__(ClassHelper.class).instanciateOne(aClass);
	}
	
	protected void __setFieldValues__(Object object) {}
	
	protected UUID __getRandomUuid__(){
		return UUID.randomUUID();
	}
	
	protected String __getRandomUuidAsString__(){
		return __getRandomUuid__().toString();
	}
	
	protected String __getRandomIdentifier__(){
		return __getRandomUuidAsString__();
	}
	
	protected String __getRandomCode__(){
		return __inject__(RandomHelper.class).getAlphabetic(3);
	}
	
	protected String __getRandomString__(){
		return __inject__(RandomHelper.class).getAlphabetic(3);
	}
	
	protected void __setFieldValueBusinessIdentifier__(Object object,Object value){
		__inject__(FieldHelper.class).setFieldValueBusinessIdentifier(object,value);
	}
	
	protected Object __getFieldValueSystemIdentifier__(Object object) {
		return __inject__(FieldHelper.class).getFieldValueSystemIdentifier(object);
	}
	
	protected Object __getFieldValueBusinessIdentifier__(Object object) {
		return __inject__(FieldHelper.class).getFieldValueBusinessIdentifier(object);
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
	
	/* Server */
	
	protected static void stopServersKafkaAndZookeeper() {
		System.out.println("Stopping servers");
		stopServerKafka();
		stopServerZookeeper();
		System.out.println("Servers stopped");
	}
	
	protected static void startServersZookeeperAndKafka() {
		stopServersKafkaAndZookeeper();
		
		System.out.println("Starting servers");
		startServerZookeeper();
		startServerKafka();
		System.out.println("Servers started");
	}

	protected static void stopServerZookeeper() {
		System.out.print("Stopping zookeeper...");
		OperatingSystemCommandExecutor operatingSystemCommandExecutor = __inject__(OperatingSystemCommandExecutor.class);
		operatingSystemCommandExecutor.getCommand(Boolean.TRUE).setCommand("zookeeper-server-stop.bat")
			//.setIsTerminalStartable(Boolean.TRUE)
			//.setIsTerminalShowable(Boolean.FALSE)
			//.setWorkingDirectory("target")
			;
		operatingSystemCommandExecutor.execute();
		System.out.println("OK");
	}
	
	protected static void startServerZookeeper() {
		String workingDirectory = __inject__(ValueHelper.class).returnOrThrowIfBlank("Zookeeper home", __inject__(SystemHelper.class).getProperty("zookeeper.home",Boolean.TRUE));
		System.out.print("Starting zookeeper("+workingDirectory+")...");
		OperatingSystemCommandExecutor operatingSystemCommandExecutor = __inject__(OperatingSystemCommandExecutor.class);
		operatingSystemCommandExecutor.getCommand(Boolean.TRUE).setCommand("zkserver").setIsTerminalStartable(Boolean.TRUE)
			.setIsTerminalShowable(Boolean.FALSE)
			.setTerminalTitle("Zookeeper")
			.setWorkingDirectory(workingDirectory)
			;
		operatingSystemCommandExecutor.setIsExecuteAsynchronously(Boolean.TRUE).execute();
		__inject__(TimeHelper.class).pause(1000l * 10);
		System.out.println("OK");
	}
	
	protected static void stopServerKafka() {
		System.out.print("Stopping kafka...");
		OperatingSystemCommandExecutor operatingSystemCommandExecutor = __inject__(OperatingSystemCommandExecutor.class);
		operatingSystemCommandExecutor.getCommand(Boolean.TRUE).setCommand("kafka-server-stop.bat")
			//.setIsTerminalStartable(Boolean.TRUE)
			//.setIsTerminalShowable(Boolean.FALSE)
			//.setWorkingDirectory("target")
			;
		operatingSystemCommandExecutor.execute();
		System.out.println("OK");
	}
	
	protected static void startServerKafka() {
		String homeDirectory = __inject__(ValueHelper.class).returnOrThrowIfBlank("Kafka home", __inject__(SystemHelper.class).getProperty("kafka.home",Boolean.TRUE));
		System.out.print("Starting kafka("+homeDirectory+")...");
		OperatingSystemCommandExecutor operatingSystemCommandExecutor = __inject__(OperatingSystemCommandExecutor.class);
		operatingSystemCommandExecutor.getCommand(Boolean.TRUE).setCommand("kafka-server-start.bat "+homeDirectory+"\\config\\server.properties")
			.setIsTerminalStartable(Boolean.TRUE)
			.setIsTerminalShowable(Boolean.FALSE)
			.setTerminalTitle("Kafka")
			.setWorkingDirectory(homeDirectory)
			;
		operatingSystemCommandExecutor.setIsExecuteAsynchronously(Boolean.TRUE).execute();
		__inject__(TimeHelper.class).pause(1000l * 12);
		System.out.println("OK");
	}
}
