package org.cyk.utility.__kernel__.representation;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;
import org.cyk.utility.__kernel__.value.Value;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface RepresentationClassNameGetter {

	String get(Arguments arguments);
	String getByEntityClassSimpleName(String entityClassSimpleName);
	String getByActionIdentifier(String actionIdentifier);
	
	/**/
	
	public static abstract class AbstractImpl extends AbstractObject implements RepresentationClassNameGetter,Serializable {		
		public static String SYSTEM_PACKAGE_NAME;
		public static String FORMAT = "%s.server.representation.api.%sRepresentation";
		
		@Override
		public String get(Arguments arguments) {
			if(arguments == null)
				return null;
			if(StringHelper.isNotBlank(arguments.getActionIdentifier()))
				return format(arguments.getSystemPackageName(),StringUtils.substringBefore(arguments.getActionIdentifier(), "."));
			return null;
		}
		
		protected String format(String systemPackageName,String entityClassSimpleName) {
			if(StringHelper.isBlank(systemPackageName))
				systemPackageName = SYSTEM_PACKAGE_NAME;
			ThrowableHelper.throwIllegalArgumentExceptionIfBlank("systemPackageName", systemPackageName);
			ThrowableHelper.throwIllegalArgumentExceptionIfBlank("entityClassSimpleName", entityClassSimpleName);
			return String.format(FORMAT, systemPackageName,entityClassSimpleName);
		}
		
		@Override
		public String getByEntityClassSimpleName(String entityClassSimpleName) {
			return get(new Arguments().setEntityClassSimpleName(entityClassSimpleName));
		}
		
		@Override
		public String getByActionIdentifier(String actionIdentifier) {
			return get(new Arguments().setActionIdentifier(actionIdentifier));
		}
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Arguments implements Serializable {
		private String systemPackageName;
		private String entityClassSimpleName;
		private String actionIdentifier;
	}
	
	/**/
	
	static RepresentationClassNameGetter getInstance() {
		return Helper.getInstance(RepresentationClassNameGetter.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
}