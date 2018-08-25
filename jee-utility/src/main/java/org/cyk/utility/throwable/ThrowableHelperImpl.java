package org.cyk.utility.throwable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Singleton;

import org.cyk.utility.array.ArrayHelper;
import org.cyk.utility.helper.AbstractHelper;

@Singleton
public class ThrowableHelperImpl extends AbstractHelper implements ThrowableHelper,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Throwable getInstanceOf(Throwable throwable,Class<?> aClass){
		Throwable index = throwable;
		while(index!=null){
			if(aClass.isAssignableFrom(index.getClass())){
				return index;
			}else
				index = index.getCause();
		}
		return null;
	}
	
	@Override
	public Throwable getInstanceOf(Throwable throwable,Class<?> aClass,Class<?>...classes){
		List<Class<?>> list = new ArrayList<>();
		list.add(aClass);
		if(__inject__(ArrayHelper.class).isNotEmpty(classes))
			list.addAll(Arrays.asList(classes));
		for(Class<?> index : list){
			Throwable instance = getInstanceOf(throwable, index);
			if(instance!=null)
				return instance;
		}
		return null;
	}
	
	@Override
	public Throwable getFirstCause(Throwable throwable){
		Throwable cause=throwable,index = throwable;
		while(index!=null){
			cause = index;
			index = index.getCause();
		}
		return cause;
	}
	
	@Override
	public void throw_(RuntimeException runtimeException) {
		throw runtimeException;
	}
	
	@Override
	public void throwRuntimeException(String message) {
		throw_(new RuntimeException(message));
	}

	@Override
	public void throwRuntimeExceptionNotYetImplemented() {
		throwRuntimeException("Not yet implemented");
	}
	
	@Override
	public void throwRuntimeExceptionImplementationOrRunnableRequired(Class<?> aClass) {
		throwRuntimeException(aClass+" : Implementation or runnable required");
	}
}
