package org.cyk.utility.common.helper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Singleton;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.cdi.AbstractBean;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Singleton
public class CollectionHelper extends AbstractHelper implements Serializable  {

	private static final long serialVersionUID = 1L;

	private static CollectionHelper INSTANCE;
	
	public static CollectionHelper getInstance() {
		if(INSTANCE == null)
			INSTANCE = new CollectionHelper();
		return INSTANCE;
	}
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	public <T> Instance<T> getCollectionInstance(Class<T> aClass){
		return new Instance<T>().setElementClass(aClass);
	}
	
	public <ELEMENT> ELEMENT getElementAt(Collection<ELEMENT> collection,Integer index){
		ELEMENT element = null;
		if(isNotEmpty(collection) && index < getSize(collection)){
			if(collection instanceof List)
				element = ((List<ELEMENT>)collection).get(index.intValue());
			else {
				Iterator<ELEMENT> iterator = collection.iterator();
				Integer count = 0;
				while (count++ <= index)
					element = iterator.next();
			}
		}
		return element;
	}
	
	public <ELEMENT> Collection<ELEMENT> removeElementAt(Collection<ELEMENT> collection,Integer index){
		Collection<ELEMENT> newCollection = collection;
		if(isNotEmpty(collection) && index < getSize(collection)){
			if(collection instanceof List){
				((List<ELEMENT>)collection).remove(index.intValue());
				newCollection = collection;
			}else {
				if(collection instanceof Set)
					newCollection = new LinkedHashSet<>();
				Iterator<ELEMENT> iterator = collection.iterator();
				Integer count = 0;
				while (iterator.hasNext()){
					ELEMENT element = iterator.next();
					if(count++ != index )
						newCollection.add(element);
				}
			}
		}
		return newCollection;
	}
	
	public <ELEMENT> Collection<ELEMENT> removeElement(Collection<ELEMENT> collection,Object element){
		Collection<ELEMENT> newCollection = collection;
		if(isNotEmpty(collection)){
			if(collection instanceof List){
				((List<ELEMENT>)collection).remove(element);
				newCollection = collection;
			}else {
				if(collection instanceof Set)
					newCollection = new LinkedHashSet<>();
				Iterator<ELEMENT> iterator = collection.iterator();
				while (iterator.hasNext()){
					ELEMENT index = iterator.next();
					if(element != index )
						newCollection.add(index);
				}
			}
		}
		return newCollection;
	}
	
	public <ELEMENT> List<ELEMENT> createList(Collection<ELEMENT> collection){
		if(collection==null)
			return null;
		return new ArrayList<>(collection);
	}
	
	public <ELEMENT> Collection<ELEMENT> filter(Collection<ELEMENT> collection,Class<?> aClass){
		if(collection==null || aClass==null)
			return null;
		return new CollectionHelper.Filter.Adapter.Default<ELEMENT>(collection).setProperty(CollectionHelper.Filter.PROPERTY_NAME_CLASS, aClass).execute();
	}
	
	public <ELEMENT> Collection<ELEMENT> filter(Collection<ELEMENT> collection,String fieldName,Object fieldValue){
		if(collection==null)
			return null;
		return new CollectionHelper.Filter.Adapter.Default<ELEMENT>(collection).setProperty(CollectionHelper.Filter.PROPERTY_NAME_FIELD_NAME, fieldName)
				.setProperty(CollectionHelper.Filter.PROPERTY_NAME_FIELD_VALUE, fieldValue).execute();
	}
	
	public <ELEMENT> Collection<ELEMENT> get(Boolean distinct,@SuppressWarnings("unchecked") ELEMENT...elements){
		Collection<ELEMENT> result = Boolean.TRUE.equals(distinct) ? new LinkedHashSet<ELEMENT>() : new ArrayList<ELEMENT>();
		if(elements!=null)
			for(ELEMENT element : elements)
				if(element!=null)
					result.add(element);
		return result;
	}
	
	public <ELEMENT> Collection<ELEMENT> get(@SuppressWarnings("unchecked") ELEMENT...elements){
		return get(Boolean.FALSE,elements);
	}
	
	public <COLLECTION extends Collection<?>,ELEMENT> Collection<ELEMENT> add(Class<COLLECTION> collectionClass,Collection<ELEMENT> collection,Boolean append,Collection<ELEMENT> elements){
		Collection<ELEMENT> result = Boolean.TRUE.equals(append) ? collection : null;
		if(result==null)
			result = Set.class.isAssignableFrom(collectionClass) ? new LinkedHashSet<ELEMENT>() : new ArrayList<ELEMENT>();
		if(Boolean.TRUE.equals(append))
			;
		else
			if(collection!=null)
				result.addAll(collection);
		result.addAll(elements);
		return result;
	}
	
	public <ELEMENT> Collection<ELEMENT> add(Collection<ELEMENT> collection,Boolean append,Collection<ELEMENT> elements){
		return add(List.class, collection, append, elements);
	}
	
	public <COLLECTION extends Collection<?>,ELEMENT> Collection<ELEMENT> add(Class<COLLECTION> collectionClass,Collection<ELEMENT> collection,Boolean append,@SuppressWarnings("unchecked") ELEMENT...elements){
		return add(collectionClass,collection,append,get(elements));
	}
	
	public <COLLECTION extends Collection<?>,ELEMENT> Collection<ELEMENT> add(Class<COLLECTION> collectionClass,Collection<ELEMENT> collection,@SuppressWarnings("unchecked") ELEMENT...elements){
		return add(collectionClass,collection,Boolean.TRUE,get(elements));
	}
	
	public <ELEMENT> Collection<ELEMENT> add(Collection<ELEMENT> collection,Boolean append,@SuppressWarnings("unchecked") ELEMENT...elements){
		return add(collection,append,get(elements));
	}
	
	public <ELEMENT> Collection<ELEMENT> add(Collection<ELEMENT> collection,@SuppressWarnings("unchecked") ELEMENT...elements){
		return add(collection,Boolean.TRUE,get(elements));
	}
		
	public <ELEMENT> Boolean contains(Collection<ELEMENT> collection1,Collection<ELEMENT> collection2){
		if(collection1==null)
			if(collection2==null)
				return Boolean.TRUE;//TODO is it correct ???
			else
				return Boolean.FALSE;
		else
			if(collection2==null)
				return Boolean.TRUE;
			else
				return collection1.containsAll(collection2);
	}
	
	@SuppressWarnings("unchecked")
	public <ELEMENT> Boolean contains(Collection<ELEMENT> collection1,ELEMENT...elements){
		return contains(collection1, (Collection<ELEMENT>) Arrays.asList(elements));
	}
	
	public <ELEMENT> Boolean equals(Collection<ELEMENT> collection1,Collection<ELEMENT> collection2){
		if(collection1==null)
			if(collection2==null)
				return Boolean.TRUE;//TODO is it correct ???
			else
				return Boolean.FALSE;
		else
			if(collection2==null)
				return Boolean.FALSE;
			else{
				Collection<ELEMENT> c1 = new ArrayList<>(collection2);
				Collection<ELEMENT> c2 = new ArrayList<>(collection1);
				c1.removeAll(collection1);
				c2.removeAll(collection2);
				return c1.isEmpty() && c2.isEmpty();
			}
	}
	
	public Boolean isEmpty(Collection<?> collection){
		return collection==null || collection.isEmpty();
	}

	public String concatenate(Collection<?> collection, Object separator) {
		if(isEmpty(collection))
			return Constant.EMPTY_STRING;
		return StringUtils.join(collection,separator == null ? null : separator.toString());
	}
	
	@SuppressWarnings("unchecked")
	public <ELEMENT> ELEMENT[] getArray(Collection<ELEMENT> collection){
		return (ELEMENT[]) (collection == null ? null : collection.toArray());
	}
	
	public Integer getSize(Collection<?> collection) {
		if(isEmpty(collection))
			return 0;
		return collection.size();
	}
	
	public Boolean isBlank(Collection<?> collection){
		for(Object object : collection){
			if(object instanceof String){
				if( !StringHelper.getInstance().isBlank( (String)object) )
					return Boolean.FALSE;
			}
		}
		return Boolean.TRUE;
	}
	
	public Boolean isNotEmpty(Collection<?> collection){
		return !Boolean.TRUE.equals(isEmpty(collection));
	}
	
	public <T> T getFirst(Collection<T> collection){
		if(isEmpty(collection))
			return null;
		return collection.iterator().next();
	}
	
	public void clear(Collection<?> collection){
		if(collection!=null)
			collection.clear();
	}
	
	@SuppressWarnings("unchecked")
	public <T> Collection<T> cast(Class<T> aClass,Collection<?> collection){
		Collection<T> result;
		if(collection==null){
			result = null;
		}else{
			result = new ArrayList<T>();
			for(Object item : collection)
				result.add((T) item);	
		}
		return result;
	}
	
	/**/
	
	public static interface Filter<TYPE>  extends org.cyk.utility.common.Action<Collection<TYPE>, Collection<TYPE>>{
		
		public static class Adapter<TYPE> extends org.cyk.utility.common.Action.Adapter.Default<Collection<TYPE>, Collection<TYPE>> implements Filter<TYPE>,Serializable {
			private static final long serialVersionUID = 1L;
			
			@SuppressWarnings("unchecked")
			public Adapter(Collection<TYPE> input) {
				super("filter",(Class<Collection<TYPE>>) ClassHelper.getInstance().getByName(Collection.class), input
						, (Class<Collection<TYPE>>) ClassHelper.getInstance().getByName(Collection.class));
			}
			
			public static class Default<TYPE> extends Filter.Adapter<TYPE> implements Serializable {
				private static final long serialVersionUID = 1L;
				
				public Default(Collection<TYPE> collection) {
					super(collection);
				}	
				
				@Override
				protected Collection<TYPE> __execute__() {
					Class<?> aClass = (Class<?>) getProperty(PROPERTY_NAME_CLASS);
					Collection<TYPE> input = getInput();
					Collection<TYPE> collection = null;
					for(TYPE instance : input){
						if(aClass==null){
							String fieldName = (String) getProperty(PROPERTY_NAME_FIELD_NAME);
							Object fieldValue = getProperty(PROPERTY_NAME_FIELD_VALUE);
							Object instanceFieldValue = FieldHelper.getInstance().read(instance, fieldName);
							if((fieldValue==null && instanceFieldValue==null) || (fieldValue!=null && fieldValue.equals(instanceFieldValue))){
								if(collection==null)
									collection = new ArrayList<>();
								collection.add(instance);
							}	
						}else {
							if(ClassHelper.getInstance().isInstanceOf(aClass, instance.getClass())){
								if(collection==null)
									collection = new ArrayList<>();
								collection.add(instance);
							}
						}
						
					}
					return collection;
				}
				
			}	
		}	
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Instance<T> extends AbstractBean implements Serializable {

		private static final long serialVersionUID = -130189077130420874L;

		private String name;
		private Class<T> elementClass;
		private Collection<T> collection;
		private Boolean synchonizationEnabled;
		private Boolean isOrderNumberComputeEnabled;
		private Collection<String> fieldNames;
		private Collection<Listener<T>> listeners;
		
		public Instance<T> addListener(Listener<T> listener){
			if(this.listeners == null)
				this.listeners = new ArrayList<>();
			this.listeners.add(listener);
			return this;
		}
		
		public Instance<T> setElementClass(Class<T> elementClass){
			this.elementClass = elementClass;
			return this;
		}
		
		public Instance<T> addOne(){
			T element = ClassHelper.getInstance().instanciateOne(getElementClass());
			addOne(element);
			return this;
		}
		
		@SuppressWarnings("unchecked")
		public Instance<T> addOne(Object element){
			if(element!=null){
				Class<T> elementClass = getElementClass();
				Boolean isInstanciatable = InstanceHelper.getInstance().getIfNotNullElseDefault(
						ListenerHelper.getInstance().listenBoolean(listeners, Listener.METHOD_NAME_IS_INSTANCIATABLE
								,MethodHelper.Method.Parameter.buildArray(Instance.class, this,Object.class,element))
						,elementClass == null ? Boolean.TRUE : element.getClass().equals(elementClass));
				
				if(!element.getClass().equals(elementClass) && Boolean.TRUE.equals(isInstanciatable)){
					element = InstanceHelper.getInstance().getIfNotNullElseDefault(
							ListenerHelper.getInstance().listenObject(listeners, Listener.METHOD_NAME_INSTANCIATE
									,MethodHelper.Method.Parameter.buildArray(Instance.class, this,Object.class,element))
							,elementClass == null ? (T)element : ClassHelper.getInstance().instanciateOne(elementClass));
				}
				
				Boolean isAddable = InstanceHelper.getInstance().getIfNotNullElseDefault(
						ListenerHelper.getInstance().listenBoolean(listeners, Listener.METHOD_NAME_IS_ADDABLE
								,MethodHelper.Method.Parameter.buildArray(Instance.class, this,Object.class,element))
						,elementClass == null ? Boolean.TRUE : element.getClass().equals(elementClass));
				
				if(Boolean.TRUE.equals(isAddable)){
					getCollection().add((T) element);
					ListenerHelper.getInstance().listen(listeners, Listener.METHOD_NAME_ADD_ONE
							,MethodHelper.Method.Parameter.buildArray(Instance.class, this,elementClass,element));
				}	
			}
			return this;
		}
		
		public Instance<T> addMany(Collection<?> elements){
			if(CollectionHelper.getInstance().isNotEmpty(elements))
				for(Object element : elements)
					addOne(element);
			return this;
		}
		
		public Collection<T> getCollection(){
			if(collection==null)
				collection=new ArrayList<>();
			return collection;
		}
		
		public Collection<String> getFieldNames(){
			if(fieldNames==null)
				fieldNames=new LinkedHashSet<>();
			return fieldNames;
		}
		
		public Instance<T> addOneFieldName(String fieldName){
			getFieldNames().add(fieldName);
			return this;
		}
		
		public Instance<T> addManyFieldName(Collection<String> fieldNames){
			getFieldNames().addAll(fieldNames);
			return this;
		}
		
		public Instance<T> addManyFieldName(String...fieldNames){
			return addManyFieldName(Arrays.asList(fieldNames));
		}
		
		public Boolean isSynchonizationEnabled(){
			return Boolean.TRUE.equals(synchonizationEnabled);
		}
		
		@SuppressWarnings("unchecked")
		public <CLASS> Collection<CLASS> filter(Class<CLASS> aClass){
			return (Collection<CLASS>) getInstance().filter(collection, aClass);
		}
		
		public <CLASS> CLASS getItemAt(Class<CLASS> aClass,Integer index){
			return getInstance().getElementAt(filter(aClass), index);
		}
		
		public <CLASS> void removeItem(CLASS element){
			collection = getInstance().removeElement(collection, element);
			ListenerHelper.getInstance().listen(listeners, Listener.METHOD_NAME_REMOVE_ELEMENT,MethodHelper.Method.Parameter.buildArray(Instance.class, this
					,getElementClass(), element));
		}
		
		public <CLASS> void removeItemAt(Class<CLASS> aClass,Integer index){
			removeItem(getItemAt(aClass, index));
		}
		
		/*@SuppressWarnings("unchecked")
		public <CLASS> Instance<T> clear(Class<CLASS> aClass){
			for(int index = 0 ; index < ((List<?>)collection).size() ; )
				if( ((List<?>)collection).get(index).getClass().equals(aClass) );
			return this;
		}*/
		
		@Override
		public String toString() {
			return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
		}
	
		/**/
		
		public static interface Listener<TYPE> {
			
			String METHOD_NAME_IS_INSTANCIATABLE = "isInstanciatable";
			Boolean isInstanciatable(Instance<TYPE> instance,Object object);
			
			String METHOD_NAME_INSTANCIATE = "instanciate";
			TYPE instanciate(Instance<TYPE> instance,Object object);
			
			String METHOD_NAME_IS_ADDABLE = "isAddable";
			Boolean isAddable(Instance<TYPE> instance,Object object);
			
			String METHOD_NAME_ADD_ONE = "addOne";
			void addOne(Instance<TYPE> instance,TYPE object);
			Listener<TYPE> addOne(Instance<TYPE> instance);
			Listener<TYPE> addMany(Instance<TYPE> instance,Collection<?> collection);
			Listener<TYPE> addMany(Instance<TYPE> instance,Object...object);
			
			String METHOD_NAME_IS_REMOVABLE = "isRemovable";
			Boolean isRemovable(Instance<TYPE> instance,Object object);
			Listener<TYPE> removeOne(Instance<TYPE> instance,Object object);
			Listener<TYPE> removeMany(Instance<TYPE> instance,Collection<?> collection);
			Listener<TYPE> removeMany(Instance<TYPE> instance,Object...objects);
			
			String METHOD_NAME_REMOVE_ELEMENT = "removeElement";
			void removeElement(Instance<TYPE> instance,TYPE element);
			
			@Getter
			public static class Adapter<TYPE> implements Listener<TYPE>,Serializable {
				private static final long serialVersionUID = 1L;

				@Override
				public Boolean isInstanciatable(Instance<TYPE> instance, Object object) {
					return null;
				}
				
				@Override
				public TYPE instanciate(Instance<TYPE> instance,Object object) {
					return null;
				}
				
				@Override
				public Boolean isAddable(Instance<TYPE> instance, Object object) {
					return null;
				}
				
				@Override
				public void addOne(Instance<TYPE> instance,TYPE object) {}

				@Override
				public Listener<TYPE> addOne(Instance<TYPE> instance) {
					return null;
				}

				@Override
				public Listener<TYPE> addMany(Instance<TYPE> instance,Collection<?> collection) {
					return null;
				}

				@Override
				public Listener<TYPE> addMany(Instance<TYPE> instance,Object... objects) {
					return null;
				}
				
				@Override
				public Boolean isRemovable(Instance<TYPE> instance, Object object) {
					return null;
				}

				@Override
				public Listener<TYPE> removeOne(Instance<TYPE> instance,Object object) {
					return null;
				}
				
				@Override
				public Listener<TYPE> removeMany(Instance<TYPE> instance, Collection<?> collection) {
					return null;
				}
				
				@Override
				public Listener<TYPE> removeMany(Instance<TYPE> instance, Object...objects) {
					return null;
				}
				
				@Override
				public void removeElement(Instance<TYPE> instance,TYPE element) {}
				
			}
		}
	}

	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Element<T> extends AbstractBean implements Serializable {
		private static final long serialVersionUID = 1L;
		
		protected T object;
		protected String name;
		protected Boolean processable=Boolean.TRUE;
		
	}
}
