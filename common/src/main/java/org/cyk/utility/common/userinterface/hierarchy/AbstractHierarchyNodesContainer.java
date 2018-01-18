package org.cyk.utility.common.userinterface.hierarchy;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import org.cyk.utility.common.Constant;
import org.cyk.utility.common.Constant.Action;
import org.cyk.utility.common.helper.ArrayHelper;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.InstanceHelper;
import org.cyk.utility.common.helper.NumberHelper;
import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.common.userinterface.Component;
import org.cyk.utility.common.userinterface.collection.DataTable;
import org.cyk.utility.common.userinterface.container.Container;

@Getter @Setter @Accessors(chain=true)
public abstract class AbstractHierarchyNodesContainer<NODE extends AbstractHierarchyNodesContainer<?>> extends Container implements Serializable {
	private static final long serialVersionUID = 1L;

	protected Long numberOfChildren;
	protected Boolean isChildrenLoaded;
	
	protected abstract Class<NODE> getNodeClass();
	
	protected NODE instanciateNode(){
		return (NODE) ClassHelper.getInstance().instanciateOne(getNodeClass());
	}
	
	public NODE addNode(Object object,Long orderNumber){
		NODE node = instanciateNode();
		node.getPropertiesMap().setValue(object);
		node.setLabelFromIdentifier((String)InstanceHelper.getInstance().getIdentifier(object));
		node.set__orderNumber__(NumberHelper.getInstance().get(Long.class,orderNumber,0l));
		node.getPropertiesMap().setTopLevelContainer(getPropertiesMap().getTopLevelContainer());
		return addNode(node);
	}
	
	public void addNodes(Collection<Object> objects,Long orderNumber){
		if(CollectionHelper.getInstance().isNotEmpty(objects))
			for(Object index : objects)
				addNode(index, orderNumber++);
	}
	
	public NODE addNode(NODE node){
		addOneChild(node);
		return node;
	}
	
	public NODE addNode(String labelStringIdentifier,String pathIdentifier,Object...queryKeyValue){
		NODE node = instanciateNode();
		node.setLabelFromIdentifier(labelStringIdentifier);
		node._setPropertyUrl(pathIdentifier, queryKeyValue);
		addNode(node);
		return node;
	}
	
	public NODE addNode(String labelStringIdentifier,Constant.Action action,Object object,Object...queryKeyValue){
		NODE node = instanciateNode();
		node.setLabelFromIdentifier(labelStringIdentifier);
		node.getPropertiesMap().setAction(action);
		if(object instanceof Class<?>)
			node.getPropertiesMap().setClass(object);
		else
			node.getPropertiesMap().setInstance(object);
		node._setPropertyUrl(action,object, queryKeyValue);
		node.__setPropertyIconBasedOnPropertyAction__();
		addNode(node);
		return node;
	}
	
	public NODE addNode(String labelStringIdentifier){
		NODE node = instanciateNode();
		node.setLabelFromIdentifier(labelStringIdentifier);
		addNode(node);
		return node;
	}
	
	public NODE addNode(Constant.Action action,Object object,Object...queryKeyValue){
		NODE node =  addNode(null, action, object, queryKeyValue);
		if(object instanceof Class<?>)
			node._setLabelPropertyValue(StringHelper.getInstance().getClazz((Class<?>)object));
		else
			node.__setLabelValueBasedOnActionProperty__();
		return node;
	}
	
	public NODE addNodeActionCreate(Object object,Object...queryKeyValue){
		return addNode(Constant.Action.CREATE, object, queryKeyValue);
	}
	
	public NODE addNodeActionRead(Object object,Object...queryKeyValue){
		return addNode(Constant.Action.READ, object, queryKeyValue);
	}
	
	public NODE addNodeActionUpdate(Object object,Object...queryKeyValue){
		return addNode(Constant.Action.UPDATE, object, queryKeyValue);
	}
	
	public NODE addNodeActionDelete(Object object,Object...queryKeyValue){
		return addNode(Constant.Action.DELETE, object, queryKeyValue);
	}
	
	public NODE addNodeActionList(Object object,Object...queryKeyValue){
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
	
	public NODE addNodeActionListManyFromPackage(Package aPackage,String labelStringIdentifier){
		NODE node = addNode(labelStringIdentifier);
		node.addNodeActionListMany(ClassHelper.getInstance().getAnnotatedWithEntityAndPackageNameStartsWith(aPackage));
		node.getChildren().setComparator(new Component.LabelPropertyValueComparator()).sort();
		return node;
	}
	
	public NODE addNodeActionListManyFromPackage(Class<?> aClass,String labelStringIdentifier){
		return addNodeActionListManyFromPackage(aClass.getPackage(),labelStringIdentifier);
	}
	
	public void addNodeActionListManyFromPackage(Class<?>...classes){
		for(Class<?> aClass : classes){
			NODE node = addNodeActionListManyFromPackage(aClass,StringHelper.getInstance().getClassIdentifier(aClass));
			node._setLabelPropertyValue(StringHelper.getInstance().getClazz(aClass));
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public NODE _setPropertyUrl(Action action, Object object,Object... queryKeyValue) {
		return (NODE) super._setPropertyUrl(action, object, queryKeyValue);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public NODE _setLabelPropertyRendered(Object rendered) {
		return (NODE) super._setLabelPropertyRendered(rendered);
	}
	
	/**/
	
	public DataTable.Column addColumn(String labelStringIdentifier,String fieldName,DataTable.Column.CellValueSource cellValueSource){
		return DataTable.Columns.add(this, labelStringIdentifier, fieldName,cellValueSource);
	}
	
	public DataTable.Column addColumn(String labelStringIdentifier,String fieldName){
		return addColumn(labelStringIdentifier, fieldName, DataTable.Column.CellValueSource.DEFAULT);
	}
	
	public DataTable.Column addColumnByFieldName(String fieldName,DataTable.Column.CellValueSource cellValueSource){
		return DataTable.Columns.addByFieldName(this, fieldName,cellValueSource);
	}
	
	public DataTable.Column addColumnByFieldName(String fieldName){
		return addColumnByFieldName(fieldName, DataTable.Column.CellValueSource.DEFAULT);
	}
	
	@SuppressWarnings("unchecked")
	public NODE addColumnsByFieldNames(DataTable.Column.CellValueSource cellValueSource,String...fieldNames){
		DataTable.Columns.addByFieldNames(this,cellValueSource, fieldNames);
		return (NODE) this;
	}
	
	public NODE addColumnsByFieldNames(String...fieldNames){
		return addColumnsByFieldNames(DataTable.Column.CellValueSource.DEFAULT, fieldNames);
	}
}
