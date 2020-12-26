package org.cyk.utility.__kernel__.uri;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.number.NumberHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;
import org.cyk.utility.__kernel__.value.Value;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface UniformResourceIdentifierPartGetter {

	String get(Arguments arguments);	
	String getSchemeFromRequest(Object request);
	String getHostFromRequest(Object request);
	Integer getPortFromRequest(Object request);
	String getContextFromRequest(Object request);
	
	/**/
	
	public static abstract class AbstractImpl extends AbstractObject implements UniformResourceIdentifierPartGetter,Serializable {
		@Override
		public String get(Arguments arguments) {
			ThrowableHelper.throwIllegalArgumentExceptionIfNull("arguments", arguments);
			ThrowableHelper.throwIllegalArgumentExceptionIfNull("part", arguments.part);
			if(arguments.request instanceof HttpServletRequest) {
				HttpServletRequest httpServletRequest = (HttpServletRequest) arguments.request;
				if(Part.SCHEME.equals(arguments.part))
					return httpServletRequest.getScheme();
				if(Part.HOST.equals(arguments.part))
					return httpServletRequest.getServerName();
				if(Part.PORT.equals(arguments.part))
					return String.valueOf(httpServletRequest.getServerPort());
				if(Part.CONTEXT.equals(arguments.part))
					return httpServletRequest.getContextPath();
				if(Part.PATH.equals(arguments.part))
					return httpServletRequest.getRequestURI().toString();
				if(Part.QUERY.equals(arguments.part))
					return httpServletRequest.getQueryString();				
				ThrowableHelper.throwNotYetImplemented("get request "+arguments.part);
			}
			return null;
		}
		
		@Override
		public String getSchemeFromRequest(Object request) {
			return get(new Arguments().setPart(Part.SCHEME).setRequest(request));
		}
		
		@Override
		public String getHostFromRequest(Object request) {
			return get(new Arguments().setPart(Part.HOST).setRequest(request));
		}
		
		@Override
		public Integer getPortFromRequest(Object request) {
			return NumberHelper.getInteger(get(new Arguments().setPart(Part.PORT).setRequest(request)));
		}
		
		@Override
		public String getContextFromRequest(Object request) {
			return get(new Arguments().setPart(Part.CONTEXT).setRequest(request));
		}
	}
	
	/**/
	
	static UniformResourceIdentifierPartGetter getInstance() {
		return Helper.getInstance(UniformResourceIdentifierPartGetter.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Arguments implements Serializable {
		private Part part;
		private Object request;
	}
	
	public static enum Part {
		
		SCHEME
		,HOST
		,PORT
		,CONTEXT//FIXME to be removed
		,PATH
		,QUERY

	}
}
