package org.cyk.utility.common.model.table;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;

import org.apache.commons.lang3.reflect.FieldUtils;

public abstract class AbstractClassFieldValueTable<CLASS,ROW extends DefaultTableRow<CLASS>,COLUMN extends DefaultTableColumn,CELL extends DefaultCell> extends 
	AbstractDataTable<ROW,COLUMN,CLASS,String,DefaultCell,String> {

	private static final long serialVersionUID = -6350569403060865536L;
	
	protected Class<ROW> rowClass;
	protected Class<COLUMN> columnClass;
	
	@SuppressWarnings("unchecked")
	public AbstractClassFieldValueTable() {
		rowClass = (Class<ROW>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[1];
		columnClass = (Class<COLUMN>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[2];
	}
	
	public void load(Collection<CLASS> datas) throws InstantiationException, IllegalAccessException {
		clear();
		@SuppressWarnings("unchecked")
		Class<CLASS> clazz = (Class<CLASS>) (datas.isEmpty()?null:datas.iterator().next().getClass());
		Collection<Field> fields = commonUtils.getAllFields(clazz);
		for(Field field : fields)
			if(showable(field)){
				COLUMN column = columnClass.newInstance();
				column.setTitle(field.getName());
				column.setFieldName(field.getName());
				addColumn(column);
			}
		for(CLASS data : datas){
			ROW row = rowClass.newInstance();
			row.setData(data);
			row.setTitle(data.toString());
			addRow(row);
			for(DefaultTableColumn column : columns){
				Object o = FieldUtils.readField(data, column.getFieldName(),true);
				row.addCell(new DefaultCell(o==null?nullValue():valueOf(o)));
			}
		}
	}
	
	protected Boolean showable(Field field){
		return !Modifier.isStatic(field.getModifiers());
	}
	
	protected abstract String nullValue();
	
	protected abstract String valueOf(Object object);

}
