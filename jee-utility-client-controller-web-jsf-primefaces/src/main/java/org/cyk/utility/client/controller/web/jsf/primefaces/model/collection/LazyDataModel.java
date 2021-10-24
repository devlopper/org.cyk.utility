package org.cyk.utility.client.controller.web.jsf.primefaces.model.collection;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import javax.ws.rs.core.Response;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.identifier.resource.ParameterName;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.number.NumberHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.rest.ResponseHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.value.ValueConverter;
import org.cyk.utility.client.controller.ControllerEntity;
import org.cyk.utility.client.controller.ControllerLayer;
import org.cyk.utility.client.controller.web.WebController;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.AbstractFilterController;
import org.cyk.utility.controller.Arguments;
import org.cyk.utility.controller.EntityReader;
import org.cyk.utility.persistence.query.Filter;
import org.cyk.utility.persistence.query.QueryExecutorArguments;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class LazyDataModel<ENTITY> extends org.primefaces.model.LazyDataModel<ENTITY> implements Serializable {

	private Map<String,ENTITY> map;
	private Class<ENTITY> entityClass;
	private ControllerEntity<ENTITY> controller;
	private String readQueryIdentifier,countQueryIdentifier,entityFieldsNamesAsString;
	private Collection<String> entityFieldsNames;
	private Collection<String> copiableFiltersEntriesKeysToFilterFields;
	private Listener<ENTITY> listener;
	
	private List<ENTITY> list;

	private Boolean readerUsable;
	private Boolean isCountEqualsListSize;
	
	private Boolean loggableAsInfo = ValueConverter.getInstance().convertToBoolean(WebController.getInstance().getRequestParameter(ParameterName.LOGGABLE_AS_INFO));
	
	private int __first__,__pageSize__;
	private Integer __count__;
	private String __sortField__,__entityFieldsNamesAsString__;
	private SortOrder __sortOrder__;
	private List<SortMeta> __multiSortMeta__;
	private Map<String, Object> __filters__;
	private Filter.Dto __filter__;
	private Properties __readProperties__;
	private Arguments<ENTITY> __readerArguments__;
	private Response __response__;
	private Listener<ENTITY> __listener__;
	
	public LazyDataModel(Class<ENTITY> entityClass) {
		this.entityClass = entityClass;
		if(this.entityClass != null) {
			this.controller = DependencyInjection.inject(ControllerLayer.class).injectInterfaceClassFromEntityClass(entityClass);
		}
	}
	
	@SuppressWarnings("unchecked")
	protected List<ENTITY> __load__(int first, int pageSize,Map<String, Object> filters) {
		long timestamp = System.currentTimeMillis();
		list = null;
		__first__ = first;
		__pageSize__ = pageSize;
		__filters__ = filters;
		if(controller == null)
			return null;
		__listener__ = listener;
		if(__listener__ == null) {
			__listener__ = listener;
			if(__listener__ == null)
				__listener__ = (Listener<ENTITY>) Listener.AbstractImpl.DefaultImpl.INSTANCE;
		}
		__filter__ = __listener__.instantiateFilter(this);
		if(Boolean.TRUE.equals(__listener__.getReaderUsable(this)))
			__readerArguments__ = __listener__.instantiateArguments(this);
		else
			__readProperties__ = __listener__.instantiateReadProperties(this);
		list = __listener__.read(this);
		__response__ = __listener__.getResponse(this);
		if(CollectionHelper.isEmpty(list))
			__count__ = 0;
		else {
			if(Boolean.TRUE.equals(isCountEqualsListSize))
				__count__ = list.size();
			else
				__count__ = __listener__.getCount(this);
		}
		setRowCount(__count__);
		long duration = System.currentTimeMillis() - timestamp;
		if(Boolean.TRUE.equals(LOGGABLE)) {
			LogHelper.log(String.format("Page(%s,%s) , duration=%s", first,pageSize,duration), LOG_LEVEL,getClass());
		}
		map = null;
		return list;
	}
	
	@Override
	public List<ENTITY> load(int first, int pageSize, List<SortMeta> multiSortMeta, Map<String, Object> filters) {
		long timestamp = System.currentTimeMillis();
		__multiSortMeta__ = multiSortMeta;
		__load__(first, pageSize, filters);
		long duration = System.currentTimeMillis() - timestamp;
		if(Boolean.TRUE.equals(LOGGABLE))
			LogHelper.log(String.format("Page(%s,%s) , duration=%s", first,pageSize,duration), LOG_LEVEL,getClass());
		map = null;
		return list;
	}
	
	@Override
	public List<ENTITY> load(int first, int pageSize, String sortField, SortOrder sortOrder,Map<String, Object> filters) {
		long timestamp = System.currentTimeMillis();
		__sortField__ = sortField;
		__sortOrder__ = sortOrder;
		__load__(first, pageSize, filters);
		long duration = System.currentTimeMillis() - timestamp;
		if(Boolean.TRUE.equals(LOGGABLE))
			LogHelper.log(String.format("Page(%s,%s) , duration=%s", first,pageSize,duration), LOG_LEVEL,getClass());
		map = null;
		return list;
	}
	
	
	
	/**/
	
	@Override
	public Object getRowKey(ENTITY entity) {
		if(entity == null)
			return null;
		Object identifier = FieldHelper.readSystemIdentifier(entity);
		if(identifier == null)
			return null;
		if(map == null)
			map = new HashMap<>();
		map.put(identifier.toString(), entity);
		return identifier;
	}
	
	@Override
	public ENTITY getRowData(String identifier) {
		if(map == null)
			return null;
		return map.get(identifier);
	}
	
	public LazyDataModel<ENTITY> copyFiltersEntriesToFilterFields(Collection<String> names) {
		if(CollectionHelper.isEmpty(names) || MapHelper.isEmpty(__filters__))
			return this;
		names.forEach(name -> {
			__filter__.addField(name,__filters__.get(name));	
		});
		return this;
	}
	
	public LazyDataModel<ENTITY> copyFiltersEntriesToFilterFields(String...names) {
		if(ArrayHelper.isEmpty(names))
			return this;
		return copyFiltersEntriesToFilterFields(CollectionHelper.listOf(names));
	}
	
	/**/
	
	public static interface Listener<T> {
		Boolean getReaderUsable(LazyDataModel<T> lazyDataModel);
		String getReadQueryIdentifier(LazyDataModel<T> lazyDataModel);
		Filter.Dto instantiateFilter(LazyDataModel<T> lazyDataModel);
		Arguments<T> instantiateArguments(LazyDataModel<T> lazyDataModel);
		Properties instantiateReadProperties(LazyDataModel<T> lazyDataModel);
		List<T> read(LazyDataModel<T> lazyDataModel);
		Response getResponse(LazyDataModel<T> lazyDataModel);
		Integer getCount(LazyDataModel<T> lazyDataModel);
		Collection<String> getCopiableFiltersEntriesKeysToFilterFields(LazyDataModel<T> lazyDataModel);
		
		AbstractFilterController getFilterController();
		Listener<?> setFilterController(AbstractFilterController filterController);
		
		/**/
		
		public static abstract class AbstractImpl<T> extends AbstractObject implements Listener<T>,Serializable{
			
			@Getter @Setter @Accessors(chain=true)
			protected AbstractFilterController filterController;
			
			@Override
			public Boolean getReaderUsable(LazyDataModel<T> lazyDataModel) {
				return lazyDataModel.readerUsable;
			}
			
			@Override
			public String getReadQueryIdentifier(LazyDataModel<T> lazyDataModel) {
				return lazyDataModel.readQueryIdentifier;
			}
			
			@Override
			public Filter.Dto instantiateFilter(LazyDataModel<T> lazyDataModel) {
				if(MapHelper.isEmpty(lazyDataModel.__filters__))
					return null;
				Filter.Dto filter = new Filter.Dto();
				lazyDataModel.__filters__.forEach((key,value) -> {
					filter.addField(key, value);
				});
				return filter;
			}
			
			@Override
			public Arguments<T> instantiateArguments(LazyDataModel<T> lazyDataModel) {
				//System.out.println("LazyDataModel.Listener.AbstractImpl.instantiateArguments() LOGGABLE AS INFO : "+lazyDataModel.loggableAsInfo);
				Arguments<T> arguments = new Arguments<T>()
					.setRepresentationArguments(new org.cyk.utility.representation.Arguments().setQueryExecutorArguments(new QueryExecutorArguments.Dto()
						.setQueryIdentifier(getReadQueryIdentifier(lazyDataModel))
						.setFirstTupleIndex(lazyDataModel.__first__)
						.setNumberOfTuples(lazyDataModel.__pageSize__)
						.setFilter(lazyDataModel.__filter__))
						.setCountable(Boolean.TRUE))
						.setLoggableAsInfo(lazyDataModel.loggableAsInfo);
				order(lazyDataModel, arguments);
				/*
				if(StringHelper.isNotBlank(lazyDataModel.__sortField__)) {
					LinkedHashMap<String, org.cyk.utility.__kernel__.computation.SortOrder> map = new LinkedHashMap<>();
					map.put(lazyDataModel.__sortField__,SortOrder.DESCENDING.equals(lazyDataModel.__sortOrder__) 
							? org.cyk.utility.__kernel__.computation.SortOrder.DESCENDING : org.cyk.utility.__kernel__.computation.SortOrder.ASCENDING);
					arguments.getRepresentationArguments().getQueryExecutorArguments().setSortOrders(map);
				}
				*/
				return arguments;
			}
			
			@Override
			public Properties instantiateReadProperties(LazyDataModel<T> lazyDataModel) {
				if(lazyDataModel.__entityFieldsNamesAsString__ == null) {
					if(lazyDataModel.entityFieldsNamesAsString == null)
						lazyDataModel.__entityFieldsNamesAsString__ = StringHelper.concatenate(lazyDataModel.entityFieldsNames, ",");
					else
						lazyDataModel.__entityFieldsNamesAsString__ = lazyDataModel.entityFieldsNamesAsString;
				}					
				Properties properties = new Properties().setQueryIdentifier(getReadQueryIdentifier(lazyDataModel))
						.setFields(lazyDataModel.__entityFieldsNamesAsString__)
						.setFilters(lazyDataModel.__filter__)
						.setIsPageable(Boolean.TRUE).setFrom(lazyDataModel.__first__).setCount(lazyDataModel.__pageSize__);
				return properties;
			}
			
			@Override
			public List<T> read(LazyDataModel<T> lazyDataModel) {
				if(Boolean.TRUE.equals(getReaderUsable(lazyDataModel))) {
					return (List<T>) EntityReader.getInstance().readMany(lazyDataModel.entityClass, lazyDataModel.__readerArguments__);
				}
				return (List<T>) lazyDataModel.controller.read(lazyDataModel.__readProperties__);
			}
			
			@Override
			public Response getResponse(LazyDataModel<T> lazyDataModel) {
				if(Boolean.TRUE.equals(getReaderUsable(lazyDataModel))) {
					if(lazyDataModel.__readerArguments__ == null)
						return null;
					return lazyDataModel.__readerArguments__.get__response__();
				}
				return (Response) lazyDataModel.__readProperties__.getResponse();
			}
			
			@Override
			public Integer getCount(LazyDataModel<T> lazyDataModel) {
				if(lazyDataModel.__response__ == null)
					return 0;
				return NumberHelper.getInteger(ResponseHelper.getHeaderXTotalCount(lazyDataModel.__response__),0);
			}
			
			@Override
			public Collection<String> getCopiableFiltersEntriesKeysToFilterFields(LazyDataModel<T> lazyDataModel) {
				return null;
			}
			
			protected void order(LazyDataModel<T> lazyDataModel,Arguments<T> arguments) {
				if(StringHelper.isNotBlank(lazyDataModel.get__sortField__()) && !SortOrder.UNSORTED.equals(lazyDataModel.get__sortOrder__()))
					orderOne(lazyDataModel, arguments);
				else if(CollectionHelper.isNotEmpty(lazyDataModel.get__multiSortMeta__()))
					orderMany(lazyDataModel, arguments);
				else if(filterController != null && MapHelper.isNotEmpty(filterController.getSortOrders()))
					arguments.getRepresentationArguments(Boolean.TRUE).getQueryExecutorArguments(Boolean.TRUE).getSortOrders(Boolean.TRUE)
					.putAll(filterController.getSortOrders());
			}
			
			protected void orderOne(LazyDataModel<T> lazyDataModel,Arguments<T> arguments) {
				order(lazyDataModel, arguments, lazyDataModel.get__sortField__(), lazyDataModel.get__sortOrder__());
			}
			
			protected void order(LazyDataModel<T> lazyDataModel,Arguments<T> arguments,String fieldName,SortOrder sortOrder) {
				if(StringHelper.isBlank(fieldName) || sortOrder == null || SortOrder.UNSORTED.equals(sortOrder))
					return;
				org.cyk.utility.__kernel__.computation.SortOrder mapping = org.cyk.utility.__kernel__.computation.SortOrder.valueOf(sortOrder.name());
				if(mapping == null) {
					LogHelper.logWarning(String.format("No sort order mapping found for %s", sortOrder), getClass());
					return;
				}
				arguments.order(fieldName, mapping);
			}
			
			protected void orderMany(LazyDataModel<T> lazyDataModel,Arguments<T> arguments) {
				if(CollectionHelper.isEmpty(lazyDataModel.get__multiSortMeta__()))
					return;
				lazyDataModel.get__multiSortMeta__().forEach(sortMeta -> {
					order(lazyDataModel, arguments, sortMeta.getSortField(), sortMeta.getSortOrder());
				});
			}
			
			/**/
			
			public static class DefaultImpl extends AbstractImpl<Object> implements Serializable {
				public static final Listener<Object> INSTANCE = new DefaultImpl();
			}
		}
	}
	
	public static Boolean LOGGABLE = Boolean.TRUE;
	public static Level LOG_LEVEL = Level.FINE;
}