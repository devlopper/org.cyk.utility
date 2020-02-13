package org.cyk.utility.client.controller.web.jsf.primefaces.model;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.object.ReadListener;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.client.controller.web.jsf.converter.ObjectConverter;

@Deprecated
public interface AutoCompleteEntityBuilder {

	static <ENTITY> AutoCompleteEntity<ENTITY> build(Class<ENTITY> entityClass,String targetWidgetVar) {
		if(entityClass == null)
			return null;
		AutoCompleteEntity<ENTITY> autoCompleteEntity = new AutoCompleteEntity<ENTITY>(entityClass);
		autoCompleteEntity.setIdentifier(StringHelper.getVariableNameFrom(entityClass.getSimpleName()));
		if(StringHelper.isBlank(targetWidgetVar)) {
			autoCompleteEntity.setConverter(DependencyInjection.inject(ObjectConverter.class));
		}else {
			String script = String.format(SCRIPT_FILTER, targetWidgetVar);
			autoCompleteEntity.getEventScripts(Boolean.TRUE).write(Event.CHANGE, script);
			autoCompleteEntity.getAjaxItemSelect().setDisabled(Boolean.FALSE);
			autoCompleteEntity.getAjaxItemSelect().getEventScripts(Boolean.TRUE).write(Event.COMPLETE, script);
			autoCompleteEntity.getAjaxItemSelect().setThrowNotYetImplemented(Boolean.FALSE);
			
			autoCompleteEntity.setReadItemValueListener(new ReadListener() {				
				@Override
				public Object read(Object object) {
					return FieldHelper.readBusinessIdentifier(object);
				}
			});
		}
		return autoCompleteEntity;
	}
	
	static <ENTITY> AutoCompleteEntity<ENTITY> build(Class<ENTITY> entityClass) {
		if(entityClass == null)
			return null;
		return build(entityClass, null);
	}
	
	/**/
	
	String SCRIPT_FILTER = "PF('%s').filter()";
}
