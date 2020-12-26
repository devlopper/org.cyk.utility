package org.cyk.utility.__kernel__;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.constant.ConstantEmpty;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;
import org.cyk.utility.__kernel__.value.Value;

public interface Helper {

	Integer DEFAULT_BUFFER_SIZE = 1024 * 1; // 1KB.
	
	static Boolean isHaveModifiers(Integer encodedModifiers,Collection<Integer> modifiers,Integer numberOfMatch) {
		if(encodedModifiers == null)
			return Boolean.FALSE;
		if( modifiers == null || modifiers.isEmpty())
			return Boolean.TRUE;
		if(numberOfMatch == null || numberOfMatch <= 0)
			return Boolean.TRUE;
		Integer countNumberOfMatch = 0;
		for(Integer modifier : modifiers) {
			if(	(
					(Modifier.isAbstract(encodedModifiers) && Modifier.isAbstract(modifier))
					||(Modifier.isFinal(encodedModifiers) && Modifier.isFinal(modifier))
					||(Modifier.isNative(encodedModifiers) && Modifier.isNative(modifier))
					||(Modifier.isPrivate(encodedModifiers) && Modifier.isPrivate(modifier))
					||(Modifier.isProtected(encodedModifiers) && Modifier.isProtected(modifier))
					||(Modifier.isPublic(encodedModifiers) && Modifier.isPublic(modifier))
					||(Modifier.isStatic(encodedModifiers) && Modifier.isStatic(modifier))
					||(Modifier.isStrict(encodedModifiers) && Modifier.isStrict(modifier))
					||(Modifier.isTransient(encodedModifiers) && Modifier.isTransient(modifier))
					||(Modifier.isVolatile(encodedModifiers) && !Modifier.isVolatile(modifier))
					)
				) {
				if(++countNumberOfMatch == numberOfMatch)
					return Boolean.TRUE;	
			}				
		}
		return Boolean.FALSE;
	}
	
	static Boolean isHaveModifiers(Integer encodedModifiers,Collection<Integer> modifiers) {
		if(encodedModifiers == null)
			return Boolean.FALSE;
		if( modifiers == null || modifiers.isEmpty())
			return Boolean.TRUE;
		return isHaveModifiers(encodedModifiers, modifiers, 1);
	}
	
	static Boolean isHaveModifiers(Integer encodedModifiers,Integer...modifiers) {
		if(encodedModifiers == null)
			return Boolean.FALSE;
		if( modifiers == null || modifiers.length == 0)
			return Boolean.TRUE;
		return isHaveModifiers(encodedModifiers, List.of(modifiers),1);
	}
	
	static Boolean isHaveAllModifiers(Integer encodedModifiers,Collection<Integer> modifiers) {
		if(encodedModifiers == null)
			return Boolean.FALSE;
		if( modifiers == null || modifiers.isEmpty())
			return Boolean.TRUE;
		return isHaveModifiers(encodedModifiers, modifiers, modifiers.size());
	}
	
	static Boolean isHaveAllModifiers(Integer encodedModifiers,Integer...modifiers) {
		if(encodedModifiers == null)
			return Boolean.FALSE;
		if( modifiers == null || modifiers.length == 0)
			return Boolean.TRUE;
		return isHaveModifiers(encodedModifiers, List.of(modifiers),modifiers.length);
	}

	static <T extends Enum<?>> T getEnumByName(Class<T> klass,String name,Boolean isCaseSensitive) {
		if(klass == null || !klass.isEnum())
			return null;
		for(Object index : klass.getEnumConstants())
			if(isCaseSensitive && ((Enum<?>)index).name().equals(name) || !isCaseSensitive && ((Enum<?>)index).name().equalsIgnoreCase(name))
				return (T) index;
		return null;		
	}
	
	static <T extends Enum<?>> T getEnumByName(Class<T> klass,String name) {
		return getEnumByName(klass, name,Boolean.FALSE);
	}

	static String getUrl(HttpServletRequest request) {
		if(request == null)
			return null;
		return request.getRequestURL().toString()+(StringHelper.isBlank(request.getQueryString()) ? ConstantEmpty.STRING : "?"+request.getQueryString());
	}
	
	static String getRequestedUrl() {
		return getUrl(DependencyInjection.inject(HttpServletRequest.class));
	}

	static void write(byte[] bytes,OutputStream outputStream) throws IOException {
		if(bytes == null || outputStream == null)
			return;
		BufferedInputStream input = null;
		BufferedOutputStream output = null;
		try {
			input = new BufferedInputStream(new ByteArrayInputStream(bytes), DEFAULT_BUFFER_SIZE);
			output = new BufferedOutputStream(outputStream, DEFAULT_BUFFER_SIZE);
			byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
			int length;
			while ((length = input.read(buffer)) > 0) {
				output.write(buffer, 0, length);
			}
		} finally {
			close(output, input);
		}
	}
	
	static void close(Collection<Closeable> closeables) {
		if(CollectionHelper.isEmpty(closeables))
			return;
		for(Closeable closeable : closeables) {
			if(closeable == null)
				continue;
			try {
				closeable.close();
			} catch (IOException exception) {
				exception.printStackTrace();
			}
		}
	}
	
	static void close(Closeable...closeables) {
		if(ArrayHelper.isEmpty(closeables))
			return;
		close(CollectionHelper.listOf(closeables));
	}
	
	/**/
	
	static <T> T getInstance(Class<T> klass,Value INSTANCE) {
		ThrowableHelper.throwIllegalArgumentExceptionIfNull("class", klass);
		ThrowableHelper.throwIllegalArgumentExceptionIfNull("instance", INSTANCE);
		T instance = (T) INSTANCE.get();
		if(instance != null)
			return instance;
		INSTANCE.set(instance = DependencyInjection.inject(klass));
		LogHelper.logFine("instance has been set. <<"+instance.getClass()+">>", klass);
		return instance;
	}
}
