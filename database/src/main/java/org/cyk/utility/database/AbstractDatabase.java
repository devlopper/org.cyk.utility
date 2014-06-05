package org.cyk.utility.database;

import java.io.Serializable;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.cyk.utility.common.cdi.AbstractBean;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractDatabase extends AbstractBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2534217567405085771L;

	public static String EMBEDDED_MODE_DEFAULT_PATH = System.getProperty("user.dir")+"\\db";
	
	protected AbstractMode mode;
	protected String name,username,password;
	
	public AbstractDatabase(AbstractMode mode, String name, String username,
			String password) {
		super();
		this.mode = mode;
		this.name = name;
		this.username = username;
		this.password = password;
	}
	
	public AbstractDatabase(AbstractMode mode) {
		super();
		this.mode = mode;
	}

	public abstract String __embeddedModeConnectionUrl__(EmbeddedMode embeddedMode);
	
	public abstract String __serverModeConnectionUrl__(ServerMode serverMode);
	
	public String getConnectionUrl(){
		String url = null;
		if(mode instanceof EmbeddedMode)
			url = __embeddedModeConnectionUrl__((EmbeddedMode) mode);
		if(mode instanceof ServerMode)
			url = __serverModeConnectionUrl__((ServerMode) mode);
		if(url==null)
			throw new RuntimeException("No Connection URL Can be obtained With this mode : \r\n"+mode);
		return url;
	}
	
	private void watchDrop(final Statement statement){
		__watchExecute__("Deleting database "+name, new Runnable() {@Override public void run() {
			try {
				__drop__(statement);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}});
	}
	
	private void watchCreate(final Statement statement){
		__watchExecute__("Creating database "+name, new Runnable() {@Override public void run() {
			try {
				__create__(statement);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}});
	}
		
	public final void drop(){
		new Statements(mode.getDriverName(), getConnectionUrl(), username, password){
			public void __execute__() throws SQLException {
				watchDrop(stmt);
			};
		}.execute();
	}
	
	public final void create(){
		new Statements(mode.getDriverName(), getConnectionUrl(), username, password){
			public void __execute__() throws SQLException {
				watchCreate(stmt);
			};
		}.execute();
	}
	
	public final void dropAndCreate(){
		new Statements(mode.getDriverName(), getConnectionUrl(), username, password){
			public void __execute__() throws SQLException {
				watchDrop(stmt);
				watchCreate(stmt);
			};
		}.execute();
	}
	
	public abstract void __drop__(Statement statement) throws SQLException;
	
	public abstract void __create__(Statement statement) throws SQLException;
	
	public static EmbeddedMode createEmbeddedMode(String driverName,String path){
		return new EmbeddedMode(driverName, "h2", path);
	}
	
	public EmbeddedMode createEmbeddedMode(String driverName){
		return createEmbeddedMode(driverName,System.getProperty("user.dir"));
	}
	
	/* Persistence API Stuff */
	/* JPA */
	public Map<String,String> getJpaPersistenceUnitProperties(){
		Map<String,String> properties = new HashMap<String, String>();
		properties.put("javax.persistence.jdbc.driver",mode.getDriverName());
		properties.put("javax.persistence.jdbc.url",getConnectionUrl());
		properties.put("javax.persistence.jdbc.user",username);
		properties.put("javax.persistence.jdbc.password",password);
		//properties.put("eclipselink.ddl-generation","create-tables");
		//if(mode.getTargetDatabase()!=null)
		//	properties.put("eclipselink.target-database",mode.getTargetDatabase());
		return properties;
	}
	
	@Override
	public String toString() {
		return "Mode : "+mode.getClass().getSimpleName()+"\r\n"+mode+"\r\nName : "+name+"\r\n"+"Username : "+username+"\r\nPassword : "+password+
				"\r\nConnection URL : "+getConnectionUrl();
	}
}
