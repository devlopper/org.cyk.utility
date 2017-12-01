package org.cyk.utility.common.security;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

public class ShiroUnitTest extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
	
	static {
		ClassHelper.getInstance().map(SecurityHelper.Listener.class, Listener.class);
		
	}
	
	@Test
	public void getShiroConfigurationStaticUsers(){
		Constant.ApplicationType.DEFAULT = Constant.ApplicationType.STAND_ALONE;
		Shiro.Ini ini = Shiro.Ini.getInstance().clean();
		ini.addUsers("root", "secret, admin","guest", "guest, guest","presidentskroob"
				, "12345, president","darkhelmet", "ludicrousspeed, darklord, schwartz"
				, "lonestarr", "vespa, goodguy, schwartz");
		
		ini.addRoles("admin", "*","schwartz", "lightsaber:*","goodguy", "winnebago:drive:eagle5");
		
		SecurityHelper.getInstance().initialize();
		
		assertEquals("secret, admin", ini.getProperty("users", "root"));
		assertEquals("lightsaber:*", ini.getProperty("roles", "schwartz"));
		
		connect();
	}
	
	@Test
	public void getShiroConfigurationJdbcMySql(){
		createDatabase("jdbc:mysql://localhost:3306/cyk_memory_db", "root", "root");
		
		Constant.ApplicationType.DEFAULT = Constant.ApplicationType.STAND_ALONE;
		Shiro.Ini ini = Shiro.Ini.getInstance().clean();
		
		ini.addDatasource("com.mysql.jdbc.jdbc2.optional.MysqlDataSource", "jdbc:mysql://localhost:3306/cyk_memory_db", "root", "root");
		ini.addRealmJdbc(org.apache.shiro.realm.jdbc.JdbcRealm.class.getName(), "SELECT PASSWORD FROM ACCOUNT WHERE USERNAME = ?"
				, "SELECT rid FROM USERROLES WHERE USERID = (SELECT identifier FROM ACCOUNT WHERE USERNAME = ?)");
		ini.addCache(org.apache.shiro.cache.MemoryConstrainedCacheManager.class.getName());
		
		SecurityHelper.getInstance().initialize();
		
		assertEquals("jdbc:mysql://localhost:3306/cyk_memory_db", ini.getProperty("main","dataSource.url"));
		assertEquals("org.apache.shiro.realm.jdbc.JdbcRealm", ini.getProperty("main","realm"));
		assertEquals("com.mysql.jdbc.jdbc2.optional.MysqlDataSource", ini.getProperty("main","dataSource"));
		assertEquals("root", ini.getProperty("main","dataSource.user"));
		assertEquals("root", ini.getProperty("main","dataSource.password"));
		assertEquals("$dataSource", ini.getProperty("main","realm.dataSource"));
		//assertEquals("SELECT PASSWORD FROM COMPTE WHERE USERNAME = ?", ini.getProperty("main","realm.authenticationQuery"));
		//assertEquals("SELECT ROLE FROM USERROLES WHERE USERID = (SELECT ID FROM COMPTE WHERE USERNAME = ?)", ini.getProperty("main","realm.userRolesQuery"));
		//assertEquals("org.apache.shiro.cache.MemoryConstrainedCacheManager", ini.getProperty("main","cacheManager"));
		assertEquals("$cacheManager", ini.getProperty("main","securityManager.cacheManager"));
		
		connect();
	}
	
	@Test
	public void getShiroConfigurationJdbcHsql(){
		createDatabase("jdbc:hsqldb:mem:mymemdb", "SA", "");
		
		Constant.ApplicationType.DEFAULT = Constant.ApplicationType.STAND_ALONE;
		Shiro.Ini ini = Shiro.Ini.getInstance().clean();
		
		//ini.addLoginUrl("/mypathto/myloginpage.html");
		
		ini.addDatasource("org.hsqldb.jdbc.JDBCDataSource", "jdbc:hsqldb:mem:mymemdb", "SA", "");
		ini.addRealmJdbc(org.apache.shiro.realm.jdbc.JdbcRealm.class.getName(), "SELECT PASSWORD FROM ACCOUNT WHERE USERNAME = ?"
				, "SELECT rid FROM USERROLES WHERE USERID = (SELECT identifier FROM ACCOUNT WHERE USERNAME = ?)");
		ini.addCache(org.apache.shiro.cache.MemoryConstrainedCacheManager.class.getName());
		
		SecurityHelper.getInstance().initialize();
		
		assertEquals("jdbc:hsqldb:mem:mymemdb", ini.getProperty("main","dataSource.url"));
		assertEquals("org.apache.shiro.realm.jdbc.JdbcRealm", ini.getProperty("main","realm"));
		assertEquals("org.hsqldb.jdbc.JDBCDataSource", ini.getProperty("main","dataSource"));
		assertEquals("SA", ini.getProperty("main","dataSource.user"));
		assertEquals("", ini.getProperty("main","dataSource.password"));
		assertEquals("$dataSource", ini.getProperty("main","realm.dataSource"));
		assertEquals("SELECT PASSWORD FROM ACCOUNT WHERE USERNAME = ?", ini.getProperty("main","realm.authenticationQuery"));
		assertEquals("SELECT rid FROM USERROLES WHERE USERID = (SELECT identifier FROM ACCOUNT WHERE USERNAME = ?)", ini.getProperty("main","realm.userRolesQuery"));
		assertEquals("org.apache.shiro.cache.MemoryConstrainedCacheManager", ini.getProperty("main","cacheManager"));
		assertEquals("$cacheManager", ini.getProperty("main","securityManager.cacheManager"));
		
		connect();

	}
	
	private void createDatabase(String url,String username,String password){
		try {
			Connection connection = (Connection) DriverManager.getConnection(url,username,password);
			Statement statement = connection.createStatement();
			statement.execute("DROP TABLE IF EXISTS USERROLES");
			statement.execute("DROP TABLE IF EXISTS ACCOUNT");
			statement.execute("DROP TABLE IF EXISTS ROLE");
			
			statement.execute("CREATE TABLE ACCOUNT (identifier int,username varchar(255),password varchar(255))");
			statement.execute("INSERT INTO ACCOUNT VALUES(159,'lonestarr','vespa')");
			
			statement.execute("CREATE TABLE ROLE (thename varchar(255))");
			statement.execute("INSERT INTO ROLE VALUES('goodguy')");
			statement.execute("INSERT INTO ROLE VALUES('schwartz')");
			
			statement.execute("CREATE TABLE USERROLES (identifier int,USERID int,rid varchar(255))");
			statement.execute("INSERT INTO USERROLES VALUES(1,159,'goodguy')");
			statement.execute("INSERT INTO USERROLES VALUES(1,159,'schwartz')");
		} catch (SQLException exception) {
			throw new RuntimeException(exception);
		}
	}
	
	private void connect(){
		assertFalse(SecurityHelper.getInstance().getIsAuthenticated());
		SecurityHelper.getInstance().login(new SecurityHelper.Credentials().setUsername("lonestarr").setPassword("vespa"));
		assertTrue(SecurityHelper.getInstance().getIsAuthenticated());
	    assertEquals("lonestarr", SecurityHelper.getInstance().getPrincipal());
	    assertFalse(SecurityHelper.getInstance().getHasRole("role1"));
	    assertTrue(SecurityHelper.getInstance().getHasRole("goodguy"));
	    assertTrue(SecurityHelper.getInstance().getHasRole("schwartz"));
	    
	    SecurityHelper.getInstance().logout();
	    
	    assertFalse(SecurityHelper.getInstance().getIsAuthenticated());
	    assertEquals(null, SecurityHelper.getInstance().getPrincipal());
	}
	
	/**/
	
	public static class Listener extends Shiro.Adapter implements Serializable {
		private static final long serialVersionUID = 1L;

		@Override
		public void initialize() {
			
			
			super.initialize();
		}
		
		@Override
		public void login(SecurityHelper.Credentials credentials) {
			UsernamePasswordToken token = new UsernamePasswordToken(credentials.getUsername(), credentials.getPassword());
			SecurityUtils.getSubject().login(token);
		}
		
		@Override
		public void logout() {
			SecurityUtils.getSubject().logout();
		}
		
		@Override
		public Boolean getIsAuthenticated() {
			return SecurityUtils.getSubject().isAuthenticated();
		}
		
		@Override
		public Object getPrincipal() {
			return SecurityUtils.getSubject().getPrincipal();
		}
		
		@Override
		public Boolean getHasRole(String role) {
			return SecurityUtils.getSubject().hasRole(role);
		}
		
	}

	
}
