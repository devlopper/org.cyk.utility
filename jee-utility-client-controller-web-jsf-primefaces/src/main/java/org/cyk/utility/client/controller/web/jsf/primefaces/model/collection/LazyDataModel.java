package org.cyk.utility.client.controller.web.jsf.primefaces.model.collection;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import javax.ws.rs.core.Response;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.controller.Arguments;
import org.cyk.utility.__kernel__.controller.EntityReader;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.number.NumberHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.persistence.query.QueryExecutorArguments;
import org.cyk.utility.__kernel__.persistence.query.filter.Filter;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.rest.ResponseHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.client.controller.ControllerEntity;
import org.cyk.utility.client.controller.ControllerLayer;
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
	private Listener<ENTITY> listener;
	
	private List<ENTITY> list;

	private Boolean readerUsable;
	private Boolean isCountEqualsListSize;
	
	private int __first__,__pageSize__;
	private Integer __count__;
	private String __sortField__,__entityFieldsNamesAsString__;
	private SortOrder __sortOrder__;
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
	@Override
	public List<ENTITY> load(int first, int pageSize, String sortField, SortOrder sortOrder,Map<String, Object> filters) {
		long timestamp = System.currentTimeMillis();
		__first__ = first;
		__pageSize__ = pageSize;
		__sortField__ = sortField;
		__sortOrder__ = sortOrder;
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
		if(Boolean.TRUE.equals(readerUsable))
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
	
	/**/
	
	public static interface Listener<T> {
		Filter.Dto instantiateFilter(LazyDataModel<T> lazyDataModel);
		Arguments<T> instantiateArguments(LazyDataModel<T> lazyDataModel);
		Properties instantiateReadProperties(LazyDataModel<T> lazyDataModel);
		List<T> read(LazyDataModel<T> lazyDataModel);
		Response getResponse(LazyDataModel<T> lazyDataModel);
		Integer getCount(LazyDataModel<T> lazyDataModel);
		/**/
		
		public static abstract class AbstractImpl<T> extends AbstractObject implements Listener<T>,Serializable{
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
				Arguments<T> arguments = new Arguments<T>()
						.setRepresentationArguments(new org.cyk.utility.__kernel__.representation.Arguments().setQueryExecutorArguments(new QueryExecutorArguments.Dto()
								.setQueryIdentifier(lazyDataModel.readQueryIdentifier)
								.setFirstTupleIndex(lazyDataModel.__first__)
								.setNumberOfTuples(lazyDataModel.__pageSize__)
								.setFilter(lazyDataModel.__filter__))
								.setCountable(Boolean.TRUE));
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
				Properties properties = new Properties().setQueryIdentifier(lazyDataModel.readQueryIdentifier)
						.setFields(lazyDataModel.__entityFieldsNamesAsString__)
						.setFilters(lazyDataModel.__filter__)
						.setIsPageable(Boolean.TRUE).setFrom(lazyDataModel.__first__).setCount(lazyDataModel.__pageSize__);
				return properties;
			}
			
			@Override
			public List<T> read(LazyDataModel<T> lazyDataModel) {
				if(Boolean.TRUE.equals(lazyDataModel.readerUsable)) {
					return (List<T>) EntityReader.getInstance().readMany(lazyDataModel.entityClass, lazyDataModel.__readerArguments__);
				}
				return (List<T>) lazyDataModel.controller.read(lazyDataModel.__readProperties__);
			}
			
			@Override
			public Response getResponse(LazyDataModel<T> lazyDataModel) {
				if(Boolean.TRUE.equals(lazyDataModel.readerUsable))
					return lazyDataModel.__readerArguments__.get__response__();
				return (Response) lazyDataModel.__readProperties__.getResponse();
			}
			
			@Override
			public Integer getCount(LazyDataModel<T> lazyDataModel) {
				return NumberHelper.getInteger(ResponseHelper.getHeaderXTotalCount(lazyDataModel.__response__),0);
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