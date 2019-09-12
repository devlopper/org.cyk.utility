package org.cyk.utility.client.controller.data;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.cyk.utility.clazz.ClassHelper;
import org.cyk.utility.clazz.ClassHelperImpl;
import org.cyk.utility.client.controller.navigation.Navigation;
import org.cyk.utility.client.controller.navigation.NavigationBuilder;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.identifier.resource.UniformResourceIdentifierHelperImpl;
import org.cyk.utility.object.ObjectByClassMap;
import org.cyk.utility.request.RequestHelper;
import org.cyk.utility.request.RequestProperty;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.node.SystemNodeClient;

public abstract class AbstractRowDataImpl<DATA extends Data> extends AbstractRowImpl implements RowData<DATA>,Serializable {
	private static final long serialVersionUID = 1L;

	private Class<DATA> dataClass;
	private DATA data;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		dataClass = (Class<DATA>) ClassHelperImpl.__getParameterAt__(getClass(), 0, Data.class);
	}
	
	@Override
	public DATA getData() {
		return data;
	}
	
	@Override
	public RowData<DATA> setData(DATA data) {
		this.data = data;
		return this;
	}
	
	@Override
	public String getUrlBySystemActionClass(Object request,Class<? extends SystemAction> aClass) {
		String url = null;
		/*
		RowListeners listeners = getListeners();
		NavigationBuilder navigationBuilder = null;
		ObjectByClassMap parametersMap = getNavigationParametersMap();		
		*/
		Data data = getData();
		Class<DATA> dataClass = getDataClass();
		if(dataClass == null)
			if(data!=null)
				dataClass = (Class<DATA>) data.getClass();
		SystemAction systemAction = __inject__(aClass).setEntityClass(dataClass);
		systemAction.getEntities(Boolean.TRUE).add(data);
		/*
		if(parametersMap!=null) {
			//Object[] parameters = (Object[]) parametersMap.get(aClass);
		}
		
		if(__inject__(CollectionHelper.class).isNotEmpty(listeners))
			for(RowListener index : listeners.get())
				index.listenSystemAction(systemAction);
		navigationBuilder = __inject__(NavigationBuilder.class).setIdentifierBuilderSystemAction(systemAction);
		
		if(parametersMap!=null)
			navigationBuilder.setParameters((Object[]) parametersMap.get(aClass));
		
		if(__inject__(CollectionHelper.class).isNotEmpty(listeners))
			for(RowListener index : listeners.get()) {
				index.listenNavigationBuilder(navigationBuilder);
			}
		Navigation navigation = navigationBuilder.execute().getOutput();
		if(__inject__(CollectionHelper.class).isNotEmpty(listeners))
			for(RowListener index : listeners.get())
				index.listenNavigation(navigation);
		
		url = navigation.getUniformResourceLocator().toString();
		System.out.println("1 : "+url);
		*/
		RequestHelper requestHelper = __inject__(RequestHelper.class);
		url = UniformResourceIdentifierHelperImpl.__build__((String) requestHelper.getProperty(request,RequestProperty.SCHEME)
				,(String) requestHelper.getProperty(request,RequestProperty.HOST),(Integer) requestHelper.getProperty(request,RequestProperty.PORT),systemAction);
		return url;
	}
	
	/*@Override
	public String getUrlBySystemActionClass(Class<? extends SystemAction> aClass) {
		String url = null;
		RowListeners listeners = getListeners();
		NavigationBuilder navigationBuilder = null;
		ObjectByClassMap parametersMap = getNavigationParametersMap();		
		Data data = getData();
		Class<DATA> dataClass = getDataClass();
		if(dataClass == null)
			if(data!=null)
				dataClass = (Class<DATA>) data.getClass();
		SystemAction systemAction = __inject__(aClass).setEntityClass(dataClass);
		systemAction.getEntities(Boolean.TRUE).add(data);
		if(parametersMap!=null) {
			//Object[] parameters = (Object[]) parametersMap.get(aClass);
		}
		
		if(__inject__(CollectionHelper.class).isNotEmpty(listeners))
			for(RowListener index : listeners.get())
				index.listenSystemAction(systemAction);
		navigationBuilder = __inject__(NavigationBuilder.class).setIdentifierBuilderSystemAction(systemAction);
		
		if(parametersMap!=null)
			navigationBuilder.setParameters((Object[]) parametersMap.get(aClass));
		
		if(__inject__(CollectionHelper.class).isNotEmpty(listeners))
			for(RowListener index : listeners.get()) {
				index.listenNavigationBuilder(navigationBuilder);
			}
		Navigation navigation = navigationBuilder.execute().getOutput();
		if(__inject__(CollectionHelper.class).isNotEmpty(listeners))
			for(RowListener index : listeners.get())
				index.listenNavigation(navigation);
		
		url = navigation.getUniformResourceLocator().toString();
		return url;
	}*/
	
	@Override
	public Class<DATA> getDataClass() {
		return dataClass;
	}
	
	@Override
	public RowData<DATA> setDataClass(Class<DATA> dataClass) {
		this.dataClass = dataClass;
		return this;
	}

}
