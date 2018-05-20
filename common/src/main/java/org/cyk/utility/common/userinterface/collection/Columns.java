package org.cyk.utility.common.userinterface.collection;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.common.Properties;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.FieldHelper;
import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.common.userinterface.Component;
import org.cyk.utility.common.userinterface.collection.Column.CellValueSource;
import org.cyk.utility.common.userinterface.output.OutputText;

public class Columns extends Component implements Serializable {
	private static final long serialVersionUID = 1L;

	/**/
	
	@Override
	protected void listenPropertiesInstanciated(Properties propertiesMap) {
		super.listenPropertiesInstanciated(propertiesMap);
		propertiesMap.setHeaderRendered(Boolean.TRUE);
		propertiesMap.setGetter(Properties.FOOTER_RENDERED, new Properties.Getter() {
			@Override
			public Object execute(Properties properties, Object key, Object value, Object nullValue) {
				@SuppressWarnings("unchecked")
				Collection<Column> columns = (Collection<Column>) getPropertiesMap().getValue();
				for(Column column : columns)
					if(column.getPropertiesMap().getFooter()!=null){
						if(column.getPropertiesMap().getFooter() instanceof OutputText && ((OutputText)column.getPropertiesMap().getFooter()).getPropertiesMap().getValue()!=null)
							return Boolean.TRUE;
					}
				return Boolean.FALSE;
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	public Columns(Component component) {
		getPropertiesMap().setAction(component.getPropertiesMap().getAction());
		getPropertiesMap().setCellListener(component.getPropertiesMap().getCellListener());
		getPropertiesMap().setDataTable(component);
		if(component.getPropertiesMap().getColumnsCollectionInstanceListenerClass()!=null){
			if(getChildren() == null)
				setChildren(instanciateChildrenCollection());
			getChildren().addListener((org.cyk.utility.common.helper.CollectionHelper.Instance.Listener<Component>) component.getPropertiesMap().getColumnsCollectionInstanceListenerClass());
		}
		
		component.getPropertiesMap().setColumns(this);
	}
	
	public Column addColumn(String labelStringIdentifier,String fieldName){
		Column column = Column.instanciateOne(labelStringIdentifier, fieldName);
		column.getPropertiesMap().setAction(getPropertiesMap().getAction());
		column.getPropertiesMap().setCellListener(getPropertiesMap().getCellListener());
		column.getPropertiesMap().setDataTable(getPropertiesMap().getDataTable());
		//column.setLabelFromIdentifier(labelStringIdentifier);
		//column.getPropertiesMap().setHeaderText(column.getLabel().getPropertiesMap().getValue());
		//column.getPropertiesMap().setFieldName(fieldName);
		addColumn(column);
		return column;
	}
	
	public static Column addColumn(Component component,String labelStringIdentifier,String fieldName){
		Columns columns = (Columns) component.getPropertiesMap().getColumns();
		if(component.getPropertiesMap().getColumns()==null){
			columns = new Columns(component);
		}
		
		return columns.addColumn(labelStringIdentifier, fieldName);
		/*
		Column.Builder builder = Column.instanciateOneBuilder();
		builder.getPropertiesMap().setActionOnClass(component.getPropertiesMap().getActionOnClass());
		builder.getPropertiesMap().setFieldName(fieldName);
		
		return columns.addColumn(builder.execute());
		*/
	}
	
	public Column addColumn(Column column){
		addOneChild(column);
		
		if(getPropertiesMap().getValue()==null)
			getPropertiesMap().setValue(getChildren().getElements());
		return column;
	}
	
	@Override
	public Component addOneChild(Component component) {
		if(component instanceof Column){
			if(component.getPropertiesMap().getSortable()==null && FieldHelper.getInstance().getIsContainSeparator((String)component.getPropertiesMap().getFieldName())){
				//component.getPropertiesMap().setSortable(Boolean.TRUE);
				//component.getPropertiesMap().setFilterable(Boolean.TRUE);
			}
		}
		return super.addOneChild(component);
	}
	
	/**/
	
	public static Column add(Component component,String labelStringIdentifier,String fieldName,CellValueSource cellValueSource){
		Column column = Column.instanciateOne(labelStringIdentifier, fieldName, cellValueSource);
		
		Columns columns;
		if( (columns = (Columns) component.getPropertiesMap().getColumns()) == null){
			columns = new Columns(component);
		}
			
		columns.addOneChild(column);
		
		if( columns.getPropertiesMap().getValue() == null){
			columns.getPropertiesMap().setValue(columns.getChildren().getElements());
		}
		
		return column;
	}
	
	public static Column add(Component component,String labelStringIdentifier,String fieldName){
		return add(component, labelStringIdentifier, fieldName, CellValueSource.DEFAULT);
	}
	
	public static Column addByFieldName(Component component,String fieldName,CellValueSource cellValueSource){
		String labelStringIdentifier = StringHelper.getInstance().getI18nIdentifier(FieldHelper.getInstance().getLast(fieldName));
		return add(component,labelStringIdentifier, fieldName,cellValueSource);
	}
	
	public static Column addByFieldName(Component component,String fieldName){
		return addByFieldName(component, fieldName, CellValueSource.DEFAULT);
	}
	
	public static void addByFieldNames(Component component,CellValueSource cellValueSource,String...fieldNames){
		for(String fieldName : fieldNames)
			addByFieldName(component,fieldName,cellValueSource);
	}
	
	public static void addByFieldNames(Component component,String...fieldNames){
		addByFieldNames(component, CellValueSource.DEFAULT, fieldNames);
	}
	
	public Column getColumn(String fieldName){
		Column column = null;
		@SuppressWarnings("unchecked")
		Collection<Column> collection = (Collection<Column>) getPropertiesMap().getValue();
		if(collection!=null)
			for(Column index : collection)
				if(fieldName==null && index.getPropertiesMap().getFieldName()==null || fieldName.equals(index.getPropertiesMap().getFieldName())){
					column = index;
					break;
				}
		logTrace("get column. field name={} , column is not null={}", fieldName,column!=null);
		return column;
	}
	
	public static Columns getProperty(Component component){
		return (Columns) component.getPropertiesMap().getColumns();
	}
	
	@SuppressWarnings("unchecked")
	public static Collection<Column> getPropertyValue(Component component){
		Columns columns = getProperty(component);
		return columns == null ? null : (Collection<Column>) columns.getPropertiesMap().getValue();
	}
	
	public static void build(Component component) {
		Columns columns = (Columns) component.getPropertiesMap().getColumns();
		if(columns!=null){
			CollectionHelper.getInstance().sort((Collection<?>) columns.getPropertiesMap().getValue());
		}
	}
	
	/**/
	
	public static interface Listener {
		
		public static class Adapter extends AbstractBean implements Listener,Serializable {
			private static final long serialVersionUID = 1L;
			
			public static class Default extends Listener.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
				
			
			}				
		}
	}

}