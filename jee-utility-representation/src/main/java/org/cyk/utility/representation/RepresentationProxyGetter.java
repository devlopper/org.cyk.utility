package org.cyk.utility.representation;

import java.io.Serializable;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.method.MethodHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;
import org.cyk.utility.__kernel__.value.Value;

public interface RepresentationProxyGetter {

	<T> T get(Class<T> klass);
	Object getByActionIdentifier(String actionIdentifier);
	
	/**/
	
	public static abstract class AbstractImpl extends AbstractObject implements RepresentationProxyGetter,Serializable {		
		
		public static String METHOD_NAME_GET_PROXY = "getProxy";
		
		@Override
		public <T> T get(Class<T> klass) {
			ThrowableHelper.throwIllegalArgumentExceptionIfNull("class", klass);
			return (T) MethodHelper.executeStatic(klass, METHOD_NAME_GET_PROXY);	
		}
		
		@Override
		public Object getByActionIdentifier(String actionIdentifier) {
			String className = RepresentationClassNameGetter.getInstance().getByActionIdentifier(actionIdentifier);
			return get(ClassHelper.getByName(className));
		}
	}
	
	/**/
	
	/**/
	
	static RepresentationProxyGetter getInstance() {
		return Helper.getInstance(RepresentationProxyGetter.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
}