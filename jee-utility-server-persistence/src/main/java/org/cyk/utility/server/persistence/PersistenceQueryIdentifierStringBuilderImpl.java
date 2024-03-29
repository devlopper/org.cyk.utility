package org.cyk.utility.server.persistence;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.Dependent;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.function.Function;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputAndStringAsOutputImpl;


@Dependent
public class PersistenceQueryIdentifierStringBuilderImpl extends AbstractFunctionWithPropertiesAsInputAndStringAsOutputImpl implements PersistenceQueryIdentifierStringBuilder, Serializable {
	private static final long serialVersionUID = 1L;

	//private static final String[] PREFIXES = {"readBy","countBy","execute"};
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		getProperties().setFromPath(new Object[]{Properties.IS,Properties.FORMAT,Properties.REQUIRED}, Boolean.TRUE);
		setFormat("%s.%s");//classSimpleName.name(fieldName or methodName or otherName)
	}
	
	@Override
	public Function<Properties, String> execute() {
		Function<Properties, String> function = super.execute();
		return function;
	}
	
	@Override
	protected Collection<Object> __getFormatArguments__(Boolean isFormatRequired, Collection<Object> formatArguments) {
		if(CollectionHelper.isEmpty(formatArguments)){
			Boolean isDerivedFromQueryIdentifier = getIsDerivedFromQueryIdentifier();
			String derivedFromQueryIdentifier = (String)getDerivedFromQueryIdentifier();
			String classSimpleName = getClassSimpleName();
			if(StringHelper.isBlank(classSimpleName)){
				if(Boolean.TRUE.equals(isDerivedFromQueryIdentifier)){
					classSimpleName =  StringUtils.substringBefore(derivedFromQueryIdentifier, ".");
				}
			}
			String name = getName();
			if(StringHelper.isBlank(name)){
				if(Boolean.TRUE.equals(isDerivedFromQueryIdentifier)){
					name =  StringUtils.substringAfter(derivedFromQueryIdentifier, ".");
					if(Boolean.TRUE.equals(getIsCountInstances()))
						//replace first occurence of read by count
						name = StringUtils.replaceOnce(name, "read", "count");
				}
			}
			formatArguments = List.of(classSimpleName,name);
		}
		return super.__getFormatArguments__(isFormatRequired, formatArguments);
	}
	
	@Override
	public PersistenceQueryIdentifierStringBuilder setClassSimpleName(String name) {
		getProperties().setFromPath(new Object[]{Properties.CLASS,Properties.NAME}, name);
		return this;
	}
	
	@Override
	public String getClassSimpleName() {
		return (String) getProperties().getFromPath(Properties.CLASS,Properties.NAME);
	}
	
	@Override
	public PersistenceQueryIdentifierStringBuilder setClassSimpleName(Class<?> aClass) {
		return setClassSimpleName(aClass.getSimpleName());
	}
	
	@Override
	public PersistenceQueryIdentifierStringBuilder setName(String name) {
		getProperties().setFromPath(new Object[]{Properties.NAME}, name);
		return this;
	}
	
	@Override
	public String getName() {
		return (String) getProperties().getFromPath(Properties.NAME);
	}
	
	@Override
	public PersistenceQueryIdentifierStringBuilder setName(Field field) {
		return setName(field.getName());
	}
	
	@Override
	public PersistenceQueryIdentifierStringBuilder setName(Method method) {
		return setName(method.getName());
	}

	@Override
	public Object getDerivedFromQueryIdentifier() {
		return getProperties().getFromPath(Properties.QUERY,Properties.IDENTIFIER);
	}

	@Override
	public PersistenceQueryIdentifierStringBuilder setDerivedFromQueryIdentifier(Object identifier) {
		getProperties().setFromPath(new Object[]{Properties.QUERY,Properties.IDENTIFIER}, identifier);
		return this;
	}

	@Override
	public Boolean getIsDerivedFromQueryIdentifier() {
		return (Boolean) getProperties().getFromPath(Properties.IS,Properties.QUERY,Properties.IDENTIFIER);
	}

	@Override
	public PersistenceQueryIdentifierStringBuilder setIsDerivedFromQueryIdentifier(Boolean value) {
		getProperties().setFromPath(new Object[]{Properties.IS,Properties.QUERY,Properties.IDENTIFIER}, value);
		return this;
	}
	
	@Override
	public Boolean getIsCountInstances() {
		return (Boolean) getProperties().getFromPath(Properties.IS,Properties.COUNT);
	}
	@Override
	public PersistenceQueryIdentifierStringBuilder setIsCountInstances(Boolean value) {
		getProperties().setFromPath(new Object[]{Properties.IS,Properties.COUNT}, value);
		return this;
	}
}
