package org.cyk.utility.common.userinterface.command;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

import org.cyk.utility.common.Constant;
import org.cyk.utility.common.helper.ArrayHelper;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.InstanceHelper;
import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.common.userinterface.container.Container;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class MenuContainer extends Container implements Serializable {
	private static final long serialVersionUID = 1L;

	public MenuNode addNode(String labelStringIdentifier,String pathIdentifier,Object...queryKeyValue){
		MenuNode menuNode = new MenuNode();
		menuNode.setLabelFromIdentifier(labelStringIdentifier);
		menuNode._setPropertyUrl(pathIdentifier, queryKeyValue);
		addOneChild(menuNode);
		return menuNode;
	}
	
	public MenuNode addNode(String labelStringIdentifier,Constant.Action action,Object object,Object...queryKeyValue){
		MenuNode menuNode = new MenuNode();
		menuNode.setLabelFromIdentifier(labelStringIdentifier);
		menuNode._setPropertyUrl(action,object, queryKeyValue);
		addOneChild(menuNode);
		return menuNode;
	}
	
	public MenuNode addNode(String labelStringIdentifier){
		MenuNode menuNode = new MenuNode();
		menuNode.setLabelFromIdentifier(labelStringIdentifier);
		addOneChild(menuNode);
		return menuNode;
	}
	
	public MenuNode addNode(MenuNode menuNode){
		addOneChild(menuNode);
		return menuNode;
	}
	
	public MenuNode addNode(Constant.Action action,Object object,Object...queryKeyValue){
		MenuNode node =  addNode(null, action, object, queryKeyValue);
		node.getLabel().getPropertiesMap().setValue(object instanceof Class<?> ? StringHelper.getInstance().getClazz((Class<?>)object) : InstanceHelper.getInstance().getLabel(object));
		return node;
	}
	
	public MenuNode addNodeActionCreate(Object object,Object...queryKeyValue){
		return addNode(Constant.Action.CREATE, object, queryKeyValue);
	}
	
	public MenuNode addNodeActionRead(Object object,Object...queryKeyValue){
		return addNode(Constant.Action.READ, object, queryKeyValue);
	}
	
	public MenuNode addNodeActionUpdate(Object object,Object...queryKeyValue){
		return addNode(Constant.Action.UPDATE, object, queryKeyValue);
	}
	
	public MenuNode addNodeActionDelete(Object object,Object...queryKeyValue){
		return addNode(Constant.Action.DELETE, object, queryKeyValue);
	}
	
	public MenuNode addNodeActionList(Object object,Object...queryKeyValue){
		return addNode(Constant.Action.LIST, object, queryKeyValue);
	}
	
	public void addNode(Constant.Action action,Collection<?> objects){
		if(CollectionHelper.getInstance().isNotEmpty(objects))
			for(Object object : objects)
				addNode(action,object);
	}
	
	public void addNodeActionListMany(Collection<?> objects){
		addNode(Constant.Action.LIST,objects);
	}
	
	public void addNodeActionListMany(Class<?>...classes){
		if(ArrayHelper.getInstance().isNotEmpty(classes))
			addNodeActionListMany(Arrays.asList(classes));
	}
	
	public void addNodeActionListManyFromPackage(Package aPackage,String labelStringIdentifier){
		addNode(labelStringIdentifier).addNodeActionListMany(ClassHelper.getInstance().getAnnotatedWithEntityAndPackageNameStartsWith(aPackage));
	}
	
	public void addNodeActionListManyFromPackage(Class<?> aClass,String labelStringIdentifier){
		addNodeActionListManyFromPackage(aClass.getPackage(),labelStringIdentifier);
	}
	
	public void addNodeActionListManyFromPackage(Class<?>...classes){
		for(Class<?> aClass : classes)
			addNodeActionListManyFromPackage(aClass,StringHelper.getInstance().getClassIdentifier(aClass));
	}
}
