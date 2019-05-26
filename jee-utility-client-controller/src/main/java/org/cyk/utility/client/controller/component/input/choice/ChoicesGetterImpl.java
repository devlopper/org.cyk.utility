package org.cyk.utility.client.controller.component.input.choice;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;

import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.client.controller.Controller;
import org.cyk.utility.field.FieldTypeGetter;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;
import org.cyk.utility.object.Objects;
import org.cyk.utility.server.representation.ResponseHelper;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.exception.ServiceNotFoundException;
import org.cyk.utility.system.layer.SystemLayerController;

public class ChoicesGetterImpl extends AbstractFunctionWithPropertiesAsInputImpl<Objects> implements ChoicesGetter,Serializable {
	private static final long serialVersionUID = 1L;

	private String query;
	private Field field;
	private Object request,context;
	private Integer maximumNumberOfChoice;
	
	@Override
	protected Objects __execute__() throws Exception {
		Field field = __injectValueHelper__().returnOrThrowIfBlank("choices getter field", getField());
		Object request = getRequest();
		Object context = getContext();
		String query = StringUtils.trimToEmpty(getQuery());
		Integer maximumNumberOfChoice = getMaximumNumberOfChoice();
		Objects objects = __inject__(Objects.class);
		Class<?> fieldType = __inject__(FieldTypeGetter.class).execute(field).getOutput();
		if(__injectClassHelper__().isInstanceOf(fieldType, Collection.class)) {
			fieldType = __injectFieldHelper__().getParameterAt(field, 0, Object.class);
		}
		
		if(fieldType.isEnum()) {
			for(Object index : fieldType.getEnumConstants()) {
				if(__injectStringHelper__().isEmpty(query) || StringUtils.containsIgnoreCase(((Enum<?>)index).name(), query)) {
					objects.add(index);
					if(maximumNumberOfChoice!=null && --maximumNumberOfChoice == 0)
						break;
				}
			}
		}else if(__inject__(SystemLayerController.class).getEntityLayer().isPackage(fieldType.getName())) {				
			Collection<?> _objects_ = null;
			Properties properties = new Properties();
			properties.setRequest(request);
			properties.setContext(context);
			properties.setFilters(__injectCollectionHelper__().instanciate(query));
			properties.setCount(maximumNumberOfChoice);
			if(properties.getCount() == null) {
				properties.setIsPageable(Boolean.FALSE);	
			}else {
				properties.setFrom(0);
				properties.setIsPageable(Boolean.TRUE);
			}
			
			try {
				_objects_ = __inject__(Controller.class).readMany(fieldType,properties);
				Response response = (Response) properties.getResponse();					
				if(Boolean.TRUE.equals(__inject__(ResponseHelper.class).isFamilyClientError(response)))
					getProperties().setThrowable(__inject__(ServiceNotFoundException.class).setSystemAction((SystemAction) properties.getAction()).setResponse(response));
			}catch(Exception exception) {
				//Because we do not want to break view building we need to handle exception
				exception.printStackTrace();
				getProperties().setThrowable(__injectThrowableHelper__().getFirstCause(exception));	
			}
			
			if(__injectCollectionHelper__().isNotEmpty(_objects_)) {
				for(Object index : _objects_) {
					objects.add(index);
				}	
			}
		}
		
		return objects;
	}
	
	@Override
	public Field getField() {
		return field;
	}
	
	@Override
	public ChoicesGetter setField(Field field) {
		this.field = field;
		return this;
	}
	
	@Override
	public String getQuery() {
		return query;
	}

	@Override
	public ChoicesGetter setQuery(String query) {
		this.query = query;
		return this;
	}
	
	@Override
	public Object getRequest() {
		return request;
	}
	
	@Override
	public ChoicesGetter setRequest(Object request) {
		this.request = request;
		return this;
	}
	
	@Override
	public Object getContext() {
		return context;
	}
	@Override
	public ChoicesGetter setContext(Object context) {
		this.context = context;
		return this;
	}
	
	@Override
	public Integer getMaximumNumberOfChoice() {
		return maximumNumberOfChoice;
	}
	
	@Override
	public ChoicesGetter setMaximumNumberOfChoice(Integer maximumNumberOfChoice) {
		this.maximumNumberOfChoice = maximumNumberOfChoice;
		return this;
	}

}
