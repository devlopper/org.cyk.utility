package org.cyk.utility.__kernel__.string;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.klass.NamingModel;
import org.cyk.utility.__kernel__.object.AbstractObject;

public abstract class AbstractStringTemplateIdentifierGetterImpl extends AbstractObject implements StringTemplateIdentifierGetter,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public String get(NamingModel namingModel) {
		if(namingModel == null || StringHelper.isOneBlank(namingModel.getNode(),namingModel.getLayer(),namingModel.getSubLayer()))
			return null;
		return __get__(namingModel);
	}
	
	protected String __get__(NamingModel namingModel) {
		String name = null;
		if(namingModel.isSubLayerEntities()) {
			if(Boolean.TRUE.equals(namingModel.getNamable()))
				name = "namable";
			else if(Boolean.TRUE.equals(namingModel.getBusinessIdentifiable()))
				name = "businessidentifiable";
			else if(Boolean.TRUE.equals(namingModel.getSystemIdentifiable()))
				name = "systemidentifiable";
			else if(StringHelper.isBlank(namingModel.getSuffix()))
				name = "systemidentifiable";
			else if(StringUtils.endsWith(namingModel.getSuffix(), "Mapper"))
				name = StringHelper.applyCase(namingModel.getSuffix(),Case.LOWER);
			
			if(StringHelper.isBlank(name))
				name = "systemidentifiable";
		}else
			name = namingModel.getSubLayer();
		return String.format(PROJECT_SYSTEM_FORMAT, namingModel.getNode(),namingModel.getLayer(),namingModel.getSubLayer(),name);
	}
	
	private static final String PROJECT_SYSTEM_FORMAT = "project/system/%s/%s/%s/%s";
}
