package org.cyk.utility.client.controller.component.window;

import java.io.Serializable;
import java.lang.reflect.Field;

import org.codehaus.plexus.util.StringUtils;
import org.cyk.utility.character.CharacterConstant;
import org.cyk.utility.client.controller.component.InputOutputBuilder;
import org.cyk.utility.client.controller.component.input.InputBuilder;
import org.cyk.utility.client.controller.component.output.OutputStringTextBuilder;
import org.cyk.utility.client.controller.component.view.ViewBuilder;
import org.cyk.utility.client.controller.data.Data;
import org.cyk.utility.client.controller.data.DataFieldsNamesGetter;
import org.cyk.utility.client.controller.data.Form;
import org.cyk.utility.field.FieldGetter;
import org.cyk.utility.field.FieldTypeGetter;
import org.cyk.utility.field.Fields;
import org.cyk.utility.internationalization.InternalizationStringBuilder;
import org.cyk.utility.string.StringLocation;
import org.cyk.utility.string.Strings;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.action.SystemActionAdd;
import org.cyk.utility.system.action.SystemActionCreate;
import org.cyk.utility.system.action.SystemActionUpdate;

public class WindowContainerManagedWindowBuilderEditDataDefaultImpl extends AbstractWindowContainerManagedWindowBuilderEditDataImpl implements WindowContainerManagedWindowBuilderEditDataDefault,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __execute__(Form form, SystemAction systemAction, Data data, ViewBuilder viewBuilder) {
		Boolean isEditable = systemAction instanceof SystemActionCreate || systemAction instanceof SystemActionUpdate || systemAction instanceof SystemActionAdd|| data.getIdentifier() == null;
		Strings fieldNames = __inject__(DataFieldsNamesGetter.class).setSystemAction(systemAction).execute().getOutput();
		if(__injectCollectionHelper__().isNotEmpty(fieldNames))
			for(String index : fieldNames.get()) {
				if(isEditable) {
					viewBuilder.addInputBuilderByObjectByFieldNames(data, isEditable, StringUtils.split(index,CharacterConstant.DOT.toString()));
				}else {
					String tempIndex = index;
					Field field = __inject__(FieldGetter.class).setToken(index).setTokenLocation(StringLocation.EXAT)
							.setClazz(__inject__(getSystemAction().getEntityClass()).getClass()).execute().getOutput().getFirst();
					Class<?> fieldType = __inject__(FieldTypeGetter.class).execute(field).getOutput();
					
					if(!__injectClassHelper__().isBelongsToJavaPackages(fieldType)) {
						Fields fields = __inject__(FieldGetter.class).setClazz(__inject__(fieldType).getClass()).addNameToken("name").addNameToken(Data.PROPERTY_CODE).execute().getOutput();
						if(__injectCollectionHelper__().isNotEmpty(fields)) {
							//TODO according to a list pick up the first matching
							//index = index + ".name";//TODO get name first else code
							//index = index + ".code";
							index = index + "." + __injectCollectionHelper__().getFirst(fields).getName();
						}else {
							
						}	
					}
					
					InputOutputBuilder<?, ?> inputOuput = (InputOutputBuilder<?, ?>) viewBuilder.addInputBuilderByObjectByFieldNames(
							data, isEditable, StringUtils.split(index,CharacterConstant.DOT.toString())
							);
					if(inputOuput instanceof InputBuilder<?, ?>)
						((InputBuilder<?, ?>)inputOuput).getLabel(Boolean.TRUE).setValue(__inject__(InternalizationStringBuilder.class).setKeyValue(org.apache.commons.lang3.StringUtils.substringAfterLast(tempIndex, ".")).execute().getOutput());
					else if(inputOuput instanceof OutputStringTextBuilder)
						((OutputStringTextBuilder)inputOuput).setValue(__inject__(InternalizationStringBuilder.class).setKeyValue(org.apache.commons.lang3.StringUtils.substringAfterLast(tempIndex, ".")).execute().getOutput());
				}
				
			}
		
	}

}
