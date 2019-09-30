package org.cyk.utility.identifier.resource;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.field.FieldName;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.string.AbstractStringFunctionImpl;
import org.cyk.utility.__kernel__.system.action.SystemAction;

@Dependent @Deprecated
public class UniformResourceIdentifierParameterNameStringBuilderImpl extends AbstractStringFunctionImpl implements UniformResourceIdentifierParameterNameStringBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private Object name;
	
	@Override
	protected String __execute__() throws Exception {
		String value = null;
		Object name = getName();
		if(name!=null) {
			if(name instanceof Class) {
				if(Class.class.equals(name))
					value = "class";
				else if(SystemAction.class.equals(name))
					value = "action";
			}else if(name instanceof FieldName) {
				FieldName fieldName = (FieldName) name;
				value = fieldName.name().toLowerCase();
			}else if(name instanceof Enum) {
				Enum<?> enumeration = (Enum<?>) name;
				value = enumeration.name().toLowerCase();
			}
			
			if(StringHelper.isNotBlank(value)) {
				value = StringUtils.remove(value, "_");
			}
				
			if(StringHelper.isBlank(value))
				throw new RuntimeException("Parameter name cannot be found for <<"+name+">>");
		}
		return value;
	}
	
	@Override
	public UniformResourceIdentifierParameterNameStringBuilder setNameAsIdentifier() {
		setName(FieldName.IDENTIFIER);
		return this;
	}
	
	@Override
	public Object getName() {
		return name;
	}

	@Override
	public UniformResourceIdentifierParameterNameStringBuilder setName(Object name) {
		this.name = name;
		return this;
	}

	@Override
	public UniformResourceIdentifierParameterNameStringBuilder setNameAsEntityClass() {
		setName(Name.ENTITY_CLASS);
		return this;
	}

	@Override
	public UniformResourceIdentifierParameterNameStringBuilder setNameAsEntityIdentifier() {
		setName(Name.ENTITY_IDENTIFIER);
		return this;
	}

	@Override
	public UniformResourceIdentifierParameterNameStringBuilder setNameAsActionClass() {
		setName(Name.ACTION_CLASS);
		return this;
	}

	@Override
	public UniformResourceIdentifierParameterNameStringBuilder setNameAsActionIdentifier() {
		setName(Name.ACTION_IDENTIFIER);
		return this;
	}
	
	@Override
	public UniformResourceIdentifierParameterNameStringBuilder setNameAsNextActionClass() {
		setName(Name.NEXT_ACTION_CLASS);
		return this;
	}

	@Override
	public UniformResourceIdentifierParameterNameStringBuilder setNameAsNextActionIdentifier() {
		setName(Name.NEXT_ACTION_IDENTIFIER);
		return this;
	}
	
	@Override
	public UniformResourceIdentifierParameterNameStringBuilder setNameAsWindowRenderTypeClass() {
		setName(Name.WINDOW_RENDER_TYPE_CLASS);
		return this;
	}
	
}
