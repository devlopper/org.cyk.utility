package org.cyk.utility.common.file;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.file.ArrayReader.Dimension.Row;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface ArrayReader<RESULT> extends FileReader<RESULT> {

	public static class Adapter<RESULT> extends FileReader.Adapter.Default<RESULT> implements ArrayReader<RESULT>,Serializable {
		private static final long serialVersionUID = 1L;
	
		public Adapter(File file) {
			super(file);
		}
		/**/
		
		public static class Default<RESULT> extends ArrayReader.Adapter<RESULT> implements Serializable {
			private static final long serialVersionUID = 1L;
			
			public Default(File file) {
				super(file);
			}
		}
		
	}

	/**/
	
	public static interface TwoDimension<CELL> extends ArrayReader<List<Dimension.Row<CELL>>> {
		/*
		Collection<Dimension.Row<CELL>> getRows();
		TwoDimension<CELL> setRows(Collection<Dimension.Row<CELL>> rows);
		*/
		//Collection<Dimension.Row<CELL>> getRowsWhereHasPrimaryKey();
		//Collection<Dimension.Row<CELL>> getRowsWhereHasNotPrimaryKey();
		
		Object[][] getValues();
		
		Dimension.Row<CELL> getRow(Integer rowIndex,CELL[] values);
		Dimension.Row<CELL> getRow(Integer rowIndex,Integer size);
		
		Set<Integer> getPrimaryKeyColumnIndexes();
		TwoDimension<CELL> setPrimaryKeyColumnIndexes(Set<Integer> indexes);
		
		Set<CELL> getPrimaryKeys();
		TwoDimension<CELL> setPrimaryKeys(Set<CELL> primaryKeys);
		
		Boolean getHasPrimaryKey();
		TwoDimension<CELL> setHasPrimaryKey(Boolean hasPrimaryKey);
		
		String getPrimaryKey(Integer rowIndex,CELL[] values);
		
		void listenAfterRowCreated(Dimension.Row<CELL> row);
		void listenAfterRowAdded(Dimension.Row<CELL> row);
		
		@Getter
		public static class Adapter<CELL> extends ArrayReader.Adapter.Default<List<Dimension.Row<CELL>>> implements TwoDimension<CELL>,Serializable {
			private static final long serialVersionUID = 1L;
			
			//private Collection<Dimension.Row<CELL>> rows;
			private Boolean hasPrimaryKey;
			private Set<CELL> primaryKeys;
			private Set<Integer> primaryKeyColumnIndexes;
			
			public Adapter(File file) {
				super(file);
			}
			
			/*@Override
			public TwoDimension<CELL> setRows(Collection<Dimension.Row<CELL>> rows) {
				this.rows = rows;
				return this;
			}*/
			
			@Override
			public Object[][] getValues() {
				return null;
			}
			
			@Override
			public TwoDimension<CELL> setHasPrimaryKey(Boolean hasPrimaryKey) {
				this.hasPrimaryKey = hasPrimaryKey;
				return this;
			}
			
			@Override
			public TwoDimension<CELL> setPrimaryKeys(Set<CELL> primaryKeys) {
				this.primaryKeys = primaryKeys;
				return this;
			}
			
			@Override
			public String getPrimaryKey(Integer rowIndex, CELL[] values) {
				return null;
			}
			
			@Override
			public ArrayReader.TwoDimension<CELL> setPrimaryKeyColumnIndexes(Set<Integer> indexes) {
				this.primaryKeyColumnIndexes = indexes;
				return this;
			}
			
			@Override
			public Row<CELL> getRow(Integer rowIndex, CELL[] values) {
				return null;
			}
			
			@Override
			public Row<CELL> getRow(Integer rowIndex, Integer size) {
				return null;
			}
			
			/*@Override
			public Collection<Row<CELL>> getRowsWhereHasPrimaryKey() {
				return null;
			}
			
			@Override
			public Collection<Row<CELL>> getRowsWhereHasNotPrimaryKey() {
				return null;
			}*/
			
			@Override
			public void listenAfterRowCreated(Row<CELL> row) {}
			
			@Override
			public void listenAfterRowAdded(Row<CELL> row) {}
			
			/**/
			
			public static class Default<CELL> extends TwoDimension.Adapter<CELL> implements Serializable {
				private static final long serialVersionUID = 1L;
				
				public Default(File file) {
					super(file);
				}
				
				@Override
				public Object[][] getValues() {
					Object[][] values = new Object[getOutput().size()][];
					int i = 0;
					for(Row<CELL> row : getOutput()){
						values[i] = new Object[row.getValues().length];
						int j = 0;
						for(CELL value : row.getValues())
							values[i][j++] = value;
						i++;
					}
					return values;
				}
				
				@Override
				public String getPrimaryKey(Integer rowIndex, CELL[] values) {
					if(values == null || values.length == 0)
						return null;
					Set<Integer> indexes = getPrimaryKeyColumnIndexes();
					if(indexes == null)
						return null;
					StringBuilder stringBuilder = new StringBuilder();
					for(Integer index : indexes)
						stringBuilder.append(values[index]);
					return stringBuilder.toString();
				}
				
				@Override
				public Row<CELL> getRow(Integer index, CELL[] values) {
					Row<CELL> row = new Row<>(index,values,getPrimaryKey(index, values));
					return row;
				}
				
				public static <T> Collection<Row<?>> getRowsWhereHasPrimaryKey(Class<T> aClass,Collection<Row<T>> rows) {
					Collection<Row<?>> collection = new ArrayList<>();
					for(Row<T> row : rows)
						if(row.hasPrimaryKey())
							collection.add(row);
					return collection;
				}
				
				
				public static <T> Collection<Row<?>> getRowsWhereHasNotPrimaryKey(Class<T> aClass,Collection<Row<T>> rows) {
					Collection<Row<?>> collection = new ArrayList<>();
					for(Row<T> row : rows)
						if(!row.hasPrimaryKey())
							collection.add(row);
					return collection;
				}
				
			}
			
		}
		
	}
	
	/**/
	
	@Deprecated /*@Getter/* @Setter*/ //@Accessors(chain=true) //TODo use table model ....model.table package
	public static class Dimension<CELL> implements Serializable {
		private static final long serialVersionUID = -4369275413090667872L;
		
		protected Integer index;
		protected CELL[] values;
		protected String primaryKey;
		
		public Dimension(Integer index,CELL[] values, String primaryKey) {
			super();
			this.index = index;
			this.values = values;
			this.primaryKey = primaryKey;
		}
		
		
		
		public Integer getIndex() {
			return index;
		}



		public void setIndex(Integer index) {
			this.index = index;
		}



		public CELL[] getValues() {
			return values;
		}



		public void setValues(CELL[] values) {
			this.values = values;
		}



		public String getPrimaryKey() {
			return primaryKey;
		}



		public void setPrimaryKey(String primaryKey) {
			this.primaryKey = primaryKey;
		}



		public Boolean hasPrimaryKey(){
			return StringUtils.isNotBlank(primaryKey);
		}
		
		public Boolean isBlank(){
			for( CELL cell : values )
				if( cell!=null && StringUtils.isNotBlank(cell.toString()) )
					return Boolean.FALSE;
			return Boolean.TRUE;
		}
		
		@Override
		public String toString() {
			return StringUtils.join(values,Constant.CHARACTER_COLON)+" PK="+StringUtils.defaultString(primaryKey);
		}
		
		/**/
		
		@Getter @Setter @Accessors(chain=true) @Deprecated
		public static class Row<CELL> extends Dimension<CELL> implements Serializable {
			private static final long serialVersionUID = -4369275413090667872L;
			
			/**/
			
			public Row(Integer index,CELL[] values, String primaryKey) {
				super(index,values, primaryKey);
			}
			
			public void set(Integer index,CELL value){
				this.values[index] = value;
			}
		}
	}
	
}
