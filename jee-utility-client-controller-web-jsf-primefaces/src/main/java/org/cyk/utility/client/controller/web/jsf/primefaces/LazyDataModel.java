package org.cyk.utility.client.controller.web.jsf.primefaces;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.client.controller.Controller;
import org.cyk.utility.client.controller.component.grid.Grid;
import org.cyk.utility.client.controller.component.grid.GridBuilder;
import org.cyk.utility.client.controller.data.Data;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.server.representation.ResponseHelper;
import org.primefaces.model.SortOrder;

public class LazyDataModel<DATA> extends org.primefaces.model.LazyDataModel<DATA> implements Serializable {
	private static final long serialVersionUID = 1L;

	private Class<DATA> dataClass;
	private Grid grid;
	
	/* Working variables */
	private Long __count__;

	public LazyDataModel(Class<DATA> dataClass) {
		this.dataClass = dataClass;
	}
	
	public LazyDataModel(Grid grid) {
		this.grid = grid;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DATA> load(int first, int pageSize, String sortField, SortOrder sortOrder,Map<String, Object> filters) {
		List<DATA> objects = null;
		GridBuilder builder = null;
		if(grid != null) {
			grid.getObjects(Boolean.TRUE).removeAll();
			builder = (GridBuilder) grid.getBuilder();
			if(dataClass == null)
				dataClass = (Class<DATA>) ClassHelper.getByName(builder.getRowDataClass().getName());
			//Class<Object> klass = (Class<Object>) ClassHelper.getByName(builder.getRowDataClass().getName());	
		}
		
		Properties properties = new Properties();
		properties.setIsPageable(Boolean.TRUE);
		//properties.setRequest(request);
		//properties.setContext(context);
		//properties.setFilters(__injectCollectionHelper__().instanciate(query));
		properties.setFrom(first);
		properties.setCount(pageSize);

		try {
			Controller controller = DependencyInjection.inject(Controller.class);
			objects = (List<DATA>) controller.read(dataClass,properties);
			Response response = (Response) properties.getResponse();		
			if(response == null) {
				
			}else {
				if(Boolean.TRUE.equals(ResponseHelper.isFamilyClientError(response))) {
					;//getProperties().setThrowable(__inject__(ServiceNotFoundException.class).setSystemAction((SystemAction) properties.getAction()).setResponse(response));
				}else {
					if(grid != null) {
						if(CollectionHelper.isNotEmpty(objects)) {
							for(Object index : objects) {
								Object row = DependencyInjection.inject(org.cyk.utility.client.controller.data.RowBuilder.class).setGrid(builder)
										.setDataClass((Class<? extends Data>) ClassHelper.getByName(dataClass.getName()))
										.setData((Data) index).setOrderNumberOffset(first)
										.execute().getOutput();			
								grid.getObjects(Boolean.TRUE).add(row);						
							}
						}	
					}					
					__count__ = ResponseHelper.getHeaderXTotalCount(response);					
					if(__count__ == null)
						__count__ = controller.count(dataClass, properties);	
				}	
			}
		}catch(Exception exception) {
			//Because we do not want to break view building we need to handle exception
			exception.printStackTrace();
			//getProperties().setThrowable(__injectThrowableHelper__().getFirstCause(exception));	
		}
		if(grid != null)
			objects = (List<DATA>) (grid.getObjects() == null ? null : grid.getObjects().get());
		return objects;
	}
	
	@Override
	public int getRowCount() {
		return __count__ == null ? -1 : __count__.intValue();
	}
	
}
