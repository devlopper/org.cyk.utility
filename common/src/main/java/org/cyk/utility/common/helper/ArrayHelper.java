package org.cyk.utility.common.helper;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import javax.inject.Singleton;

import org.apache.commons.lang3.ArrayUtils;

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
	
	public <T> T[] get(Class<T> aClass,String[] fieldNames,Object...objects){
		@SuppressWarnings("unchecked")
		T[] array = (T[]) Array.newInstance(aClass, objects.length / 2);
		int j = 0;
		for(int i = 0 ; i < objects.length ;){
			T instance = ClassHelper.getInstance().instanciateOne(aClass);
			for(String fieldName : fieldNames){
				InstanceHelper.getInstance().callSetMethod(instance,fieldName,objects[i].getClass(),objects[i++]);
			}
			array[j] = instance;
		}
		return array;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T[] get(Class<T> aClass,Collection<T> collection){
		if(CollectionHelper.getInstance().isEmpty(collection))
			return null;
		return (T[]) collection.toArray((T[]) Array.newInstance(aClass, 0));
	}
	
	public Object[] reverse(Object[] objects){
		ArrayUtils.reverse(objects);
		return objects;
	}
	
	public Integer size(Object[] objects){
		return objects == null ? 0 : objects.length;
	}
	
	public Boolean isEmpty(Object[] objects){
		return objects == null ? Boolean.TRUE : objects.length == 0;
	}
	
	public Object[][] filter(Object[][] array,Integer index,Object value){
		if(array == null || array.length == 0)
			return null;
		Collection<Object[]> collection = new ArrayList<>();
		for(Object[] objectArray : array){
			if((value==null && objectArray[index]==null) || value.equals(objectArray[index]))
				collection.add(objectArray);
		}
		if(CollectionHelper.getInstance().isEmpty(collection))
			return null;
		Object[][] result = new Object[collection.size()][array[0].length];
		int i = 0;
		for(Object[] objectArray : collection){
			result[i++] = objectArray;
		}
		return result;
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
									String separator = getSeparator();
									if(!StringHelper.getInstance().isBlank(separator))
										addLoggingMessageBuilderNamedParameters("separator",separator);
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

	/**/
	
	public static interface Filter<INPUT, OUTPUT>  extends org.cyk.utility.common.Builder<INPUT, OUTPUT>{
		
		public static class Adapter<INPUT, OUTPUT> extends org.cyk.utility.common.Builder.Adapter.Default<INPUT, OUTPUT> implements Filter<INPUT, OUTPUT>,Serializable {
			private static final long serialVersionUID = 1L;
			
			public Adapter(Class<INPUT> inputClass, INPUT input, Class<OUTPUT> outputClass) {
				super(inputClass, input, outputClass);
			}
			
			public static class Default<INPUT, OUTPUT> extends Filter.Adapter<INPUT, OUTPUT> implements Serializable {
				private static final long serialVersionUID = 1L;
				
				public Default(Class<INPUT> inputClass, INPUT input, Class<OUTPUT> outputClass) {
					super(inputClass, input, outputClass);
				}	
			}
			
		}
		
		/**/
		
		public static interface OneDimension<INPUT, OUTPUT> extends Filter<INPUT, OUTPUT[]> {
			
			public static class Adapter<INPUT, OUTPUT> extends Filter.Adapter.Default<INPUT, OUTPUT[]> implements OneDimension<INPUT, OUTPUT>,Serializable {
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

			}
		}
		
		public static interface TwoDimensions<INPUT, OUTPUT> extends Filter<INPUT[][], OUTPUT[][]> {
			
			public static class Adapter<INPUT, OUTPUT> extends Filter.Adapter.Default<INPUT[][], OUTPUT[][]> implements TwoDimensions<INPUT, OUTPUT>,Serializable {
				private static final long serialVersionUID = 1L;
				
				public Adapter(Class<INPUT[][]> inputClass, INPUT[][] input, Class<OUTPUT[][]> outputClass) {
					super(inputClass, input, outputClass);
				}
				
				public static class Default<INPUT, OUTPUT> extends TwoDimensions.Adapter<INPUT, OUTPUT> implements Serializable {
					private static final long serialVersionUID = 1L;
					
					public Default(Class<INPUT[][]> inputClass, INPUT[][] input, Class<OUTPUT[][]> outputClass) {
						super(inputClass, input, outputClass);
					}
					
					@Override
					protected OUTPUT[][] __execute__() {
						/*Object[][] array = getInput();
						if(array.length == 0)
							return null;
						Collection<Object[]> collection = new ArrayList<>();
						for(Object[] objectArray : array){
							if((value==null && objectArray[index]==null) || value.equals(objectArray[index]))
								collection.add(objectArray);
						}
						Object[][] result = new Object[collection.size()][array[0].length];
						int i = 0;
						for(Object[] objectArray : collection){
							result[i++] = objectArray;
						}
						return result;
						*/
						return null;
					}
				}
				
			}
		}
		
	}
	
}
