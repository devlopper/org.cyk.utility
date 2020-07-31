package org.cyk.utility.__kernel__.persistence.query;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.value.Value;
import org.cyk.utility.__kernel__.value.ValueHelper;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface QueryIdentifierBuilder {

	default String build(Arguments arguments) {
		if(arguments == null)
			return null;
		String classSimpleName = arguments.getClassSimpleName();
		if(StringHelper.isBlank(classSimpleName)){
			if(Boolean.TRUE.equals(arguments.getIsDerivedFromQueryIdentifier())){
				classSimpleName =  StringUtils.substringBefore(arguments.getDerivedFromQueryIdentifier(), ".");
			}
		}
		String name = arguments.getName();
		if(StringHelper.isBlank(name)){
			if(Boolean.TRUE.equals(arguments.getIsDerivedFromQueryIdentifier())){
				name =  StringUtils.substringAfter(arguments.getDerivedFromQueryIdentifier(), ".");
				if(Boolean.TRUE.equals(arguments.getIsCountInstances()))
					//replace first occurence of read by count
					name = StringUtils.replaceOnce(name, "read", "count");
			}
		}
		String format = ValueHelper.defaultToIfBlank(arguments.getFormat(),FORMAT);
		return String.format(format, classSimpleName,name);
	}
	
	default String build(Class<?> klass,String name) {
		if(klass == null || StringHelper.isBlank(name))
			return null;
		return build(new Arguments().setClassSimpleName(klass.getSimpleName()).setName(name));
	}
	
	String buildCountFrom(String identifier);
	
	String buildReadWhereCodeOrNameLike(Class<?> klass);
	
	/**/
	
	public abstract class AbstractImpl extends AbstractObject implements QueryIdentifierBuilder,Serializable {
		private static final long serialVersionUID = 1L;
		
		@Override
		public String buildCountFrom(String identifier) {
			Arguments arguments = new Arguments();
			arguments.setIsDerivedFromQueryIdentifier(Boolean.TRUE).setDerivedFromQueryIdentifier(identifier).setIsCountInstances(Boolean.TRUE);
			return build(arguments);
		}
		
		@Override
		public String buildReadWhereCodeOrNameLike(Class<?> klass) {
			return build(klass, QueryName.READ_WHERE_CODE_OR_NAME_LIKE.getValue());
		}
	}
	
	/**/
	
	static QueryIdentifierBuilder getInstance() {
		QueryIdentifierBuilder instance = (QueryIdentifierBuilder) INSTANCE.get();
		if(instance != null)
			return instance;
		INSTANCE.set(instance = DependencyInjection.inject(QueryIdentifierBuilder.class));
		LogHelper.logInfo("instance has been set. <<"+instance.getClass()+">>", QueryIdentifierBuilder.class);
		return instance;
	}
	
	Value INSTANCE = new Value();
	String FORMAT = "%s.%s";
	
	@Getter @Setter @Accessors(chain=true)
	public static class Arguments implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private Boolean isDerivedFromQueryIdentifier,isCountInstances;
		private String name,derivedFromQueryIdentifier,classSimpleName,format = FORMAT;
	}	
}