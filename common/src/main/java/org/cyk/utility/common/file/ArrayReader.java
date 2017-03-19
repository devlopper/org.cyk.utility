package org.cyk.utility.common.file;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import lombok.Getter;

public interface ArrayReader<RESULT> extends FileReader<RESULT> {

	public static class Adapter<RESULT> extends FileReader.Adapter.Default<RESULT> implements ArrayReader<RESULT>,Serializable {
		private static final long serialVersionUID = 1L;
	
		/**/
		
		public static class Default<RESULT> extends ArrayReader.Adapter<RESULT> implements Serializable {
			private static final long serialVersionUID = 1L;
			
		}
		
	}
	
	/**/
	
	public static interface TwoDimension<CELL> extends ArrayReader<List<CELL[]>> {
		
		Integer getPrimaryKeyColumnIndex();
		TwoDimension<CELL> setPrimaryKeyColumnIndex(Integer primaryKeyColumnIndex);
		
		Set<CELL> getPrimaryKeys();
		TwoDimension<CELL> setPrimaryKeys(Set<CELL> primaryKeys);
		
		@Getter
		public static class Adapter<CELL> extends ArrayReader.Adapter.Default<List<CELL[]>> implements TwoDimension<CELL>,Serializable {
			private static final long serialVersionUID = 1L;
			
			private Integer primaryKeyColumnIndex;
			private Set<CELL> primaryKeys;
			
			@Override
			public org.cyk.utility.common.file.ArrayReader.TwoDimension<CELL> setPrimaryKeyColumnIndex(Integer primaryKeyColumnIndex) {
				this.primaryKeyColumnIndex = primaryKeyColumnIndex;
				return this;
			}
			
			@Override
			public org.cyk.utility.common.file.ArrayReader.TwoDimension<CELL> setPrimaryKeys(Set<CELL> primaryKeys) {
				this.primaryKeys = primaryKeys;
				return this;
			}
			
			/**/
			
			public static class Default<CELL> extends TwoDimension.Adapter<CELL> implements Serializable {
				private static final long serialVersionUID = 1L;
				
			}
			
		}
		
	}
	
}
