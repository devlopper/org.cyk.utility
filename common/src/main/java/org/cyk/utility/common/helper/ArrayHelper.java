package org.cyk.utility.common.helper;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.inject.Singleton;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.file.ArrayReader.Dimension.Row;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Singleton
public class ArrayHelper extends AbstractHelper implements Serializable  {

	private static final long serialVersionUID = 1L;

	private static ArrayHelper INSTANCE;
	
	public static ArrayHelper getInstance() {
		if(INSTANCE == null)
			INSTANCE = new ArrayHelper();
		return INSTANCE;
	}
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	public Object[] reverse(Object[] objects){
		ArrayUtils.reverse(objects);
		return objects;
	}
	
	public Integer size(Object[] objects){
		return objects == null ? 0 : objects.length;
	}
	
	/**/
	
	@Getter @Setter @NoArgsConstructor
	public static class Element<INSTANCE> implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private static final java.lang.String TO_STRING_FORMAT = "%s[%s]";
		
		private Integer index;
		private INSTANCE instance;
		
		public Element(Integer index, INSTANCE instance) {
			super();
			this.index = index;
			this.instance = instance;
		}
		
		@Override
		public java.lang.String toString() {
			return java.lang.String.format(TO_STRING_FORMAT, instance,index);
		}
		
		/**/
		
		@NoArgsConstructor
		public static class String extends Element<java.lang.String> implements Serializable {
			private static final long serialVersionUID = 1L;

			public String(Integer index, java.lang.String string) {
				super(index, string);
			}
			
		}
	}

	/**/
	
	public static interface Builder<INPUT, OUTPUT>  extends org.cyk.utility.common.Builder<INPUT, OUTPUT>{
		
		public static class Adapter<INPUT, OUTPUT> extends org.cyk.utility.common.Builder.Adapter.Default<INPUT, OUTPUT> implements Builder<INPUT, OUTPUT>,Serializable {
			private static final long serialVersionUID = 1L;
			
			public Adapter(Class<INPUT> inputClass, INPUT input, Class<OUTPUT> outputClass) {
				super(inputClass, input, outputClass);
			}
			
			public static class Default<INPUT, OUTPUT> extends Builder.Adapter<INPUT, OUTPUT> implements Serializable {
				private static final long serialVersionUID = 1L;
				
				public Default(Class<INPUT> inputClass, INPUT input, Class<OUTPUT> outputClass) {
					super(inputClass, input, outputClass);
				}	
			}
			
		}
		
		/**/
		
		public static interface OneDimension<INPUT, OUTPUT> extends Builder<INPUT, OUTPUT[]> {
			
			public static class Adapter<INPUT, OUTPUT> extends Builder.Adapter.Default<INPUT, OUTPUT[]> implements OneDimension<INPUT, OUTPUT>,Serializable {
				private static final long serialVersionUID = 1L;
				
				public Adapter(Class<INPUT> inputClass, INPUT input, Class<OUTPUT[]> outputClass) {
					super(inputClass, input, outputClass);
				}
				
				public static class Default<INPUT, OUTPUT> extends OneDimension.Adapter<INPUT, OUTPUT> implements Serializable {
					private static final long serialVersionUID = 1L;
					
					public Default(Class<INPUT> inputClass, INPUT input, Class<OUTPUT[]> outputClass) {
						super(inputClass, input, outputClass);
					}	
				}
				
			}
		
			/**/
			
			@Getter @Setter @Accessors(chain=true)
			public static class Values implements Serializable {
				private static final long serialVersionUID = 1L;
				
				private Key primaryKey;
				
			}
			
			@Getter @Setter @Accessors(chain=true)
			public static class Key implements Serializable {
				private static final long serialVersionUID = 1L;
				
				private Set<Integer> indexes;
				private String separator;
				
				public String get(Object[] objects){
					if(objects==null || indexes==null)
						return null;
					Collection<String> values = new ArrayList<>();
					for(Integer index : indexes)
						values.add(String.valueOf(objects[index]));
					return StringHelper.getInstance().concatenate(values, separator);
				}
				
				public static interface Builderr extends org.cyk.utility.common.Builder.NullableInput<Key> {
					
				}
			}
		}
		
		public static interface TwoDimensions<INPUT, OUTPUT> extends Builder<INPUT, OUTPUT[][]> {
			
			public static class Adapter<INPUT, OUTPUT> extends Builder.Adapter.Default<INPUT, OUTPUT[][]> implements TwoDimensions<INPUT, OUTPUT>,Serializable {
				private static final long serialVersionUID = 1L;
				
				public Adapter(Class<INPUT> inputClass, INPUT input, Class<OUTPUT[][]> outputClass) {
					super(inputClass, input, outputClass);
				}
				
				public static class Default<INPUT, OUTPUT> extends TwoDimensions.Adapter<INPUT, OUTPUT> implements Serializable {
					private static final long serialVersionUID = 1L;
					
					public Default(Class<INPUT> inputClass, INPUT input, Class<OUTPUT[][]> outputClass) {
						super(inputClass, input, outputClass);
					}	
				}
				
			}
		}
		
		/**/
		
		@Getter @Setter @Accessors(chain=true)
		public static class Key implements Serializable {
			private static final long serialVersionUID = 1L;
			
			private Set<Integer> indexes;
			private String separator;
			
			public String get(Object[] objects){
				if(objects==null || indexes==null)
					return null;
				Collection<String> values = new ArrayList<>();
				for(Integer index : indexes)
					values.add(String.valueOf(objects[index]));
				return StringHelper.getInstance().concatenate(values, separator);
			}
			
		}
	}
	
	public static interface Dimension {
		
		@Getter @Setter @Accessors(chain=true) @NoArgsConstructor @EqualsAndHashCode(of={"value"})
		public static class Key implements Serializable {
			private static final long serialVersionUID = 1L;
		
			private String value;
			
			public Key(String value) {
				super();
				this.value = value;
			}
			
			@Override
			public String toString() {
				return value;
			}

			public static interface Builder extends org.cyk.utility.common.Builder<Object[],Key> {
				
				String getSeparator();
				Builder setSeparator(String separator);
				
				@Getter
				public static class Adapter extends org.cyk.utility.common.Builder.Adapter.Default<Object[],Key> implements Builder,Serializable{
					private static final long serialVersionUID = 1L;
					
					protected String separator;
					
					public Adapter(Object[] objects) {
						super(Object[].class,objects,Key.class);
					}
					
					@Override
					public Builder setSeparator(String separator) {
						return null;
					}
					
					public static class Default extends Builder.Adapter implements Serializable{
						private static final long serialVersionUID = 1L;
						
						public Default(Object[] objects) {
							super(objects);
						}
						
						public Default() {
							super(null);
						}
						
						@Override
						public Builder setSeparator(String separator) {
							this.separator = separator;
							return this;
						}
						
						@Override
						protected Key __execute__() {
							Key key = new Key();
							Object[] objects = getInput();
							if(objects.length>0){
								Collection<Object> indexes = getParameters();
								Collection<String> values = new ArrayList<>();
								if(indexes!=null){
									for(Object index : indexes)
										if(index instanceof Integer)
											values.add(String.valueOf(objects[(Integer)index]));
									key.setValue(StringHelper.getInstance().concatenate(values, separator));
								}	
							}
							return key;
						}
						
					}
				}
				
			}
		}
	}
	
	
	
	public static interface ArrayReader<RESULT> extends FileHelper.Read<RESULT> {

		public static class Adapter<RESULT> extends FileHelper.Read.Adapter.Default<RESULT> implements ArrayReader<RESULT>,Serializable {
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
				public Dimension.Row<CELL> getRow(Integer rowIndex, CELL[] values) {
					return null;
				}
				
				@Override
				public Dimension.Row<CELL> getRow(Integer rowIndex, Integer size) {
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
				public void listenAfterRowCreated(Dimension.Row<CELL> row) {}
				
				@Override
				public void listenAfterRowAdded(Dimension.Row<CELL> row) {}
				
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
						for(Dimension.Row<CELL> row : getOutput()){
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
					public Dimension.Row<CELL> getRow(Integer index, CELL[] values) {
						Dimension.Row<CELL> row = new Dimension.Row<>(index,values,getPrimaryKey(index, values));
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
		
		/*@Getter/* @Setter*/ //@Accessors(chain=true) //TODo use table model ....model.table package
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
			
			@Getter @Setter @Accessors(chain=true)
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

}
