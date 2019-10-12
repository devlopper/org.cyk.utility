package org.cyk.utility.__kernel__.identifier.resource;

public interface ProxyHelper {
	
	static <T> T get(Class<T> klass,ProxyGetter getter) {
		if(klass == null)
			return null;
		if(getter == null)
			getter = ProxyGetter.INSTANCE;
		return getter.get(klass);
	}
	
	static <T> T get(Class<T> klass) {
		return get(klass,ProxyGetter.INSTANCE);
	}
	
	/*
	static String buildServerUniformResourceIdentifier(String uniformResourceIdentifier) {
		if(StringHelper.isBlank(uniformResourceIdentifier))
			return null;		
		String context = ProxyHelperVariables.CONTEXT_CLIENT;
		context = StringHelper.addToBeginIfDoesNotStartWith(context, ConstantCharacter.SLASH.toString());
		context = StringHelper.addToEndIfDoesNotEndWith(context, ConstantCharacter.SLASH.toString());		
	
		uniformResourceIdentifierString = __inject__(UniformResourceIdentifierStringBuilder.class).setRequest(request).setContext(context);
		
		return uniformResourceIdentifierString.execute().getOutput();	
	}
	
	Collection<Object> CONSTANTS = new ArrayList<>();
	*/
}
