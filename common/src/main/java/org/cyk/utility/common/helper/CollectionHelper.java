package org.cyk.utility.common.helper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.cdi.AbstractBean;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Singleton @Named
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
	
	public <T> Instance<T> getCollectionInstance(Class<T> elementClass,Class<?> elementObjectClass,Class<?> sourceClass,Class<?> sourceObjectClass){
		return new Instance<T>().setElementClass(elementClass).setElementObjectClass(elementObjectClass)
				.setSourceClass(sourceClass).setSourceObjectClass(sourceObjectClass);
	}
	
	public <T> Instance<T> getCollectionInstance(Class<T> aClass){
		return getCollectionInstance(aClass,null,null,null);
	}
	
	public <ELEMENT> ELEMENT getElementAt(Collection<ELEMENT> collection,Integer index){
		ELEMENT element = null;
		if(isNotEmpty(collection) && index < getSize(collection)){
			if(collection instanceof List)
				element = ((List<ELEMENT>)collection).get(index.intValue());
			else {
				java.util.Iterator<ELEMENT> iterator = collection.iterator();
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
				java.util.Iterator<ELEMENT> iterator = collection.iterator();
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
				java.util.Iterator<ELEMENT> iterator = collection.iterator();
				while (iterator.hasNext()){
					ELEMENT index = iterator.next();
					if(element != index )
						newCollection.add(index);
				}
			}
		}
		return newCollection;
	}
	
	public Collection<?> remove(Collection<?> collection,Collection<?> elements){
		Collection<?> newCollection = collection;
		//TODO think better
		if(isNotEmpty(collection) && isNotEmpty(elements)){
			if(collection instanceof List){
				((List<?>)collection).removeAll(elements);
			}else {
				if(collection instanceof Set){
					((Set<?>)collection).removeAll(elements);
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
	
	public Boolean isEmpty(Instance<?> collection){
		return collection == null || isEmpty(collection.getElements());
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
	
	public Boolean isNotEmpty(Instance<?> collection){
		return collection != null && isNotEmpty(collection.getElements());
	}
	
	public <T> T getFirst(Collection<T> collection){
		if(isEmpty(collection))
			return null;
		return collection.iterator().next();
	}
	
	public <T> T getLast(Collection<T> collection){
		if(isEmpty(collection))
			return null;
		if(collection instanceof List)
			return ((List<T>)collection).get(((List<T>)collection).size()-1);
		new RuntimeException("cannot find last on collection of type "+collection.getClass());
		return null;
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
	
	public <T> void sort(Collection<T> elements,Comparator<T> comparator){
		if(isNotEmpty(elements))
			if(elements instanceof List){
				if(comparator == null)
					comparator = new org.cyk.utility.common.Comparator<T>(AbstractBean.FIELD___ORDER_NUMBER__);
				Collections.sort((List<T>)elements, comparator);
			}else
				ThrowableHelper.getInstance().throw_("cannot sort a collection of type "+elements.getClass());
	}
	
	public <T> void sort(Collection<T> elements){
		sort(elements,null);
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
		private Class<?> elementObjectClass,sourceClass,sourceObjectClass,fieldsContainerClass;
		@SuppressWarnings("rawtypes")
		private Collection sources;
		private String getSourceObjectMethodName;
		private Collection<T> elements;
		private Boolean synchonizationEnabled,isOrderNumberComputeEnabled,isSourceDisjoint=Boolean.TRUE;
		private Collection<String> fieldNames;
		private Map<String,String> elementFieldsAndElementObjectFieldsMap;
		private Collection<Listener<T>> listeners;
		private Boolean isCreatable=Boolean.TRUE,isReadable=Boolean.TRUE,isUpdatable=Boolean.TRUE,isRemovable=Boolean.TRUE,isNullAddable=Boolean.FALSE;
		private Boolean isEachElementHasSource,isElementObjectCreatable,hasAlreadyContainedElements;
		private Instance<Object> masterElementObjectCollection;
		private Comparator<T> comparator;
		
		public Boolean isEditable(){
			return Boolean.TRUE.equals(getIsCreatable()) || Boolean.TRUE.equals(getIsUpdatable()) || Boolean.TRUE.equals(getIsRemovable());
		}
		
		public Boolean isNotEditable(){
			return !Boolean.TRUE.equals(isEditable());
		}
		
		public Instance<T> mapElementFieldsAndElementObjectFields(String...strings){
			if(ArrayHelper.getInstance().isNotEmpty(strings)){
				if(elementFieldsAndElementObjectFieldsMap==null)
					elementFieldsAndElementObjectFieldsMap = new HashMap<String, String>();
				for(Integer index = 0 ; index < strings.length ; index = index + 2)
					MapHelper.getInstance().addKeyValue(elementFieldsAndElementObjectFieldsMap, (Object[])strings);
			}
			return this;
		}
		
		public Instance<T> mapElementObjectFields(String...strings){
			if(ArrayHelper.getInstance().isNotEmpty(strings)){
				Collection<String> collection = new ArrayList<String>();
				for(String string : strings){
					if(StringUtils.contains(string, Constant.CHARACTER_DOT.toString()))
						collection.add(StringUtils.substringAfterLast(string, Constant.CHARACTER_DOT.toString()));
					else
						collection.add(string);
					collection.add(string);
				}
				mapElementFieldsAndElementObjectFields(collection.toArray(new String[]{}));
			}
			return this;
		}
		
		public Instance<T> addListener(Listener<T> listener){
			if(this.listeners == null)
				this.listeners = new ArrayList<>();
			this.listeners.add(listener);
			return this;
		}
		
		public Instance<T> addListeners(Collection<Listener<T>> listeners){
			if(getInstance().isNotEmpty(listeners))
				for(Listener<T> listener : listeners)
					addListener(listener);
			return this;
		}
		
		public Instance<T> addOne(){
			T element = ClassHelper.getInstance().instanciateOne(getElementClass());
			addOne(element);
			return this;
		}
		
		@SuppressWarnings("unchecked")
		public Instance<T> addOne(Object element){
			if(Boolean.TRUE.equals(getIsCreatable()) && (!Boolean.TRUE.equals(getIsNullAddable()) && element!=null)){
				Class<T> elementClass = getElementClass();
				Class<?> elementObjectClass = getElementObjectClass();
				if(ClassHelper.getInstance().isEqual(elementObjectClass, element.getClass())){
					Object elementObject = element;
					if(elementClass!=null)
						element = ClassHelper.getInstance().instanciateOne(elementClass);
					if(element instanceof Element){
						((Element<Object>)element).setObject(elementObject);
						((Element<Object>)element).setCollection(this);
						((Element<Object>)element).read();
					}
				}
				Class<?> sourceObjectClass = getSourceObjectClass();
				Object source = ListenerHelper.getInstance().listenObject(listeners, Listener.METHOD_NAME_GET_SOURCE
								,MethodHelper.Method.Parameter.buildArray(Instance.class, this,Object.class,element));
				Object sourceObject = null;
				if(source==null){
					if(sourceObjectClass!=null /*&& getSourceClass().equals(element.getClass())*/)
						if(getInstance().isNotEmpty(sources))
							for(Object index : sources){
								sourceObject = ListenerHelper.getInstance().listenObject(listeners, Listener.METHOD_NAME_GET_SOURCE_OBJECT
										,MethodHelper.Method.Parameter.buildArray(Instance.class, this,Object.class,index));
								if(sourceObject==null){
									if(StringHelper.getInstance().isNotBlank(getSourceObjectMethodName))
										sourceObject = MethodHelper.getInstance().call(index, Object.class, getSourceObjectMethodName);
								}
								if(sourceObject==element){
									source = index;
									break;
								}
							}
					if(source==null)
						source = elementClass == null ? null : element;
				}
				
				//Object sourceObject = ListenerHelper.getInstance().listenObject(listeners, Listener.METHOD_NAME_GET_SOURCE_OBJECT
				//		,MethodHelper.Method.Parameter.buildArray(Instance.class, this,Object.class,source));
				/*if(sourceObject==null){
					if(sourceObjectClass!=null)
						for(Object index : getSources()){
							sourceObject = ListenerHelper.getInstance().listenObject(listeners, Listener.METHOD_NAME_GET_SOURCE_OBJECT
									,MethodHelper.Method.Parameter.buildArray(Instance.class, this,Object.class,index));
							if(sourceObject==element){
								break;
							}
						}
				}*/
				Boolean isInstanciatable = InstanceHelper.getInstance().getIfNotNullElseDefault(
						ListenerHelper.getInstance().listenBoolean(listeners, Listener.METHOD_NAME_IS_INSTANCIATABLE
								,MethodHelper.Method.Parameter.buildArray(Instance.class, this,Object.class,element))
						,elementClass == null ? Boolean.TRUE : sourceObjectClass == null ? ClassHelper.getInstance().isInstanceOf(elementClass, element.getClass()) : element.getClass().equals(sourceObjectClass));
				
				if(!ClassHelper.getInstance().isInstanceOf(elementClass, element.getClass()) && Boolean.TRUE.equals(isInstanciatable)){
					element = InstanceHelper.getInstance().getIfNotNullElseDefault(
							ListenerHelper.getInstance().listenObject(listeners, Listener.METHOD_NAME_INSTANCIATE
									,MethodHelper.Method.Parameter.buildArray(Instance.class, this,Object.class,element))
							,elementClass == null ? (T)element : ClassHelper.getInstance().instanciateOne(elementClass));
				}
				
				Boolean isAddable = InstanceHelper.getInstance().getIfNotNullElseDefault(
						ListenerHelper.getInstance().listenBoolean(listeners, Listener.METHOD_NAME_IS_ADDABLE
								,MethodHelper.Method.Parameter.buildArray(Instance.class, this,Object.class,element))
						,elementClass == null ? Boolean.TRUE : ClassHelper.getInstance().isInstanceOf(elementClass, element.getClass()));
				
				if(Boolean.TRUE.equals(isAddable)){
					if(getElements().add((T) element)){
						if(hasAlreadyContainedElements == null)
							hasAlreadyContainedElements = Boolean.TRUE;
						if(element instanceof AbstractBean){
							if(((AbstractBean)element).get__orderNumber__() == null)
								((AbstractBean)element).set__orderNumber__(new Long(getElements().size()));
						}
						if(element instanceof Element){
							((Element<T>)element).setCollection(this).setSource(source);
							if(Boolean.TRUE.equals(getIsElementObjectCreatable()) && ((Element<T>)element).getObject()==null)
								if(elementObjectClass!=null){
									if(((Element<T>)element).getObject()==null){
										((Element<T>)element).setObject((T) ListenerHelper.getInstance().listenObject(listeners, Listener.METHOD_NAME_INSTANCIATE
										,MethodHelper.Method.Parameter.buildArray(Instance.class, this,Object.class,element)));
										
										if(((Element<T>)element).getObject()==null)
											((Element<T>)element).setObject((T) ClassHelper.getInstance().instanciateOne(elementObjectClass));
									}
								}
							Class<?> fieldsContainerClass = InstanceHelper.getInstance().getIfNotNullElseDefault(getFieldsContainerClass(),elementClass);
							((Element<T>)element).set__fieldsContainer__(elementClass.equals(fieldsContainerClass) ? element : ((Element<T>)element).getObject());
							((Element<T>)element).setIsReadable(getIsReadable()).setIsUpdatable(getIsUpdatable()).setIsRemovable(getIsRemovable());
							if(((Element<T>) element).get__name__()==null)
								((Element<T>)element).set__name__(((Element<T>) element).getObject() == null ? element.toString()
									: InstanceHelper.getInstance().getLabel(((Element<T>) element).getObject()));
						}
						if(Boolean.TRUE.equals(getIsSourceDisjoint()) && sources!=null){
							sources.remove(source);
						}
						ListenerHelper.getInstance().listen(listeners, Listener.METHOD_NAME_ADD_ONE
								,MethodHelper.Method.Parameter.buildArray(Instance.class, this,elementClass,element,Object.class,source,Object.class,sourceObject));	
					}
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
		
		public Instance<T> setMany(Collection<?> elements){
			removeAll().addMany(elements);
			return this;
		}
		
		public Collection<T> getElements(){
			if(elements==null)
				elements=new ArrayList<>();
			return elements;
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
			return (Collection<CLASS>) getInstance().filter(getElements(), aClass);
		}
		
		public <CLASS> CLASS getOneAt(Class<CLASS> aClass,Integer index){
			return getInstance().getElementAt(filter(aClass), index);
		}
		
		@SuppressWarnings("unchecked")
		public Instance<T> removeOne(Object element){
			if(Boolean.TRUE.equals(getIsRemovable())){
				Class<T> elementClass = getElementClass();
				elements = getInstance().removeElement(getElements(), element);
				if(element instanceof Element){
					((Element<T>)element).setCollection(null);
				}
				if(Boolean.TRUE.equals(getIsSourceDisjoint()) && sources!=null){
					Boolean isHasSource = getIsEachElementHasSource();
					if(isHasSource==null || Boolean.FALSE.equals(isHasSource))
						isHasSource = InstanceHelper.getInstance().getIfNotNullElseDefault(
							ListenerHelper.getInstance().listenBoolean(listeners, Listener.METHOD_NAME_IS_HAS_SOURCE
									,MethodHelper.Method.Parameter.buildArray(Instance.class, this,elementClass,element))
							,elementClass == null ? Boolean.FALSE : !element.getClass().equals(elementClass));
					if(Boolean.TRUE.equals(isHasSource)){
						Object elementSource = InstanceHelper.getInstance().getIfNotNullElseDefault(
								ListenerHelper.getInstance().listenObject(listeners, Listener.METHOD_NAME_GET_SOURCE
										,MethodHelper.Method.Parameter.buildArray(Instance.class, this,Object.class,element)),elementClass == null ? null : null);
						if(elementSource==null){
							if(elementClass.equals(element.getClass())){
								if(element instanceof Element)
									elementSource = ((Element<T>)element).getSource();
								else
									elementSource = element;
							}else if(getSourceClass().equals(element.getClass())){
								for(Object index : getSources()){
									Object sourceObject = ListenerHelper.getInstance().listenObject(listeners, Listener.METHOD_NAME_GET_SOURCE_OBJECT
											,MethodHelper.Method.Parameter.buildArray(Instance.class, this,Object.class,index));
									if(sourceObject==element){
										elementSource = index;
										break;
									}
								}
							}
						}
						if(elementSource==null){
							elementSource = element instanceof Element ? ((Element<T>)element).getSource() : element;
						}
						sources.add(elementSource);
					}
				}
				ListenerHelper.getInstance().listen(listeners, Listener.METHOD_NAME_REMOVE_ONE,MethodHelper.Method.Parameter.buildArray(Instance.class, this
						,elementClass, element));	
			}
			return this;
		}
		
		public <CLASS> Instance<T> removeOneAt(Class<CLASS> aClass,Integer index){
			removeOne(getOneAt(aClass, index));
			return this;
		}
		
		public <CLASS> Instance<T> removeAll(){
			if(elements!=null)
				elements.clear();
			return this;
		}
		
		public <CLASS> Instance<T> removeMany(java.util.Collection<?> collection){
			if(getInstance().isNotEmpty(collection))
				for(Object object : collection)
					removeOne(object);
			return this;
		}
		
		/*@SuppressWarnings("unchecked")
		public <CLASS> Instance<T> clear(Class<CLASS> aClass){
			for(int index = 0 ; index < ((List<?>)collection).size() ; )
				if( ((List<?>)collection).get(index).getClass().equals(aClass) );
			return this;
		}*/
		
		@SuppressWarnings("unchecked")
		public Instance<T> read(){
			if(ClassHelper.getInstance().isInstanceOf(Element.class, getElementClass())){
				for(Object object : getElements())
					((Element<T>)object).read();
			}
			return this;
		}
		
		@SuppressWarnings("unchecked")
		public Instance<T> write(){
			if(ClassHelper.getInstance().isInstanceOf(Element.class, getElementClass())){
				for(Object object : getElements())
					((Element<T>)object).write();
			}
			if(masterElementObjectCollection!=null){
				//System.out.println("CollectionHelper.Instance.write() : "+masterElementObjectCollection.getElements());
				CollectionHelper.getInstance().remove(masterElementObjectCollection.getElements(),masterElementObjectCollection.filter(getElementObjectClass()));
				CollectionHelper.getInstance().add(masterElementObjectCollection.getElements(), Boolean.TRUE,getElementObjects());
			}
			
			return this;
		}
		
		@SuppressWarnings("unchecked")
		public <CLASS> Collection<CLASS> getElementObjects(Class<CLASS> aClass){
			if(ClassHelper.getInstance().isInstanceOf(Element.class, elementClass)){
				Collection<CLASS> collection = new ArrayList<>();
				for(Object element : elements)
					collection.add((CLASS) ((Element<T>)element).getObject());
				return collection;
			}
			return null;
		}
		
		@SuppressWarnings("unchecked")
		public <CLASS> Collection<CLASS> getElementObjects(){
			return (Collection<CLASS>) getElementObjects(getElementObjectClass());
		}

		public Boolean isEmpty(){
			return getInstance().isEmpty(elements);
		}
		
		public Boolean isNotEmpty(){
			return getInstance().isNotEmpty(elements);
		}
		
		public Instance<T> sort(){
			if(getInstance().isNotEmpty(elements))
				getInstance().sort(elements, comparator);
			return this;
		}
		
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
			
			String METHOD_NAME_INSTANCIATE_OBJECT = "instanciateObject";
			Object instanciateObject(Instance<TYPE> instance,TYPE element);
			
			String METHOD_NAME_IS_HAS_SOURCE = "isHasSource";
			Boolean isHasSource(Instance<TYPE> instance,TYPE element);
			
			String METHOD_NAME_GET_SOURCE = "getSource";
			Object getSource(Instance<TYPE> instance,Object object);
			
			String METHOD_NAME_GET_SOURCE_OBJECT = "getSourceObject";
			Object getSourceObject(Instance<TYPE> instance,Object object);
			
			String METHOD_NAME_IS_ADDABLE = "isAddable";
			Boolean isAddable(Instance<TYPE> instance,Object object);
			
			String METHOD_NAME_ADD_ONE = "addOne";
			void addOne(Instance<TYPE> instance,TYPE object,Object source,Object sourceObject);
			Listener<TYPE> addOne(Instance<TYPE> instance);
			
			Listener<TYPE> addMany(Instance<TYPE> instance,Collection<?> collection);
			Listener<TYPE> addMany(Instance<TYPE> instance,Object...object);
			
			String METHOD_NAME_IS_REMOVABLE = "isRemovable";
			Boolean isRemovable(Instance<TYPE> instance,Object object);
			//Listener<TYPE> removeOne(Instance<TYPE> instance,Object object);
			Listener<TYPE> removeMany(Instance<TYPE> instance,Collection<?> collection);
			Listener<TYPE> removeMany(Instance<TYPE> instance,Object...objects);
			
			String METHOD_NAME_REMOVE_ONE = "removeOne";
			void removeOne(Instance<TYPE> instance,TYPE element);
			
			@Getter
			public static class Adapter<TYPE> implements Listener<TYPE>,Serializable {
				private static final long serialVersionUID = 1L;

				@Override
				public Boolean isHasSource(Instance<TYPE> instance, TYPE element) {
					return null;
				}
				
				@Override
				public Object getSource(Instance<TYPE> instance, Object object) {
					return null;
				}
				
				@Override
				public Boolean isInstanciatable(Instance<TYPE> instance, Object object) {
					return null;
				}
				
				@Override
				public TYPE instanciate(Instance<TYPE> instance,Object object) {
					return null;
				}
				
				@Override
				public Object instanciateObject(Instance<TYPE> instance, TYPE element) {
					return null;
				}
				
				@Override
				public Boolean isAddable(Instance<TYPE> instance, Object object) {
					return null;
				}
				
				@Override
				public void addOne(Instance<TYPE> instance,TYPE element,Object source,Object sourceObject) {}

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

				/*@Override
				public Listener<TYPE> removeOne(Instance<TYPE> instance,Object object) {
					return null;
				}*/
				
				@Override
				public Listener<TYPE> removeMany(Instance<TYPE> instance, Collection<?> collection) {
					return null;
				}
				
				@Override
				public Listener<TYPE> removeMany(Instance<TYPE> instance, Object...objects) {
					return null;
				}
				
				@Override
				public void removeOne(Instance<TYPE> instance,TYPE element) {}

				@Override
				public Object getSourceObject(Instance<TYPE> instance, Object object) {
					return null;
				}
				
			}
		}
	}

	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Element<T> extends AbstractBean implements Serializable {
		private static final long serialVersionUID = 1L;
		
		protected CollectionHelper.Instance<?> collection;
		protected Object sourceObject;
		protected Object source;
		protected T object;
		protected String __name__;
		protected Boolean __processable__=Boolean.TRUE;
		protected Object __fieldsContainer__;
		protected Boolean isReadable,isUpdatable,isRemovable;
		
		protected CommandHelper.Command readCommand,updateCommand,removeCommand;
		protected MapHelper.Map<String,CommandHelper.Command> commandMap;// = new MapHelper.Map<String, CommandHelper.Command>(String.class, CommandHelper.Command.class);
		
		public MapHelper.Map<String,CommandHelper.Command> getCommandMap(){
			if(commandMap==null)
				commandMap = new MapHelper.Map<String, CommandHelper.Command>(String.class, CommandHelper.Command.class);
			return commandMap;
		}
		
		public Element<T> read(){
			if(collection!=null){
				Map<String,String> map = collection.getElementFieldsAndElementObjectFieldsMap();
				if(MapHelper.getInstance().isNotEmpty(map)){
					Object object = getObject();
					for(Entry<String,String> entry : map.entrySet())
						read(object,entry.getValue(),entry.getKey());
						//FieldHelper.getInstance().set(this,FieldHelper.getInstance().read(object, entry.getValue()),entry.getKey());
				}	
			}
			return this;
		}
		
		protected Object read(Object object,String fieldName){
			return FieldHelper.getInstance().read(object, fieldName);
		}
		
		protected void read(Object object,String sourceFieldName,String destinationFieldName){
			FieldHelper.getInstance().set(this,read(object, sourceFieldName),destinationFieldName);
		}
		
		public Element<T> write(){
			if(collection!=null){
				Map<String,String> map = collection.getElementFieldsAndElementObjectFieldsMap();
				if(MapHelper.getInstance().isNotEmpty(map)){
					Object object = getObject();
					for(Entry<String,String> entry : map.entrySet())
						write(object,entry.getKey(),entry.getValue());
						//FieldHelper.getInstance().set(object,FieldHelper.getInstance().read(this, entry.getKey()),entry.getValue());
				}	
			}
			return this;
		}
		
		protected void write(Object object,String fieldName,Object value){
			FieldHelper.getInstance().set(object,value,fieldName);
		}
		
		protected void write(Object object,String sourceFieldName,String destinationFieldName){
			write(object, destinationFieldName, FieldHelper.getInstance().read(this, sourceFieldName));
			//FieldHelper.getInstance().set(object,FieldHelper.getInstance().read(this, sourceFieldName),destinationFieldName);
		}
		
		@Override
		public String toString() {
			return __name__;
		}
	}

	public static interface Iterator<T> extends org.cyk.utility.common.Action<Collection<T>, Void>{
		
		public static class Adapter<T> extends org.cyk.utility.common.Action.Adapter.Default<Collection<T>, Void> implements Iterator<T>,Serializable {
			private static final long serialVersionUID = 1L;
			
			@SuppressWarnings("unchecked")
			public Adapter(Collection<T> input) {
				super("iterate", (Class<Collection<T>>) ClassHelper.getInstance().getByName(Collection.class), input, Void.class);
			}
			
			public static class Default<T> extends Iterator.Adapter<T> implements Serializable {
				private static final long serialVersionUID = 1L;
				
				public Default(Collection<T> collection) {
					super(collection);
					setIsInputRequired(Boolean.FALSE);
					setIsProduceOutputOnly(Boolean.TRUE);
				}
				
				@Override
				protected Void __execute__() {
					if(getInstance().isNotEmpty(getInput()))
						for(T object : getInput())
							__executeForEach__(object);
					return null;
				}
				
				protected void __executeForEach__(T object){
					ThrowableHelper.getInstance().throwNotYetImplemented();
				}
			}
		}
	}

	/**/

}
