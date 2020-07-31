package org.cyk.utility.__kernel__;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;

public interface JsonHelper {

	static <T> List<T> getList(Class<T> klass,String json,Type type) {
		Gson gson = new Gson();
		List<T> list = gson.fromJson(json, type == null ? klass : type);
		return list;
	}
	
	static <T> List<T> getList(Class<T> klass,String json) {
		return getList(klass, json, null);
	}
}
