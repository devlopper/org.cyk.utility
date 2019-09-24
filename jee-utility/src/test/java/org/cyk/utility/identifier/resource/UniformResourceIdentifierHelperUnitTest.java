package org.cyk.utility.identifier.resource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.cyk.utility.identifier.resource.UniformResourceIdentifierHelper.build;
import static org.cyk.utility.identifier.resource.UniformResourceIdentifierHelper.buildParameterValue;
import static org.cyk.utility.identifier.resource.UniformResourceIdentifierHelper.mapParameterValue;
import static org.cyk.utility.identifier.resource.UniformResourceIdentifierHelper.buildPath;
import static org.cyk.utility.identifier.resource.UniformResourceIdentifierHelper.buildPathIdentifier;
import static org.cyk.utility.identifier.resource.UniformResourceIdentifierHelper.buildQuery;
import static org.cyk.utility.identifier.resource.UniformResourceIdentifierHelper.getComponent;
import static org.cyk.utility.identifier.resource.UniformResourceIdentifierHelper.setPathByIdentifier;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.cyk.utility.__kernel__.identifier.resource.Component;
import org.cyk.utility.__kernel__.identifier.resource.ParameterName;
import org.cyk.utility.__kernel__.identifier.resource.QueryParameterValueGetter;
import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.__kernel__.object.dynamic.Objectable;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.action.SystemActionCreate;
import org.cyk.utility.system.action.SystemActionCreateImpl;
import org.cyk.utility.system.action.SystemActionDelete;
import org.cyk.utility.system.action.SystemActionList;
import org.cyk.utility.system.action.SystemActionRead;
import org.cyk.utility.system.action.SystemActionReadImpl;
import org.cyk.utility.system.action.SystemActionTree;
import org.cyk.utility.system.action.SystemActionUpdate;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class UniformResourceIdentifierHelperUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenBefore__() {
		super.__listenBefore__();
		UniformResourceIdentifierHelper.PATHS_MAP.clear();
		UniformResourceIdentifierHelper.PATHS_CACHE_MAP.clear();
		Component.PATH_ROOT = null;
	}
	
	@Test
	public void getComponent_null(){
		__assertComponents__(null, null, null, null, null, null, null, null, null);
	}
	
	@Test
	public void getComponent_http_user_pass_localhost_8080_mypath_q1v1_f01(){
		__assertComponents__("http://user:pass@localhost:8080/mypath?q1=v1#f01", "http", "user", "pass", "localhost", "8080", "/mypath", "q1=v1", "f01");
	}
	
	@Test
	public void getComponent_http_user_pass_localhost_8080_emptyPath(){
		__assertComponents__("http://user:pass@localhost:8080", "http", "user", "pass", "localhost", "8080", null, null, null);
	}
	
	@Test
	public void getComponent_http_user_pass_localhost_8080_pathWithSlashOnly(){
		__assertComponents__("http://user:pass@localhost:8080/", "http", "user", "pass", "localhost", "8080", "/", null, null);
	}
	
	@Test
	public void getComponent_http_user_pass_localhost_8080_pathWithSlashOnly_questionMark(){
		__assertComponents__("http://user:pass@localhost:8080/?", "http", "user", "pass", "localhost", "8080", "/", "", null);
	}

	@Test
	public void buildParameterValue__null(){
		assertThat(buildParameterValue(null)).isEqualTo(null);
	}
	
	@Test
	public void buildParameterValue__string(){
		assertThat(buildParameterValue("v01")).isEqualTo("v01");
	}
	
	@Test
	public void buildParameterValue__class(){
		assertThat(buildParameterValue(Class.class)).isEqualTo("class");
	}
	
	@Test
	public void buildParameterValue__class_system_action(){
		assertThat(buildParameterValue(SystemAction.class)).isEqualTo("action");
	}
	
	@Test
	public void buildParameterValue__class_system_action_create(){
		assertThat(buildParameterValue(SystemActionCreate.class)).isEqualTo("create");
	}
	
	@Test
	public void buildParameterValue__class_system_action_tree(){
		assertThat(buildParameterValue(SystemActionTree.class)).isEqualTo("tree");
	}
	
	@Test
	public void buildParameterValue__system_action_create_create(){
		assertThat(buildParameterValue(__inject__(SystemActionCreate.class))).isEqualTo("create");
	}
	
	@Test
	public void buildParameterValue__system_action_create_custom(){
		assertThat(buildParameterValue(__inject__(SystemActionCreate.class).setIdentifier("custom"))).isEqualTo("custom");
	}
	
	@Test
	public void buildParameterValue__system_action_list_list(){
		assertThat(buildParameterValue(__inject__(SystemActionList.class))).isEqualTo("list");
	}
	
	@Test
	public void buildParameterValue__system_action_tree_tree(){
		assertThat(buildParameterValue(__inject__(SystemActionTree.class))).isEqualTo("tree");
	}
	
	@Test
	public void buildParameterValue__identifier_MyId(){
		assertThat(buildParameterValue(__inject__(Klass.class).setIdentifier("MyId"))).isEqualTo("MyId");
	}
	
	@Test
	public void mapParameterValue_systemAction_create_myEntity_12(){
		ParameterName.ENTITY_CLASS.setType(MyEntity.class);
		ParameterName.MAP.put(SystemActionCreate.class, "create");
		ParameterName.MAP.put(MyEntity.class, "myentity");
		Object mappedValue = mapParameterValue(ParameterName.ACTION_CLASS.getValue(),new QueryParameterValueGetter() {			
			@Override
			public String get(String name) {
				if(name.equals("actionclass"))
					return "create";
				if(name.equals("actionidentifier"))
					return "create";
				if(name.equals("entityclass"))
					return "myentity";
				if(name.equals("entityidentifier"))
					return "12";
				return null;
			}
		});
		assertThat(mappedValue).isNotNull();
		assertThat(mappedValue).isInstanceOf(SystemActionCreate.class);
		assertThat(((SystemAction)mappedValue).getIdentifier()).isEqualTo("create");
		assertThat(((SystemAction)mappedValue).getEntityClass()).isEqualTo(MyEntity.class);
		assertThat(((SystemAction)mappedValue).getEntitiesIdentifiers()).isNotNull();
		assertThat(((SystemAction)mappedValue).getEntitiesIdentifiers().get()).contains("12");
	}
	
	/* query */
	
	@Test
	public void buildQuery_null(){
		assertThat(buildQuery((Map<Object,List<Object>>)null)).isEqualTo(null);
	}
	
	@Test
	public void buildQuery_classEqc01(){
		Map<Object,List<Object>> map = new LinkedHashMap<>();
		map.put(Class.class, Arrays.asList("c01"));
		assertThat(buildQuery(map)).isEqualTo("class=c01");
	}
	
	@Test
	public void buildQuery_systemActionClass_systemActionIdentifier_entityClass_entities_nextSystemActionClass_nextSystemActionIdentifier() {
		assertThat(buildQuery(SystemActionRead.class,null,MyEntity.class,Arrays.asList(new MyEntityImpl().setIdentifier(17)),null,null,null))
			.isEqualTo("entityclass=myentity&entityidentifier=17&actionclass=read&actionidentifier=read");
	}
	
	@Test
	public void buildQuery_systemActionRead_myEntity() {
		SystemAction systemAction = __inject__(SystemActionRead.class);
		systemAction.getEntities(Boolean.TRUE).setElementClass(MyEntity.class);
		systemAction.getEntities(Boolean.TRUE).add(__inject__(MyEntity.class).setIdentifier("123"));
		assertThat(buildQuery(systemAction)).isEqualTo("entityclass=myentity&entityidentifier=123&actionclass=read&actionidentifier=read");
	}
	
	/* path */
	
	@Test
	public void buildPathIdentifier_systemAction_null_entityClass_null_isRecurise_null() {
		assertThat(buildPathIdentifier(null,null,null)).isEqualTo(null);
	}
	
	@Test
	public void buildPathIdentifier_systemAction_null_entityClass_null_isRecurise_true() {
		assertThat(buildPathIdentifier(null,null,Boolean.TRUE)).isEqualTo(null);
	}
	
	@Test
	public void buildPathIdentifier_systemAction_null_entityClass_myEntity_isRecurise_null() {
		assertThat(buildPathIdentifier(null,MyEntity.class,null)).isEqualTo(null);
	}
	
	@Test
	public void buildPathIdentifier_systemAction_null_entityClass_myEntity_isRecurise_true() {
		assertThat(buildPathIdentifier(null,MyEntity.class,Boolean.TRUE)).isEqualTo(null);
	}
	
	@Test
	public void buildPathIdentifier_systemAction_create_entityClass_null_isRecurise_null() {
		assertThat(buildPathIdentifier(SystemActionCreate.class,null,null)).isEqualTo("__entity__CreateView");
	}
	
	@Test
	public void buildPathIdentifier_systemAction_create_entityClass_null_isRecurise_true() {
		assertThat(buildPathIdentifier(SystemActionCreate.class,null,Boolean.TRUE)).isEqualTo("__entity__EditView");
	}
	
	@Test
	public void buildPathIdentifier_systemAction_create_entityClass_myEntity_isRecurise_null() {
		assertThat(buildPathIdentifier(SystemActionCreate.class,MyEntity.class,null)).isEqualTo("myEntityCreateView");
	}
	
	@Test
	public void buildPathIdentifier_systemAction_create_entityClass_myEntity_isRecurise_true() {
		assertThat(buildPathIdentifier(SystemActionCreate.class,MyEntity.class,Boolean.TRUE)).isEqualTo("myEntityEditView");
	}
	
	@Test
	public void buildPathIdentifier_myEntityCreateView() {
		SystemAction systemAction = __inject__(SystemActionCreate.class);
		systemAction.getEntities(Boolean.TRUE).setElementClass(MyEntity.class);
		assertThat(buildPathIdentifier(systemAction)).isEqualTo("myEntityCreateView");
	}
	
	@Test
	public void buildPathIdentifier_myEntityEditView() {
		assertThat(buildPathIdentifier(SystemActionCreate.class,MyEntity.class,Boolean.TRUE)).isEqualTo("myEntityEditView");
	}
	
	@Test
	public void buildPathIdentifier_systemActionCreate() {
		SystemAction systemAction = __inject__(SystemActionCreate.class);
		assertThat(buildPathIdentifier(systemAction)).isEqualTo("__entity__EditView");
	}
	
	@Test
	public void buildPathIdentifier_systemActionCreateImpl() {
		SystemAction systemAction = __inject__(SystemActionCreateImpl.class);
		assertThat(buildPathIdentifier(systemAction)).isEqualTo("__entity__EditView");
	}
	
	@Test
	public void buildPathIdentifier_myEntityUpdateView() {
		SystemAction systemAction = __inject__(SystemActionUpdate.class);
		systemAction.getEntities(Boolean.TRUE).setElementClass(MyEntity.class);
		assertThat(buildPathIdentifier(systemAction)).isEqualTo("myEntityUpdateView");
	}
	
	@Test
	public void buildPathIdentifier_updateView() {
		SystemAction systemAction = __inject__(SystemActionUpdate.class);
		assertThat(buildPathIdentifier(systemAction)).isEqualTo("__entity__EditView");
	}
	
	@Test
	public void buildPathIdentifier_myEntityDeleteView() {
		SystemAction systemAction = __inject__(SystemActionDelete.class);
		systemAction.getEntities(Boolean.TRUE).setElementClass(MyEntity.class);
		assertThat(buildPathIdentifier(systemAction)).isEqualTo("myEntityDeleteView");
	}
	
	@Test
	public void buildPathIdentifier_deleteView() {
		SystemAction systemAction = __inject__(SystemActionDelete.class);
		assertThat(buildPathIdentifier(systemAction)).isEqualTo("__entity__EditView");
	}
	
	@Test
	public void buildPathIdentifier_myEntityListView() {
		SystemAction systemAction = __inject__(SystemActionList.class);
		systemAction.getEntities(Boolean.TRUE).setElementClass(MyEntity.class);
		assertThat(buildPathIdentifier(systemAction)).isEqualTo("myEntityListView");
	}
	
	@Test
	public void buildPathIdentifier_listView() {
		SystemAction systemAction = __inject__(SystemActionList.class);
		assertThat(buildPathIdentifier(systemAction)).isEqualTo("__entity__ListView");
	}
	
	@Test
	public void buildPathIdentifier_treeView() {
		SystemAction systemAction = __inject__(SystemActionTree.class);
		assertThat(buildPathIdentifier(systemAction)).isEqualTo("__entity__TreeView");
	}
	
	@Test
	public void buildPath_null(){
		assertThat(buildPath(null,null)).isEqualTo(null);
	}
	
	@Test
	public void buildPath_identifier_i01(){
		setPathByIdentifier("i01", "/p01/p02");
		assertThat(buildPath("i01",null)).isEqualTo("/p01/p02");
	}
	
	@Test
	public void buildPath_identifier_i01_defaultContext(){
		Component.PATH_ROOT = "mycontext";
		setPathByIdentifier("i01", "/p01/p02");
		assertThat(buildPath("i01",null)).isEqualTo("/mycontext/p01/p02");
	}
	
	@Test
	public void buildPath_context_c01(){
		assertThat(buildPath(null,"c01")).isEqualTo("/c01");
	}
	
	@Test
	public void buildPath_context_c01_identifier_01(){
		setPathByIdentifier("i01", "/folder/sub");
		assertThat(buildPath("i01","c01")).isEqualTo("/c01/folder/sub");
	}
	
	@Test
	public void buildPath_context_c01_slash_identifier_01(){
		setPathByIdentifier("i01", "/folder/sub");
		assertThat(buildPath("i01","c01/")).isEqualTo("/c01/folder/sub");
	}
	
	@Test
	public void buildPath_context_c01_slash_identifier_01_slash(){
		setPathByIdentifier("i01", "/folder/sub/");
		assertThat(buildPath("i01","c01/")).isEqualTo("/c01/folder/sub/");
	}
	
	@Test
	public void buildPath_systemAction_create_entityClassIsSet(){
		setPathByIdentifier("myEntityCreateView", "/myentity/create");
		assertThat(buildPath(__inject__(SystemActionCreate.class).setEntityClass(MyEntity.class))).isEqualTo("/myentity/create");
	}
	
	@Test
	public void buildPath_systemAction_create_entityClassIsSet_generic(){
		Component.PATH_ROOT = "/mycontext";
		setPathByIdentifier("__entity__EditView", "/__entity__/edit");
		assertThat(buildPath(__inject__(SystemActionCreate.class).setEntityClass(MyEntity.class))).isEqualTo("/mycontext/__entity__/edit");
	}
	
	@Test
	public void buildPath_systemAction_create_entityClassIsSet_notFound(){
		setPathByIdentifier("__entity__EditView", "/__entity__/edit");
		assertThat(buildPath(__inject__(SystemActionCreate.class).setEntityClass(MyEntity.class))).isEqualTo("/__entity__/edit");
	}
	
	/**/
	
	@Test
	public void build_http_localhost_8080(){
		assertThat(build("http", null, null, "localhost", 8080, null, null, null, null)).isEqualTo("http://localhost:8080");
	}
	
	@Test
	public void build_http_localhost(){
		assertThat(build("http", null, null, "localhost", (Integer)null, null, null, null, null)).isEqualTo("http://localhost");
	}
	
	@Test
	public void build_http_localhost_query_a_equal_b(){
		assertThat(build("http", null, null, "localhost", null, null, "a=b", null, null)).isEqualTo("http://localhost?a=b");
	}
	
	@Test
	public void build_https_www_google_com(){
		assertThat(build("https", null, null, "www.google.com", (Integer)null, null, null, null, null)).isEqualTo("https://www.google.com");
	}
	
	@Test
	public void build_http_localhost_8080_slash(){
		assertThat(build("http", null, null, "localhost", 8080, "/", null, null, null)).isEqualTo("http://localhost:8080/");
	}
	
	@Test
	public void build_http_localhost_8080_folder(){
		assertThat(build("http", null, null, "localhost", 8080, "folder", null, null, null)).isEqualTo("http://localhost:8080/folder");
	}
	
	@Test
	public void build_http_localhost_8080_slash_folder(){
		assertThat(build("http", null, null, "localhost", 8080, "/folder", null, null, null)).isEqualTo("http://localhost:8080/folder");
	}
	
	@Test
	public void build_http_localhost_8080_app_folder(){
		assertThat(build("http", null, null, "localhost", 8080, "app/folder", null, null, null)).isEqualTo("http://localhost:8080/app/folder");
	}
	
	@Test
	public void build_http_localhost_8080_app_slash_folder(){
		assertThat(build("http", null, null, "localhost", 8080, "/app/folder", null, null, null)).isEqualTo("http://localhost:8080/app/folder");
	}
	
	@Test
	public void build_ftp_wwwCykDotCom_app_slash_folder(){
		assertThat(build("http", null, null, "localhost", 80, "app/folder", null, null, null)).isEqualTo("http://localhost/app/folder");
	}
	
	@Test
	public void build_usingSystemActionCreateMyEntity(){
		setPathByIdentifier("__entity__EditView", "/__entity__/edit");
		SystemAction systemAction = __inject__(SystemActionCreate.class).setEntityClass(MyEntity.class);
		assertThat(build("http", "localhost", 80, systemAction)).isEqualTo("http://localhost/__entity__/edit?entityclass=myentity&actionclass=create&actionidentifier=create");
	}
	
	@Test
	public void build_usingSystemActionCreateMyEntity_callTwice(){
		setPathByIdentifier("__entity__EditView", "/__entity__/edit");
		SystemAction systemAction = __inject__(SystemActionCreate.class).setEntityClass(MyEntity.class);
		assertThat(build("http", "localhost", 80, systemAction)).isEqualTo("http://localhost/__entity__/edit?entityclass=myentity&actionclass=create&actionidentifier=create");
		assertThat(build("http", "localhost", 80, systemAction)).isEqualTo("http://localhost/__entity__/edit?entityclass=myentity&actionclass=create&actionidentifier=create");
	}
	
	@Test
	public void build_systemActionClassRead_entityClassMyEntity(){
		setPathByIdentifier("myEntityReadView", "/__entity__/edit");
		assertThat(build("http", "localhost", 80,SystemActionRead.class ,null,MyEntity.class,Arrays.asList(new MyEntityImpl().setIdentifier(12)),null,null,null))
			.isEqualTo("http://localhost/__entity__/edit?entityclass=myentity&entityidentifier=12&actionclass=read&actionidentifier=read");
	}
	
	@Test
	public void build_systemActionClassReadImpl_entityClassMyEntityImpl(){
		setPathByIdentifier("myEntityReadView", "/__entity__/edit");
		Collection<MyEntityImpl> list = new ArrayList<>();
		MyEntityImpl entity = new MyEntityImpl();
		entity.setIdentifier(12);
		list.add(entity);
		assertThat(build("http", "localhost", 80,SystemActionReadImpl.class ,null,MyEntityImpl.class,list,null,null,null))
			.isEqualTo("http://localhost/__entity__/edit?entityclass=myentity&entityidentifier=12&actionclass=read&actionidentifier=read");
	}
	
	/**/
	
	private void __assertComponents__(String uniformResourceIdentifier,String expectedScheme,String expectedUser,String expectedPassword,String expectedHost,String expectedPort
			,String expectedPath,String expectedQuery,String expectedFragment) {
		__assertComponent__(uniformResourceIdentifier, Component.SCHEME, expectedScheme);
		__assertComponent__(uniformResourceIdentifier, Component.USER, expectedUser);
		__assertComponent__(uniformResourceIdentifier, Component.PASSWORD, expectedPassword);
		__assertComponent__(uniformResourceIdentifier, Component.HOST, expectedHost);
		__assertComponent__(uniformResourceIdentifier, Component.PORT, expectedPort);
		__assertComponent__(uniformResourceIdentifier, Component.PATH, expectedPath);
		__assertComponent__(uniformResourceIdentifier, Component.QUERY, expectedQuery);
		__assertComponent__(uniformResourceIdentifier, Component.FRAGMENT, expectedFragment);
	}
	
	private void __assertComponent__(String uniformResourceIdentifier,Component component,String expected) {
		assertThat(getComponent(component,uniformResourceIdentifier)).as("%s of %s", component,uniformResourceIdentifier).isEqualTo(expected);
	}
	
	public static class Klass extends AbstractObject {
		private static final long serialVersionUID = 1L;
			
	}
	
	private static interface MyEntity extends Objectable {
		
	}
	
	private static class MyEntityImpl extends AbstractObject implements MyEntity,Serializable {
		private static final long serialVersionUID = 1L;
		
		@Override
		public MyEntity setIdentifier(Object identifier) {
			return (MyEntity) super.setIdentifier(identifier);
		}
	}
}
