package org.cyk.utility.database;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import lombok.Getter;
import lombok.Setter;

import org.cyk.utility.database.definition.MySQL;

@Getter @Setter
public abstract class AbstractDatasource {
	
	public static MySQL MYSQL_TEST_DB = new MySQL(new ServerMode(MySQL.SERVER_DRIVER_NAME, "MySQL", MySQL.DEFAULT_PORT),"testdb","root","root");;
	
	protected AbstractDatabase database;
	protected boolean opened = false;
	protected boolean dropCreateDatabase = false;
	protected boolean debug;
	
	protected Map<String, String> environmentInfos = new LinkedHashMap<String, String>();
	
	public AbstractDatasource() {
		__init__();
		database = createDatabase();
	}
	
	protected void showInfos(){
		System.out.println("###############################################################");
		System.out.println(database);
		System.out.println("Clear DB : "+dropCreateDatabase);
		for(Entry<String, String> entry : environmentInfos.entrySet())
			System.out.println(entry.getKey()+" : "+entry.getValue());
		System.out.println("Debug : "+debug);
		System.out.println("###############################################################");
	}
	
	protected void __init__(){}
	
	protected AbstractDatabase createDatabase(){
		return MYSQL_TEST_DB;
	}

	public boolean beforeOpen(){return true;}
	
	public final void open(){
		if(beforeOpen()){
			if(database==null){
				System.err.println("No Database Specified. Please set one and retry.");
				return;
			}
			if(database.getMode()==null){
				System.err.println("No Database Mode {Embedded or Server} Specified. Please set one and retry.");
				return;
			}
			System.out.println("Opening database...");
			__open__();
			System.out.println("Opened...");
			opened = true;
			afterOpen();
			
			if(dropCreateDatabase)
				database.dropAndCreate();
		}
	}
	
	protected abstract void __open__();
	 
	public void afterOpen(){}
	
	protected abstract void __close__();
	
	public void close(){
		if(opened){
			System.out.println("Closing database...");
			__close__();
			System.out.println("Closed...");
		}
	}
	
	protected abstract void __execute__();
	
	public final void execute(){
		showInfos();
		open();
		__execute__();
		close();
	}

}
