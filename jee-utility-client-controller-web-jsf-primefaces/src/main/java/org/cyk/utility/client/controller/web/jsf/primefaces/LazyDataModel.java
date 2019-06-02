package org.cyk.utility.client.controller.web.jsf.primefaces;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.clazz.ClassHelper;
import org.cyk.utility.client.controller.Controller;
import org.cyk.utility.client.controller.component.grid.Grid;
import org.cyk.utility.client.controller.component.grid.GridBuilder;
import org.cyk.utility.client.controller.data.Data;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.number.NumberHelper;
import org.cyk.utility.server.representation.ResponseHelper;
import org.primefaces.model.SortOrder;

public class LazyDataModel<OBJECT> extends org.primefaces.model.LazyDataModel<OBJECT> implements Serializable {
	private static final long serialVersionUID = 1L;

	private Grid grid;
	private Long __count__;

	public LazyDataModel(Grid grid) {
		this.grid = grid;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<OBJECT> load(int first, int pageSize, String sortField, SortOrder sortOrder,Map<String, Object> filters) {
		//System.out.println("LazyDataModel.load() filters : "+filters);
		Collection<Object> objects = null;
		grid.getObjects(Boolean.TRUE).removeAll();
		GridBuilder builder = (GridBuilder) grid.getBuilder();
		Class<Object> klass = (Class<Object>) DependencyInjection.inject(ClassHelper.class).getByName(builder.getRowDataClass().getName());
		Properties properties = new Properties();
		properties.setIsPageable(Boolean.TRUE);
		//properties.setRequest(request);
		//properties.setContext(context);
		//properties.setFilters(__injectCollectionHelper__().instanciate(query));
		properties.setFrom(first);
		properties.setCount(pageSize);

		try {
			Controller controller = DependencyInjection.inject(Controller.class);
			objects = controller.readMany(klass,properties);
			Response response = (Response) properties.getResponse();					
			if(Boolean.TRUE.equals(DependencyInjection.inject(ResponseHelper.class).isFamilyClientError(response))) {
				;//getProperties().setThrowable(__inject__(ServiceNotFoundException.class).setSystemAction((SystemAction) properties.getAction()).setResponse(response));
			}else {
				if(DependencyInjection.inject(CollectionHelper.class).isNotEmpty(objects)) {
					for(Object index : objects) {
						Object row = DependencyInjection.inject(org.cyk.utility.client.controller.data.RowBuilder.class).setGrid(builder)
								.setDataClass((Class<? extends Data>) DependencyInjection.inject(ClassHelper.class).getByName(klass.getName()))
								.setData((Data) index).setOrderNumberOffset(first)
								.execute().getOutput();			
						grid.getObjects(Boolean.TRUE).add(row);						
					}
				}
				if(response!=null) {
					__count__ = DependencyInjection.inject(NumberHelper.class).getLong(response.getHeaderString("X-Total-Count"), null);
				}
				
				if(__count__ == null)
					__count__ = controller.count(klass, properties);	
			}
		}catch(Exception exception) {
			//Because we do not want to break view building we need to handle exception
			exception.printStackTrace();
			//getProperties().setThrowable(__injectThrowableHelper__().getFirstCause(exception));	
		}
		//System.out.println("LOAD : "+first+"|"+pageSize+" : count = "+__count__+" :::: "+(grid.getObjects() == null ? null : grid.getObjects().get()));
		return (List<OBJECT>) (grid.getObjects() == null ? null : grid.getObjects().get());
	}
	
	@Override
	public int getRowCount() {
		return __count__ == null ? -1 : __count__.intValue();
	}
	
}
