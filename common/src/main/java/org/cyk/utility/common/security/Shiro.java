package org.cyk.utility.common.security;

import java.io.Serializable;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.helper.MapHelper;
import org.cyk.utility.common.helper.StringHelper;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class Shiro extends AbstractBean implements Serializable {
	private static final long serialVersionUID = 1L;

	
	@Getter @Setter @Accessors(chain=true)
	public static class Ini extends org.apache.shiro.config.Ini implements Serializable {
		private static final long serialVersionUID = 1L;

		private static final Shiro.Ini INSTANCE = new Shiro.Ini();
		
		//private DatasourceHelper.Datasource datasource = new DatasourceHelper.Datasource();
		//private Class<?> realmClass,filterClass,cacheManagerClass;
		private String user = "user",cacheManager="cacheManager",securityManager="securityManager",realm="realm",dataSource="dataSource";
		
		private Ini() {}
		
		public Ini initialize() {
			//Section  main = addSection("main");
			/*putClass(main, FILTER_VAR, filterClass);
			putClass(main, REALM_VAR, realmClass);
			putClass(main, CACHE_MANAGER_VAR, cacheManagerClass);
			*/
			
			//main.put("user.loginUrl", "/public/security/login.jsf");
			
			/*main.put("securityManager."+CACHE_MANAGER_VAR, "$"+CACHE_MANAGER_VAR);		
			main.put(DATA_SOURCE_VAR, datasource.getService().getDriver());
			main.put(DATA_SOURCE_VAR+".URL", datasource.computeUrl());
			main.put(DATA_SOURCE_VAR+".user", datasource.getCredentials().getUsername());
			main.put(DATA_SOURCE_VAR+".password", datasource.getCredentials().getPassword());
			
			main.put(REALM_VAR+"."+DATA_SOURCE_VAR, "$"+DATA_SOURCE_VAR);
			*/
			/*
			Section urls = addSection("urls");
			urls.put("/public/security/login.jsf", user);
			urls.put("/private/**", user);
			*/
			if(Constant.ApplicationType.STAND_ALONE.equals(Constant.ApplicationType.DEFAULT)){
				Factory<SecurityManager> factory = new IniSecurityManagerFactory(this);
			    SecurityManager securityManager = factory.getInstance();
			    SecurityUtils.setSecurityManager(securityManager);
			}
			
			return this;
		}
		
		protected Section __getSection__(String name){
			Section section = getSection(name);
			if(section == null)
				section = addSection(name);
			return section;
		}
	
		public Ini addUsers(Object...credentials){
			Section section = __getSection__(SECTION_USERS);
			MapHelper.getInstance().addKeyValue(section, credentials);
			return this;
		}
		
		public Ini addRoles(Object...roles){
			Section section = __getSection__(SECTION_ROLES);
			MapHelper.getInstance().addKeyValue(section, roles);
			return this;
		}
		
		public Ini addUrls(Object...urls){
			Section section = __getSection__(SECTION_URLS);
			MapHelper.getInstance().addKeyValue(section, urls);
			return this;
		}
		
		public Ini addUrlsForUser(String...urls){
			Object[] objects = new Object[urls.length*2];
			for(int i = 0 , j = 0 ; i < urls.length ; i++ , j = j+2){
				objects[j] = urls[i];
				objects[j+1] = user;
			}
			addUrls(objects);
			return this;
		}
		
		public Ini addFoldersForUser(String...folders){
			for(int i = 0 ; i < folders.length ; i++){
				folders[i] = StringHelper.getInstance().addAtBeginingIfDoesNotStartWith(folders[i], "/");
				folders[i] = StringHelper.getInstance().appendIfDoesNotEndWith(folders[i], "/");
				folders[i] = StringHelper.getInstance().appendIfDoesNotEndWith(folders[i], "**");
			}
			addUrlsForUser(folders);
			return this;
		}
		
		public Ini addLoginUrl(String url){
			Section section = __getSection__(SECTION_MAIN);
			section.put(user+".loginUrl", url);
			return this;
		}
		
		public Ini addCache(String className){
			Section section = __getSection__(SECTION_MAIN);
			section.put("cacheManager", className);
			section.put("securityManager.cacheManager", "$cacheManager");
			return this;
		}
		
		public Ini addDatasource(String driverClassName,String url,String user,String password){
			Section section = __getSection__(SECTION_MAIN);
			section.put(dataSource, driverClassName);
			section.put(dataSource+".url", url);
			section.put(dataSource+".user", user);
			section.put(dataSource+".password", password);
			return this;
		}
		
		public Ini addRealmJdbc(String className,String authenticationQuery,String userRolesQuery){
			Section section = __getSection__(SECTION_MAIN);
			section.put(realm, className);
			section.put(realm+".dataSource", "$"+dataSource);
			section.put(realm+".authenticationQuery", authenticationQuery);
			section.put(realm+".userRolesQuery", userRolesQuery);
			return this;
		}
		
		protected void putClass(Section section,String name,Class<?> aClass){
			if(aClass!=null)
				section.put(name,aClass.getName());
		}
		
		public static Shiro.Ini getInstance() {
			return INSTANCE;
		}
		
		public String getProperty(String section,String name){
			return getSection(section).get(name);
		}
		
		public Ini clean(){
			clear();
			return this;
		}
		
		@Override
		public String toString() {
			StringBuilder stringBuilder = new StringBuilder();
			for(org.apache.shiro.config.Ini.Section section : getSections()){
				stringBuilder.append(section.getName()+"\r\n");
				for(Map.Entry<String, String> entry : section.entrySet())
					stringBuilder.append(entry.getKey()+"="+entry.getValue()+"\r\n");
			}
			return stringBuilder.toString();
		}
		
		/**/
		
		public static final String SECTION_MAIN = "main";
		public static final String SECTION_USERS = "users";
		public static final String SECTION_ROLES = "roles";
		public static final String SECTION_URLS = "urls";
		
		public static final String FILTER_VAR = "user";
		public static final String REALM_VAR = "realm";
		public static final String CACHE_MANAGER_VAR = "cacheManager";
		public static final String DATA_SOURCE_VAR = "dataSource";
		
		//public static final String AUTHORISATION_FILTER = "org.cyk.ui.web.api.security.shiro.AuthorisationFilter";
		//public static final String REALM = "org.cyk.ui.web.api.security.shiro.Realm";
		//public static final String CACHE_MANAGER = "org.apache.shiro.cache.ehcache.EhCacheManager";
		
		public static final String ROLES_FORMAT = "%s , roles[%s]";
		public static final String PERMISSIONS_FORMAT = "%s , perms[%s]";
		
	}
	
	public static interface Realm {
		
		public static class Jdbc extends org.apache.shiro.realm.jdbc.JdbcRealm implements Serializable {
			private static final long serialVersionUID = -659895508700873034L;
			
			public static String QUERY_AUTHENTICATION = "QUERY_AUTHENTICATION";
			public static String QUERY_USER_ROLES = "QUERY_USER_ROLES";
			public static String QUERY_PERMISSIONS = "QUERY_PERMISSIONS";
			public static Boolean PERMISSIONS_LOOKUP_ENABLED = Boolean.FALSE;
			
			public Jdbc() {
				authenticationQuery = QUERY_AUTHENTICATION;
				userRolesQuery = QUERY_USER_ROLES;
				permissionsQuery = QUERY_PERMISSIONS;
				permissionsLookupEnabled=PERMISSIONS_LOOKUP_ENABLED;
			}
		}
		
	}

	/**/
	
	public static class Adapter extends SecurityHelper.Listener.Adapter.Default implements Serializable {
		private static final long serialVersionUID = 1L;
		
		@Override
		public void initialize() {
			Ini.INSTANCE.initialize();
		}
		
		@Override
		public void login(SecurityHelper.Credentials credentials) {
			UsernamePasswordToken token = new UsernamePasswordToken(credentials.getUsername(), credentials.getPassword());
			getUser().login(token);
		}
		
		@Override
		public void logout() {
			getUser().logout();
		}
		
		@Override
		public Boolean getIsAuthenticated() {
			return getUser().isAuthenticated();
		}
		
		@Override
		public Object getPrincipal() {
			return getUser().getPrincipal();
		}
		
		@Override
		public Boolean getHasRole(String role) {
			return getUser().hasRole(role);
		}
		
		/**/
		
		protected Subject getUser(){
			return SecurityUtils.getSubject();
		}
	}
	
}
