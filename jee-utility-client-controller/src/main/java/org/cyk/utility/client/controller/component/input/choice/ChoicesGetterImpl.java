package org.cyk.utility.client.controller.component.input.choice;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;

import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.object.Objects;
import org.cyk.utility.__kernel__.persistence.query.filter.Filter;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.system.action.SystemAction;
import org.cyk.utility.__kernel__.throwable.ServiceNotFoundException;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;
import org.cyk.utility.__kernel__.value.ValueHelper;
import org.cyk.utility.client.controller.Controller;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;
import org.cyk.utility.server.representation.ResponseHelper;
import org.cyk.utility.system.layer.SystemLayerController;

public class ChoicesGetterImpl extends AbstractFunctionWithPropertiesAsInputImpl<Objects> implements ChoicesGetter,Serializable {
	private static final long serialVersionUID = 1L;

	private Class<?> fieldDeclaringClass;
	private String query;
	private Field field;
	private Object request,context;
	private Integer maximumNumberOfChoice;
	
	@Override
	protected Objects __execute__() throws Exception {
		Field field = ValueHelper.returnOrThrowIfBlank("choices getter field", getField());
		Class<?> fieldDeclaringClass = getFieldDeclaringClass();
		if(fieldDeclaringClass == null)
			fieldDeclaringClass = field.getDeclaringClass();
		Object request = getRequest();
		Object context = getContext();
		String query = StringUtils.trimToEmpty(getQuery());
		Integer maximumNumberOfChoice = getMaximumNumberOfChoice();
		Objects objects = __inject__(Objects.class);
		Type fieldType = FieldHelper.getType(field, fieldDeclaringClass);
		Class<?> choiceClass = null;//(Class<?>) fieldType;
		if(ClassHelper.isInstanceOf(Collection.class, field.getType())) {
			choiceClass = (Class<?>) ((ParameterizedType)fieldType).getActualTypeArguments()[0];
		}else {
			choiceClass = (Class<?>) fieldType;
		}
		
		if(choiceClass.isEnum()) {
			for(Object index : choiceClass.getEnumConstants()) {
				if(StringHelper.isEmpty(query) || StringUtils.containsIgnoreCase(((Enum<?>)index).name(), query)) {
					objects.add(index);
					if(maximumNumberOfChoice!=null && --maximumNumberOfChoice == 0)
						break;
				}
			}
		}else if(__inject__(SystemLayerController.class).getEntityLayer().isPackage(choiceClass.getName())) {				
			Collection<?> _objects_ = null;
			Properties properties = new Properties();
			properties.setRequest(request);
			properties.setContext(context);
			Filter.Dto filter = new Filter.Dto().setValue(query);
			//properties.setFilters(__injectMapHelper__().instanciateKeyAsStringValueAsObject("__global_query__",query));
			properties.setFilters(filter);
			properties.setCount(maximumNumberOfChoice);
			if(properties.getCount() == null) {
				properties.setIsPageable(Boolean.FALSE);	
			}else {
				properties.setFrom(0);
				properties.setIsPageable(Boolean.TRUE);
			}
			
			try {
				_objects_ = __inject__(Controller.class).read(choiceClass,properties);
				Response response = (Response) properties.getResponse();					
				if(Boolean.TRUE.equals(ResponseHelper.isFamilyClientError(response)))
					getProperties().setThrowable(__inject__(ServiceNotFoundException.class).setSystemAction((SystemAction) properties.getAction()).setResponse(response));
			}catch(Exception exception) {
				//Because we do not want to break view building we need to handle exception
				exception.printStackTrace();
				getProperties().setThrowable(ThrowableHelper.getFirstCause(exception));	
			}
			
			if(CollectionHelper.isNotEmpty(_objects_)) {
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
	public Class<?> getFieldDeclaringClass() {
		return fieldDeclaringClass;
	}
	
	@Override
	public ChoicesGetter setFieldDeclaringClass(Class<?> fieldDeclaringClass) {
		this.fieldDeclaringClass = fieldDeclaringClass;
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
