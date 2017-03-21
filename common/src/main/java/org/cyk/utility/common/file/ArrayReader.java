package org.cyk.utility.common.file;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

import lombok.Getter;

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
	
	public static interface TwoDimension<CELL> extends ArrayReader<List<CELL[]>> {
		
		Integer getPrimaryKeyColumnIndex();
		TwoDimension<CELL> setPrimaryKeyColumnIndex(Integer primaryKeyColumnIndex);
		
		Set<CELL> getPrimaryKeys();
		TwoDimension<CELL> setPrimaryKeys(Set<CELL> primaryKeys);
		
		Integer getPrimaryKeyExistColumnIndex();
		TwoDimension<CELL> setPrimaryKeyExistColumnIndex(Integer primaryKeyExistColumnIndex);
		
		Boolean isPrimaryKeyExist(Integer rowIndex,String[] values);
		
		@Getter
		public static class Adapter<CELL> extends ArrayReader.Adapter.Default<List<CELL[]>> implements TwoDimension<CELL>,Serializable {
			private static final long serialVersionUID = 1L;
			
			private Integer primaryKeyColumnIndex;
			private Set<CELL> primaryKeys;
			private Integer primaryKeyExistColumnIndex;
			
			public Adapter(File file) {
				super(file);
			}
			
			@Override
			public TwoDimension<CELL> setPrimaryKeyColumnIndex(Integer primaryKeyColumnIndex) {
				this.primaryKeyColumnIndex = primaryKeyColumnIndex;
				return this;
			}
			
			@Override
			public TwoDimension<CELL> setPrimaryKeys(Set<CELL> primaryKeys) {
				this.primaryKeys = primaryKeys;
				return this;
			}
			
			@Override
			public TwoDimension<CELL> setPrimaryKeyExistColumnIndex(Integer primaryKeyExistColumnIndex) {
				this.primaryKeyExistColumnIndex = primaryKeyExistColumnIndex;
				return this;
			}
			
			@Override
			public Boolean isPrimaryKeyExist(Integer rowIndex, String[] values) {
				return null;
			}
			
			/**/
			
			public static class Default<CELL> extends TwoDimension.Adapter<CELL> implements Serializable {
				private static final long serialVersionUID = 1L;
				
				public Default(File file) {
					super(file);
				}
				
				@Override
				public Boolean isPrimaryKeyExist(Integer rowIndex, String[] values) {
					if(getPrimaryKeyColumnIndex()!=null && getPrimaryKeys()!=null){
						return getPrimaryKeys().contains(values[getPrimaryKeyColumnIndex()]);
					}
					return super.isPrimaryKeyExist(rowIndex, values);
				}
				
			}
			
		}
		
	}
	
}
