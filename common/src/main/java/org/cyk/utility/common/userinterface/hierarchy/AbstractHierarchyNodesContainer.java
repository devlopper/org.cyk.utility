package org.cyk.utility.common.userinterface.hierarchy;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

import org.cyk.utility.common.Constant;
import org.cyk.utility.common.helper.ArrayHelper;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.InstanceHelper;
import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.common.userinterface.collection.DataTable;
import org.cyk.utility.common.userinterface.container.Container;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public abstract class AbstractHierarchyNodesContainer<NODE extends AbstractHierarchyNodesContainer<?>> extends Container implements Serializable {
	private static final long serialVersionUID = 1L;

	protected abstract Class<NODE> getNodeClass();
	
	protected NODE instanciateNode(){
		return (NODE) ClassHelper.getInstance().instanciateOne(getNodeClass());
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
		node._setPropertyUrl(action,object, queryKeyValue);
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
		node.getLabel().getPropertiesMap().setValue(object instanceof Class<?> ? StringHelper.getInstance().getClazz((Class<?>)object) : InstanceHelper.getInstance().getLabel(object));
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
