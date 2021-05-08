package org.cyk.utility.persistence.query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.annotation.H2;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;
import org.cyk.utility.persistence.PersistenceHelper;

@H2
public class NativeQueryStringBuilderH2Impl extends NativeQueryStringBuilder.AbstractImpl implements Serializable {

	@Override
	public String buildInsertManyFromMaps(Class<?> klass, Collection<Map<String, String>> maps) {
		ThrowableHelper.throwIllegalArgumentExceptionIfNull("klass", klass);
		ThrowableHelper.throwIllegalArgumentExceptionIfEmpty("maps", maps);
		String tableName = getTableName(klass);
		Collection<String> intos = new ArrayList<String>();
		maps.forEach(map -> {
			ThrowableHelper.throwIllegalArgumentExceptionIfEmpty(String.format("columns names of class %s", klass),map.keySet());
			ThrowableHelper.throwIllegalArgumentExceptionIfEmpty(String.format("columns values of class %s", klass),map.values());
			intos.add(String.format("(%s)", StringHelper.concatenate(map.values(), ",")));
		});
		return String.format(INSERT_ALL_FORMAT, tableName,StringHelper.concatenate(maps.iterator().next().keySet(),","), StringUtils.join(intos,","));
	}
	
	@Override
	public <T> String buildInsertMany(Class<T> klass, Collection<T> collection) {
		ThrowableHelper.throwIllegalArgumentExceptionIfNull("klass", klass);
		ThrowableHelper.throwIllegalArgumentExceptionIfEmpty("collection", collection);
		Collection<Map<String, String>> maps = new ArrayList<>();
		for(Object object : collection) {
			Map<String, String> map = PersistenceHelper.getColumnsValuesAsMap(object);
			ThrowableHelper.throwIllegalArgumentExceptionIfEmpty(String.format("columns values of class %s", klass),map);
			maps.add(map);
		}
		return buildInsertManyFromMaps(klass, maps);
	}
	
	private static final String INSERT_ALL_FORMAT = "INSERT INTO %s (%s) VALUES %s";
}