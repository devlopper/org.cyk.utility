package org.cyk.utility.enumeration;

import java.io.Serializable;

import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;

public class EnumGetterImpl extends AbstractFunctionWithPropertiesAsInputImpl<Enum<?>> implements EnumGetter,Serializable {
	private static final long serialVersionUID = 1L;

	private Object name;
	private Boolean isNameCaseSensitive;
	private Class<? extends Enum<?>> clazz;
	
	@Override
	protected Enum<?> __execute__() throws Exception {
		Class<? extends Enum<?>> clazz = getClazz();
		Enum<?> enumValue = null;
		String name = getName() == null ? null : getName().toString();
		if(__injectStringHelper__().isNotBlank(name)) {
			Boolean isNameCaseSensitive = Boolean.TRUE.equals(getIsNameCaseSensitive());
			for(Enum<?> index : clazz.getEnumConstants())
				if(index.name().equals(name)) {
					enumValue = index;
					break;
				}
			
			if(enumValue == null && !isNameCaseSensitive) {
				for(Enum<?> index : clazz.getEnumConstants())
					if(index.name().equalsIgnoreCase(name)) {
						enumValue = index;
						break;
					}
			}
		}
		return enumValue;
	}
	
	@Override
	public Class<? extends Enum<?>> getClazz() {
		return clazz;
	}
	
	@Override
	public EnumGetter setClazz(Class<? extends Enum<?>> clazz) {
		this.clazz = clazz;
		return this;
	}
	
	@Override
	public EnumGetter setName(Object name) {
		this.name = name;
		return this;
	}

	@Override
	public Object getName() {
		return name;
	}

	@Override
	public EnumGetter setIsNameCaseSensitive(Boolean isNameCaseSensitive) {
		this.isNameCaseSensitive = isNameCaseSensitive;
		return this;
	}

	@Override
	public Boolean getIsNameCaseSensitive() {
		return isNameCaseSensitive;
	}

}
