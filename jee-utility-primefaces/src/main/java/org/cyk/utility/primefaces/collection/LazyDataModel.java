package org.cyk.utility.primefaces.collection;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import javax.json.bind.JsonbBuilder;
import javax.ws.rs.core.Response;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.number.NumberHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.persistence.query.Filter;
import org.cyk.utility.rest.ResponseHelper;
import org.cyk.utility.service.FilterFormat;
import org.cyk.utility.service.SpecificService;
import org.cyk.utility.service.client.SpecificServiceGetter;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public abstract class LazyDataModel<ENTITY> extends org.primefaces.model.LazyDataModel<ENTITY> implements Serializable {

	private Map<String,ENTITY> map;
	private Class<ENTITY> entityClass;
	private Listener<ENTITY> listener;
	
	private Boolean readerUsable;
	private Boolean isCountEqualsListSize;
	
	//private Boolean loggableAsInfo = ValueConverter.getInstance().convertToBoolean(WebController.getInstance().getRequestParameter(ParameterName.LOGGABLE_AS_INFO));
	
	protected int __first__,__pageSize__;
	protected List<ENTITY> __list__;
	private Integer __count__;
	private String __sortField__,__entityFieldsNamesAsString__;
	protected SortOrder __sortOrder__;
	protected List<SortMeta> __multiSortMeta__;
	protected Map<String, Object> __filters__;
	protected LinkedHashMap<String,SortOrder> __sortOrders__;
	private Response __response__;
	private Listener<ENTITY> __listener__;
	
	@SuppressWarnings("unchecked")
	public LazyDataModel() {
		this.entityClass = (Class<ENTITY>) ClassHelper.getParameterAt(getClass(), 0);
	}
	
	protected Object getService() {
		return DependencyInjection.inject(SpecificServiceGetter.class).get(entityClass);
	}
	
	@SuppressWarnings("unchecked")
	protected Response serve(Map<String, Object> filters,LinkedHashMap<String,SortOrder> sortOrders,int firstTupleIndex, int numberOfTuples) {
		__filters__ = filters;
		__sortOrders__ = sortOrders;
		__first__ = firstTupleIndex;
		__pageSize__ = numberOfTuples;
		Object service = getService();
		Filter.Dto filter = getFilter(filters, sortOrders, firstTupleIndex, numberOfTuples);
		String filterAsJson = filter == null ? null : JsonbBuilder.create().toJson(filter);
		List<String> projections = getProjections(filters, sortOrders, firstTupleIndex, numberOfTuples);
		if(service instanceof SpecificService)
			return ((SpecificService<ENTITY>)service).get(filterAsJson,FilterFormat.JSON, projections, Boolean.TRUE, Boolean.TRUE, firstTupleIndex, numberOfTuples);
		throw new RuntimeException(String.format("Service of type %s not yet handled", service.getClass()));
	}
	
	protected List<String> getProjections(Map<String, Object> filters,LinkedHashMap<String,SortOrder> sortOrders,int firstTupleIndex, int numberOfTuples) {
		return null;
	}
	
	protected Filter.Dto getFilter(Map<String, Object> filters,LinkedHashMap<String,SortOrder> sortOrders,int firstTupleIndex, int numberOfTuples) {
		return null;
	}
	
	protected void load(Map<String, Object> filters,LinkedHashMap<String,SortOrder> sortOrders,int first, int pageSize) {
		__response__ = serve(filters, null, first, pageSize);
		__list__ = getList(__response__);
		if(CollectionHelper.isEmpty(__list__)) {
			__count__ = 0;
		}else {
			__count__ = NumberHelper.getInteger(ResponseHelper.getHeaderXTotalCount(__response__),0);
			process(__list__);
		}
		setRowCount(__count__);
	}
	
	protected List<ENTITY> getList(Response response) {
		return ResponseHelper.getEntityAsListFromJson(entityClass, response);
	}
	
	protected void process(Collection<ENTITY> list) {
		list.forEach(entity -> process(entity));
	}
	
	protected void process(ENTITY entity) {}
	
	@Override
	public List<ENTITY> load(int first, int pageSize, List<SortMeta> multiSortMeta, Map<String, Object> filters) {
		long timestamp = System.currentTimeMillis();
		__multiSortMeta__ = multiSortMeta;
		load(filters, null, first, pageSize);
		long duration = System.currentTimeMillis() - timestamp;
		if(Boolean.TRUE.equals(LOGGABLE))
			LogHelper.log(String.format("Page(%s,%s) , duration=%s", first,pageSize,duration), LOG_LEVEL,getClass());
		map = null;
		return __list__;
	}
	
	@Override
	public List<ENTITY> load(int first, int pageSize, String sortField, SortOrder sortOrder,Map<String, Object> filters) {
		long timestamp = System.currentTimeMillis();
		__sortField__ = sortField;
		__sortOrder__ = sortOrder;		
		load(filters, null, first, pageSize);		
		long duration = System.currentTimeMillis() - timestamp;
		if(Boolean.TRUE.equals(LOGGABLE))
			LogHelper.log(String.format("Page(%s,%s) , duration=%s", first,pageSize,duration), LOG_LEVEL,getClass());
		map = null;
		return __list__;
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
		if(!map.containsKey(identifier.toString()))
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
		//Response load(LazyDataModel<T> lazyDataModel);
		
		public static abstract class AbstractImpl<T> extends AbstractObject implements Listener<T>,Serializable{	
			/*
			@Override
			public Integer getCount(LazyDataModel<T> lazyDataModel) {
				if(lazyDataModel.__response__ == null)
					return 0;
				return NumberHelper.getInteger(ResponseHelper.getHeaderXTotalCount(lazyDataModel.__response__),0);
			}
			*/
			
			/**/
			
			public static class DefaultImpl extends AbstractImpl<Object> implements Serializable {
				public static final Listener<Object> INSTANCE = new DefaultImpl();
			}
		}
	}
	
	public static Boolean LOGGABLE = Boolean.TRUE;
	public static Level LOG_LEVEL = Level.FINE;
}